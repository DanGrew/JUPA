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
 * {@link JsonValueHandler} test.
 */
public class JsonValueHandlerTest extends JsonKeyHandlerTest {

   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new JsonValueHandler<>( handle );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void startedObjectShouldDirectAppropriately() {
      systemUnderTest.startedObject( KEY );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void finishedObjectShouldDirectAppropriately() {
      systemUnderTest.finishedObject( KEY );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void startedArrayShouldDirectAppropriately() {
      systemUnderTest.startedArray( KEY );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void finishedArrayShouldDirectAppropriately() {
      systemUnderTest.finishedArray( KEY );
   }//End Method

}//End Class
