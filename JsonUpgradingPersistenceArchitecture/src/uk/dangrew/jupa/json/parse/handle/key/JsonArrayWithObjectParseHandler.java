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

import java.util.function.Consumer;

/**
 * The {@link JsonArrayWithObjectParseHandler} provides a handler for reading keys from a JSON stream
 * where an array is expected with json object inside each element.
 * @param <HandledTypeT> the type of value being handled.
 */
public class JsonArrayWithObjectParseHandler< HandledTypeT > extends JsonKeyParseHandler< HandledTypeT > implements JsonKeyParseHandle< HandledTypeT >{

   private static final String NO_VALUES_EXPECTED = ": no values expected.";
   
   /** 
    * Constructs a new {@link JsonKeyParseHandler}.
    * @param startedObject the method to call when an object is started.
    * @param finishedObject the method to call when an object is finished.
    * @param startedArray the method to call when an array is started.
    * @param finishedArray the method to call when an array is finished.
    */
   public JsonArrayWithObjectParseHandler(
            Consumer< String > startedObject,
            Consumer< String > finishedObject,
            Consumer< String > startedArray,
            Consumer< String > finishedArray         
   ) {
      super( null, startedObject, finishedObject, startedArray, finishedArray );
   }//End Constructor
   
   /** 
    * Constructs a new {@link JsonKeyParseHandler}.
    * @param startedObject the method to call when an object is started.
    * @param finishedObject the method to call when an object is finished.
    * @param startedArray the method to call when an array is started.
    * @param finishedArray the method to call when an array is finished.
    */
   public JsonArrayWithObjectParseHandler(
            Runnable startedObject,
            Runnable finishedObject,
            Runnable startedArray,
            Runnable finishedArray         
   ) {
      super( null, startedObject, finishedObject, startedArray, finishedArray );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handle( String key, HandledTypeT value ) {
      //do nothing, the element is not present i.e. null
   }//End Method

}//End Class
