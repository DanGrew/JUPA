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

import uk.dangrew.jupa.json.JsonNavigation;

/**
 * The {@link JsonKeyParseHandle} provides an interface for defining handlers of
 * values that provides instructions and triggers to another model that handles
 * parsed input.
 * @param <HandledTypeT> the type of value being handled.
 */
public interface JsonKeyParseHandle< HandledTypeT > extends JsonNavigation {
   
   /**
    * Method to handle a value parsed for a key.
    * @param key the key parsed for.
    * @param value the value parsed.
    */
   public void handle( String key, HandledTypeT value );
   
}//End Interface
