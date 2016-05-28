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

import javafx.scene.layout.Priority;
import uk.dangrew.jupa.json.parse.handle.key.JsonKeyParseHandle;

/**
 * {@link EnumParseHandle} test.
 */
public class EnumParseHandleTest extends JsonParseHandleImplTest< Priority > {
   
   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new EnumParseHandle< Priority >( Priority.class, handle );
   }//End Method

   @Test public void shouldHandleObjectAndProvideValue() {
      JSONObject object = new JSONObject();
      
      final Priority value = Priority.ALWAYS;
      object.put( KEY, value );
      
      systemUnderTest.handle( KEY, object );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldHandleArrayAndProvideValue() {
      JSONArray array = new JSONArray();
      
      final Priority value = Priority.ALWAYS;
      final int index = 1;
      array.put( "" );
      array.put( value );
      array.put( "" );
      
      systemUnderTest.handle( KEY, array, index );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method

   @Test @Override public void handleShouldForwardToKeyHandle(){
      final Priority value = Priority.ALWAYS;
      systemUnderTest.handle( KEY, value );
      verify( handle ).handle( KEY, value );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test @Override public void methodConstructorShouldUseMethodInHandle(){
      @SuppressWarnings("unchecked") //safe - mocking generic objects
      BiConsumer< String, Priority > methodHandle = mock( BiConsumer.class );
      systemUnderTest = new EnumParseHandle< Priority >( Priority.class, methodHandle );
      
      final Priority value = Priority.ALWAYS;
      systemUnderTest.handle( KEY, value );
      verify( methodHandle ).accept( KEY, value );
      verifyNoMoreInteractions( methodHandle );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void constructorShouldNotAcceptNullEnumType(){
      new EnumParseHandle<>( null, handle );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) @Override public void constructorShouldNotAcceptNullHandle() {
      new EnumParseHandle<>( Priority.class, ( JsonKeyParseHandle< Priority > )null );
   }//End Method
   
}//End Class
