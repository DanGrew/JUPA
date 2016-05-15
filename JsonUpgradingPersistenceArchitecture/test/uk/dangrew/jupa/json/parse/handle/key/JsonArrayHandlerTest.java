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

import org.junit.Before;
import org.junit.Test;

/**
 * {@link JsonArrayHandler} test.
 */
public class JsonArrayHandlerTest extends JsonKeyHandlerTest {

   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new JsonArrayHandler<>( handle, startedArray, finishedArray );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void startedObjectShouldDirectAppropriately() {
      systemUnderTest.startedObject( KEY );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void finishedObjectShouldDirectAppropriately() {
      systemUnderTest.finishedObject( KEY );
   }//End Method

}//End Class
