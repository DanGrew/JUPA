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

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link JsonNavigableArrayImpl} test.
 */
public class JsonNavigableArrayImplTest {
   
   private static final int INDEX = 2;
   private JsonNavigableArrayImpl systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      systemUnderTest = new JsonNavigableArrayImpl( INDEX );
   }//End Method

   @Test public void shouldNavigateThroughArrayUsingIndex() {
      JSONArray array = new JSONArray();
      array.put( new JSONObject() );
      array.put( new JSONObject() );
      JSONObject specific = new JSONObject();
      array.put( specific );
      
      assertThat( systemUnderTest.navigate( array ), is( specific ) );
   }//End Method
   
   @Test public void shouldIgnoreMissingArrayIndexSafely() {
      JSONArray array = new JSONArray();
      array.put( new JSONObject() );
      array.put( new JSONObject() );
      
      assertThat( systemUnderTest.navigate( array ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNavigateJsonArray(){
      systemUnderTest.navigate( new JSONObject() );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptAnyObject(){
      systemUnderTest.navigate( new Object() );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNull(){
      systemUnderTest.navigate( null );
   }//End Method
   
}//End Class
