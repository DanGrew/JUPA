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

import uk.dangrew.jupa.json.JsonNavigation;

/**
 * The {@link JsonKeyWriteHandle} provides an interface to be implemented by objects that
 * can handle the retrieval of a value for a key found when parsing a json structure.
 */
public interface JsonKeyWriteHandle extends JsonNavigation {

   /**
    * Method to retrieve the value associated with the given key.
    * @param key the key parsed for.
    * @return the value for the key. If null, it should be ignored.
    */
   public Object retrieve( String key );

   /**
    * Method to retrieve the value associated with the given key at an index in an array.
    * @param key the key parsed for.
    * @param index the index in the array.
    * @return the value for the key. If null, it should be ignored.
    */
   public Object retrieve( String key, int index );
   
}//End Interface
