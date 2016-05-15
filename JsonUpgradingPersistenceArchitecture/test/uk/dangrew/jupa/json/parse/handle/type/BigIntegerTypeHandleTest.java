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

import java.math.BigInteger;
import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;

/**
 * {@link BigIntegerTypeHandle} test.
 */
public class BigIntegerTypeHandleTest extends JsonParseHandleImplTest< BigInteger > {
   
   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new BigIntegerTypeHandle( handle );
   }//End Method

   @Test public void shouldHandleObjectAndProvideValue() {
      JSONObject object = new JSONObject();
      
      final BigInteger value = BigInteger.valueOf( 348756 );
      object.put( KEY, value );
      
      systemUnderTest.handle( KEY, object );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldHandleArrayAndProvideValue() {
      JSONArray array = new JSONArray();
      
      final BigInteger value = BigInteger.valueOf( 348756 );
      final int index = 1;
      array.put( "" );
      array.put( value );
      array.put( "" );
      
      systemUnderTest.handle( KEY, array, index );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method

   @Test @Override public void handleShouldForwardToKeyHandle(){
      final BigInteger value = BigInteger.valueOf( 348756 );
      systemUnderTest.handle( KEY, value );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test @Override public void methodConstructorShouldUseMethodInHandle(){
      @SuppressWarnings("unchecked") //safe - mocking generic objects
      BiConsumer< String, BigInteger > methodHandle = mock( BiConsumer.class );
      systemUnderTest = new BigIntegerTypeHandle( methodHandle );
      
      final BigInteger value = BigInteger.valueOf( 348756 );
      systemUnderTest.handle( KEY, value );
      verify( methodHandle ).accept( KEY, value );
      verifyNoMoreInteractions( methodHandle );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) @Override public void constructorShouldNotAcceptNullHandle() {
      new BigIntegerTypeHandle( ( JsonKeyHandle< BigInteger > )null );
   }//End Method
   
}//End Class
