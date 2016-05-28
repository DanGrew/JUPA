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

public interface JsonNavigation {

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