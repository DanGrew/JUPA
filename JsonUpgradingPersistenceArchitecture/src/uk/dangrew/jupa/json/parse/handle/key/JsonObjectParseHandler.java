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
 * The {@link JsonObjectParseHandler} is responsible for providing a {@link JsonKeyParseHandler}
 * that only expects objects to be parsed.
 * @param <HandledTypeT> the type of value being handled.
 */
public class JsonObjectParseHandler< HandledTypeT > extends JsonKeyParseHandler< HandledTypeT > {

   private static final String EXPECTED_OBJECT_ONLY = ": expected object only.";

   /** 
    * Constructs a new {@link JsonObjectParseHandler}.
    * @param startedObject the method to call when an object is started.
    * @param finishedObject the method to call when an object is finished.
    */
   public JsonObjectParseHandler(
            Consumer< String > startedObject,
            Consumer< String > finishedObject
   ) {
      super( null, startedObject, finishedObject, null, null );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handle( String key, HandledTypeT value ) {
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
