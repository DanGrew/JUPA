package uk.dangrew.jupa.json.parse;

import static org.mockito.Mockito.inOrder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.json.JsonHandle;
import uk.dangrew.kode.launch.TestApplication;

public class JsonHandleInterceptorTest {

   private static final String KEY = "anyKey";
   private static final JSONObject JSON = new JSONObject();
   private static final JSONArray ARRAY = new JSONArray();
   private InOrder verifier;
   
   @Mock private JsonHandle original;
   @Mock private JsonHandle interceptor;
   private JsonHandleInterceptor systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      verifier = inOrder( original, interceptor );
      
      systemUnderTest = new JsonHandleInterceptor( original, interceptor );
   }//End Method

   @Test public void shouldStartObjectInOrder() {
      systemUnderTest.startedObject( KEY );
      verifier.verify( interceptor ).startedObject( KEY );
      verifier.verify( original ).startedObject( KEY );
   }//End Method
   
   @Test public void shouldFinishObjectInOrder() {
      systemUnderTest.finishedObject( KEY );
      verifier.verify( interceptor ).finishedObject( KEY );
      verifier.verify( original ).finishedObject( KEY );
   }//End Method
   
   @Test public void shouldStartArrayInOrder() {
      systemUnderTest.startedArray( KEY );
      verifier.verify( interceptor ).startedArray( KEY );
      verifier.verify( original ).startedArray( KEY );
   }//End Method
   
   @Test public void shouldFinishArrayInOrder() {
      systemUnderTest.finishedArray( KEY );
      verifier.verify( interceptor ).finishedArray( KEY );
      verifier.verify( original ).finishedArray( KEY );
   }//End Method
   
   @Test public void shouldHandleJsonInOrder() {
      systemUnderTest.handle( KEY, JSON );
      verifier.verify( interceptor ).handle( KEY, JSON );
      verifier.verify( original ).handle( KEY, JSON );
   }//End Method
   
   @Test public void shouldHandleArrayInOrder() {
      systemUnderTest.handle( KEY, ARRAY, 3 );
      verifier.verify( interceptor ).handle( KEY, ARRAY, 3 );
      verifier.verify( original ).handle( KEY, ARRAY, 3 );
   }//End Method

}//End Class
