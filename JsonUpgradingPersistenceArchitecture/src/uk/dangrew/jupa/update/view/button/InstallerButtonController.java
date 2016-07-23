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

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.stream.ArtifactLocationGenerator;
import uk.dangrew.jupa.update.stream.ThreadedFileStreamer;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * The {@link InstallerButtonController} is responsible for providing an interface between the
 * ui and the state of the {@link InstallerButton}.
 */
class InstallerButtonController {

   private final InstallerButton subject;
   private final ThreadedFileStreamer streamer;
   private final ArtifactLocationGenerator locationGenerator;
   
   /**
    * Constructs a new {@link InstallerButtonController}.
    * @param subject the {@link InstallerButton} subject of the calls.
    * @param locationGenerator the {@link ArtifactLocationGenerator} for producing {@link JarProtocol}s for {@link ReleaseDefinition}s.
    */
   InstallerButtonController( InstallerButton subject, ArtifactLocationGenerator locationGenerator ) {
      this( subject, new ThreadedFileStreamer(), locationGenerator );
   }//End Constructor
   
   /**
    * Constructs a new {@link InstallerButtonController}.
    * @param subject the {@link InstallerButton} subject of the calls.
    * @param streamer the {@link ThreadedFileStreamer} for streamer files on a separate thread.
    * @param locationGenerator the {@link ArtifactLocationGenerator} for producing {@link JarProtocol}s for {@link ReleaseDefinition}s.
    */
   InstallerButtonController( InstallerButton subject, ThreadedFileStreamer streamer, ArtifactLocationGenerator locationGenerator ) {
      this.subject = subject;
      this.streamer = streamer;
      this.streamer.setOnCompletion( this::streamFinished );
      this.locationGenerator = locationGenerator;
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
      JarProtocol artifactProtocol = locationGenerator.fetchJarLocation( release );
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
      
      JarProtocol artifactProtocol = locationGenerator.fetchJarLocation( release );
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
    */
   void launchConfirmed() {
      subject.launchConfirmed();
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
         PlatformImpl.runAndWait( subject::downloadFinished );
      } else {
         PlatformImpl.runAndWait( subject::downloadFailed );
      }
   }//End Method
   
   /**
    * Method to determine whether the given {@link ArtifactLocationGenerator} is associated.
    * @param generator the {@link ArtifactLocationGenerator} in question.
    * @return true if associated.
    */
   boolean hasArtifactLocationGenerator( ArtifactLocationGenerator generator ) {
      return this.locationGenerator == generator;
   }//End Method

}//End Class
