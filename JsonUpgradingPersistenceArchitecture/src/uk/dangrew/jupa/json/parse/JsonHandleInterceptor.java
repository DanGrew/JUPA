package uk.dangrew.jupa.json.parse;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.dangrew.jupa.json.JsonHandle;

public class JsonHandleInterceptor implements JsonHandle {

   private final JsonHandle interceptor;
   private final JsonHandle original;
   
   public JsonHandleInterceptor( JsonHandle original, JsonHandle interceptor ) {
      this.interceptor = interceptor;
      this.original = original;
   }//End Constructor
   
   @Override public void startedObject( String key ) {
      interceptor.startedObject( key );
      original.startedObject( key );
   }//End Method

   @Override public void finishedObject( String key ) {
      interceptor.finishedObject( key );
      original.finishedObject( key );
   }//End Method

   @Override public void startedArray( String key ) {
      interceptor.startedArray( key );
      original.startedArray( key );
   }//End Method

   @Override public void finishedArray( String key ) {
      interceptor.finishedArray( key );
      original.finishedArray( key );
   }//End Method

   @Override public void handle( String key, JSONObject object ) {
      interceptor.handle( key, object );
      original.handle( key, object );
   }//End Method

   @Override public void handle( String key, JSONArray object, int index ) {
      interceptor.handle( key, object, index );
      original.handle( key, object, index );
   }//End Method

}//End Class
