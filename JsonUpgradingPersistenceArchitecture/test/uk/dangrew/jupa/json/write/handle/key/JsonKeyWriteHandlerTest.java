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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * {@link JsonKeyWriteHandle} test.
 */
public class JsonKeyWriteHandlerTest {

   protected static final String KEY = "Key";
   protected static final int INDEX = 4;
   
   @Mock protected Function< String, Object > objectRetriever;
   @Mock protected BiFunction< String, Integer, Object > arrayRetriever;
   @Mock protected Consumer< String > startedObject;
   @Mock protected Consumer< String > finishedObject;
   @Mock protected Consumer< String > startedArray;
   @Mock protected Consumer< String > finishedArray;
   protected JsonKeyWriteHandle systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JsonKeyWriteHandler( 
               objectRetriever, arrayRetriever, startedObject, finishedObject, startedArray, finishedArray 
      );
   }//End Method
   
   @Test public void startedObjectShouldDirectAppropriately() {
      systemUnderTest.startedObject( KEY );
      verify( startedObject ).accept( KEY );
      verifyNoMoreInteractions( objectRetriever, arrayRetriever, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void finishedObjectShouldDirectAppropriately() {
      systemUnderTest.finishedObject( KEY );
      verify( finishedObject ).accept( KEY );
      verifyNoMoreInteractions( objectRetriever, arrayRetriever, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void startedArrayShouldDirectAppropriately() {
      systemUnderTest.startedArray( KEY );
      verify( startedArray ).accept( KEY );
      verifyNoMoreInteractions( objectRetriever, arrayRetriever, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void finishedArrayShouldDirectAppropriately() {
      systemUnderTest.finishedArray( KEY );
      verify( finishedArray ).accept( KEY );
      verifyNoMoreInteractions( objectRetriever, arrayRetriever, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void retrieveObjectShouldDirectAppropriately(){
      systemUnderTest.retrieve( KEY );
      verify( objectRetriever ).apply( KEY );
      verifyNoMoreInteractions( objectRetriever, arrayRetriever, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void retrieveArrayShouldDirectAppropriately(){
      systemUnderTest.retrieve( KEY, INDEX );
      verify( arrayRetriever ).apply( KEY, INDEX );
      verifyNoMoreInteractions( objectRetriever, arrayRetriever, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method

}//End Class
