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
 * {@link JsonNavigableArrayImpl} test.
 */
public class JsonNavigableArrayImplTest {
   
   private static final int INDEX = 2;
   private JSONArray parent;
   private JsonNavigableArrayImpl systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      parent = new JSONArray();
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
   
   @Test public void shouldIgnoreJsonObject(){
      systemUnderTest.navigate( new JSONObject() );
      systemUnderTest.put( new JSONObject(), "anything" );
   }//End Method
   
   @Test public void shouldIgnoreAnyObject(){
      systemUnderTest.navigate( new Object() );
      systemUnderTest.put( new Object(), "anything" );
   }//End Method
   
   @Test public void shouldIgnoreNull(){
      systemUnderTest.navigate( null );
      systemUnderTest.put( null, "anything" );
   }//End Method
   
   @Test public void shouldPutNullValueIntoParent(){
      systemUnderTest.put( parent, null );
      assertThat( parent.opt( INDEX ), is( nullValue() ) );
      
      parent.put( INDEX, "anything" );
      assertThat( parent.opt( INDEX ), is( "anything" ) );
      
      systemUnderTest.put( parent, null );
      assertThat( parent.opt( INDEX ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldPutSpecificValueTypeIntoParent(){
      final String value = "something special";
      systemUnderTest.put( parent, value );
      assertThat( parent.opt( INDEX ), is( value ) );
   }//End Method
   
   @Test public void shouldPutNestedJsonObjectIntoParent(){
      final JSONObject value = new JSONObject();
      systemUnderTest.put( parent, value );
      assertThat( parent.opt( INDEX ), is( value ) );
   }//End Method
   
   @Test public void shouldPutJsonArrayIntoParent(){
      final JSONArray value = new JSONArray();
      systemUnderTest.put( parent, value );
      assertThat( parent.opt( INDEX ), is( value ) );
   }//End Method
   
   @Test public void shouldGenerateArrayRepeatedly(){
      Object structure = systemUnderTest.generateStructure();
      assertThat( structure, is( instanceOf( JSONArray.class ) ) );
      assertThat( systemUnderTest.generateStructure(), is( not( structure ) ) );
   }//End Method
   
}//End Class
