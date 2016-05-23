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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

/**
 * {@link JsonPathPopulator} test.
 */
public class JsonPathPopulatorTest {
   
   private static final String FIRST_KEY = "First Key";
   private static final String SECOND_KEY = "Second Key";
   private static final int INDEX = 0;
   
   private JSONObject parent;
   private JsonNavigable firstNavigable;
   private JsonNavigable secondNavigable;
   private JsonNavigable arrayNavigable;
   
   private JsonPathPopulator systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      parent = new JSONObject();
      firstNavigable = new JsonNavigableObjectImpl( FIRST_KEY );
      secondNavigable = new JsonNavigableObjectImpl( SECOND_KEY );
      arrayNavigable = new JsonNavigableArrayImpl( INDEX );
      systemUnderTest = new JsonPathPopulator();
   }//End Method
   
   @Test public void shouldNavigateOverExistingObjectIdentifyingNoChild() {
      Object result = systemUnderTest.apply( firstNavigable, parent );
      assertThat( result, is( nullValue() ) );
   }//End Method
   
   @Test public void shouldInsertNewObjectWhenNotPresentAndPreviousNavigableHeld(){
      Object firstResult = systemUnderTest.apply( firstNavigable, parent );
      Object secondResult = systemUnderTest.apply( secondNavigable, firstResult );
      
      assertThat( firstResult, is( nullValue() ) );
      assertThat( secondResult, is( nullValue() ) );
      
      JSONObject firstKeyValue = parent.getJSONObject( FIRST_KEY );
      assertThat( firstKeyValue, is( instanceOf( JSONObject.class ) ) );
      assertThat( firstKeyValue.has( SECOND_KEY ), is( false ) );
   }//End Method
   
   @Test public void shouldNavigateOverExistingObjectWhenPresentAndPreviousNavigableHeld(){
      JSONObject firstObject = new JSONObject();
      parent.put( FIRST_KEY, firstObject );
      
      JSONObject secondObject = new JSONObject();
      firstObject.put( SECOND_KEY, secondObject );
      
      Object firstResult = systemUnderTest.apply( firstNavigable, parent );
      Object secondResult = systemUnderTest.apply( secondNavigable, firstResult );
      
      assertThat( parent.get( FIRST_KEY ), is( firstResult ) );
      assertThat( firstResult, is( firstObject ) );
      assertThat( firstObject.get( SECOND_KEY ), is( secondResult ) );
      assertThat( secondResult, is( secondObject) );
   }//End Method
   
   @Test public void shouldInsertNewObjectAndArrayWhenNotPresentAndPreviousNavigablesHeld(){
      Object firstResult = systemUnderTest.apply( firstNavigable, parent );
      Object secondResult = systemUnderTest.apply( arrayNavigable, firstResult );
      Object thirdResult = systemUnderTest.apply( secondNavigable, secondResult );
      
      assertThat( firstResult, is( nullValue() ) );
      assertThat( secondResult, is( nullValue() ) );
      assertThat( thirdResult, is( nullValue() ) );
      
      JSONArray firstKeyValue = parent.getJSONArray( FIRST_KEY );
      assertThat( firstKeyValue, is( instanceOf( JSONArray.class ) ) );
      assertThat( firstKeyValue.length(), is( 1 ) );
      
      JSONObject secondKeyValue = firstKeyValue.getJSONObject( INDEX );
      assertThat( secondKeyValue, is( instanceOf( JSONObject.class ) ) );
      assertThat( secondKeyValue.has( SECOND_KEY ), is( false ) );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullNavigableWithParent(){
      systemUnderTest.apply( null, parent );      
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullNavigableWithoutParent(){
      systemUnderTest.apply( null, null );      
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullParentInitially(){
      systemUnderTest.apply( firstNavigable, null );
   }//End Method

}//End Class
