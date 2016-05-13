/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse;

/**
 * {@link JsonParseHandle} provides a functional interface for defining how a key
 * should be handled when found by the {@link JsonParser}.
 */
@FunctionalInterface 
public interface JsonParseHandle {

   /**
    * Method to handle the given key when found and the object found associated with it.
    * @param key the key found.
    * @param value the value found for the key, null if another {@link org.json.JSONObject}
    * or {@link org.json.JSONArray}.
    */
   public void handle( String key, Object value );
}//End Interface
