/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse.handle.key;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.function.BiConsumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.json.JsonNavigationHandlerImplTest;

/**
 * {@link JsonKeyParseHandler} test.
 */
public class JsonKeyParseHandlerTest extends JsonNavigationHandlerImplTest {

   @Mock protected BiConsumer< String, String > handle;
   protected JsonKeyParseHandle< String > systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JsonKeyParseHandler<>( 
               handle, startedObject, finishedObject, startedArray, finishedArray 
      );
      super.systemUnderTest = systemUnderTest;
   }//End Method
   
   @Test public void handleShouldDirectAppropriately(){
      final String value = "anything";
      systemUnderTest.handle( KEY, value );
      verify( handle ).accept( KEY, value );
      verifyNoMoreInteractions( handle, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method

}//End Class
