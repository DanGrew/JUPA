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

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyParseHandle;

/**
 * {@link DoubleParseHandle} test.
 */
public class DoubleParseHandleTest extends JsonParseHandleImplTest< Double > {
   
   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new DoubleParseHandle( handle );
   }//End Method

   @Test public void shouldHandleObjectAndProvideValue() {
      JSONObject object = new JSONObject();
      
      final double value = 347.294857;
      object.put( KEY, value );
      
      systemUnderTest.handle( KEY, object );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldHandleArrayAndProvideValue() {
      JSONArray array = new JSONArray();
      
      final double value = 347.294857;
      final int index = 1;
      array.put( "" );
      array.put( value );
      array.put( "" );
      
      systemUnderTest.handle( KEY, array, index );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method

   @Test public void shouldHandleMissingKeyInObjectAndProvideValue() {
      JSONObject object = new JSONObject();
      
      systemUnderTest.handle( KEY, object );
      verify( handle ).handle( KEY, null );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldHandleMissingItemArrayAndProvideValue() {
      JSONArray array = new JSONArray();
      
      final int index = 1;
      
      systemUnderTest.handle( KEY, array, index );
      verify( handle ).handle( KEY, null );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test @Override public void handleShouldForwardToKeyHandle(){
      final double value = 347.294857;
      systemUnderTest.handle( KEY, value );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test @Override public void methodConstructorShouldUseMethodInHandle(){
      @SuppressWarnings("unchecked") //safe - mocking generic objects
      BiConsumer< String, Double > methodHandle = mock( BiConsumer.class );
      systemUnderTest = new DoubleParseHandle( methodHandle );
      
      final double value = 347.294857;
      systemUnderTest.handle( KEY, value );
      verify( methodHandle ).accept( KEY, value );
      verifyNoMoreInteractions( methodHandle );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) @Override public void constructorShouldNotAcceptNullHandle() {
      new DoubleParseHandle( ( JsonKeyParseHandle< Double > )null );
   }//End Method
   
   @Test public void shouldNotSetNaN(){
      JSONObject object = new JSONObject();
      object.put( KEY, "anything" );
      systemUnderTest.handleKeyPresent( KEY, object );
      verify( handle, never() ).handle( anyString(), anyDouble() );
      
      JSONArray array = new JSONArray();
      array.put( "anything" );
      systemUnderTest.handleArrayIndexPresent( KEY, array, 0 );
      verify( handle, never() ).handle( anyString(), anyDouble() );
   }//End Method
   
}//End Class
