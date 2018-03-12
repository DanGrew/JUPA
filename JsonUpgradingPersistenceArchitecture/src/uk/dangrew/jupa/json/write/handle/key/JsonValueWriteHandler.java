/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.write.handle.key;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The {@link JsonValueWriteHandler} provides a {@link JsonKeyWriteHandler} that only needs to
 * write values and expects no nested objects or arrays.
 */
public class JsonValueWriteHandler extends JsonKeyWriteHandler {

   private static final String EXPECTED_VALUE_ONLY = ": expected value only.";

   /** 
    * Constructs a new {@link JsonValueWriteHandler}.
    * @param handle the method to call when handling a parsed value.
    */
   public JsonValueWriteHandler( Function< String, Object > handle ) {
      super( handle, null, null, null, null, null );
   }//End Constructor
   
   /** 
    * Constructs a new {@link JsonValueWriteHandler}.
    * @param handle the method to call when handling a parsed value.
    */
   public JsonValueWriteHandler( Supplier< Object > handle ) {
      super( handle, null, null, null, null, null );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public Object retrieve( String key, int index ) {
      throw new IllegalStateException( key + EXPECTED_VALUE_ONLY );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void startedObject( String key ) {
      throw new IllegalStateException( key + EXPECTED_VALUE_ONLY );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void finishedObject( String key ) {
      throw new IllegalStateException( key + EXPECTED_VALUE_ONLY );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void startedArray( String key ) {
      throw new IllegalStateException( key + EXPECTED_VALUE_ONLY );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void finishedArray( String key ) {
      throw new IllegalStateException( key + EXPECTED_VALUE_ONLY );
   }//End Method
}//End Class
