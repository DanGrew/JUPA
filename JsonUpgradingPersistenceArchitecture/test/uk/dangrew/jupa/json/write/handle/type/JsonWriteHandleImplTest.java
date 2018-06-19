/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.write.handle.type;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.json.write.handle.key.JsonKeyWriteHandle;

/**
 * {@link JsonWriteHandleImpl} test framework.
 */
public class JsonWriteHandleImplTest {
   
   private static final String KEY = "Key";
   private static final String VALUE = "any value";
   private static final int INDEX = 3;
   
   private JsonWriteHandleImpl systemUnderTest;
   @Mock private JsonKeyWriteHandle handle;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JsonWriteHandleImpl( handle );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullHandle(){
      new JsonWriteHandleImpl( null );
   }//End Method

   @Test public void startedObjectShouldForwardToHandle() {
      systemUnderTest.startedObject( KEY );
      verify( handle ).startedObject( KEY );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void finishedObjectShouldForwardToHandle() {
      systemUnderTest.finishedObject( KEY );
      verify( handle ).finishedObject( KEY );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void startedArrayShouldForwardToHandle() {
      systemUnderTest.startedArray( KEY );
      verify( handle ).startedArray( KEY );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void finishedArrayShouldForwardToHandle() {
      systemUnderTest.finishedArray( KEY );
      verify( handle ).finishedArray( KEY );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldRetrieveObjectAndSet() {
      JSONObject object = new JSONObject();
      
      when( handle.retrieve( KEY ) ).thenReturn( VALUE );
      systemUnderTest.handle( KEY, object );
      assertThat( object.get( KEY ), is( VALUE ) );
      
      verify( handle ).retrieve( KEY );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldPutNullRetrievedObject() {
      JSONObject object = new JSONObject();
      object.put( KEY, VALUE );
      
      when( handle.retrieve( KEY ) ).thenReturn( null );
      systemUnderTest.handle( KEY, object );
      assertThat( object.get( KEY ), is( JSONObject.NULL ) );
      
      verify( handle ).retrieve( KEY );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldPutNullRetrieveForArray() {
      JSONArray array = new JSONArray();
      
      when( handle.retrieve( KEY, INDEX ) ).thenReturn( null );
      systemUnderTest.handle( KEY, array, INDEX );
      assertThat( array.opt( INDEX ), is( nullValue() ) );
      
      verify( handle ).retrieve( KEY, INDEX );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldRetrieveArrayItemAndSet() {
      JSONArray array = new JSONArray();
      
      when( handle.retrieve( KEY, INDEX ) ).thenReturn( VALUE );
      systemUnderTest.handle( KEY, array, INDEX );
      assertThat( array.get( INDEX ), is( VALUE ) );
      
      verify( handle ).retrieve( KEY, INDEX );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldIgnoreNullArrayRetrieved() {
      JSONArray array = new JSONArray();
      array.put( INDEX, VALUE );
      
      when( handle.retrieve( KEY, INDEX ) ).thenReturn( null );
      systemUnderTest.handle( KEY, array, INDEX );
      assertThat( array.get( INDEX ), is( VALUE ) );
      
      verify( handle ).retrieve( KEY, INDEX );
      verifyNoMoreInteractions( handle );
   }//End Method
}//End Class
