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
import uk.dangrew.jupa.update.launch.SystemHandover;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.sd.core.source.Source;

/**
 * The {@link InstallerButton} is responsible for displaying the state of the current download.
 * Initially displaying the {@link ReleaseDefinition} it changes based on the users input.
 */
public class InstallerButton extends BorderPane {
   
   static final double MIN_HEIGHT = 100;
   static final String LAUNCHING_LABEL = "Your downloaded version will launch when this "
            + "version has shutdown. Please be patient.";
   static final String FAILED_LAUNCH_LABEL = "The new version failed to start. This version will "
            + "continue to function. Please manually launch the new version.";
   
   private final ReleaseDefinition release;
   private final InstallerButtonController controller;
   
   /**
    * Constructs a new {@link InstallerButton}.
    * @param release the {@link ReleaseDefinition} associated.
    * @param handover the {@link SystemHandover} for managing the handover.
    */
   public InstallerButton( ReleaseDefinition release, SystemHandover handover ) {
      this.release = release;
      this.controller = new InstallerButtonController( this, handover );
      
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
      setCenter( new LaunchConfirmation( controller, release ) );
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
    * Method to indicate that the launch of the new version has been cancelled.
    */
   void launchCancelled(){
      setCenter( new ReleaseButton( controller, release ) );
   }//End Method
   
   /**
    * Method to indicate that the launch of the new version failed.
    */
   void launchFailed() {
      Label launchingLabel = new Label( FAILED_LAUNCH_LABEL );
      launchingLabel.setWrapText( true );
               
      setCenter( launchingLabel );
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
    * Method to determine whether the given {@link SystemHandover} is associated.
    * @param handover the {@link SystemHandover} in question.
    * @return true if associated.
    */
   public boolean hasSystemHandover( SystemHandover handover ) {
      return controller.hasSystemHandover( handover );
   }//End Method
   
   InstallerButtonController controller(){
      return controller;
   }//End Method

}//End Class
