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
 * The {@link JsonKeyParseHandler} provides a handler for reading keys from a JSON stream. 
 * Each method provided will be invoked when the associated point in parsing is reached.
 * @param <HandledTypeT> the type of value being handled.
 */
public class JsonKeyParseHandler< HandledTypeT > implements JsonKeyParseHandle< HandledTypeT >{

   private final BiConsumer< String, HandledTypeT > handle;
   private final Consumer< String > startedObject;
   private final Consumer< String > finishedObject;
   private final Consumer< String > startedArray;
   private final Consumer< String > finishedArray;
   
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
      this.handle = handle;
      this.startedObject = startedObject;
      this.finishedObject = finishedObject;
      this.startedArray = startedArray;
      this.finishedArray = finishedArray;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handle( String key, HandledTypeT value ) {
      handle.accept( key, value );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void startedObject( String key ) {
      startedObject.accept( key );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void finishedObject( String key ) {
      finishedObject.accept( key );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void startedArray( String key ) {
      startedArray.accept( key );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void finishedArray( String key ) {
      finishedArray.accept( key );
   }//End Method

}//End Class
