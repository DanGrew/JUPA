/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.controlsfx.control.NotificationPane;

import com.sun.javafx.application.PlatformImpl;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import uk.dangrew.jupa.update.launch.SystemHandover;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.view.button.InstallerButton;

/**
 * The {@link ReleaseSummaryPanel} is responsible for displaying the {@link ReleaseDefinition}s
 * in a column where they can be selected to trigger an install.
 */
public class ReleaseSummaryPanel extends NotificationPane {
   
   static final String DESCRIPTION_TEXT = "Click the version you would like to install and that verison will "
            + "be downloaded and launched. This system will be shutdown.";

   static final String HEADER_TEXT = "New releases of the Jenkins Test Tracker are available below.";

   static final double DEVELOPMENT_MESSAGE_HEIGHT = 100.0;

   static final String DEVELOPMENT_MESSAGE = 
            "I bet you'd like that wouldn't you? Click to install "
            + "another version... well fear not, you will not have to "
            + "wait long - watch this space!";
   
   private final Label developmentMessage;
   private final VBox releasesContainer;
   private final ScrollPane scroller;
   private final GridPane textContent;
   private final BorderPane panelStructure;
   private final Label headerLabel;
   private final Label descriptionLabel;
   
   private final Map< ReleaseDefinition, InstallerButton > releases;
   private final SystemHandover handover;

   /**
    * Constructs a new {@link ReleaseSummaryPanel}.
    * @param handover the {@link SystemHandover} to manage the handover to the new version.
    */
   public ReleaseSummaryPanel( SystemHandover handover ) {
      this.releases = new LinkedHashMap<>();
      this.handover = handover;
      
      this.developmentMessage = new Label( DEVELOPMENT_MESSAGE );
      this.developmentMessage.setWrapText( true );
      this.developmentMessage.setPadding( new Insets( 5 ) );
      this.developmentMessage.setPrefHeight( DEVELOPMENT_MESSAGE_HEIGHT );
      setGraphic( developmentMessage );
      
      getStyleClass().add( NotificationPane.STYLE_CLASS_DARK );
      
      this.releasesContainer = new VBox();
      this.releasesContainer.setPrefWidth( 400 );
      
      this.scroller = new ScrollPane( releasesContainer );
      this.scroller.setHbarPolicy( ScrollBarPolicy.NEVER );
      this.scroller.setVbarPolicy( ScrollBarPolicy.AS_NEEDED );
      
      this.panelStructure = new BorderPane( scroller );
      this.headerLabel = new Label( HEADER_TEXT );
      this.headerLabel.setWrapText( true );
      this.headerLabel.setFont( Font.font( headerLabel.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 13 ) );
      this.descriptionLabel = new Label( DESCRIPTION_TEXT );
      this.descriptionLabel.setWrapText( true );
      
      this.textContent = new GridPane();
      this.textContent.setPadding( new Insets( 10 ) );
      this.textContent.add( headerLabel, 0, 0 );
      this.textContent.add( descriptionLabel, 0, 1 );
      
      this.panelStructure.setTop( this.textContent );
      setContent( this.panelStructure );
   }//End Constructor
   
   /**
    * Method to set the {@link ReleaseDefinition}s to display.
    * @param releases the {@link List} of {@link ReleaseDefinition}s to display.
    */
   public void setReleases( List< ReleaseDefinition > releasesToDisplay ) {
      Map< ReleaseDefinition, InstallerButton > previousButtons = new HashMap<>( this.releases );
      
      this.releases.clear();
      
      PlatformImpl.runAndWait( () -> {
         releasesContainer.getChildren().clear();
         releasesToDisplay.forEach( release -> {
            InstallerButton button = previousButtons.get( release );
            if ( button == null ) {
               button = new InstallerButton( release, handover );
            }
            this.releases.put( release, button );
            releasesContainer.getChildren().add( button );
         } );
      } );
   }//End Method
   
   /**
    * Method to determine whether this {@link ReleaseSummaryPanel} is using the given {@link List} of {@link ReleaseDefinition}s.
    * @param checkReleases the {@link ReleaseDefinition}s expected.
    * @return true if identical.
    */
   public boolean hasReleases( List< ReleaseDefinition > checkReleases ) {
      List< ReleaseDefinition > currentReleases = new ArrayList<>( releases.keySet() );
      return currentReleases.equals( checkReleases );
   }//End Method

   Label developmentMessage(){
      return developmentMessage;
   }//End Method
   
   VBox releaseContainer(){
      return releasesContainer;
   }//End Method
   
   Label headerLabel() {
      return headerLabel;
   }//End Method
   
   Label descriptionLabel() {
      return descriptionLabel;
   }//End Method
   
   GridPane textContent() {
      return textContent;
   }//End Method
   
   ScrollPane scroller() {
      return scroller;
   }//End Method
   
   BorderPane panelStructure() {
      return panelStructure;
   }//End Method
   
   SystemHandover artifactLocationGenerator(){
      return handover;
   }//End Method
}//End Class
