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

import java.math.BigDecimal;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyParseHandle;
import uk.dangrew.jupa.json.parse.handle.key.JsonValueParseHandler;

/**
 * {@link BigDecimal} {@link JsonParseHandleImpl}.
 */
public class BigDecimalParseHandle extends JsonParseHandleImpl< BigDecimal > {
   
   /**
    * Constructs a new {@link BigDecimalParseHandle}.
    * @param handle the {@link JsonKeyParseHandle} associated.
    */
   public BigDecimalParseHandle( JsonKeyParseHandle< BigDecimal > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link BigDecimalParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public BigDecimalParseHandle( BiConsumer< String, BigDecimal > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link BigDecimalParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public BigDecimalParseHandle( Consumer< BigDecimal > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleKeyPresent( String key, JSONObject object ) {
      BigDecimal value = object.optBigDecimal( key, null );
      super.handle( key, value );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleArrayIndexPresent( String key, JSONArray array, int index ) {
      BigDecimal value = array.optBigDecimal( index, null );
      super.handle( key, value );
   }//End Method

}//End Class
