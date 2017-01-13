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

import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link JsonArrayWithObjectParseHandler} test.
 */
public class JsonArrayWithObjectParseHandlerTest extends JsonKeyParseHandlerTest {

   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new JsonArrayWithObjectParseHandler<>( startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test @Override public void handleShouldDirectAppropriately() {
      systemUnderTest.handle( KEY, VALUE );
      verifyZeroInteractions( startedObject, finishedObject, startedArray, finishedArray );
   }//End Method

}//End Class
