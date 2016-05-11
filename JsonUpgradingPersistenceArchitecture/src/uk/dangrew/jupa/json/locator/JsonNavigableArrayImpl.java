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
   @Override public Object navigate( Object object ) {
      if ( object instanceof JSONArray ) {
         JSONArray array = ( JSONArray )object;
         return array.opt( index );
      }
      
      if ( object instanceof JSONObject ) {
         throw new IllegalArgumentException( "Json Object found where Json Array expected." );
      } else {
         throw new IllegalArgumentException( "Unknown Object found where Json Array expected." );
      }
   }//End Method

}//End Class
