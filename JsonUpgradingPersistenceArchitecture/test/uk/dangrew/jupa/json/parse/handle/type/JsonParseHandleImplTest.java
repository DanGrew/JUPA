/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse.handle.type;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;

/**
 * {@link JsonParseHandleImpl} test framework.
 */
public abstract class JsonParseHandleImplTest< HandledTypeT > {
   
   protected static final String KEY = "Key";
   
   protected JsonParseHandleImpl< HandledTypeT > systemUnderTest;
   @Mock protected JsonKeyHandle< HandledTypeT > handle;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
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
   
   @Test public void shouldHandleMissingKeyInObjectAndProvideValue() {
      JSONObject object = new JSONObject();
      
      systemUnderTest.handle( KEY, object );
      verify( handle ).handle( KEY, null );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void shouldHandleMissingItemArrayAndProvideValue() {
      JSONArray array = new JSONArray();
      
      final int index = 1;
      
      systemUnderTest.handle( KEY, array, index );
      verify( handle ).handle( KEY, null );
      verifyNoMoreInteractions( handle );
   }//End Method
   
   @Test public void objectHasKeyShouldCheckObjectForKey(){
      JSONObject object = new JSONObject();
      assertThat( systemUnderTest.objectHasKey( KEY, object ), is( false ) );
      
      object.put( KEY, "anything" );
      assertThat( systemUnderTest.objectHasKey( KEY, object ), is( true ) );
      
      object.put( KEY, ( String )null );
      assertThat( systemUnderTest.objectHasKey( KEY, object ), is( false ) );
   }//End Method
   
   @Test public void arrayHasIndexShouldCheckIndexWithinRange(){
      JSONArray array = new JSONArray();
      array.put( true );
      array.put( false );
      array.put( false );
      array.put( true );
      
      assertThat( systemUnderTest.arrayHasIndex( 0, array ), is( true ) );
      assertThat( systemUnderTest.arrayHasIndex( 3, array ), is( true ) );
      assertThat( systemUnderTest.arrayHasIndex( 4, array ), is( false ) );
      assertThat( systemUnderTest.arrayHasIndex( -1, array ), is( false ) );
   }//End Method
   
   @Test public abstract void handleShouldForwardToKeyHandle();
   
   @Test public abstract void methodConstructorShouldUseMethodInHandle();
   
   @Test public abstract void constructorShouldNotAcceptNullHandle();
}//End Class
