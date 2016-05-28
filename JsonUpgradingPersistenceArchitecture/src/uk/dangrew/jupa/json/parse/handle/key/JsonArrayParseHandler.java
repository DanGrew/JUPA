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
import java.util.function.Consumer;

/**
 * The {@link JsonArrayParseHandler} provides a {@link JsonKeyParseHandler} specifically for handling
 * {@link org.json.JSONArray}s.
 * @param <HandledTypeT> the type of value being handled.
 */
public class JsonArrayParseHandler< HandledTypeT > extends JsonKeyParseHandler< HandledTypeT > {

   private static final String EXPECTED_ARRAY_WITH_VALUES_ONLY = ": expected array with values only.";

   /**
    * Constructs a new {@link JsonArrayParseHandler}.
    * @param handle the method to call when handling a value.
    * @param startedArray the method to call when handling the start of an array.
    * @param finishedArray the method to call when handling the end of an array.
    */
   public JsonArrayParseHandler(
            BiConsumer< String, HandledTypeT > handle,
            Consumer< String > startedArray,
            Consumer< String > finishedArray         
   ) {
      super( handle, null, null, startedArray, finishedArray );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void startedObject( String key ) {
      throw new IllegalStateException( key + EXPECTED_ARRAY_WITH_VALUES_ONLY );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void finishedObject( String key ) {
      throw new IllegalStateException( key + EXPECTED_ARRAY_WITH_VALUES_ONLY );
   }//End Method
   
}//End Class
