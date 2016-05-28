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

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static uk.dangrew.jupa.json.parse.DefaultKeyRecorder.ARRAY_FINISHED;
import static uk.dangrew.jupa.json.parse.DefaultKeyRecorder.ARRAY_STARTED;
import static uk.dangrew.jupa.json.parse.DefaultKeyRecorder.OBJECT_FINISHED;
import static uk.dangrew.jupa.json.parse.DefaultKeyRecorder.OBJECT_STARTED;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.json.parse.handle.type.JsonParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;

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
   
   private JsonParseHandle handle;
   private DefaultKeyRecorder keyRecorder;
   private JsonParser systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      keyRecorder = new DefaultKeyRecorder();
      handle = new StringParseHandle( keyRecorder );
      systemUnderTest = new JsonParser();
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNull(){
      systemUnderTest.parse( null );
   }//End Method

   @Test public void whenShouldNotCallThroughWhenNothingEncountered() {
      systemUnderTest.when( KEY_A, handle );
      systemUnderTest.parse( new JSONObject() );
      keyRecorder.expectKeysFound();
   }//End Method
   
   @Test public void whenShouldCallThroughToHandlerForSingle() {
      systemUnderTest.when( KEY_A, handle );
      
      JSONObject object = new JSONObject();
      object.put( KEY_A, VALUE_A );
      systemUnderTest.parse( object );

      keyRecorder.expect( KEY_A, VALUE_A );
      keyRecorder.expectKeysFound();
   }//End Method
   
   @Test public void whenShouldCallThroughToHandlerForSingleAndIgnoreOtherKey() {
      systemUnderTest.when( KEY_A, handle );
      
      JSONObject object = new JSONObject();
      object.put( KEY_A, VALUE_A );
      object.put( KEY_B, VALUE_B );
      systemUnderTest.parse( object );
      
      keyRecorder.expect( KEY_A, VALUE_A );
      keyRecorder.expectKeysFound();
   }//End Method
   
   @Test public void whenShouldCallThroughToHandlerForMultiple() {
      systemUnderTest.when( KEY_A, handle );
      systemUnderTest.when( KEY_B, handle );
      
      JSONObject object = new JSONObject();
      object.put( KEY_A, VALUE_A );
      object.put( KEY_B, VALUE_B );
      systemUnderTest.parse( object );
      
      keyRecorder.expect( KEY_A, VALUE_A );
      keyRecorder.expect( KEY_B, VALUE_B );
      keyRecorder.expectKeysFound();
   }//End Method
   
   @Test public void whenShouldCallThroughToHandlerForNested() {
      systemUnderTest.when( KEY_A, handle );
      systemUnderTest.when( KEY_B, handle );
      
      JSONObject object = new JSONObject();
      JSONObject nested = new JSONObject();
      object.put( KEY_A, nested );
      nested.put( KEY_B, VALUE_B );
      systemUnderTest.parse( object );

      keyRecorder.expect( KEY_A, OBJECT_STARTED );
      keyRecorder.expect( KEY_B, VALUE_B );
      keyRecorder.expect( KEY_A, OBJECT_FINISHED );
      keyRecorder.expectKeysFound();
   }//End Method
   
   @Test public void whenShouldNavigateThroughArraysToObjectsAndProcessAllFieldsInTurn() {
      systemUnderTest.when( KEY_A, handle );
      systemUnderTest.when( KEY_B, handle );
      systemUnderTest.when( KEY_C, handle );
      
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
      
      keyRecorder.expect( KEY_A, ARRAY_STARTED );
      for ( int i = 1; i < 21; i++ ) {
         keyRecorder.expect( KEY_A, OBJECT_STARTED );
         i++;
         keyRecorder.expect( KEY_B, VALUE_B );
         i++;
         keyRecorder.expect( KEY_C, VALUE_C );
         i++;
         keyRecorder.expect( KEY_A, OBJECT_FINISHED );
      }
      keyRecorder.expect( KEY_A, ARRAY_FINISHED );
      keyRecorder.expectKeysFound();
   }//End Method
   
   @Test public void whenShouldNavigateThroughArraysToValues() {
      systemUnderTest.when( KEY_A, handle );
      
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
      
      keyRecorder.expect( KEY_A, ARRAY_STARTED );
      keyRecorder.expect( KEY_A, VALUE_A );
      keyRecorder.expect( KEY_A, VALUE_A );
      keyRecorder.expect( KEY_A, VALUE_B );
      keyRecorder.expect( KEY_A, VALUE_C );
      keyRecorder.expect( KEY_A, VALUE_C );
      keyRecorder.expect( KEY_A, VALUE_C );
      keyRecorder.expect( KEY_A, ARRAY_FINISHED );
      keyRecorder.expectKeysFound();
   }//End Method
   
   @Test public void shouldSkipOverObjectWhenNoHandle() {
      systemUnderTest.when( KEY_B, handle );
      
      JSONObject object = new JSONObject();
      JSONObject nested = new JSONObject();
      object.put( KEY_A, nested );
      nested.put( KEY_B, VALUE_B );
      systemUnderTest.parse( object );
      
      keyRecorder.expect( KEY_B, VALUE_B );
      keyRecorder.expectKeysFound();
   }//End Method
   
   @Test public void shouldSkipOverArrayWhenNoHandle() {
      systemUnderTest.when( KEY_B, handle );
      
      JSONObject object = new JSONObject();
      JSONArray nested = new JSONArray();
      object.put( KEY_A, nested );
      
      JSONObject arrayObject = new JSONObject();
      nested.put( arrayObject );
      arrayObject.put( KEY_B, VALUE_B );
      systemUnderTest.parse( object );
      
      keyRecorder.expect( KEY_B, VALUE_B );
      keyRecorder.expectKeysFound();
   }//End Method
   
   @Test public void alphabeticalKeySorterShouldSortCorrectly(){
      assertThat( JsonParser.ALPHABETICAL.compare( "anything", "else" ), is ( lessThan( 0 ) ) );
      assertThat( JsonParser.ALPHABETICAL.compare( "a", "b" ), is ( lessThan( 0 ) ) );
      assertThat( JsonParser.ALPHABETICAL.compare( "something", "nothing" ), is ( greaterThan( 0 ) ) );
      assertThat( JsonParser.ALPHABETICAL.compare( "b", "a" ), is ( greaterThan( 0 ) ) );
      assertThat( JsonParser.ALPHABETICAL.compare( "something", "something" ), is ( 0 ) );
   }//End Method
   
   @Test public void shouldIdentifyNestedArraysUsngDepthFirst(){
      systemUnderTest.when( KEY_A, handle );
      
      JSONArray root = new JSONArray();
      
      for ( int i = 0; i < 2; i++ ) {
         JSONArray firstLevel = new JSONArray();
         
         for ( int j = 0; j < 2; j++ ) {
            JSONArray secondLevel = new JSONArray();

            secondLevel.put( VALUE_A );
            secondLevel.put( VALUE_B );
            secondLevel.put( VALUE_C );
            
            firstLevel.put( secondLevel );
         }
         
         root.put( firstLevel );
      }
      
      JSONObject input = new JSONObject();
      input.put( KEY_A, root );
      systemUnderTest.parse( input );
      
      //begin root
      keyRecorder.expect( KEY_A, ARRAY_STARTED );
      
      //begin first                 
      keyRecorder.expect( KEY_A, ARRAY_STARTED );
                                    
      //begin second                
      keyRecorder.expect( KEY_A, ARRAY_STARTED );
                                    
      //second 1  
      keyRecorder.expect( KEY_A, VALUE_A );
      keyRecorder.expect( KEY_A, VALUE_B );
      keyRecorder.expect( KEY_A, VALUE_C );
                                    
      //begin next second     
      keyRecorder.expect( KEY_A, ARRAY_FINISHED );
      keyRecorder.expect( KEY_A, ARRAY_STARTED );
                                    
      //second 2                    
      keyRecorder.expect( KEY_A, VALUE_A );
      keyRecorder.expect( KEY_A, VALUE_B );
      keyRecorder.expect( KEY_A, VALUE_C );
      
      //begin next first              
      keyRecorder.expect( KEY_A, ARRAY_FINISHED );
      keyRecorder.expect( KEY_A, ARRAY_FINISHED );
      keyRecorder.expect( KEY_A, ARRAY_STARTED );
                                    
      //begin second                
      keyRecorder.expect( KEY_A, ARRAY_STARTED );
                                    
      //second 1  
      keyRecorder.expect( KEY_A, VALUE_A );
      keyRecorder.expect( KEY_A, VALUE_B );
      keyRecorder.expect( KEY_A, VALUE_C );
                                    
      //begin next second           
      keyRecorder.expect( KEY_A, ARRAY_FINISHED );
      keyRecorder.expect( KEY_A, ARRAY_STARTED );
                                    
      //second 2                    
      keyRecorder.expect( KEY_A, VALUE_A );
      keyRecorder.expect( KEY_A, VALUE_B );
      keyRecorder.expect( KEY_A, VALUE_C );
      
      keyRecorder.expect( KEY_A, ARRAY_FINISHED );
      keyRecorder.expect( KEY_A, ARRAY_FINISHED );
      keyRecorder.expect( KEY_A, ARRAY_FINISHED );
      
      keyRecorder.expectKeysFound();
   }//End Method
   
}//End Class
