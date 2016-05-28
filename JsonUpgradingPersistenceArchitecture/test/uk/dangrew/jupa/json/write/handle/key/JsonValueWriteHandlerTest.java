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
 * {@link JsonValueWriteHandler} test.
 */
public class JsonValueWriteHandlerTest extends JsonKeyWriteHandlerTest {

   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new JsonValueWriteHandler( objectRetriever );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void retrieveArrayShouldDirectAppropriately() {
      systemUnderTest.retrieve( KEY, INDEX );
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
