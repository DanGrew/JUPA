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
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * {@link JsonKeyParseHandler} test.
 */
public class JsonKeyParseHandlerTest {

   protected static final String KEY = "Key";
   
   @Mock protected BiConsumer< String, String > handle;
   @Mock protected Consumer< String > startedObject;
   @Mock protected Consumer< String > finishedObject;
   @Mock protected Consumer< String > startedArray;
   @Mock protected Consumer< String > finishedArray;
   protected JsonKeyParseHandle< String > systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JsonKeyParseHandler<>( 
               handle, startedObject, finishedObject, startedArray, finishedArray 
      );
   }//End Method
   
   @Test public void startedObjectShouldDirectAppropriately() {
      systemUnderTest.startedObject( KEY );
      verify( startedObject ).accept( KEY );
      verifyNoMoreInteractions( handle, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void finishedObjectShouldDirectAppropriately() {
      systemUnderTest.finishedObject( KEY );
      verify( finishedObject ).accept( KEY );
      verifyNoMoreInteractions( handle, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void startedArrayShouldDirectAppropriately() {
      systemUnderTest.startedArray( KEY );
      verify( startedArray ).accept( KEY );
      verifyNoMoreInteractions( handle, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void finishedArrayShouldDirectAppropriately() {
      systemUnderTest.finishedArray( KEY );
      verify( finishedArray ).accept( KEY );
      verifyNoMoreInteractions( handle, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void handleShouldDirectAppropriately(){
      final String value = "anything";
      systemUnderTest.handle( KEY, value );
      verify( handle ).accept( KEY, value );
      verifyNoMoreInteractions( handle, startedObject, finishedObject, startedArray, finishedArray );
   }//End Method

}//End Class
