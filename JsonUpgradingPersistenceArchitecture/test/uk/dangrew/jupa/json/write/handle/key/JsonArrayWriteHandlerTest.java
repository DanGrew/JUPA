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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link JsonArrayWriteHandler} test.
 */
public class JsonArrayWriteHandlerTest extends JsonKeyWriteHandlerTest {

   @Before @Override public void initialiseSystemUnderTest() {
      super.initialiseSystemUnderTest();
      systemUnderTest = new JsonArrayWriteHandler( arrayRetriever, startedArray, finishedArray );
   }//End Method
   
   @Test @Override public void retrieveObjectShouldDirectAppropriately() {
      assertThat( systemUnderTest.retrieve( KEY ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void startedObjectShouldDirectAppropriately() {
      systemUnderTest.startedObject( KEY );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) @Override public void finishedObjectShouldDirectAppropriately() {
      systemUnderTest.finishedObject( KEY );
   }//End Method

}//End Class
