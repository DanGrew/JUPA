/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse.handle.key;

import java.util.function.BiConsumer;

/**
 * The {@link JsonValueHandler} provides a {@link JsonKeyHandler} that only needs to
 * parse values and expects no nested objects or arrays.
 * @param <HandledTypeT> the type of value being handled.
 */
public class JsonValueHandler< HandledTypeT > extends JsonKeyHandler< HandledTypeT > {

   private static final String EXPECTED_VALUE_ONLY = ": expected value only.";

   /** 
    * Constructs a new {@link JsonValueHandler}.
    * @param handle the method to call when handling a parsed value.
    */
   public JsonValueHandler( BiConsumer< String, HandledTypeT > handle ) {
      super( handle, null, null, null, null );
   }//End Constructor
   
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
