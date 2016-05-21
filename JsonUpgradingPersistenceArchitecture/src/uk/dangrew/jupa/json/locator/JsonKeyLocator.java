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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.json.JSONObject;

/**
 * The {@link JsonKeyLocator} is responsible for creating a path to a key
 * in a {@link JSONObject} with a builder style interface that allows modification
 * and retrieval of the value associated with the key.
 */
public class JsonKeyLocator {

   static final BiFunction< JsonNavigable, Object, Object > NAVIGATE = ( navigable, parent ) -> navigable.navigate( parent );
   
   private List< JsonNavigable > path;
   
   /**
    * Constructs a new {@link JsonKeyLocator}.
    */
   public JsonKeyLocator() {
      path = new ArrayList<>();
   }//End Constructor
   
   /**
    * Method to append a child to the path. This should be used when the child is a mapping of key to 
    * value, i.e. arrays are not appropriate.
    * @param child the key associated with the child.
    * @return this {@link JsonKeyLocator}.
    */
   public JsonKeyLocator child( String child ) {
      path.add( new JsonNavigableObjectImpl( child ) );
      return this;
   }//End Method
   
   /**
    * Method to append an element of an array. This should only be used when the path leads to a
    * {@link org.json.JSONArray}.
    * @param arrayIndex the array index of the object to append.
    * @return the {@link JsonKeyLocator}.
    */
   public JsonKeyLocator array( int arrayIndex ) {
      path.add( new JsonNavigableArrayImpl( arrayIndex ) );
      return this;
   }//End Method
   
   /**
    * Method to find the value of the key given the path configured. Note that the key must be
    * set for this operation to work.
    * @param object the root of the json structure to look in, must not be null.
    * @return the {@link Object} found, can be null if not found for path not found.
    */
   public Object find( Object object ) {
      return new JsonPathNavigator( object, path, NAVIGATE, NAVIGATE ).navigate();
   }//End Method
   
   /**
    * Method to put the value of the associated key in the {@link JSONObject} at the end of the 
    * configured path.
    * @param object the root of the json structure, must not be null.
    * @param value the value to put. Can be null to remove the key. Can be different data type.
    */
   public void put( Object object, Object value ) {
      new JsonPathNavigator( object, path, NAVIGATE, new JsonValueSetterFunction( value ) ).navigate();
   }//End Method

}//End Class
