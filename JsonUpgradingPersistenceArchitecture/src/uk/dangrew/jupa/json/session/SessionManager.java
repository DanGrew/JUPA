/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.session;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import uk.dangrew.jupa.json.marshall.ModelMarshaller;

/**
 * The {@link SecurityManager} is responsible for triggering a {@link ModelMarshaller}
 * when any associated {@link ObjectProperty} changes. Multiple properties can change
 * and the manager will only trigger the write while there is something new to write, i.e.
 * multiple requests during write will only result in one more write.
 */
public class SessionManager {
   
   private final ModelMarshaller marshaller;
   private final AtomicBoolean running = new AtomicBoolean( true );
   private final BlockingQueue< Object > queue = new ArrayBlockingQueue<>( 1 );
   private final Set< Object > registeredProperties;
   
   /**
    * Constructs a new {@link SecurityManager}.
    * @param marshaller the {@link ModelMarshaller}.
    */
   public SessionManager( ModelMarshaller marshaller ) {
      this.marshaller = marshaller;
      this.registeredProperties = new HashSet<>();
      startSessionWriter();
   }//End Constructor

   /**
    * Method to register an {@link ObservableValue} that, when changes, triggers a new session
    * write.
    * @param property the {@link ObservableValue} to listen to.
    */
   public void triggerWriteOnChange( ObservableValue< ? > property ) {
      if ( registeredProperties.contains( property ) ) {
         return;
      }
      property.addListener( ( source, old, updated ) -> queue.offer( new Object() ) );
      registeredProperties.add( property );
   }//End Method
   
   /**
    * Method to register an {@link ObservableMap} that, when changes, triggers a new session
    * write.
    * @param property the {@link ObservableMap} to listen to.
    */
   public < K, V > void triggerWriteOnChange( ObservableMap< K, V > property ) {
      if ( registeredProperties.contains( property ) ) {
         return;
      }
      property.addListener( ( MapChangeListener.Change< ? extends K, ? extends V > change ) -> queue.offer( new Object() ) );
      registeredProperties.add( property );
   }//End Method
   
   /**
    * Method to register an {@link ObservableList} that, when changes, triggers a new session
    * write.
    * @param property the {@link ObservableList} to listen to.
    */
   public < V > void triggerWriteOnChange( ObservableList< V > property ) {
      if ( registeredProperties.contains( property ) ) {
         return;
      }
      property.addListener( ( Change< ? extends V > c ) -> queue.offer( new Object() ) );
      registeredProperties.add( property );
   }//End Method
   
   /**
    * Method to start the separate {@link Thread} for writing.
    */
   private void startSessionWriter(){
      new Thread( () -> {
         while( running.get() ) {
            try {
               queue.take();
               if ( running.get() ) {
                  marshaller.write();
               }
            } catch ( InterruptedException exception ) {
               exception.printStackTrace();
            }
         }
      } ).start();
   }//End Method
   
   /**
    * Method to stop the writing {@link Thread}
    */
   public void stop(){
      running.set( false );
   }//End Method
}//End Class
