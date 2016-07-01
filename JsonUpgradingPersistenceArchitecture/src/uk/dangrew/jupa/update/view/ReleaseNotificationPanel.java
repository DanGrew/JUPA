/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.view;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.dangrew.jupa.update.model.ReleaseAvailabilityObserver;
import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * The {@link ReleaseNotificationPanel} is a wrapper that provides a basic notification of {@link ReleaseDefinition}s
 * being made available. To nest something inside, simple call {@link #setContent(javafx.scene.Node)}.
 */
public class ReleaseNotificationPanel extends NotificationPane implements ReleaseAvailabilityObserver {

   static final String INSTALL_BUTTON_TEXT = "Install";
   static final String NEW_VERSIONS_MESSAGE = "New versions of JTT are available. Click here to install...";
   
   private Stage summaryStage;
   private final ReleaseSummaryPanel summaryPanel;
   private final Action installButton;
   private List< ReleaseDefinition > releases;
   
   /**
    * Constructs a new {@link ReleaseNotificationPanel}.
    */
   public ReleaseNotificationPanel() {
      this.releases = new ArrayList<>();
      this.summaryPanel = new ReleaseSummaryPanel();
      PlatformImpl.runAndWait( () -> {
         this.summaryStage = new Stage();
         summaryStage.setScene( new Scene( summaryPanel )  );
         this.summaryStage.hide();
      } );
      
      getStyleClass().add( NotificationPane.STYLE_CLASS_DARK );
      
      setText( NEW_VERSIONS_MESSAGE );
      installButton = new Action( INSTALL_BUTTON_TEXT, event -> {
            summaryStage.show();
            hide();
      } );
      getActions().add( installButton );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void releasesAreNowAvailable( List< ReleaseDefinition > newReleases ) {
      releases.clear();
      releases.addAll( newReleases );
      
      summaryPanel.setReleases( releases );
      if ( releases.isEmpty() ) {
         hide();
      } else if ( !summaryStage.isShowing() ){
         show();
      } else {
         hide();
      }
   }//End Method
   
   Stage summaryStage(){
      return summaryStage;
   }//End Method
   
   ReleaseSummaryPanel summaryPanel(){
      return summaryPanel;
   }//End Method
   
   Action install(){
      return installButton;
   }//End Method

}//End Class
