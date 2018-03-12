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
 * {@link Double} {@link JsonParseHandleImpl}.
 */
public class DoubleParseHandle extends JsonParseHandleImpl< Double >{

   /**
    * Constructs a new {@link IntegerParseHandle}.
    * @param handle the {@link JsonKeyParseHandle} associated.
    */
   public DoubleParseHandle( JsonKeyParseHandle< Double > keyHandle ) {
      super( keyHandle );
   }//End Constructor
   
   /**
    * Constructs a new {@link DoubleParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public DoubleParseHandle( BiConsumer< String, Double > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link DoubleParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public DoubleParseHandle( Consumer< Double > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void handleKeyPresent( String key, JSONObject object ) {
      double value = object.optDouble( key );
      super.handle( key, value );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleArrayIndexPresent( String key, JSONArray array, int index ) {
      double value = array.optDouble( index );
      super.handle( key, value );
   }//End Method
}//End Class
