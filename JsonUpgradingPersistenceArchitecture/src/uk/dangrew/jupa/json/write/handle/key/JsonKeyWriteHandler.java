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

/**
 * The {@link JsonKeyWriteHandler} provides a handler for writing key values to a JSON stream. 
 * Each method provided will be invoked when the associated point in writing is reached.
 */
public class JsonKeyWriteHandler implements JsonKeyWriteHandle{

   private final Function< String, Object > objectRetriever;
   private final BiFunction< String, Integer, Object > arrayRetriever;
   private final Consumer< String > startedObject;
   private final Consumer< String > finishedObject;
   private final Consumer< String > startedArray;
   private final Consumer< String > finishedArray;
   
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
      this.objectRetriever = objectRetriever;
      this.arrayRetriever = arrayRetriever;
      this.startedObject = startedObject;
      this.finishedObject = finishedObject;
      this.startedArray = startedArray;
      this.finishedArray = finishedArray;
   }//End Constructor
   
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
