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
 * {@link Integer} {@link JsonParseHandleImpl}.
 */
public class IntegerTypeHandle extends JsonParseHandleImpl< Integer >{

   /**
    * Constructs a new {@link IntegerTypeHandle}.
    * @param handle the {@link JsonKeyHandle} associated.
    */
   public IntegerTypeHandle( JsonKeyHandle< Integer > keyHandle ) {
      super( keyHandle );
   }//End Constructor
   
   /**
    * Constructs a new {@link IntegerTypeHandle} with the given method in a {@link JsonValueHandler}.
    * @param handle the handle to use in a {@link JsonValueHandler}.
    */
   public IntegerTypeHandle( BiConsumer< String, Integer > handle ) {
      this( new JsonValueHandler<>( handle ) );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void handleKeyPresent( String key, JSONObject object ) {
      int value = object.optInt( key );
      super.handle( key, value );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleArrayIndexPresent( String key, JSONArray array, int index ) {
      int value = array.optInt( index );
      super.handle( key, value );
   }//End Method
}//End Class
