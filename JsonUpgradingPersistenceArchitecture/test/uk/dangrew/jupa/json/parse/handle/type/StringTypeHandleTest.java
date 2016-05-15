/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse.handle.type;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;

/**
 * {@link StringTypeHandle} test.
 */
public class StringTypeHandleTest extends JsonParseHandleImplTest< String > {
   
   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new StringTypeHandle( handle );
   }//End Method

   @Test public void shouldHandleObjectAndProvideValue() {
      JSONObject object = new JSONObject();
      
      final String value = "some special value";
      object.put( KEY, value );
      
      systemUnderTest.handle( KEY, object );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldHandleArrayAndProvideValue() {
      JSONArray array = new JSONArray();
      
      final String value = "some special value";
      final int index = 1;
      array.put( "" );
      array.put( value );
      array.put( "" );
      
      systemUnderTest.handle( KEY, array, index );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method

   @Test @Override public void handleShouldForwardToKeyHandle(){
      final String value = "some specific value";
      systemUnderTest.handle( KEY, value );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test @Override public void methodConstructorShouldUseMethodInHandle(){
      @SuppressWarnings("unchecked") //safe - mocking generic objects
      BiConsumer< String, String > methodHandle = mock( BiConsumer.class );
      systemUnderTest = new StringTypeHandle( methodHandle );
      
      final String value = "some specific value";
      systemUnderTest.handle( KEY, value );
      verify( methodHandle ).accept( KEY, value );
      verifyNoMoreInteractions( methodHandle );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) @Override public void constructorShouldNotAcceptNullHandle() {
      new StringTypeHandle( ( JsonKeyHandle< String > )null );
   }//End Method
   
}//End Class
