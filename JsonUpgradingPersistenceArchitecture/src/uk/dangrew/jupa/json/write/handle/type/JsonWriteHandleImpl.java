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

import org.json.JSONArray;
import org.json.JSONObject;

import uk.dangrew.jupa.json.JsonHandle;
import uk.dangrew.jupa.json.write.handle.key.JsonKeyWriteHandle;

/**
 * The {@link JsonWriteHandleImpl} provides an implementation of {@link JsonHandle} that
 * provides common structure and processing of a write handle that retrieves values from
 * an associated {@link JsonKeyWriteHandle}.
 */
public class JsonWriteHandleImpl implements JsonHandle {
   
   private final JsonKeyWriteHandle keyHandle;
   
   /**
    * Constructs a new {@link JsonWriteHandleImpl}.
    * @param keyHandle the {@link JsonKeyWriteHandle} associated to trigger.
    */
   public JsonWriteHandleImpl( JsonKeyWriteHandle keyHandle ) {
      if ( keyHandle == null ) {
         throw new IllegalArgumentException( "Null Key Handle is not permitted." );
      }
      this.keyHandle = keyHandle;
   }//End Class
   
   /**
    * {@inheritDoc}
    */
   @Override public void startedObject( String key ) {
      keyHandle.startedObject( key );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void finishedObject( String key ) {
      keyHandle.finishedObject( key );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void startedArray( String key ) {
      keyHandle.startedArray( key );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void finishedArray( String key ) {
      keyHandle.finishedArray( key );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handle( String key, JSONObject object ) {
      Object valueToPut = keyHandle.retrieve( key );
      if ( valueToPut == null ) {
         return;
      }
      object.put( key, valueToPut );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handle( String key, JSONArray array, int index ) {
      Object valueToPut = keyHandle.retrieve( key, index );
      if ( valueToPut == null ) {
         return;
      }
      array.put( index, valueToPut );
   }//End Method
   
}//End Class
