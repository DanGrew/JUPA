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

import java.util.function.Consumer;

/**
 * The {@link JsonArrayWithObjectWriteHandler} provides a handler for reading keys from a JSON stream
 * where an array is expected with a json object for each item. 
 */
public class JsonArrayWithObjectWriteHandler extends JsonKeyWriteHandler {

   private static final String NO_VALUES_EXPECTED = ": no values expected.";
   
   /** 
    * Constructs a new {@link JsonKeyParseHandler}.
    * @param startedObject the method to call when an object is started.
    * @param finishedObject the method to call when an object is finished.
    * @param startedArray the method to call when an array is started.
    * @param finishedArray the method to call when an array is finished.
    */
   public JsonArrayWithObjectWriteHandler(
            Consumer< String > startedObject,
            Consumer< String > finishedObject,
            Consumer< String > startedArray,
            Consumer< String > finishedArray         
   ) {
      super( null, null, startedObject, finishedObject, startedArray, finishedArray );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Object retrieve( String key ) {
      throw new IllegalStateException( key + NO_VALUES_EXPECTED );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Object retrieve( String key, int index ) {
      throw new IllegalStateException( key + NO_VALUES_EXPECTED );
   }//End Method

}//End Class
