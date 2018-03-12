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
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyParseHandle;
import uk.dangrew.jupa.json.parse.handle.key.JsonValueParseHandler;

/**
 * {@link Boolean} {@link JsonParseHandleImpl}.
 */
public class BooleanParseHandle extends JsonParseHandleImpl< Boolean > {
   
   /**
    * Constructs a new {@link BooleanParseHandle}.
    * @param handle the {@link JsonKeyParseHandle} associated.
    */
   public BooleanParseHandle( JsonKeyParseHandle< Boolean > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link BooleanParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public BooleanParseHandle( BiConsumer< String, Boolean > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link BooleanParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public BooleanParseHandle( Consumer< Boolean > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
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
