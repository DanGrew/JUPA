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

import java.math.BigDecimal;
import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;

/**
 * {@link BigDecimalTypeHandle} test.
 */
public class BigDecimalTypeHandleTest extends JsonParseHandleImplTest< BigDecimal > {
   
   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new BigDecimalTypeHandle( handle );
   }//End Method

   @Test public void shouldHandleObjectAndProvideValue() {
      JSONObject object = new JSONObject();
      
      final BigDecimal value = BigDecimal.valueOf( 348756 );
      object.put( KEY, value );
      
      systemUnderTest.handle( KEY, object );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldHandleArrayAndProvideValue() {
      JSONArray array = new JSONArray();
      
      final BigDecimal value = BigDecimal.valueOf( 348756 );
      final int index = 1;
      array.put( "" );
      array.put( value );
      array.put( "" );
      
      systemUnderTest.handle( KEY, array, index );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method

   @Test @Override public void handleShouldForwardToKeyHandle(){
      final BigDecimal value = BigDecimal.valueOf( 348756 );
      systemUnderTest.handle( KEY, value );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test @Override public void methodConstructorShouldUseMethodInHandle(){
      @SuppressWarnings("unchecked") //safe - mocking generic objects
      BiConsumer< String, BigDecimal > methodHandle = mock( BiConsumer.class );
      systemUnderTest = new BigDecimalTypeHandle( methodHandle );
      
      final BigDecimal value = BigDecimal.valueOf( 348756 );
      systemUnderTest.handle( KEY, value );
      verify( methodHandle ).accept( KEY, value );
      verifyNoMoreInteractions( methodHandle );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) @Override public void constructorShouldNotAcceptNullHandle() {
      new BigDecimalTypeHandle( ( JsonKeyHandle< BigDecimal > )null );
   }//End Method
   
}//End Class