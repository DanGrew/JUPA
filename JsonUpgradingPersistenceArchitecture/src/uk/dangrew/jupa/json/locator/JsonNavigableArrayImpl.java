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
 * {@link JsonNavigableArrayImpl} provides an implementation for navigating to an index in
 * a {@link JSONArray}.
 */
class JsonNavigableArrayImpl implements JsonNavigable {

   private final int index;
   
   /**
    * Constructus a new {@link JsonNavigableArrayImpl}.
    * @param index the index to navigate to.
    */
   JsonNavigableArrayImpl( int index ) {
      this.index = index;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public Object navigate( Object parent ) {
      JSONArray extracted = extractArray( parent );
      if ( extracted == null ){
         return null;
      }
      return extracted.opt( index );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void put( Object parent, Object value ) {
      JSONArray extracted = extractArray( parent );
      if ( extracted == null ) {
         return;
      }
      extracted.put( index, value );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Object generateStructure() {
      return new JSONArray();
   }//End Method
   
   /**
    * Method o extract the {@link JSONArray} from the given generic parent.
    * @param parent the {@link Object} that may be a {@link JSONArray}.
    * @return the {@link JSONArray} or null if not one.
    */
   private JSONArray extractArray( Object parent ) {
      if ( parent instanceof JSONArray ) {
         return ( JSONArray )parent;
      }
      return null;
   }//End Method

}//End Class
