/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json;

import java.util.function.Consumer;

/**
 * The {@link JsonNavigationHandlerImpl} provides an implementation of {@link JsonNavigation} that
 * binds separate functions to the methods in the interface.
 */
public class JsonNavigationHandlerImpl implements JsonNavigation {
   
   private final Consumer< String > startedObject;
   private final Consumer< String > finishedObject;
   private final Consumer< String > startedArray;
   private final Consumer< String > finishedArray;
   
   /**
    * Constructs a new {@link JsonNavigationHandlerImpl}.
    * @param startedObject the method to call when an object is started, can be null.
    * @param finishedObject the method to call when an object is finished, can be null.
    * @param startedArray the method to call when an array is started, can be null.
    * @param finishedArray the method to call when an array is finished, can be null.
    */
   public JsonNavigationHandlerImpl(
            Runnable startedObject,
            Runnable finishedObject,
            Runnable startedArray,
            Runnable finishedArray         
   ) {
      this( 
         JsonNavigation.consumeKey( startedObject ),
         JsonNavigation.consumeKey( finishedObject ),
         JsonNavigation.consumeKey( startedArray ),
         JsonNavigation.consumeKey( finishedArray )
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link JsonNavigationHandlerImpl}.
    * @param startedObject the method to call when an object is started, can be null.
    * @param finishedObject the method to call when an object is finished, can be null.
    * @param startedArray the method to call when an array is started, can be null.
    * @param finishedArray the method to call when an array is finished, can be null.
    */
   public JsonNavigationHandlerImpl(
            Consumer< String > startedObject,
            Consumer< String > finishedObject,
            Consumer< String > startedArray,
            Consumer< String > finishedArray         
   ) {
      this.startedObject = startedObject == null ? DO_NOTHING_CONSUMER : startedObject;
      this.finishedObject = finishedObject == null ? DO_NOTHING_CONSUMER : finishedObject;
      this.startedArray = startedArray == null ? DO_NOTHING_CONSUMER : startedArray;
      this.finishedArray = finishedArray == null ? DO_NOTHING_CONSUMER : finishedArray;
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

}//End Class
