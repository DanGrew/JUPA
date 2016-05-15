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

import java.math.BigInteger;
import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;
import uk.dangrew.jupa.json.parse.handle.key.JsonValueHandler;

/**
 * {@link BigInteger} {@link JsonParseHandleImpl}.
 */
public class BigIntegerTypeHandle extends JsonParseHandleImpl< BigInteger > {
   
   /**
    * Constructs a new {@link BigIntegerTypeHandle}.
    * @param handle the {@link JsonKeyHandle} associated.
    */
   public BigIntegerTypeHandle( JsonKeyHandle< BigInteger > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link BigIntegerTypeHandle} with the given method in a {@link JsonValueHandler}.
    * @param handle the handle to use in a {@link JsonValueHandler}.
    */
   public BigIntegerTypeHandle( BiConsumer< String, BigInteger > handle ) {
      this( new JsonValueHandler<>( handle ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleKeyPresent( String key, JSONObject object ) {
      BigInteger value = object.optBigInteger( key, null );
      super.handle( key, value );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleArrayIndexPresent( String key, JSONArray array, int index ) {
      BigInteger value = array.optBigInteger( index, null );
      super.handle( key, value );
   }//End Method

}//End Class
