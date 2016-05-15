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
 * {@link Boolean} {@link JsonParseHandleImpl}.
 */
public class BooleanTypeHandle extends JsonParseHandleImpl< Boolean > {
   
   /**
    * Constructs a new {@link BooleanTypeHandle}.
    * @param handle the {@link JsonKeyHandle} associated.
    */
   public BooleanTypeHandle( JsonKeyHandle< Boolean > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link BooleanTypeHandle} with the given method in a {@link JsonValueHandler}.
    * @param handle the handle to use in a {@link JsonValueHandler}.
    */
   public BooleanTypeHandle( BiConsumer< String, Boolean > handle ) {
      this( new JsonValueHandler<>( handle ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleKeyPresent( String key, JSONObject object ) {
      boolean value = object.optBoolean( key );
      super.handle( key, value );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleArrayIndexPresent( String key, JSONArray array, int index ) {
      boolean value = array.optBoolean( index );
      super.handle( key, value );
   }//End Method
}//End Class
