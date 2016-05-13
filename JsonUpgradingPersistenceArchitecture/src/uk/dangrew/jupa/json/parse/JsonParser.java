/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The {@link JsonParser} provides a method of parsing a {@link JSONObject} notifying
 * {@link JsonParseHandle}s whenever keys are encountered.
 */
public class JsonParser {
   
   static final Comparator< String > ALPHABETICAL = ( a, b ) -> a.compareTo( b );
   private Map< String, JsonParseHandle > handles;
   
   /**
    * Constructs a new {@link JsonParser}.
    */
   public JsonParser() {
      handles = new HashMap<>();
   }//End Constructor

   /**
    * Method to define the {@link JsonParseHandle} to trigger when the given key has
    * been found.
    * @param key the key to encounter.
    * @param handle the {@link JsonParseHandle} to trigger.
    */
   public void when( String key, JsonParseHandle handle ) {
      handles.put( key, handle );
   }//End Method

   /**
    * Method to parse the given {@link JSONObject} according to the {@link #when(String, JsonParseHandle)}
    * instructions provided.
    * @param jsonObject the {@link JSONObject} to parse.
    */
   public void parse( JSONObject jsonObject ) {
      if ( jsonObject == null ) {
         throw new IllegalArgumentException( "Cannot parse null JSONObject." );
      }
      
      List< String > keys = new ArrayList<>( jsonObject.keySet() );
      keys.sort( ALPHABETICAL );
      
      for ( String key : keys ) {
         Object value = jsonObject.get( key );
         
         handleFoundKey( key, value );
      }
   }//End Method
   
   /**
    * Method to handle a key when found. This will identify the {@link JsonParseHandle} 
    * and trigger it if appropriate. It will also then navigate along the branches of
    * the given object.
    * @param key the key encountered.
    * @param value the object associated with the key.
    */
   private void handleFoundKey( String key, Object value ){
      if ( !handles.containsKey( key ) ) {
         return;
      }
    
      JsonParseHandle handle = handles.get( key );
      
      boolean isJsonObject = value instanceof JSONObject;
      boolean isJsonArray = value instanceof JSONArray;
      
      if ( isJsonObject ) {
         handle.handle( key, null );
         navigateObject( ( JSONObject ) value );
      } else if ( isJsonArray ) {
         navigateArray( key, ( JSONArray ) value );
      } else {
         handle.handle( key, value );
      }
   }//End Method
   
   /**
    * Method to navigate through the given {@link JSONObject} to the next set of keys.
    * @param jsonObject the {@link JSONObject} to navigate through.
    */
   private void navigateObject( JSONObject jsonObject ) {
      parse( jsonObject );
   }//End Method
   
   /**
    * Method to navigate along a {@link JSONArray} to the next set of keys for each element
    * in the {@link JSONArray}.
    * @param key the key associated with the {@link JSONArray}.
    * @param jsonArray the {@link JSONArray} navigate through.
    */
   private void navigateArray( String key, JSONArray jsonArray ) {
      for ( Object arrayObject : jsonArray ) {
         handleFoundKey( key, arrayObject );
      }
   }//End Method

}//End Class
