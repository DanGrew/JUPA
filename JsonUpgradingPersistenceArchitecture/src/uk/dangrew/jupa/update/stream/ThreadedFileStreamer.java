/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.stream;

import java.io.IOException;

import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * The {@link ThreadedFileStreamer} provides a {@link FileStreamer} that runs on a separate {@link Thread}.
 */
public class ThreadedFileStreamer implements FileStreamer {

   private volatile boolean successful = false;
   private final FileStreamer streamer;
   private Runnable onCompletion;
   
   /**
    * Constructs a new {@link ThreadedFileStreamer}.
    */
   public ThreadedFileStreamer() {
      this( new FileStreamerImpl() );
   }//End Constructor
   
   /**
    * Constructs a new {@link ThreadedFileStreamer}.
    * @param streamer the {@link FileStreamer} being threaded.
    */
   ThreadedFileStreamer( FileStreamer streamer ) {
      if ( streamer == null  ) {
         throw new IllegalArgumentException( "Must not provide null parameters." );
      }
      
      this.streamer = streamer;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    * Wraps the associated {@link FileStreamer} in its own {@link Thread}.
    */
   @Override public void streamFile( String downloadLink, JarProtocol fileProtocol ) throws IOException {
      new Thread( () -> {
         try {
            streamer.streamFile( downloadLink, fileProtocol );
            successful = true;
         } catch ( Exception e ) {
            successful = false;
         }
         
         if ( onCompletion != null ) {
            onCompletion.run();
         }
      } ).start();
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Source getSourceForProgress() {
      return streamer.getSourceForProgress();
   }//End Method

   /**
    * Method to determine whether the streaming was successful.
    * @return true if success, false otherwise.
    */
   public boolean wasSuccessful() {
      return successful;
   }//End Method

   /**
    * Method to set the {@link Runnable} to execute on completion. Note that this will
    * run regardless of the result.
    * @param onCompletion the {@link Runnable} to run.
    */
   public void setOnCompletion( Runnable onCompletion ) {
      this.onCompletion = onCompletion;
   }//End Method

}//End Class
