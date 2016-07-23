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
import java.util.List;

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
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.stream.ArtifactLocationGenerator;
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
   
   private final List< ReleaseDefinition > releases;
   private final ArtifactLocationGenerator artifactGenerator;

   /**
    * Constructs a new {@link ReleaseSummaryPanel}.
    * @param artifactGenerator the {@link ArtifactLocationGenerator} to generate locations to store
    * artifacts.
    */
   public ReleaseSummaryPanel( ArtifactLocationGenerator artifactGenerator ) {
      this.releases = new ArrayList<>();
      this.artifactGenerator = artifactGenerator;
      
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
      this.releases.clear();
      this.releases.addAll( releasesToDisplay );
      
      PlatformImpl.runAndWait( () -> {
         releasesContainer.getChildren().clear();
         releases.forEach( release -> {
            InstallerButton button = new InstallerButton( release, artifactGenerator );
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
      return this.releases.equals( checkReleases );
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
   
   ArtifactLocationGenerator artifactLocationGenerator(){
      return artifactGenerator;
   }//End Method
}//End Class
