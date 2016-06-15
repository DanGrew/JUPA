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

import java.util.function.Function;

import org.json.JSONObject;

/**
 * The {@link JsonStructure} provides an object that can be used to construct
 * a json structure, and then populate it with placeholder values. The intension is
 * this structure is then read in and populated using the same trigger based mechanism
 * as the {@link uk.dangrew.jupa.json.parse.JsonParser}.
 */
public class JsonStructure {
   
   private JsonStructureTree tree;
   private JsonStructureBuilder builder;
   
   /**
    * Constructs a new {@link JsonStructure}.
    */
   public JsonStructure() {
      tree = new JsonStructureTree();
      builder = new JsonStructureBuilder( tree );
   }//End Constructor

   /**
    * Constructs a new {@link JsonStructure}.
    * @param tree the {@link JsonStructureTree} to use.
    * @param builder the {@link JsonStructureBuilder} to use.
    */
   JsonStructure( JsonStructureTree tree, JsonStructureBuilder builder ) {
      this.tree = tree;
      this.builder = builder;
   }//End Constructor

   /**
    * Method to get the root of the structure.
    * @return the {@link String} root.
    */
   public String root() {
      return tree.getRoot();
   }//End Method
   
   /**
    * Method to get the placeholder used when built.
    * @return the placeholder {@link Object}.
    */
   public Object placeholder(){
      return builder.getPlaceholder();
   }//End Method

   /**
    * Method to record a child relationship to a parent, specifically a {@link JSONObject}
    * child within the parent with the given key.
    * @param child the unique id for the child, used to refer to throughout construction.
    * @param parent the unique id for the parent, used to refer to throughout construction.
    */
   public void child( String child, String parent ) {
      tree.addChild( child, parent );
   }//End Method

   /**
    * Method to record a value relationship to a parent, specifically a value rather than child
    * within the parent with the given key.
    * @param value the unique id for the vale, used to refer to throughout construction.
    * @param parent the unique id for the parent, used to refer to throughout construction.
    */
   public void value( String value, String parent ) {
      tree.addChild( value, parent );
   }//End Method
   
   /**
    * Method to record an array relationship to a parent, specifically a {@link org.json.JSONArray}
    * child within the parent with the given key.
    * @param array the unique id for the array, used to refer to throughout construction.
    * @param parent the unique id for the parent, used to refer to throughout construction.
    * @param arraySizeFunction the {@link Function} used to retrieve the current array size when building.
    */
   public void array( String array, String parent, Function< String, Integer > arraySizeFunction ) {
      tree.addArray( array, parent, arraySizeFunction );
   }//End Method
   
   /**
    * Method to build the structure into the given {@link JSONObject}.
    * @param jsonObject the {@link JSONObject} to build into.
    */
   public void build( JSONObject jsonObject ) {
      builder.build( jsonObject );
   }//End Method

}//End Class
