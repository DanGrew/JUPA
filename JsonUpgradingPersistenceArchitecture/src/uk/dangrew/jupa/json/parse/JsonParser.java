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

import uk.dangrew.jupa.json.JsonHandle;
import uk.dangrew.jupa.json.JsonNavigation;

/**
 * The {@link JsonParser} provides a method of parsing a {@link JSONObject} notifying
 * {@link JsonHandle}s whenever keys are encountered.
 */
public class JsonParser {
   
   static final Comparator< String > ALPHABETICAL = ( a, b ) -> a.compareTo( b );
   private Map< String, JsonHandle > handles;
   
   /**
    * Constructs a new {@link JsonParser}.
    */
   public JsonParser() {
      handles = new HashMap<>();
   }//End Constructor

   /**
    * Method to define the {@link JsonHandle} to trigger when the given key has
    * been found.
    * @param key the key to encounter.
    * @param handle the {@link JsonHandle} to trigger.
    */
   public void when( String key, JsonHandle handle ) {
      handles.put( key, handle );
   }//End Method

   /**
    * Method to parse the given {@link JSONObject} according to the {@link #when(String, JsonHandle)}
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
         handleKey( key, jsonObject );
      }
   }//End Method
   
   /**
    * Method to handle a key when found. This will identify the {@link JsonHandle} 
    * and trigger it if appropriate. It will also then navigate along the branches of
    * the given object.
    * @param key the key encountered.
    * @param parent the parent of the key.
    */
   private void handleKey( String key, JSONObject parent ){
      invokeHandleForObject( key, parent );
      
      Object value = parent.get( key );
      
      boolean isJsonObject = value instanceof JSONObject;
      boolean isJsonArray = value instanceof JSONArray;
      
      if ( isJsonObject ) {
         navigateObject( key, ( JSONObject ) value );
      } else if ( isJsonArray ) {
         navigateArray( key, ( JSONArray ) value );
      } else {
         //nothing to navigate - hit value
      }
   }//End Method
   
   /**
    * Method to invoke the handle for the given key.
    * @param key the key to invoke the handle for.
    * @param parent the {@link JSONObject} parent for the handle to extract from.
    */
   private void invokeHandleForObject( String key, JSONObject parent ) {
      if ( !handles.containsKey( key ) ) {
         return;
      }
    
      if ( shouldNotHandleObject( parent.get( key ) ) ) {
         return;
      }
      
      JsonHandle handle = handles.get( key );
      handle.handle( key, parent );
   }//End Method
   
   /**
    * Method to invoke the handle for the given key.
    * @param key the key to invoke the handle for.
    * @param array the {@link JSONObject} parent for the handle to extract from.
    * @param index the index of the item in the array to extract.
    */
   private void invokeHandleForArray( String key, JSONArray array, int index ) {
      if ( !handles.containsKey( key ) ) {
         return;
      }

      if ( shouldNotHandleObject( array.get( index ) ) ) {
         return;
      }
      
      JsonHandle handle = handles.get( key );
      handle.handle( key, array, index );
   }//End Method
   
   /**
    * Method to determine whether to handle the given {@link Object}.
    * @param object the {@link Object} in question.
    * @return true if anything other than a {@link JSONObject} or {@link JSONArray}.
    */
   private boolean shouldNotHandleObject( Object object ) {
      if ( object instanceof JSONArray ) {
         return true;
      }
      
      if ( object instanceof JSONObject ) {
         return true;
      }
      
      return false;
   }//End Method
   
   /**
    * Method to process the starting of an object for the given key.
    * @param key the key in question.
    */
   private void objectStarted( String key ) {
      if ( !handles.containsKey( key ) ) {
         return;
      }
      
      JsonNavigation handle = handles.get( key );
      handle.startedObject( key );
   }//End Method
   
   /**
    * Method to process the finishing of an object for the given key.
    * @param key the key in question.
    */
   private void objectFinished( String key ) {
      if ( !handles.containsKey( key ) ) {
         return;
      }
      
      JsonNavigation handle = handles.get( key );
      handle.finishedObject( key );
   }//End Method
   
   /**
    * Method to process the starting of an array for the given key.
    * @param key the key in question.
    */
   private void arrayStarted( String key ) {
      if ( !handles.containsKey( key ) ) {
         return;
      }
      
      JsonNavigation handle = handles.get( key );
      handle.startedArray( key );
   }//End Method
   
   /**
    * Method to process the finishing of an array for the given key.
    * @param key the key in question.
    */
   private void arrayFinised( String key ) {
      if ( !handles.containsKey( key ) ) {
         return;
      }
      
      JsonNavigation handle = handles.get( key );
      handle.finishedArray( key );
   }//End Method
   
   /**
    * Method to navigate through the given {@link JSONObject} to the next set of keys.
    * @param key the key the object is navigating from.
    * @param jsonObject the {@link JSONObject} to navigate through.
    */
   private void navigateObject( String key, JSONObject jsonObject ) {
      objectStarted( key );
      parse( jsonObject );
      objectFinished( key );
   }//End Method
   
   /**
    * Method to navigate along a {@link JSONArray} to the next set of keys for each element
    * in the {@link JSONArray}.
    * @param key the key associated with the {@link JSONArray}.
    * @param jsonArray the {@link JSONArray} navigate through.
    */
   private void navigateArray( String key, JSONArray jsonArray ) {
      arrayStarted( key );
      for ( int i = 0; i < jsonArray.length(); i++ ) {
         invokeHandleForArray( key, jsonArray, i );
         
         Object arrayObject = jsonArray.get( i );
         if ( arrayObject instanceof JSONArray ) {
            navigateArray( key, ( JSONArray ) arrayObject );
            
         } else if ( arrayObject instanceof JSONObject ) {
            navigateObject( key, ( JSONObject )arrayObject );
         }
      }
      arrayFinised( key );
   }//End Method

   /**
    * Method to intercept the existing {@link JsonHandle} if one exists. This will call the given
    * before the existing for each type of handling. If there is nothing to intercept, this becomes
    * a {@link #when(String, JsonHandle)} call.
    * @param key the key to intercept.
    * @param interceptor the {@link JsonHandle} to intercept with.
    */
   public void intercept( String key, JsonHandle interceptor ) {
      if ( !handles.containsKey( key ) ) {
         when( key, interceptor );
         return;
      }
      
      JsonHandle existing = handles.get( key );
      JsonHandle interceptHandler = new JsonHandleInterceptor( existing, interceptor );
      when( key, interceptHandler );
   }//End Method

}//End Class
