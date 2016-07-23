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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * The {@link ConfirmationPane} provides a basic yes no pane that can be nested
 * inside another element, maybe replacing such element temporarily.
 */
public class ConfirmationPane extends GridPane {
   
   static final String YES_TEXT = "Yes";
   static final String NO_TEXT = "No";
   
   private final Label confirmation;
   private final Button yes;
   private final Button no;
   
   /**
    * Constructs a new {@link ConfirmationPane}.
    * @param message the message to display, generally providing a question.
    */
   public ConfirmationPane( String message ) {
      confirmation = new Label( message );
      confirmation.setWrapText( true );
      add( confirmation, 0, 0 );
      GridPane.setColumnSpan( confirmation, 2 );
      
      yes = new Button( YES_TEXT );
      yes.setMaxWidth( Double.MAX_VALUE );
      add( yes, 0, 1 );
      
      no = new Button( NO_TEXT );
      no.setMaxWidth( Double.MAX_VALUE );
      add( no, 1, 1 );
      
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth( 50 );
      getColumnConstraints().addAll( column, column );
      
      RowConstraints row = new RowConstraints();
      row.setPercentHeight( 50 );
      getRowConstraints().addAll( row, row );
   }//End Constructor
   
   /**
    * Setter for the action response for the yes button.
    * @param handler the {@link EventHandler}.
    */
   protected void setYesAction( EventHandler< ActionEvent > handler ) {
      yes.setOnAction( handler );
   }//End Method

   /**
    * Setter for the action response for the no button.
    * @param handler the {@link EventHandler}.
    */
   protected void setNoAction( EventHandler< ActionEvent > handler ) {
      no.setOnAction( handler );
   }//End Method
   
   Label confirmation(){
      return confirmation;
   }//End Method
   
   Button yesButton(){
      return yes;
   }//End Method
   
   Button noButton(){
      return no;
   }//End Method
}//End Class
