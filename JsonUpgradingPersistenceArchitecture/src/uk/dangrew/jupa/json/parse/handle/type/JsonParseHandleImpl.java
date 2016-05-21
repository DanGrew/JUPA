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

import org.json.JSONArray;
import org.json.JSONObject;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;

/**
 * The {@link JsonParseHandleImpl} provides an implementation of {@link JsonParseHandle} that
 * provides common structure and processing of a parse handle where the type specific methods
 * are defined by extensions.
 * @param <HandledTypeT> the type of value handled.
 */
public abstract class JsonParseHandleImpl< HandledTypeT > implements JsonParseHandle {
   
   private final JsonKeyHandle< HandledTypeT > keyHandle;
   
   /**
    * Constructs a new {@link JsonParseHandleImpl}.
    * @param keyHandle the {@link JsonKeyHandle} associated to trigger.
    */
   protected JsonParseHandleImpl( JsonKeyHandle< HandledTypeT > keyHandle ) {
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
    * Method to handle a parsed value for a key where defaults have been handled.
    * @param key the key parsed for.
    * @param value the value parsed.
    */
   protected final void handle( String key, HandledTypeT value ) {
      keyHandle.handle( key, value );
   }//End Method
   
   /**
    * Method to determine whether the {@link JSONObject} has the given key.
    * @param key the key in question.
    * @param object the {@link JSONObject} to check in.
    * @return true if the key is present.
    */
   protected final boolean objectHasKey( String key, JSONObject object ) {
      return object.has( key );
   }//End Method
   
   /**
    * Method to determine whether the {@link JSONArray} has the index given.
    * @param index the index in question.
    * @param array the {@link JSONArray} to look in.
    * @return true if the index is present.
    */
   protected final boolean arrayHasIndex( int index, JSONArray array ) {
      return index >= 0 && index < array.length();
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handle( String key, JSONObject object ) {
      if ( objectHasKey( key, object ) ) {
         handleKeyPresent( key, object );
      } else {
         handle( key, ( HandledTypeT )null );
      }
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handle( String key, JSONArray array, int index ) {
      if ( arrayHasIndex( index, array ) ) {
         handleArrayIndexPresent( key, array, index );
      } else {
         handle( key, ( HandledTypeT )null );
      }
   }//End Method
   
   /**
    * Method to retrieve the value from the {@link JSONObject} as the correct type.
    * @param key the key to retrieve value from.
    * @param object the {@link JSONObject} to look in.
    */
   public abstract void handleKeyPresent( String key, JSONObject object );
   
   /**
    * Method to retrieve the value from the {@link JSONArray} as the correct type.
    * @param key the key to retrieve value from.
    * @param object the {@link JSONArray} to look in.
    * @param index the index of item to get.
    */
   public abstract void handleArrayIndexPresent( String key, JSONArray object, int index );
   
}//End Class
