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
 * The {@link JsonObjectWriteHandler} is responsible for providing a {@link JsonKeyWriteHandler}
 * that only expects objects to be written.
 */
public class JsonObjectWriteHandler extends JsonKeyWriteHandler {

   private static final String EXPECTED_OBJECT_ONLY = ": expected object only.";

   /** 
    * Constructs a new {@link JsonObjectWriteHandler}.
    * @param startedObject the method to call when an object is started.
    * @param finishedObject the method to call when an object is finished.
    */
   public JsonObjectWriteHandler(
            Consumer< String > startedObject,
            Consumer< String > finishedObject
   ) {
      super( null, null, startedObject, finishedObject, null, null );
   }//End Constructor
   
   /** 
    * Constructs a new {@link JsonObjectWriteHandler}.
    * @param startedObject the method to call when an object is started.
    * @param finishedObject the method to call when an object is finished.
    */
   public JsonObjectWriteHandler(
            Runnable startedObject,
            Runnable finishedObject
   ) {
      super( null, null, startedObject, finishedObject, null, null );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public Object retrieve( String key ) {
      throw new IllegalStateException( key + EXPECTED_OBJECT_ONLY );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Object retrieve( String key, int index ) {
      throw new IllegalStateException( key + EXPECTED_OBJECT_ONLY );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void startedArray( String key ) {
      throw new IllegalStateException( key + EXPECTED_OBJECT_ONLY );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void finishedArray( String key ) {
      throw new IllegalStateException( key + EXPECTED_OBJECT_ONLY );
   }//End Method
}//End Class
