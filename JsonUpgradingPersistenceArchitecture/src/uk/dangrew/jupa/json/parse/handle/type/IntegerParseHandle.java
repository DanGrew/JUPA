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
 * {@link Integer} {@link JsonParseHandleImpl}.
 */
public class IntegerParseHandle extends JsonParseHandleImpl< Integer >{

   /**
    * Constructs a new {@link IntegerParseHandle}.
    * @param handle the {@link JsonKeyParseHandle} associated.
    */
   public IntegerParseHandle( JsonKeyParseHandle< Integer > keyHandle ) {
      super( keyHandle );
   }//End Constructor
   
   /**
    * Constructs a new {@link IntegerParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public IntegerParseHandle( BiConsumer< String, Integer > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link IntegerParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public IntegerParseHandle( Consumer< Integer > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
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
