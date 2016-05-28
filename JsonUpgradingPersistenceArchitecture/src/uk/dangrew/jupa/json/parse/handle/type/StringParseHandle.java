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
 * {@link String} {@link JsonParseHandleImpl}.
 */
public class StringParseHandle extends JsonParseHandleImpl< String > {
   
   /**
    * Constructs a new {@link StringParseHandle}.
    * @param handle the {@link JsonKeyParseHandle} associated.
    */
   public StringParseHandle( JsonKeyParseHandle< String > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link StringParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public StringParseHandle( BiConsumer< String, String > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleKeyPresent( String key, JSONObject object ) {
      String value = object.optString( key );
      super.handle( key, value );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleArrayIndexPresent( String key, JSONArray array, int index ) {
      String value = array.optString( index );
      super.handle( key, value );
   }//End Method

}//End Class
