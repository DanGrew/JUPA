/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.locator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * {@link JsonNavigableObjectImpl} provides a {@link JsonNavigable} that navigates through
 * a an object following a key to a value.
 */
class JsonNavigableObjectImpl implements JsonNavigable {

   private final String key;
   
   /**
    * Constructs a new {@link JsonNavigableObjectImpl}.
    * @param key the key to navigate to the value of.
    */
   JsonNavigableObjectImpl( String key ) {
      this.key = key;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Object navigate( Object object ) {
      if ( object instanceof JSONObject ) {
         JSONObject jsonObject = ( JSONObject )object;
         return jsonObject.opt( key );
      }
      
      if ( object instanceof JSONArray ) {
         throw new IllegalArgumentException( "JsonArray found where JsonObject expected for " + key + "." );
      } else {
         throw new IllegalArgumentException( "Unknown object found where JsonObject expected for " + key + "." );
      }
   }//End Method

}//End Class
