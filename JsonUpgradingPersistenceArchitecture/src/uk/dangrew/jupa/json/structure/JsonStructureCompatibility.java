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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The {@link JsonStructureCompatibility} provides a mechanism for verifying that a given
 * {@link JSONObject} matches the {@link JsonStructureTree} associated. Extra keys are permitted
 * but the structure defined ones must be present.
 */
class JsonStructureCompatibility {
   
   private final JsonStructureTree structureTree;

   /**
    * Constructs a new {@link JsonStructureCompatibility}.
    * @param structureTree the {@link JsonStructureTree} to comply with.
    */
   JsonStructureCompatibility( JsonStructureTree structureTree ) {
      if ( structureTree == null ) {
         throw new IllegalArgumentException( "Null structure tree is not acceptable." );
      }
      
      this.structureTree = structureTree;
   }//End Constructor

   /**
    * Method to determine whether the given {@link JSONObject} is compatible with the associated
    * {@link JsonStructureTree}. This is determine by checking the presence of all keys and relationships
    * defined in the {@link JsonStructureTree}.
    * @param object the {@link JSONObject} in question.
    * @return true if compatible (can be parsed), false otherwise. 
    */
   boolean isCompatible( JSONObject object ) {
      return isCompatible( object, structureTree.getRoot() );
   }//End Method
   
   /**
    * Method to determine whether the given {@link Object} is compatible given its associated node name
    * in the {@link JsonStructureTree}.
    * @param object the {@link Object} to check.
    * @param nodeName the node name of the {@link Object} in the {@link JsonStructureTree}.
    */
   private boolean isCompatible( Object object, String nodeName ) {
      if ( structureTree.isArray( nodeName ) ) {
         return processExpectedArray( object, nodeName );
      } else {
         return processExpectedObject( object, nodeName );
      }
   }//End Method
   
   /**
    * Method to process an {@link Object} for an expected {@link JSONArray}.
    * @param object the {@link Object} expected to be a {@link JSONArray}.
    * @param nodeName the node name of the array in the tree.
    * @return true if the {@link JSONArray} is defined according to the {@link JsonStructureTree}, 
    * and all children.
    */
   private boolean processExpectedArray( Object object, String nodeName ) {
      if ( !( object instanceof JSONArray ) ) {
         return false;
      }
      
      Collection< String > rootElements = structureTree.getChildrenOf( nodeName );
      if ( rootElements.isEmpty() ) {
         return true;
      } else if ( rootElements.size() > 1 ) {
         return false;
      }
      
      JSONArray jsonArray = ( JSONArray ) object;
      if ( jsonArray.length() == 0 ) {
         //used to return false, but if there are no elements then it shouldn't be invalid?
         return true;
      }
      
      String onlyChild = rootElements.iterator().next();
      for ( int i = 0; i < jsonArray.length(); i++ ) {
         boolean successful = isCompatible( jsonArray.get( i ), onlyChild );
         if ( !successful ) {
            return false;
         }
      }
      
      return true;
   }//End Method
   
   /**
    * Method to process an {@link Object} that can be either a key value or a nested {@link JSONObject}.
    * @param object the {@link Object} process}.
    * @param nodeName the node name of the array in the tree.
    * @return true if it is a {@link JSONObject} and is compliant (and all children) with the {@link JsonStructureTree},
    * also true if any other object where no children are expected.
    */
   private boolean processExpectedObject( Object object, String nodeName ) {
      Collection< String > rootElements = structureTree.getChildrenOf( nodeName );
      if ( rootElements.isEmpty() ) {
         return true;
      }
      
      if ( !( object instanceof JSONObject ) ) {
         return false;
      }
      
      JSONObject jsonObject = ( JSONObject ) object;
      
      for ( String element : rootElements ) {
         if ( !jsonObject.has( element ) && !structureTree.isOptional( element, nodeName ) ) {
            return false;
         }
      }
      
      for ( String element : rootElements ) {
         if ( !jsonObject.has( element ) ) {
            continue;
         }
         boolean successful = isCompatible( jsonObject.get( element ), element );
         if ( !successful ) {
            return false;
         }
      }
      
      return true;
   }//End Method
   
}//End Class
