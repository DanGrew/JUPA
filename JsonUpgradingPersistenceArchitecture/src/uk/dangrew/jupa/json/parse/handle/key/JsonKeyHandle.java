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

/**
 * The {@link JsonKeyHandle} provides an interface for defining handlers of
 * values that provides instructions and triggers to another model that handles
 * parsed input.
 * @param <HandledTypeT> the type of value being handled.
 */
public interface JsonKeyHandle< HandledTypeT > {
   
   /**
    * Method to handle a value parsed for a key.
    * @param key the key parsed for.
    * @param value the value parsed.
    */
   public void handle( String key, HandledTypeT value );
   
   /**
    * Method to indicate that an object has been started with the given key.
    * @param key the key started with.
    */
   public void startedObject( String key );
   
   /**
    * Method to indicate that an object has been finished with the given key.
    * @param key the key finished with.
    */
   public void finishedObject( String key );

   /**
    * Method to indicate that an array has been started with the given key.
    * @param key the key started with.
    */
   public void startedArray( String key );
   
   /**
    * Method to indicate that an array has been finished with the given key.
    * @param key the key finished with.
    */
   public void finishedArray( String key );

}//End Interface
