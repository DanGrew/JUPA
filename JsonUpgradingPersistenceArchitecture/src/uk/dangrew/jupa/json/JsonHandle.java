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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * {@link JsonHandle} provides a functional interface for defining how a key
 * should be handled when found by the {@link JsonParser}.
 */
public interface JsonHandle {

   /**
    * Method to handle the given key when found and the object found associated with it.
    * @param key the key found.
    * @param value the value found for the key.
    */
   public void handle( String key, JSONObject object );
   
   /**
    * Method to handle the given key when found and the {@link JSONArray} associated with it.
    * @param key the key found.
    * @param object the {@link JSONArray} for the key.
    * @param index the index to handle.
    */
   public void handle( String key, JSONArray object, int index );
   
   /**
    * Method to handle the start of a {@link JSONObject}.
    * @param key the key started for.
    */
   public void startedObject( String key );

   /**
    * Method to handle the end of a {@link JSONObject}.
    * @param key the key ending for.
    */
   public void finishedObject( String key );
   
   /**
    * Method to handle the start of a {@link JSONArray}.
    * @param key the key started for.
    */
   public void startedArray( String key );
   
   /**
    * Method to handle the end of a {@link JSONArray}.
    * @param key the key ending for.
    */
   public void finishedArray( String key );
   
}//End Interface
