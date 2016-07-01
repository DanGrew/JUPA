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
package uk.dangrew.jupa.update.view;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.NotificationPane;

import com.sun.javafx.application.PlatformImpl;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * The {@link ReleaseSummaryPanel} is responsible for displaying the {@link ReleaseDefinition}s
 * in a column where they can be selected to trigger an install.
 */
public class ReleaseSummaryPanel extends NotificationPane {
   
   static final double DEVELOPMENT_MESSAGE_HEIGHT = 100.0;

   static final String DEVELOPMENT_MESSAGE = 
            "I bet you'd like that wouldn't you? Click to install "
            + "another version... well fear not, you will not have to "
            + "wait long - watch this space!";
   
   private final Label developmentMessage;
   private final VBox releasesContainer;
   
   private final List< ReleaseDefinition > releases;

   /**
    * Constructs a new {@link ReleaseSummaryPanel}.
    */
   public ReleaseSummaryPanel() {
      this.releases = new ArrayList<>();
      
      this.developmentMessage = new Label( DEVELOPMENT_MESSAGE );
      this.developmentMessage.setWrapText( true );
      this.developmentMessage.setPadding( new Insets( 5 ) );
      this.developmentMessage.setPrefHeight( DEVELOPMENT_MESSAGE_HEIGHT );
      setGraphic( developmentMessage );
      
      getStyleClass().add( NotificationPane.STYLE_CLASS_DARK );
      
      this.releasesContainer = new VBox();
      this.releasesContainer.setPrefSize( 400, 600 );
      
      setContent( releasesContainer );
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
         releasesToDisplay.forEach( release -> {
            ReleaseButton button = new ReleaseButton( release );
            button.setOnAction( event -> show() );
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
   
}//End Class
