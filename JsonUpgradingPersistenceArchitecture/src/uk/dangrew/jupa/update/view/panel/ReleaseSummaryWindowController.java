/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.view.panel;

import java.util.List;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.dangrew.jupa.update.launch.SystemHandover;
import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * {@link ReleaseSummaryWindowController} provides a controller for a {@link Stage} containing
 * the {@link ReleaseSummaryPanel}, that can be shown and hidden.
 */
public class ReleaseSummaryWindowController {
   
   private Stage summaryStage;
   private final ReleaseSummaryPanel summaryPanel;
   
   /**
    * Constructs a new {@link ReleaseSummaryWindowController}.
    * @param handover the {@link SystemHandover} when launching downloaded version.
    */
   public ReleaseSummaryWindowController( SystemHandover handover ) {
      this.summaryPanel = new ReleaseSummaryPanel( handover );
      PlatformImpl.runAndWait( () -> {
         this.summaryStage = new Stage();
         this.summaryStage.setScene( new Scene( summaryPanel, 400, 600 )  );
         this.summaryStage.setResizable( false );
         this.summaryStage.hide();
      } );
   }//End Constructor

   /**
    * Method to update the {@link ReleaseDefinition}s on the {@link ReleaseSummaryPanel}.
    * @param newReleases the {@link ReleaseDefinition}s to show.
    */
   public void releasesAreNowAvailable( List< ReleaseDefinition > newReleases ) {
      summaryPanel.setReleases( newReleases );
      show();
   }// End Method
   
   /**
    * Method to show the summary panel.
    */
   public void show(){
      PlatformImpl.runAndWait( summaryStage::show );
   }//End Method
   
   /**
    * Method to hide the summary panel.
    */
   public void hide(){
      PlatformImpl.runAndWait( summaryStage::hide );
   }//End Method

   Stage summaryStage() {
      return summaryStage;
   }// End Method

   ReleaseSummaryPanel summaryPanel() {
      return summaryPanel;
   }// End Method
}//End Class
