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

import org.json.JSONArray;
import org.json.JSONObject;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;
import uk.dangrew.jupa.json.parse.handle.key.JsonValueHandler;

/**
 * {@link BigDecimal} {@link JsonParseHandleImpl}.
 */
public class BigDecimalTypeHandle extends JsonParseHandleImpl< BigDecimal > {
   
   /**
    * Constructs a new {@link BigDecimalTypeHandle}.
    * @param handle the {@link JsonKeyHandle} associated.
    */
   public BigDecimalTypeHandle( JsonKeyHandle< BigDecimal > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link BigDecimalTypeHandle} with the given method in a {@link JsonValueHandler}.
    * @param handle the handle to use in a {@link JsonValueHandler}.
    */
   public BigDecimalTypeHandle( BiConsumer< String, BigDecimal > handle ) {
      this( new JsonValueHandler<>( handle ) );
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
