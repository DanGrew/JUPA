/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.structure;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * The {@link JsonStructureTree} is responsible for defining the relationships between items
 * within a {@link org.json.JSONObject} in the form of a tree.
 */
class JsonStructureTree {
   
   static final String ROOT = "Root-Node-Representation";
   
   private final Map< String, Set< String > > parentToChildren;
   private final HashMap< String, Function< String, Integer > > arrays;
   private final Map< String, Set< String > > optionals;

   /**
    * Constructs a new {@link JsonStructureTree}.
    */
   JsonStructureTree() {
      this.parentToChildren = new HashMap<>();
      this.arrays = new HashMap<>();
      this.optionals = new HashMap<>();
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
    * Method to add a child relationship to the given parent. Note that arrays can have values
    * or {@link org.json.JSONObject}s therefore 0 or 1 children, no more. This relationship is 
    * optional and is not enforced.
    * @param child the child name to add.
    * @param parent the parent the child is for.
    */
   void addOptionalChild( String child, String parent ) {
      addChild( child, parent );
      Set< String > optionalSet = optionals.get( parent );
      if ( optionalSet == null ) {
         optionalSet = new LinkedHashSet<>();
         optionals.put( parent, optionalSet );
      }
      optionalSet.add( child );
   }//End Method

   /**
    * Method to add an array to the given parent.
    * @param array the key for the array.
    * @param parent the parent to add to.
    * @param arraySizeFunction the {@link Function} used to retrieve the current array size when building.
    */
   void addArray( String array, String parent, Function< String, Integer > arraySizeFunction ) {
      if ( arraySizeFunction == null ) {
         throw new NullPointerException( "Must provide method of determining array size." );
      }
      
      addChild( array, parent );
      arrays.put( array, arraySizeFunction );
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
      Function< String, Integer > arraySizeFunction = arrays.get( child );
      return arraySizeFunction.apply( child );
   }//End Method

   boolean isOptional( String child, String parent ) {
      Set< String > optionalSet = optionals.get( parent );
      if ( optionalSet == null ) {
         return false;
      }
      
      return optionalSet.contains( child );
   }//End Method

}//End Class
