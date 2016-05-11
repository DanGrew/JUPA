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

import org.json.JSONObject;

/**
 * The {@link JsonKeyLocator} is responsible for creating a path to a key
 * in a {@link JSONObject} with a builder style interface that allows modification
 * and retrieval of the value associated with the key.
 */
public class JsonKeyLocator {

   private String key;
   private List< String > children;
   
   /**
    * Constructs a new {@link JsonKeyLocator}.
    */
   public JsonKeyLocator() {
      children = new ArrayList<>();
   }//End Constructor
   
   /**
    * Method to specify the key value to look for in the {@link JSONObject} structure.
    * Note that this can be reset but it is not recommended - set only once.
    * @param key the {@link String} key, must not be null or empty.
    */
   public void key( String key ) {
      verifyKey( key );
      this.key = key;
   }//End Method
   
   /**
    * Method to append a child to the path. In this case the child is specifically another
    * {@link JSONObject}.
    * @param child the key associated with the child.
    * @return this {@link JsonKeyLocator}.
    */
   public JsonKeyLocator child( String child ) {
      children.add( child );
      return this;
   }//End Method
   
   /**
    * Method to navigate through children to the leaf of the {@link JSONObject} tree.
    * @param object the {@link JSONObject} to navigate through.
    * @return the {@link JSONObject} and the end of the path, or null if can't be found.
    */
   private JSONObject navigate( JSONObject object ) {
      JSONObject subject = object;
      
      for ( String child : children ) {
         subject = subject.optJSONObject( child );
         if ( subject == null ) {
            return null;
         }
      }
      return subject;
   }//End Method

   /**
    * Method to find the value of the key given the path configured. Note that the key must be
    * set for this operation to work.
    * @param object the {@link JSONObject} to look in, must not be null.
    * @return the {@link Object} found, can be null if not found for path not found.
    */
   public Object find( JSONObject object ) {
      verifyInput( object );
      verifyKeyNotNull();
      
      JSONObject subject = navigate( object );
      if ( subject == null ) {
         return null;
      }
      
      return subject.opt( key );
   }//End Method
   
   /**
    * Method to put the value of the associated key in the {@link JSONObject} at the end of the 
    * configured path.
    * @param jsonObject the {@link JSONObject} root, must not be null.
    * @param value the value to put. Can be null to remove the key. Can be different data type.
    */
   public void put( JSONObject jsonObject, Object value ) {
      verifyInput( jsonObject );
      verifyKeyNotNull();
      
      JSONObject subject = navigate( jsonObject );
      if ( subject == null ) {
         return;
      }
      
      subject.put( key, value );
   }//End Method
   
   /**
    * Method to verify that the given input is suitable for locating.
    * @param input the {@link JSONObject} to locate a key in.
    */
   private void verifyInput( JSONObject input ) {
      if ( input == null ) {
         throw new IllegalArgumentException( "Operation failed: null input." );
      }
   }//End Method
   
   /**
    * Method to verify that the associated key is not null.
    */
   private void verifyKeyNotNull(){
      if ( key == null ) {
         throw new IllegalStateException( "Operation failed: key has not been set." );
      }  
   }//End Method
   
   /**
    * Method to verify that the key is valid.
    * @param key the key to verify.
    */
   private void verifyKey( String key ) {
      if ( key == null ) {
         throw new IllegalArgumentException( "Key is null which is not permitted." );
      } else if ( key.trim().length() == 0 ) {
         throw new IllegalArgumentException( "Key is empty which is not permitted." );
      }
   }//End Method

}//End Class
