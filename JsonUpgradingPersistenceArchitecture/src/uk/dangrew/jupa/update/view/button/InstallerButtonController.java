/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.view.button;

import java.io.IOException;

import uk.dangrew.jupa.update.launch.SystemHandover;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.stream.ThreadedFileStreamer;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.javafx.platform.PlatformLifecycle;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * The {@link InstallerButtonController} is responsible for providing an interface between the
 * ui and the state of the {@link InstallerButton}.
 */
class InstallerButtonController {

   private final InstallerButton subject;
   private final ThreadedFileStreamer streamer;
   private final SystemHandover handover;
   
   /**
    * Constructs a new {@link InstallerButtonController}.
    * @param subject the {@link InstallerButton} subject of the calls.
    * @param handover the {@link SystemHandover} for managing the handover.
    */
   InstallerButtonController( InstallerButton subject, SystemHandover handover ) {
      this( subject, new ThreadedFileStreamer(), handover );
   }//End Constructor
   
   /**
    * Constructs a new {@link InstallerButtonController}.
    * @param subject the {@link InstallerButton} subject of the calls.
    * @param streamer the {@link ThreadedFileStreamer} for streamer files on a separate thread.
    * @param handover the {@link SystemHandover} for managing the handover.
    */
   InstallerButtonController( InstallerButton subject, ThreadedFileStreamer streamer, SystemHandover handover ) {
      this.subject = subject;
      this.streamer = streamer;
      this.streamer.setOnCompletion( this::streamFinished );
      this.handover = handover;
   }//End Constructor
   
   /**
    * Method to start the update process.
    */
   void startUpdate() {
      subject.downloadInitiated();
   }//End Method
   
   /**
    * Method to check for a file clash on the given {@link ReleaseDefinition}.
    * @param release the {@link ReleaseDefinition} to check for.
    */
   void checkForArtifactFileClash( ReleaseDefinition release ){
      JarProtocol artifactProtocol = handover.fetchJarLocation( release );
      if ( artifactProtocol.getSource().exists() ) {
         subject.fileExists();
      } else {
         startDownload( release );
      }
   }//End Method

   /**
    * Method to start the download of the given {@link ReleaseDefinition}.
    * @param release the {@link ReleaseDefinition} to download.
    */
   void startDownload( ReleaseDefinition release ) {
      subject.downloadConfirmed( streamer.getSourceForProgress() );
      
      JarProtocol artifactProtocol = handover.fetchJarLocation( release );
      try {
         streamer.streamFile( 
                  release.getDownloadLocation(), 
                  artifactProtocol
         );
      } catch ( IOException exception ) {
         subject.downloadFailed();
      }
   }//End Method

   /**
    * Method to cancel the download.
    */
   void cancelDownload() {
      subject.downloadCancelled();
   }//End Method

   /**
    * Method to perform the launch of the new version.
    * @param release the {@link ReleaseDefinition} to launch.
    */
   void launchConfirmed( ReleaseDefinition release ) {
      subject.launchConfirmed();

      boolean launched = handover.launch( release );
      if ( launched ) {
         handover.shutdown();
         PlatformLifecycle.shutdown();
      } else {
         subject.launchFailed();
      }
   }//End Method

   /**
    * Method to cancel the launch of the new version.
    */
   void launchCancelled() {
      subject.launchCancelled();
   }//End Method
   
   /**
    * Method to handle the next step of the process following the download.
    */
   void streamFinished(){
      if ( streamer.wasSuccessful() ) {
         JavaFxThreading.runAndWait( subject::downloadFinished );
      } else {
         JavaFxThreading.runAndWait( subject::downloadFailed );
      }
   }//End Method
   
   /**
    * Method to determine whether the given {@link SystemHandover} is associated.
    * @param handover the {@link SystemHandover} in question.
    * @return true if associated.
    */
   boolean hasSystemHandover( SystemHandover handover ) {
      return this.handover == handover;
   }//End Method

}//End Class
