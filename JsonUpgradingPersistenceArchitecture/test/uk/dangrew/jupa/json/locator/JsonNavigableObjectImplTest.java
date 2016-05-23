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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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
   private JSONObject parent;
   private JsonNavigableObjectImpl systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      parent = new JSONObject();
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
   
   @Test public void shouldIgnoreObjectOtherThanJsonObject(){
      systemUnderTest.navigate( new Object() );
      systemUnderTest.put( new Object(), "anything" );
   }//End Method
   
   @Test public void shouldAcceptJsonObject(){
      assertThat( systemUnderTest.navigate( new JSONObject() ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldIgnoreJsonArray(){
      systemUnderTest.navigate( new JSONArray() );
      systemUnderTest.put( new JSONArray(), "anything" );
   }//End Method

   @Test public void shouldIgnoreNull(){
      systemUnderTest.navigate( null );
      systemUnderTest.put( null, "anything" );
   }//End Method
   
   @Test public void shouldPutNullValueIntoParent(){
      systemUnderTest.put( parent, null );
      assertThat( parent.opt( KEY ), is( nullValue() ) );
      
      parent.put( KEY, "anything" );
      assertThat( parent.opt( KEY ), is( "anything" ) );
      
      systemUnderTest.put( parent, null );
      assertThat( parent.opt( KEY ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldPutSpecificValueTypeIntoParent(){
      final String value = "something special";
      systemUnderTest.put( parent, value );
      assertThat( parent.opt( KEY ), is( value ) );
   }//End Method
   
   @Test public void shouldPutNestedJsonObjectIntoParent(){
      final JSONObject value = new JSONObject();
      systemUnderTest.put( parent, value );
      assertThat( parent.opt( KEY ), is( value ) );
   }//End Method
   
   @Test public void shouldPutJsonArrayIntoParent(){
      final JSONArray value = new JSONArray();
      systemUnderTest.put( parent, value );
      assertThat( parent.opt( KEY ), is( value ) );
   }//End Method
   
   @Test public void shouldGenerateObjectRepeatedly(){
      Object structure = systemUnderTest.generateStructure();
      assertThat( structure, is( instanceOf( JSONObject.class ) ) );
      assertThat( systemUnderTest.generateStructure(), is( not( structure ) ) );
   }//End Method
}//End Class
