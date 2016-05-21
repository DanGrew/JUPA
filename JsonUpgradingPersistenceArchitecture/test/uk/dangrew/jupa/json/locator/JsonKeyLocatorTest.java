/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.locator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link JsonKeyLocator} test.
 */
public class JsonKeyLocatorTest {
   
   private static final String KEY = "KEY";
   private static final String CHILD_A = "ChildA";
   private static final String CHILD_B = "ChildB";
   private static final String CHILD_C = "ChildC";
   private static final String ARRAY_A = "ArrayA";
   private static final String ARRAY_B = "ArrayB";
   private static final String VALUE = "VALUE";
   
   private JSONObject jsonObject;
   private JSONObject childObjectA;
   private JSONObject childObjectB;
   private JSONObject childObjectC;
   private JSONArray array;
   private JsonKeyLocator systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      jsonObject = new JSONObject();
      childObjectA = new JSONObject();
      childObjectB = new JSONObject();
      childObjectC = new JSONObject();
      array = new JSONArray();
      for ( int i = 0; i < 5; i++ ) {
         array.put( new JSONObject() );
      }
      systemUnderTest = new JsonKeyLocator();
   }//End Method
   
   @Test public void findShouldIgnoreMissingKey(){
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void findShouldNotPermitNullObject(){
      systemUnderTest.find( null );
   }//End Method

   @Test public void shouldFindKeyInTopLevelObject() {
      jsonObject.put( KEY, VALUE );
      
      systemUnderTest.child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldSafelyIgnoreMissingKeyInTopLevelObject() {
      systemUnderTest.child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldFindKeyInChildObject(){
      jsonObject.put( CHILD_A, childObjectA );
      childObjectA.put( KEY, VALUE );
      
      systemUnderTest.child( CHILD_A ).child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldFindKeyInFurtherChildren(){
      jsonObjectContainsChildrenPath();
      childObjectC.put( KEY, VALUE );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldSafelyIgnoreMissingKeyInFurtherChildren(){
      jsonObjectContainsChildrenPath();
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method

   /** Setup method for creating the children path A to C.**/
   private void jsonObjectContainsChildrenPath() {
      jsonObject.put( CHILD_A, childObjectA );
      childObjectA.put( CHILD_B, childObjectB );
      childObjectB.put( CHILD_C, childObjectC );
   }//End Method
   
   /** Setup method for creating the children path A to C, with the key present in C.**/
   private void jsonObjectContainsChildrenPathAndKey(){
      jsonObjectContainsChildrenPath();
      childObjectC.put( KEY, VALUE );
   }//End Method
   
   @Test public void shouldSafelyIgnoreMissingChildren(){
      jsonObject.put( CHILD_A, childObjectA );
      childObjectA.put( CHILD_B, childObjectB );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void putShouldNotPermitNullObject(){
      systemUnderTest.put( null, "anything" );
   }//End Method
   
   @Test public void shouldSetKeyWhenNotPresent(){
      jsonObjectContainsChildrenPath();
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).child( KEY );
      
      systemUnderTest.put( jsonObject, VALUE );
      assertThat( childObjectC.get( KEY ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldNotSetKeyWhenPathNotValid(){
      jsonObjectContainsChildrenPath();
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( "anything" ).child( KEY );
      
      systemUnderTest.put( jsonObject, VALUE );
      assertThat( childObjectC.has( KEY ), is( false ) );
   }//End Method
   
   @Test public void shouldSetKeyWhenPresent(){
      jsonObjectContainsChildrenPathAndKey();
      assertThat( childObjectC.get( KEY ), is( VALUE ) );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).child( KEY );
      
      final String anotherValue = "anything else in the world";
      systemUnderTest.put( jsonObject, anotherValue );
      assertThat( childObjectC.get( KEY ), is( anotherValue ) );
   }//End Method
   
   @Test public void shouldSetKeyToDifferentValueType(){
      jsonObjectContainsChildrenPathAndKey();
      assertThat( childObjectC.get( KEY ), is( VALUE ) );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).child( KEY );
      
      final Double anotherValue = 348576.0;
      systemUnderTest.put( jsonObject, anotherValue );
      assertThat( childObjectC.get( KEY ), is( anotherValue ) );
   }//End Method
   
   @Test public void shouldSetKeyToNull(){
      jsonObjectContainsChildrenPathAndKey();
      assertThat( childObjectC.get( KEY ), is( VALUE ) );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).child( KEY );
      
      systemUnderTest.put( jsonObject, null );
      assertThat( childObjectC.has( KEY ), is( false ) );
   }//End Method
   
   /** Setup method for creating the array path A -> array with 5 -> array with 2.**/
   private void jsonObjectContainsArrayPath() {
      jsonObject.put( CHILD_A, childObjectA );
      childObjectA.put( ARRAY_A, array );
      for ( int i = 0; i < 3; i++ ) {
         JSONObject first = new JSONObject();
         first.put( KEY, VALUE );
         
         array.getJSONObject( i ).put( 
                  ARRAY_B, 
                  new JSONArray( Arrays.asList( first, new JSONObject() ) ) 
         );
      }
   }//End Method
   
   @Test public void shouldNavigateThroughArrayToKeyAtIndex(){
      jsonObjectContainsArrayPath();
      
      systemUnderTest.child( CHILD_A ).child( ARRAY_A ).array( 0 ).child( ARRAY_B ).array( 0 ).child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldSafelyIgnoreMissingKeyInArray(){
      jsonObjectContainsArrayPath();
      
      systemUnderTest.child( CHILD_A ).child( ARRAY_A ).array( 0 ).child( ARRAY_B ).array( 1 ).child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNotFindWherePathDoesNotEndOnJsonObject(){
      jsonObjectContainsArrayPath();
      
      systemUnderTest.child( CHILD_A ).child( ARRAY_A ).child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNavigateThroughMultipleArraysWithNoIntermediateObjects(){
      JSONArray arrayRoot = new JSONArray();
      jsonObject.put( CHILD_A, arrayRoot );
      
      for ( int i = 0; i < 5; i++ ) {
         JSONArray array = new JSONArray();
         arrayRoot.put( array );
         
         for ( int j = 0; j < 5; j++ ) {
            JSONArray nested = new JSONArray();
            array.put( nested );
            
            JSONObject object = new JSONObject();
            object.put( KEY, "" + i + "," + j );
            nested.put( object );
         }
      }
      
      systemUnderTest.child( CHILD_A ).array( 2 ).array( 3 ).array( 0 ).child( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( "2,3" ) );
   }//End Method
   
   @Test public void shouldPutValueIntoArrayIndex(){
      jsonObjectContainsArrayPath();
      
      systemUnderTest.child( CHILD_A ).child( ARRAY_A ).array( 2 ).child( ARRAY_B ).array( 1 ).child( KEY );
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
      
      final String value = "anything"; 
      systemUnderTest.put( jsonObject, value );
      assertThat( systemUnderTest.find( jsonObject ), is( value ) );
   }//End Method
   
}//End Class

