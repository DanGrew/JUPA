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

import uk.dangrew.jupa.json.JsonNavigationHandlerImpl;

/**
 * The {@link JsonKeyWriteHandler} provides a handler for writing key values to a JSON stream. 
 * Each method provided will be invoked when the associated point in writing is reached.
 */
public class JsonKeyWriteHandler extends JsonNavigationHandlerImpl implements JsonKeyWriteHandle{

   private final Function< String, Object > objectRetriever;
   private final BiFunction< String, Integer, Object > arrayRetriever;
   
   /** 
    * Constructs a new {@link JsonKeyWriteHandler}.
    * @param objectRetriever the method to call when a value for a key is required.
    * @param startedObject the method to call when a value for an item in an array is required.
    * @param finishedObject the method to call when an object is finished.
    * @param startedArray the method to call when an array is started.
    * @param finishedArray the method to call when an array is finished.
    */
   public JsonKeyWriteHandler(
            Function< String, Object > objectRetriever,
            BiFunction< String, Integer, Object > arrayRetriever,
            Consumer< String > startedObject,
            Consumer< String > finishedObject,
            Consumer< String > startedArray,
            Consumer< String > finishedArray         
   ) {
      super( startedObject, finishedObject, startedArray, finishedArray );
      this.objectRetriever = objectRetriever;
      this.arrayRetriever = arrayRetriever;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public Object retrieve( String key ) {
      return objectRetriever.apply( key );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Object retrieve( String key, int index ) {
      return arrayRetriever.apply( key, index );
   }//End Method

}//End Class
