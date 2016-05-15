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

import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;
import uk.dangrew.jupa.json.parse.handle.key.JsonValueHandler;

/**
 * {@link Long} {@link JsonParseHandleImpl}.
 */
public class LongTypeHandle extends JsonParseHandleImpl< Long > {
   
   /**
    * Constructs a new {@link LongTypeHandle}.
    * @param handle the {@link JsonKeyHandle} associated.
    */
   public LongTypeHandle( JsonKeyHandle< Long > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link LongTypeHandle} with the given method in a {@link JsonValueHandler}.
    * @param handle the handle to use in a {@link JsonValueHandler}.
    */
   public LongTypeHandle( BiConsumer< String, Long > handle ) {
      this( new JsonValueHandler<>( handle ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleKeyPresent( String key, JSONObject object ) {
      long value = object.optLong( key );
      super.handle( key, value );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleArrayIndexPresent( String key, JSONArray array, int index ) {
      long value = array.optLong( index );
      super.handle( key, value );
   }//End Method
}//End Class
