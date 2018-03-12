/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface JsonNavigation {

   public static Consumer< String > DO_NOTHING_CONSUMER = key -> { /* do nothing */ };
   
   public static Consumer< String > consumeKey( Runnable runnable ) {
      return runnable == null ? DO_NOTHING_CONSUMER : k -> runnable.run();
   }//End Method
   
   public static < TypeT > BiConsumer< String, TypeT > consumeKey( Consumer< TypeT > consumer ) {
      return consumer == null ? ( k, v ) -> {} : ( k, v ) -> consumer.accept( v );
   }//End Method
   
   public static Function< String, Object > consumeKey( Supplier< Object > supplier ) {
      return supplier == null ? k -> null : k -> supplier.get();
   }//End Method
   
   public static < TypeT > BiFunction< String, Integer, TypeT > consumeKey( Function< Integer, TypeT > consumer ) {
      return consumer == null ? ( k, i ) -> null : ( k, i ) -> consumer.apply( i );
   }//End Method
   
   /**
    * Method to handle the start of a {@link JSONObject}.
    * @param key the key started for.
    */
   public void startedObject( String key );

   /**
    * Method to handle the end of a {@link JSONObject}.
    * @param key the key ending for.
    */
   public void finishedObject( String key );

   /**
    * Method to handle the start of a {@link JSONArray}.
    * @param key the key started for.
    */
   public void startedArray( String key );

   /**
    * Method to handle the end of a {@link JSONArray}.
    * @param key the key ending for.
    */
   public void finishedArray( String key );
   
}//End Interface