/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.write;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * The {@link JsonStructureTree} is responsible for defining the relationships between items
 * within a {@link org.json.JSONObject} in the form of a tree.
 */
class JsonStructureTree {
   
   static final String ROOT = "Root-Node-Representation";
   
   private final Map< String, Set< String > > parentToChildren;
   private final HashMap< String, Integer > arrays;

   /**
    * Constructs a new {@link JsonStructureTree}.
    */
   JsonStructureTree() {
      this.parentToChildren = new HashMap<>();
      this.arrays = new HashMap<>();
   }//End Constructor

   /**
    * Getter for the root of the tree.
    * @return the {@link String} root.
    */
   String getRoot() {
      return ROOT;
   }//End Method

   /**
    * Method to get the children associated with the given parent.
    * @param parent the parent id to get children for.
    * @return a {@link Collection} of {@link String} children, note that this includes arrays.
    */
   Collection< String > getChildrenOf( String parent ) {
      Collection< String > children = parentToChildren.get( parent );
      if ( children == null ) {
         return new LinkedHashSet<>();
      } else {
         return children;
      }
   }//End Method

   /**
    * Method to add a child relationship to the given parent. Note that arrays can have values
    * or {@link org.json.JSONObject}s therefore 0 or 1 children, no more.
    * @param child the child name to add.
    * @param parent the parent the child is for.
    */
   void addChild( String child, String parent ) {
      Set< String > children = parentToChildren.get( parent );
      if ( children == null ) {
         children = new LinkedHashSet<>();
         parentToChildren.put( parent, children );
      }
      
      if ( isArray( parent ) && !children.isEmpty() ) {
         throw new IllegalStateException( "Cannot add more than one child to an array." );
      }
      
      children.add( child );
   }//End Method

   /**
    * Method to add an array to the given parent.
    * @param array the key for the array.
    * @param parent the parent to add to.
    */
   void addArray( String array, String parent ) {
      addChild( array, parent );
      arrays.put( array, null );
   }//End Method

   /**
    * Method to set the array size for the given array name.
    * @param array the child array name.
    * @param arrayLength the length of the array.
    */
   void setArraySize( String array, int arrayLength ) {
      if ( !isArray( array ) ) {
         throw new IllegalStateException( array + " is not an array in this strucutre." );
      }
      
      arrays.put( array, arrayLength );
   }//End Method

   /**
    * Method to determine whether the given child is an array.
    * @param child the child in question.
    * @return true if it is an array.
    */
   boolean isArray( String child ) {
      return arrays.containsKey( child );
   }//End Method

   /**
    * Getter for the size of the array associated with the given child.
    * @return the array size, or null if not set.
    */
   Integer getArraySize( String child ) {
      return arrays.get( child );
   }//End Method

}//End Class