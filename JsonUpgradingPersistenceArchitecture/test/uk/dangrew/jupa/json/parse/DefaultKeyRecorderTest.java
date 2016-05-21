/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link DefaultKeyRecorder} test.
 */
public class DefaultKeyRecorderTest {

   private static final String KEY = "some key";
   private static final String VALUE = "some specific value for a key";
   
   private DefaultKeyRecorder systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      systemUnderTest = new DefaultKeyRecorder();
      
      systemUnderTest.startedObject( KEY );
      systemUnderTest.startedArray( KEY );
      systemUnderTest.handle( KEY, VALUE );
      systemUnderTest.handle( KEY, null );
      systemUnderTest.finishedArray( KEY );
      systemUnderTest.finishedObject( KEY );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenNothingExpected() {
      systemUnderTest.expectKeysFound();
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenLessExpected() {
      systemUnderTest.expect( KEY, VALUE );
      systemUnderTest.expectKeysFound();
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenStartedObjectNotRecorded() {
      systemUnderTest.expect( KEY, VALUE );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenKeyDoesntMatch() {
      systemUnderTest.expect( "anything", VALUE );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenStartedArrayNotRecorded() {
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_STARTED );
      systemUnderTest.expect( KEY, VALUE );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenHandleNotRecorded() {
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_STARTED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_STARTED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_STARTED );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenFinishedArrayObjectNotRecorded() {
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_STARTED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_STARTED );
      systemUnderTest.expect( KEY, VALUE );
      systemUnderTest.expect( KEY, VALUE );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenNullIsNotRecorded() {
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_STARTED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_STARTED );
      systemUnderTest.expect( KEY, null );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenKeyDoesNotMatchButEverythingElseDoes() {
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_STARTED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_STARTED );
      systemUnderTest.expect( "anything", VALUE );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenFinishedObjectNotRecorded() {
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_STARTED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_STARTED );
      systemUnderTest.expect( KEY, VALUE );
      systemUnderTest.expect( KEY, null );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_FINISHED );
      systemUnderTest.expect( KEY, VALUE );
   }//End Method
   
   @Test public void shouldCompleteAssertionsWhenEverythingSuccessful() {
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_STARTED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_STARTED );
      systemUnderTest.expect( KEY, VALUE );
      systemUnderTest.expect( KEY, null );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_FINISHED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_FINISHED );
      systemUnderTest.expectKeysFound();
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailAssertionWhenNotEnoughRecorded() {
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_STARTED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_STARTED );
      systemUnderTest.expect( KEY, VALUE );
      systemUnderTest.expect( KEY, null );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.ARRAY_FINISHED );
      systemUnderTest.expect( KEY, DefaultKeyRecorder.OBJECT_FINISHED );
      systemUnderTest.expect( KEY, VALUE );
   }//End Method

}//End Class
