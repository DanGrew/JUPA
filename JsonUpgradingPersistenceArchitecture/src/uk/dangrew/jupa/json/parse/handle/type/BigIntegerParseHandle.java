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

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyParseHandle;
import uk.dangrew.jupa.json.parse.handle.key.JsonValueParseHandler;

/**
 * {@link BigInteger} {@link JsonParseHandleImpl}.
 */
public class BigIntegerParseHandle extends JsonParseHandleImpl< BigInteger > {
   
   /**
    * Constructs a new {@link BigIntegerParseHandle}.
    * @param handle the {@link JsonKeyParseHandle} associated.
    */
   public BigIntegerParseHandle( JsonKeyParseHandle< BigInteger > handle ) {
      super( handle );
   }//End Constructor
   
   /**
    * Constructs a new {@link BigIntegerParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public BigIntegerParseHandle( BiConsumer< String, BigInteger > handle ) {
      this( new JsonValueParseHandler<>( handle ) );
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
