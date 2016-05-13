/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;

/**
 * {@link JsonParser} test.
 */
public class JsonParserTest {
   
   private static final String KEY_A = "KeyA";
   private static final String VALUE_A = "ValueA";
   private static final String KEY_B = "KeyB";
   private static final String VALUE_B = "ValueB";
   private static final String KEY_C = "KeyC";
   private static final String VALUE_C = "ValueC";
   
   private List< Pair< String, Object > > recordedKeys;
   private JsonParser systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      recordedKeys = new ArrayList<>();
      systemUnderTest = new JsonParser();
   }//End Method
   
   /**
    * Method to record a key when encountered.
    * @param key th key found.
    * @param value the value found.
    */
   private void recordKey( String key, Object value ){
      recordedKeys.add( new Pair<>( key, value ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNull(){
      systemUnderTest.parse( null );
   }//End Method

   @Test public void whenShouldNotCallThroughWhenNothingEncountered() {
      systemUnderTest.when( KEY_A, this::recordKey );
      systemUnderTest.parse( new JSONObject() );
      assertThat( recordedKeys, is( empty() ) );
   }//End Method
   
   @Test public void whenShouldCallThroughToHandlerForSingle() {
      systemUnderTest.when( KEY_A, this::recordKey );
      
      JSONObject object = new JSONObject();
      object.put( KEY_A, VALUE_A );
      systemUnderTest.parse( object );
      
      assertThat( recordedKeys, hasSize( 1 ) );
      assertThat( recordedKeys.get( 0 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 0 ).getValue(), is( VALUE_A ) );
   }//End Method
   
   @Test public void whenShouldCallThroughToHandlerForSingleAndIgnoreOtherKey() {
      systemUnderTest.when( KEY_A, this::recordKey );
      
      JSONObject object = new JSONObject();
      object.put( KEY_A, VALUE_A );
      object.put( KEY_B, VALUE_B );
      systemUnderTest.parse( object );
      
      assertThat( recordedKeys, hasSize( 1 ) );
      assertThat( recordedKeys.get( 0 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 0 ).getValue(), is( VALUE_A ) );
   }//End Method
   
   @Test public void whenShouldCallThroughToHandlerForMultiple() {
      systemUnderTest.when( KEY_A, this::recordKey );
      systemUnderTest.when( KEY_B, this::recordKey );
      
      JSONObject object = new JSONObject();
      object.put( KEY_A, VALUE_A );
      object.put( KEY_B, VALUE_B );
      systemUnderTest.parse( object );
      
      assertThat( recordedKeys, hasSize( 2 ) );
      assertThat( recordedKeys.get( 0 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 0 ).getValue(), is( VALUE_A ) );
      assertThat( recordedKeys.get( 1 ).getKey(), is( KEY_B ) );
      assertThat( recordedKeys.get( 1 ).getValue(), is( VALUE_B ) );
   }//End Method
   
   @Test public void whenShouldCallThroughToHandlerForNested() {
      systemUnderTest.when( KEY_A, this::recordKey );
      systemUnderTest.when( KEY_B, this::recordKey );
      
      JSONObject object = new JSONObject();
      JSONObject nested = new JSONObject();
      object.put( KEY_A, nested );
      nested.put( KEY_B, VALUE_B );
      systemUnderTest.parse( object );
      
      assertThat( recordedKeys, hasSize( 2 ) );
      assertThat( recordedKeys.get( 0 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 0 ).getValue(), is( nullValue() ) );
      assertThat( recordedKeys.get( 1 ).getKey(), is( KEY_B ) );
      assertThat( recordedKeys.get( 1 ).getValue(), is( VALUE_B ) );
   }//End Method
   
   @Test public void whenShouldNavigateThroughArraysToObjectsAndProcessAllFieldsInTurn() {
      systemUnderTest.when( KEY_A, this::recordKey );
      systemUnderTest.when( KEY_B, this::recordKey );
      systemUnderTest.when( KEY_C, this::recordKey );
      
      JSONObject object = new JSONObject();
      JSONArray nested = new JSONArray();
      object.put( KEY_A, nested );
      for ( int i = 0; i < 5; i++ ) {
         JSONObject arrayObject = new JSONObject();
         arrayObject.put( KEY_B, VALUE_B );
         arrayObject.put( KEY_C, VALUE_C );
         nested.put( arrayObject );
      }
      systemUnderTest.parse( object );
      
      assertThat( recordedKeys, hasSize( 15 ) );
      for ( int i = 0; i < 15; i++ ) {
         assertThat( recordedKeys.get( i ).getKey(), is( KEY_A ) );
         assertThat( recordedKeys.get( i ).getValue(), is( nullValue() ) );
         
         i++;
         assertThat( recordedKeys.get( i ).getKey(), is( KEY_B ) );
         assertThat( recordedKeys.get( i ).getValue(), is( VALUE_B ) );
         
         i++;
         assertThat( recordedKeys.get( i ).getKey(), is( KEY_C ) );
         assertThat( recordedKeys.get( i ).getValue(), is( VALUE_C ) );
      }
   }//End Method
   
   @Test public void whenShouldNavigateThroughArraysToValues() {
      systemUnderTest.when( KEY_A, this::recordKey );
      
      JSONObject object = new JSONObject();
      JSONArray nested = new JSONArray();
      object.put( KEY_A, nested );
      nested.put( VALUE_A );
      nested.put( VALUE_A );
      nested.put( VALUE_B );
      nested.put( VALUE_C );
      nested.put( VALUE_C );
      nested.put( VALUE_C );
      systemUnderTest.parse( object );
      
      assertThat( recordedKeys, hasSize( 6 ) );
      assertThat( recordedKeys.get( 0 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 0 ).getValue(), is( VALUE_A ) );
      assertThat( recordedKeys.get( 1 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 1 ).getValue(), is( VALUE_A ) );
      assertThat( recordedKeys.get( 2 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 2 ).getValue(), is( VALUE_B ) );
      assertThat( recordedKeys.get( 3 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 3 ).getValue(), is( VALUE_C ) );
      assertThat( recordedKeys.get( 4 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 4 ).getValue(), is( VALUE_C ) );
      assertThat( recordedKeys.get( 5 ).getKey(), is( KEY_A ) );
      assertThat( recordedKeys.get( 5 ).getValue(), is( VALUE_C ) );
   }//End Method
   
   @Test public void alphabeticalKeySorterShouldSortCorrectly(){
      assertThat( JsonParser.ALPHABETICAL.compare( "anything", "else" ), is ( lessThan( 0 ) ) );
      assertThat( JsonParser.ALPHABETICAL.compare( "a", "b" ), is ( lessThan( 0 ) ) );
      assertThat( JsonParser.ALPHABETICAL.compare( "something", "nothing" ), is ( greaterThan( 0 ) ) );
      assertThat( JsonParser.ALPHABETICAL.compare( "b", "a" ), is ( greaterThan( 0 ) ) );
      assertThat( JsonParser.ALPHABETICAL.compare( "something", "something" ), is ( 0 ) );
   }//End Method

}//End Class
