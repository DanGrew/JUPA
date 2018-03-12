/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * {@link JsonNavigationHandlerImpl} test.
 */
public class JsonNavigationHandlerImplTest {

   protected static final String KEY = "Key";
   protected static final String VALUE = "Value";
   protected static final int INDEX = 4;
   
   @Mock protected Consumer< String > startedObject;
   @Mock protected Consumer< String > finishedObject;
   @Mock protected Consumer< String > startedArray;
   @Mock protected Consumer< String > finishedArray;
   
   @Mock protected Runnable startedObjectWithoutKey;
   @Mock protected Runnable finishedObjectWithoutKey;
   @Mock protected Runnable startedArrayWithoutKey;
   @Mock protected Runnable finishedArrayWithoutKey;
   protected JsonNavigation systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JsonNavigationHandlerImpl( 
               startedObject, finishedObject, startedArray, finishedArray 
      );
   }//End Method
   
   @Test public void startedObjectShouldDirectAppropriately() {
      systemUnderTest.startedObject( KEY );
      verify( startedObject ).accept( KEY );
      verifyNoMoreInteractions( startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void finishedObjectShouldDirectAppropriately() {
      systemUnderTest.finishedObject( KEY );
      verify( finishedObject ).accept( KEY );
      verifyNoMoreInteractions( startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void startedArrayShouldDirectAppropriately() {
      systemUnderTest.startedArray( KEY );
      verify( startedArray ).accept( KEY );
      verifyNoMoreInteractions( startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void finishedArrayShouldDirectAppropriately() {
      systemUnderTest.finishedArray( KEY );
      verify( finishedArray ).accept( KEY );
      verifyNoMoreInteractions( startedObject, finishedObject, startedArray, finishedArray );
   }//End Method
   
   @Test public void nullsShouldBePermittedByConstructor(){
      startedObjectWithoutKey  = null;
      finishedObjectWithoutKey = null;
      startedArrayWithoutKey   = null;
      finishedArrayWithoutKey  = null;
      systemUnderTest = new JsonNavigationHandlerImpl( 
               startedObjectWithoutKey, 
               finishedObjectWithoutKey, 
               startedArrayWithoutKey, 
               finishedArrayWithoutKey 
      );
      systemUnderTest.startedObject( KEY );
      systemUnderTest.finishedObject( KEY );
      systemUnderTest.startedArray( KEY );
      systemUnderTest.finishedArray( KEY );
   }//End Method
   
   @Test public void startedObjectShouldDirectAppropriatelyWithKeyConsumption() {
      systemUnderTest = new JsonNavigationHandlerImpl( 
               startedObjectWithoutKey, 
               finishedObjectWithoutKey, 
               startedArrayWithoutKey, 
               finishedArrayWithoutKey 
      );
      systemUnderTest.startedObject( KEY );
      verify( startedObjectWithoutKey ).run();
      verifyNoMoreInteractions( startedObjectWithoutKey, finishedObjectWithoutKey, startedArrayWithoutKey, finishedArrayWithoutKey );
   }//End Method
   
   @Test public void finishedObjectShouldDirectAppropriatelyWithKeyConsumption() {
      systemUnderTest = new JsonNavigationHandlerImpl( 
               startedObjectWithoutKey, 
               finishedObjectWithoutKey, 
               startedArrayWithoutKey, 
               finishedArrayWithoutKey 
      );
      systemUnderTest.finishedObject( KEY );
      verify( finishedObjectWithoutKey ).run();
      verifyNoMoreInteractions( startedObjectWithoutKey, finishedObjectWithoutKey, startedArrayWithoutKey, finishedArrayWithoutKey );
   }//End Method
   
   @Test public void startedArrayShouldDirectAppropriatelyWithKeyConsumption() {
      systemUnderTest = new JsonNavigationHandlerImpl( 
               startedObjectWithoutKey, 
               finishedObjectWithoutKey, 
               startedArrayWithoutKey, 
               finishedArrayWithoutKey 
      );
      systemUnderTest.startedArray( KEY );
      verify( startedArrayWithoutKey ).run();
      verifyNoMoreInteractions( startedObjectWithoutKey, finishedObjectWithoutKey, startedArrayWithoutKey, finishedArrayWithoutKey );
   }//End Method
   
   @Test public void finishedArrayShouldDirectAppropriatelyWithKeyConsumption() {
      systemUnderTest = new JsonNavigationHandlerImpl( 
               startedObjectWithoutKey, 
               finishedObjectWithoutKey, 
               startedArrayWithoutKey, 
               finishedArrayWithoutKey 
      );
      systemUnderTest.finishedArray( KEY );
      verify( finishedArrayWithoutKey ).run();
      verifyNoMoreInteractions( startedObjectWithoutKey, finishedObjectWithoutKey, startedArrayWithoutKey, finishedArrayWithoutKey );
   }//End Method
   
}//End Class
