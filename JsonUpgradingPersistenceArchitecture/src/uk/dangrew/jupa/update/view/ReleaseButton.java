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

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * The {@link ReleaseButton} provides a {@link Button} that when clicked can handle the
 * installation of the version of software it represents.
 */
public class ReleaseButton extends Button {
   
   static final String DESCRIPTION_LABEL = "Description:";
   static final String DATE_LABEL = "Date:";
   static final String RELEASE_LABEL = "Release:";
   
   private final Label versionLabel;
   private final Label version;
   private final Label dateLabel;
   private final Label date;
   private final Label descriptionLabel;
   private final Label description;
   
   /**
    * Constructs a new {@link ReleaseButton}.
    * @param release the associated {@link ReleaseDefinition}.
    */
   public ReleaseButton( ReleaseDefinition release ) {
      GridPane graphic = new GridPane();
      
      versionLabel = createBoldLabel( RELEASE_LABEL, 13 );
      graphic.add( versionLabel, 0, 0 );
      version = new Label( release.getIdentification() );
      graphic.add( version, 0, 1 );
      
      if ( release.getDate() != null ) {
         dateLabel = createBoldLabel( DATE_LABEL, 13 );
         graphic.add( dateLabel, 0, 2 );
         date = new Label( release.getDate() );
         graphic.add( date, 0, 3 );
      } else {
         dateLabel = null;
         date = null;
      }
      
      descriptionLabel = createBoldLabel( DESCRIPTION_LABEL, 13 );
      graphic.add( descriptionLabel, 0, 4 );
      description = new Label( release.getDescription() );
      description.setWrapText( true );
      graphic.add( description, 0, 5 );
      
      setGraphic( graphic );
      setMaxWidth( Double.MAX_VALUE );
      setWrapText( true );
   }//End Constructor

   /**
    * Method to create a bold {@link Label}.
    * @param title the text in the {@link Label}.
    * @param fontSize the size of the {@link Font}.
    * @return the constructed {@link Label}.
    */
   private Label createBoldLabel( String title, double fontSize ) {
      Label label = new Label( title );
      Font existingFont = label.getFont();
      label.setFont( Font.font( existingFont.getFamily(), FontWeight.BOLD, FontPosture.REGULAR, fontSize ) );
      return label;
   }//End Method
   
   Label versionLabel(){
      return versionLabel;
   }//End Method
   
   Label version(){
      return version;
   }//End Method
   
   Label descriptionLabel(){
      return descriptionLabel;
   }//End Method
   
   Label description(){
      return description;
   }//End Method

   Label dateLabel() {
      return dateLabel;
   }//End Method
   
   Label date() {
      return date;
   }//End Method
}//End Class
