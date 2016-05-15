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
 * {@link IntegerTypeHandle} test.
 */
public class IntegerTypeHandleTest extends JsonParseHandleImplTest< Integer > {
   
   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new IntegerTypeHandle( handle );
   }//End Method

   @Test public void shouldHandleObjectAndProvideValue() {
      JSONObject object = new JSONObject();
      
      final int value = 347;
      object.put( KEY, value );
      
      systemUnderTest.handle( KEY, object );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldHandleArrayAndProvideValue() {
      JSONArray array = new JSONArray();
      
      final int value = 39457;
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
      final int value = 394857;
      systemUnderTest.handle( KEY, value );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test @Override public void methodConstructorShouldUseMethodInHandle(){
      @SuppressWarnings("unchecked") //safe - mocking generic objects
      BiConsumer< String, Integer > methodHandle = mock( BiConsumer.class );
      systemUnderTest = new IntegerTypeHandle( methodHandle );
      
      final int value = 3094;
      systemUnderTest.handle( KEY, value );
      verify( methodHandle ).accept( KEY, value );
      verifyNoMoreInteractions( methodHandle );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) @Override public void constructorShouldNotAcceptNullHandle() {
      new IntegerTypeHandle( ( JsonKeyHandle< Integer > )null );
   }//End Method
   
}
