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
 * {@link JsonNavigableObjectImpl} test.
 */
public class JsonNavigableObjectImplTest {
   
   private static final String KEY = "KEY";
   private JsonNavigableObjectImpl systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      systemUnderTest = new JsonNavigableObjectImpl( KEY );
   }//End Method

   @Test public void shouldFindObjectAndReturn() {
      JSONObject input = new JSONObject();
      JSONObject output = new JSONObject();
      input.put( KEY, output );
      
      assertThat( systemUnderTest.navigate( input ), is( output ) );
   }//End Method
   
   @Test public void shouldNotFindObjectAndReturnSafely() {
      JSONObject input = new JSONObject();
      
      assertThat( systemUnderTest.navigate( input ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptObjectOtherThanJsonObject(){
      systemUnderTest.navigate( new Object() );
   }//End Method
   
   @Test public void shouldAcceptJsonObject(){
      assertThat( systemUnderTest.navigate( new JSONObject() ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptJsonArray(){
      systemUnderTest.navigate( new JSONArray() );
   }//End Method

   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNull(){
      systemUnderTest.navigate( null );
   }//End Method
}//End Class
