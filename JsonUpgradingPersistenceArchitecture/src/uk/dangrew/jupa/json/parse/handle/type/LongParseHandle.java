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

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyParseHandle;
import uk.dangrew.jupa.json.parse.handle.key.JsonValueParseHandler;

/**
 * {@link Long} {@link JsonParseHandleImpl}.
 */
public class LongParseHandle extends JsonParseHandleImpl< Long > {
   
   /**
    * Constructs a new {@link LongParseHandle}.
    * @param handle the {@link JsonKeyParseHandle} associated.
    */
   public LongParseHandle( JsonKeyParseHandle< Long > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link LongParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public LongParseHandle( BiConsumer< String, Long > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
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
