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

import uk.dangrew.jupa.json.JsonNavigation;
import uk.dangrew.jupa.json.JsonNavigationHandlerImpl;

/**
 * The {@link JsonKeyParseHandler} provides a handler for reading keys from a JSON stream. 
 * Each method provided will be invoked when the associated point in parsing is reached.
 * @param <HandledTypeT> the type of value being handled.
 */
public class JsonKeyParseHandler< HandledTypeT > extends JsonNavigationHandlerImpl implements JsonKeyParseHandle< HandledTypeT >{

   private final BiConsumer< String, HandledTypeT > handle;
   
   /** 
    * Constructs a new {@link JsonKeyParseHandler}.
    * @param handle the method to call when handling a parsed value.
    * @param startedObject the method to call when an object is started.
    * @param finishedObject the method to call when an object is finished.
    * @param startedArray the method to call when an array is started.
    * @param finishedArray the method to call when an array is finished.
    */
   public JsonKeyParseHandler(
            BiConsumer< String, HandledTypeT > handle,
            Consumer< String > startedObject,
            Consumer< String > finishedObject,
            Consumer< String > startedArray,
            Consumer< String > finishedArray         
   ) {
      super( startedObject, finishedObject, startedArray, finishedArray );
      this.handle = handle;
   }//End Constructor
   
   /** 
    * Constructs a new {@link JsonKeyParseHandler}.
    * @param handle the method to call when handling a parsed value.
    * @param startedObject the method to call when an object is started.
    * @param finishedObject the method to call when an object is finished.
    * @param startedArray the method to call when an array is started.
    * @param finishedArray the method to call when an array is finished.
    */
   public JsonKeyParseHandler(
            Consumer< HandledTypeT > handle,
            Runnable startedObject,
            Runnable finishedObject,
            Runnable startedArray,
            Runnable finishedArray         
   ) {
      this( 
               JsonNavigation.consumeKey( handle ),
               JsonNavigation.consumeKey( startedObject ), 
               JsonNavigation.consumeKey( finishedObject ), 
               JsonNavigation.consumeKey( startedArray ), 
               JsonNavigation.consumeKey( finishedArray ) 
      );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handle( String key, HandledTypeT value ) {
      handle.accept( key, value );
   }//End Method

}//End Class
