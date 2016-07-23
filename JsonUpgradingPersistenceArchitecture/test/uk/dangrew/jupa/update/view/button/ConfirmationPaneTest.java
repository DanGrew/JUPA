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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import uk.dangrew.jupa.graphics.launch.TestApplication;

/**
 * {@link ConfirmationPane} test.
 */
public class ConfirmationPaneTest {

   private static final String MESSAGE = "some special confirmation message";
   private ConfirmationPane systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      systemUnderTest = new ConfirmationPane( MESSAGE );
   }//End Method
   
   @Test public void confirmationShouldBeConfiguredAndPresent() {
      assertThat( systemUnderTest.confirmation().getText(), is( MESSAGE ) );
      assertThat( systemUnderTest.confirmation().isWrapText(), is( true ) );
      
      assertThat( GridPane.getRowIndex( systemUnderTest.confirmation() ), is( 0 ) );
      assertThat( GridPane.getColumnIndex( systemUnderTest.confirmation() ), is( 0 ) );
      assertThat( GridPane.getColumnSpan( systemUnderTest.confirmation() ), is( 2 ) );
   }//End Method
   
   @Test public void yesShouldBeConfiguredAndPresent(){
      assertThat( systemUnderTest.yesButton().getText(), is( ConfirmationPane.YES_TEXT ) );
      assertThat( systemUnderTest.yesButton().getMaxWidth(), is( Double.MAX_VALUE ) );
      assertThat( systemUnderTest.getChildren().contains( systemUnderTest.yesButton() ), is( true ) );
   }//End Method

   @Test public void noShouldBeConfiguredAndPresent(){
      assertThat( systemUnderTest.noButton().getText(), is( ConfirmationPane.NO_TEXT ) );
      assertThat( systemUnderTest.noButton().getMaxWidth(), is( Double.MAX_VALUE ) );
      assertThat( systemUnderTest.getChildren().contains( systemUnderTest.noButton() ), is( true ) );
   }//End Method
   
   @Test public void shouldDisplayRowsAndColumnsProportionally(){
      assertThat( systemUnderTest.getColumnConstraints(), hasSize( 2 ) );
      assertThat( systemUnderTest.getColumnConstraints().get( 0 ).getPercentWidth(), is( 50.0 ) );
      assertThat( systemUnderTest.getColumnConstraints().get( 1 ).getPercentWidth(), is( 50.0 ) );
      
      assertThat( systemUnderTest.getRowConstraints(), hasSize( 2 ) );
      assertThat( systemUnderTest.getRowConstraints().get( 0 ).getPercentHeight(), is( 50.0 ) );
      assertThat( systemUnderTest.getRowConstraints().get( 1 ).getPercentHeight(), is( 50.0 ) );
   }//End Method
   
   @Test public void shouldSetListenersOnButtons(){
      EventHandler< ActionEvent > yesAction = event -> {};
      systemUnderTest.setYesAction( yesAction );
      assertThat( systemUnderTest.yesButton().getOnAction(), is( yesAction ) );
      
      EventHandler< ActionEvent > noAction = event -> {};
      systemUnderTest.setNoAction( noAction );
      assertThat( systemUnderTest.noButton().getOnAction(), is( noAction ) );
   }//End Method
}//End Class
