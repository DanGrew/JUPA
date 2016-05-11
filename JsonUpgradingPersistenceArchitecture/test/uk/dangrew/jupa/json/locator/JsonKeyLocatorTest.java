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
   private static final String VALUE = "VALUE";
   
   private JSONObject jsonObject;
   private JSONObject childObjectA;
   private JSONObject childObjectB;
   private JSONObject childObjectC;
   private JsonKeyLocator systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      jsonObject = new JSONObject();
      childObjectA = new JSONObject();
      childObjectB = new JSONObject();
      childObjectC = new JSONObject();
      systemUnderTest = new JsonKeyLocator();
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void findShouldNotPermitMissingKey(){
      systemUnderTest.find( jsonObject );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void findShouldNotPermitNullObject(){
      systemUnderTest.find( null );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void keyShouldNotAcceptNullValue(){
      systemUnderTest.key( null );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void keyShouldNotAcceptInvalidValue(){
      systemUnderTest.key( "   " );
   }//End Method

   @Test public void shouldFindKeyInTopLevelObject() {
      jsonObject.put( KEY, VALUE );
      
      systemUnderTest.key( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldSafelyIgnoreMissingKeyInTopLevelObject() {
      systemUnderTest.key( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldFindKeyInChildObject(){
      jsonObject.put( CHILD_A, childObjectA );
      childObjectA.put( KEY, VALUE );
      
      systemUnderTest.child( CHILD_A ).key( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldFindKeyInFurtherChildren(){
      jsonObjectContainsChildrenPath();
      childObjectC.put( KEY, VALUE );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldSafelyIgnoreMissingKeyInFurtherChildren(){
      jsonObjectContainsChildrenPath();
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
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
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void putShouldNotPermitMissingKey(){
      systemUnderTest.put( jsonObject, "anything" );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void putShouldNotPermitNullObject(){
      systemUnderTest.put( null, "anything" );
   }//End Method
   
   @Test public void shouldSetKeyWhenNotPresent(){
      jsonObjectContainsChildrenPath();
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
      systemUnderTest.put( jsonObject, VALUE );
      assertThat( childObjectC.get( KEY ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldSetKeyWhenPresent(){
      jsonObjectContainsChildrenPathAndKey();
      assertThat( childObjectC.get( KEY ), is( VALUE ) );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
      final String anotherValue = "anything else in the world";
      systemUnderTest.put( jsonObject, anotherValue );
      assertThat( childObjectC.get( KEY ), is( anotherValue ) );
   }//End Method
   
   @Test public void shouldSetKeyToDifferentValueType(){
      jsonObjectContainsChildrenPathAndKey();
      assertThat( childObjectC.get( KEY ), is( VALUE ) );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
      final Double anotherValue = 348576.0;
      systemUnderTest.put( jsonObject, anotherValue );
      assertThat( childObjectC.get( KEY ), is( anotherValue ) );
   }//End Method
   
   @Test public void shouldSetKeyToNull(){
      jsonObjectContainsChildrenPathAndKey();
      assertThat( childObjectC.get( KEY ), is( VALUE ) );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
      systemUnderTest.put( jsonObject, null );
      assertThat( childObjectC.has( KEY ), is( false ) );
   }//End Method
   
}//End Class

