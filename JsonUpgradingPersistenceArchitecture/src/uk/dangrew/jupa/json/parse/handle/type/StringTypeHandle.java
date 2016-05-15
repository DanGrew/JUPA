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
 * {@link String} {@link JsonParseHandleImpl}.
 */
public class StringTypeHandle extends JsonParseHandleImpl< String > {
   
   /**
    * Constructs a new {@link StringTypeHandle}.
    * @param handle the {@link JsonKeyHandle} associated.
    */
   public StringTypeHandle( JsonKeyHandle< String > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link StringTypeHandle} with the given method in a {@link JsonValueHandler}.
    * @param handle the handle to use in a {@link JsonValueHandler}.
    */
   public StringTypeHandle( BiConsumer< String, String > handle ) {
      this( new JsonValueHandler<>( handle ) );
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
