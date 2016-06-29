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

import java.util.List;

import org.controlsfx.control.NotificationPane;

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

   /**
    * Constructs a new {@link ReleaseSummaryPanel}.
    * @param releases the {@link List} of {@link ReleaseDefinition}s to display.
    */
   public ReleaseSummaryPanel( List< ReleaseDefinition > releases ) {
      developmentMessage = new Label( DEVELOPMENT_MESSAGE );
      developmentMessage.setWrapText( true );
      developmentMessage.setPadding( new Insets( 5 ) );
      developmentMessage.setPrefHeight( DEVELOPMENT_MESSAGE_HEIGHT );
      setGraphic( developmentMessage );
      
      getStyleClass().add( NotificationPane.STYLE_CLASS_DARK );
      
      releasesContainer = new VBox();
      releases.forEach( release -> {
         ReleaseButton button = new ReleaseButton( release );
         button.setOnAction( event -> show() );
         releasesContainer.getChildren().add( button );
      } );
      releasesContainer.setPrefSize( 400, 600 );
      
      setContent( releasesContainer );
   }//End Constructor

   Label developmentMessage(){
      return developmentMessage;
   }//End Method
   
   VBox releaseContainer(){
      return releasesContainer;
   }//End Method
   
}//End Class
