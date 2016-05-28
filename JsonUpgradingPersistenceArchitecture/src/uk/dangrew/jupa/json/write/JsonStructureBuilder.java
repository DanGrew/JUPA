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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The {@link JsonStructureBuilder} is responsible for providing a mechanism for building
 * a {@link JsonStructureTree} into a {@link JSONObject}.
 */
class JsonStructureBuilder {
   
   static final Object PLACEHOLDER = new Object();
   
   private final JsonStructureTree tree;
   
   /**
    * Constructs a new {@link JsonStructureBuilder}.
    * @param tree the {@link JsonStructureTree} to build from.
    */
   JsonStructureBuilder( JsonStructureTree tree ) {
      this.tree = tree;
   }//End Constructor
   
   /**
    * Method to build the associated {@link JsonStructureTree} into the given {@link JSONObject}. Note that
    * this does not change the {@link JsonStructureTree}, it is read only, therefore this can be called multiple
    * times with different {@link JSONObject}s.
    * @param object the {@link JSONObject} to build the structre into.
    */
   void build( JSONObject object ){
      String root = tree.getRoot();
      
      Collection< String > rootChildren = tree.getChildrenOf( root );
      rootChildren.forEach( child -> buildChild( object, child ) );
   }//End Method
   
   /**
    * Method to build a child into the given {@link Object}.
    * @param object either the {@link JSONObject} or {@link JSONArray} to put a child in.
    * @param node the name of the node to get children for.
    */
   private void buildChild( Object object, String node ){
      if ( tree.isArray( node ) ) {
         buildJsonArray( object, node );
      } else {
         buildJsonObject( object, node );
      }
   }//End Method
   
   /**
    * Method to build a {@link JSONObject} for the the given node into the given {@link Object}.
    * @param object either a {@link JSONObject} or {@link JSONArray} to build into.
    * @param node the node to build for.
    */
   private void buildJsonObject( Object object, String node ){
      Collection< String > children = tree.getChildrenOf( node );
      if ( children.isEmpty() ) {
         put( object, node, PLACEHOLDER );
         return;
      }
      
      JSONObject childObject = new JSONObject();
      put( object, node, childObject );
      
      for ( String child : children ) {
         buildChild( childObject, child );
      }
   }//End Method
   
   /**
    * Method to build a {@link JSONArray} for the the given node into the given {@link Object}.
    * @param object either a {@link JSONObject} or {@link JSONArray} to build into.
    * @param node the node to build for.
    */
   private void buildJsonArray( Object object, String node ){
      Collection< String > children = tree.getChildrenOf( node );
      
      JSONArray array = new JSONArray();
      put( object, node, array );

      Integer size = tree.getArraySize( node );
      if ( size == null ) {
         throw new NullPointerException( node + " should have array size." );
      }
      
      if ( children.isEmpty() ) {
         for ( int i = 0; i < size; i++ ) {
            array.put( PLACEHOLDER );
         }
         return;
      }
      
      if ( children.size() != 1 ) {
         throw new IllegalStateException( node + " should have at most one child." );
      }
      
      for ( int i = 0; i < size; i++ ) {
         buildChild( array, children.iterator().next() );
      }
   }//End Method
   
   /**
    * Convenience method to put a value into the given json structure {@link Object}.
    * @param jsonStructure either a {@link JSONObject} or {@link JSONArray}.
    * @param node the node name to put.
    * @param value the value to put in.
    */
   private void put( Object jsonStructure, String node, Object value ) {
      if ( jsonStructure instanceof JSONObject ) {
         JSONObject jsonObject = ( JSONObject ) jsonStructure;
         jsonObject.put( node, value );
         
      } else if ( jsonStructure instanceof JSONArray ) {
         JSONArray jsonArray = ( JSONArray ) jsonStructure;
         jsonArray.put( value );
      }
   }//End Method

   /**
    * Getter for the placeholder object.
    * @return the placeholder.
    */
   public Object getPlaceholder() {
      return PLACEHOLDER;
   }//End Method
   
}//End Class
