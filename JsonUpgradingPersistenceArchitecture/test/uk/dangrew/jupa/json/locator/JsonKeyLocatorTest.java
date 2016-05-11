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
      jsonObject.put( CHILD_A, childObjectA );
      childObjectA.put( CHILD_B, childObjectB );
      childObjectB.put( CHILD_C, childObjectC );
      childObjectC.put( KEY, VALUE );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( VALUE ) );
   }//End Method
   
   @Test public void shouldSafelyIgnoreMissingKeyInFurtherChildren(){
      jsonObject.put( CHILD_A, childObjectA );
      childObjectA.put( CHILD_B, childObjectB );
      childObjectB.put( CHILD_C, childObjectC );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldSafelyIgnoreMissingChildren(){
      jsonObject.put( CHILD_A, childObjectA );
      childObjectA.put( CHILD_B, childObjectB );
      
      systemUnderTest.child( CHILD_A ).child( CHILD_B ).child( CHILD_C ).key( KEY );
      
      assertThat( systemUnderTest.find( jsonObject ), is( nullValue() ) );
   }//End Method
   
}//End Class

