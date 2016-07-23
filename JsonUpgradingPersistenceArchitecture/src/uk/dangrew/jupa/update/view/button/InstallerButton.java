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

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.stream.ArtifactLocationGenerator;
import uk.dangrew.sd.core.source.Source;

/**
 * The {@link InstallerButton} is responsible for displaying the state of the current download.
 * Initially displaying the {@link ReleaseDefinition} it changes based on the users input.
 */
public class InstallerButton extends BorderPane {
   
   static final double MIN_HEIGHT = 100;
   static final String LAUNCHING_LABEL = "You must now launch the new release manually. You'll be able to "
            + "do this automatically very soon!";
   
   private final ReleaseDefinition release;
   private final InstallerButtonController controller;
   
   /**
    * Constructs a new {@link InstallerButton}.
    * @param release the {@link ReleaseDefinition} associated.
    * @param artifactGenerator the {@link ArtifactLocationGenerator} for identify the artifact location.
    */
   public InstallerButton( ReleaseDefinition release, ArtifactLocationGenerator artifactGenerator ) {
      this.release = release;
      this.controller = new InstallerButtonController( this, artifactGenerator );
      
      ReleaseButton button = new ReleaseButton( controller, release );
      setCenter( button );
      setMinHeight( MIN_HEIGHT );
   }//End Constructor
   
   /**
    * Method to indicate that the download has been initiated.
    */
   void downloadInitiated(){
      setCenter( new DownloadConfirmation( controller, release ) );
   }//End Method
   
   /**
    * Method to indicate that the download has been confirmed and started.
    */
   void downloadConfirmed( Source progressSource ){
      setCenter( new DownloadProgress( progressSource ) );
   }//End Method
   
   /**
    * Method to indicate that the download has been cancelled.
    */
   void downloadCancelled(){
      setCenter( new ReleaseButton( controller, release ) );
   }//End Method
   
   /**
    * Method to indicate that the download has finished.
    */
   void downloadFinished(){
      setCenter( new LaunchConfirmation( controller ) );
   }//End Method
   
   /**
    * Method to indicate that the launch of the new version has been triggered.
    */
   void launchConfirmed(){
      Label launchingLabel = new Label( LAUNCHING_LABEL );
      launchingLabel.setWrapText( true );
               
      setCenter( launchingLabel );
   }//End Method
   
   /**
    * Method to indicare that the launch of the new version has been cancelled.
    */
   void launchCancelled(){
      setCenter( new ReleaseButton( controller, release ) );
   }//End Method

   /**
    * Method to indicate that the artifact file already exists and should be checked.
    */
   void fileExists() {
      setCenter( new OverwriteConfirmation( controller, release ) );
   }//End Method

   /**
    * Method to indicate that the download attempt has failed and should be handled.
    */
   void downloadFailed() {
      setCenter( new RestartAfterFailureConfirmation( controller, release ) );
   }//End Method
   
   /**
    * Method to determine whether the given {@link ReleaseDefinition} is associated.
    * @param release the {@link ReleaseDefinition} to check.
    * @return true if associated.
    */
   public boolean hasRelease( ReleaseDefinition release ) {
      return this.release == release;
   }//End Method
   
   /**
    * Method to determine whether the given {@link ArtifactLocationGenerator} is associated.
    * @param generator the {@link ArtifactLocationGenerator} in question.
    * @return true if associated.
    */
   public boolean hasArtifactLocationGenerator( ArtifactLocationGenerator generator ) {
      return controller.hasArtifactLocationGenerator( generator );
   }//End Method
   
   InstallerButtonController controller(){
      return controller;
   }//End Method

}//End Class
