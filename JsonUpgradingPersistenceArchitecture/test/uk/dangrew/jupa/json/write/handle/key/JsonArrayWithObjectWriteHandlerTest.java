/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.write.handle.key;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link JsonArrayWithObjectWriteHandler} test.
 */
public class JsonArrayWithObjectWriteHandlerTest extends JsonKeyWriteHandlerTest {

   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new JsonArrayWithObjectWriteHandler( startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void retrieveArrayShouldDirectAppropriately() {
      systemUnderTest.retrieve( KEY, INDEX );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void retrieveObjectShouldDirectAppropriately() {
      systemUnderTest.retrieve( KEY );
   }//End Method

}//End Class
