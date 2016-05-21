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
   @Override public Object navigate( Object parent ) {
      JSONObject extracted = extractJsonObject( parent );
      if ( extracted == null ) {
         return null;
      }
      
      return extracted.opt( key );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void put( Object parent, Object value ) {
      JSONObject extracted = extractJsonObject( parent );
      if ( extracted == null ) {
         return;
      }
      
      extracted.put( key, value );
   }//End Method
   
   /**
    * Method o extract the {@link JSONObject} from the given generic parent.
    * @param parent the {@link Object} that may be a {@link JSONObject}.
    * @return the {@link JSONObject} or null if not one.
    */
   private JSONObject extractJsonObject( Object parent ){
      if ( parent instanceof JSONObject ) {
         return ( JSONObject )parent;
      }
      
      return null;
   }//End Method

}//End Class
