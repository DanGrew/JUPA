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
 * {@link JsonObjectHandler} test.
 */
public class JsonObjectHandlerTest extends JsonKeyHandlerTest {

   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new JsonObjectHandler<>( startedObject, finishedObject );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void handleShouldDirectAppropriately() {
      systemUnderTest.handle( KEY, "anything" );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void startedArrayShouldDirectAppropriately() {
      systemUnderTest.startedArray( KEY );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void finishedArrayShouldDirectAppropriately() {
      systemUnderTest.finishedArray( KEY );
   }//End Method

}//End Class
