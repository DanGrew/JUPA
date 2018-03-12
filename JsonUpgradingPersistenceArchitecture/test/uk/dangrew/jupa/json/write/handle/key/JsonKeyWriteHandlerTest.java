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
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.json.JsonNavigationHandlerImplTest;

/**
 * {@link JsonKeyWriteHandle} test.
 */
public class JsonKeyWriteHandlerTest extends JsonNavigationHandlerImplTest {

   protected static final String KEY = "Key";
   protected static final int INDEX = 4;
   
   @Mock protected Function< String, Object > objectRetriever;
   @Mock protected BiFunction< String, Integer, Object > arrayRetriever;
   
   @Mock protected Supplier< Object > objectRetrieverWithoutKey;
   @Mock protected Function< Integer, Object > arrayRetrieverWithoutKey;
   protected JsonKeyWriteHandle systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JsonKeyWriteHandler( 
               objectRetriever, arrayRetriever, startedObject, finishedObject, startedArray, finishedArray 
      );
      super.systemUnderTest = systemUnderTest;
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

   @Test public void retrieveObjectShouldDirectAppropriatelyWithKeyConsumption(){
      systemUnderTest = new JsonKeyWriteHandler( 
               objectRetrieverWithoutKey, 
               arrayRetrieverWithoutKey, 
               startedObjectWithoutKey, 
               finishedObjectWithoutKey, 
               startedArrayWithoutKey, 
               finishedArrayWithoutKey 
      );
      systemUnderTest.retrieve( KEY );
      verify( objectRetrieverWithoutKey ).get();
      verifyNoMoreInteractions( 
               objectRetrieverWithoutKey, 
               arrayRetrieverWithoutKey, 
               startedObjectWithoutKey, 
               finishedObjectWithoutKey, 
               startedArrayWithoutKey, 
               finishedArrayWithoutKey 
      );
   }//End Method
   
   @Test public void retrieveArrayShouldDirectAppropriatelyWithKeyConsumption(){
      systemUnderTest = new JsonKeyWriteHandler( 
               objectRetrieverWithoutKey, 
               arrayRetrieverWithoutKey, 
               startedObjectWithoutKey, 
               finishedObjectWithoutKey, 
               startedArrayWithoutKey, 
               finishedArrayWithoutKey 
      );
      systemUnderTest.retrieve( KEY, INDEX );
      verify( arrayRetrieverWithoutKey ).apply( INDEX );
      verifyNoMoreInteractions( 
               objectRetrieverWithoutKey, 
               arrayRetrieverWithoutKey, 
               startedObjectWithoutKey, 
               finishedObjectWithoutKey, 
               startedArrayWithoutKey, 
               finishedArrayWithoutKey 
      );
   }//End Method
}//End Class
