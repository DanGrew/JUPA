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

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The {@link JsonArrayWriteHandler} provides a {@link JsonKeyWriteHandler} specifically for handling
 * {@link org.json.JSONArray}s.
 */
public class JsonArrayWriteHandler extends JsonKeyWriteHandler {

   private static final String EXPECTED_ARRAY_WITH_VALUES_ONLY = ": expected array with values only.";
   private static final Function< String, Object > ARRAY_FOUND_FUNCTION_HANDLE = key -> null;
   private static final Supplier< Object > ARRAY_FOUND_SUPPLIER_HANDLE = () -> null;
   private static final BiFunction< String, Integer, Object > ARRAY_INDEX_HANDLE = ( key, index ) -> null;

   /**
    * Constructs a new {@link JsonArrayWriteHandler}.
    * @param arrayItemHandle the method to call when an item in an index has been found.
    * @param startedArray the method to call when handling the start of an array.
    * @param finishedArray the method to call when handling the end of an array.
    */
   public JsonArrayWriteHandler(
            Function< Integer, Object > arrayItemHandle,
            Runnable startedArray,
            Runnable finishedArray         
   ) {
      super( ARRAY_FOUND_SUPPLIER_HANDLE, arrayItemHandle, null, null, startedArray, finishedArray );
   }//End Constructor
   
   /**
    * Constructs a new {@link JsonArrayWriteHandler}.
    * @param arrayItemHandle the method to call when an item in an index has been found.
    * @param startedArray the method to call when handling the start of an array.
    * @param finishedArray the method to call when handling the end of an array.
    */
   public JsonArrayWriteHandler(
            BiFunction< String, Integer, Object > arrayItemHandle,
            Consumer< String > startedArray,
            Consumer< String > finishedArray         
   ) {
      super( ARRAY_FOUND_FUNCTION_HANDLE, arrayItemHandle, null, null, startedArray, finishedArray );
   }//End Constructor
   
   /**
    * Constructs a new {@link JsonArrayWriteHandler}.
    * @param arrayItemHandle the method to call when an item in an index has been found.
    */
   public JsonArrayWriteHandler(
            BiFunction< String, Integer, Object > arrayItemHandle
   ) {
      this( arrayItemHandle, DO_NOTHING_CONSUMER, DO_NOTHING_CONSUMER );
   }//End Constructor
   
   /**
    * Constructs a new {@link JsonArrayWriteHandler}.
    * @param startedArray the method to call when handling the start of an array.
    * @param finishedArray the method to call when handling the end of an array.
    */
   public JsonArrayWriteHandler(
            Consumer< String > startedArray,
            Consumer< String > finishedArray
   ) {
      this( ARRAY_INDEX_HANDLE, startedArray, finishedArray );
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
