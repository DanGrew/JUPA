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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.controlsfx.control.NotificationPane;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javafx.event.ActionEvent;
import uk.dangrew.jupa.graphics.launch.TestApplication;
import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * {@link ReleaseSummaryPanel} test.
 */
public class ReleaseSummaryPanelTest {
   
   private ReleaseDefinition releaseA;
   private ReleaseDefinition releaseB;
   private ReleaseDefinition releaseC;
   private ReleaseSummaryPanel systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      
      releaseA = new ReleaseDefinition( 
               "1.2.34", "anything", 
               "Fixed a bug in a panel where the number wouldn't refresh." 
      );
      releaseB = new ReleaseDefinition( 
               "1.2.35", "anything", 
               "Orientation feature is now available in preferences such that "
               + "when you change it the orientation of the core panel changes." 
      );
      releaseC = new ReleaseDefinition( 
               "1.3.1", "anything", 
               "This is the next major release that provides of very abstract "
               + "changes that should entice you amongst lots of refactoring "
               + "which you will clearly care about." 
      );
      
      systemUnderTest = new ReleaseSummaryPanel( Arrays.asList( releaseA, releaseB, releaseC ) );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 100000 );
   }
   
   @Test public void shouldUseDarkThemeForNotification(){
      assertThat( systemUnderTest.getStyleClass().contains( NotificationPane.STYLE_CLASS_DARK ), is( true ) );
   }//End Method
   
   @Test public void shouldDefineNotificationMessageAndWrapText(){
      assertThat( systemUnderTest.getGraphic(), is( systemUnderTest.developmentMessage() ) );
      assertThat( systemUnderTest.developmentMessage().isWrapText(), is( true ) );
      assertThat( systemUnderTest.developmentMessage().getPrefHeight(), is( ReleaseSummaryPanel.DEVELOPMENT_MESSAGE_HEIGHT ) );
   }//End Method
   
   @Test public void shouldProvideReleaseButtonForEachRelease(){
      assertThat( systemUnderTest.getContent(), is( systemUnderTest.releaseContainer() ) );
      assertThat( systemUnderTest.releaseContainer().getChildren(), hasSize( 3 ) );
      
      assertThat( systemUnderTest.releaseContainer().getChildren().get( 0 ), is( instanceOf( ReleaseButton.class ) ) );
      ReleaseButton firstButton = ( ReleaseButton ) systemUnderTest.releaseContainer().getChildren().get( 0 );
      assertThat( firstButton.version().getText(), is( releaseA.getIdentification() ) );
      
      assertThat( systemUnderTest.releaseContainer().getChildren().get( 1 ), is( instanceOf( ReleaseButton.class ) ) );
      ReleaseButton secondButton = ( ReleaseButton ) systemUnderTest.releaseContainer().getChildren().get( 1 );
      assertThat( secondButton.version().getText(), is( releaseB.getIdentification() ) );
      
      assertThat( systemUnderTest.releaseContainer().getChildren().get( 2 ), is( instanceOf( ReleaseButton.class ) ) );
      ReleaseButton thirdButton = ( ReleaseButton ) systemUnderTest.releaseContainer().getChildren().get( 2 );
      assertThat( thirdButton.version().getText(), is( releaseC.getIdentification() ) );
   }//End Method
   
   @Test public void buttonShouldTriggerNotification(){
      assertThat( systemUnderTest.isShowing(), is( false ) );
      
      ReleaseButton firstButton = ( ReleaseButton ) systemUnderTest.releaseContainer().getChildren().get( 0 );
      firstButton.getOnAction().handle( new ActionEvent() );
      assertThat( systemUnderTest.isShowing(), is( true ) );
      
      systemUnderTest.hide();
      
      ReleaseButton secondButton = ( ReleaseButton ) systemUnderTest.releaseContainer().getChildren().get( 1 );
      secondButton.getOnAction().handle( new ActionEvent() );
      assertThat( systemUnderTest.isShowing(), is( true ) );
      
      systemUnderTest.hide();
      
      ReleaseButton thirdButton = ( ReleaseButton ) systemUnderTest.releaseContainer().getChildren().get( 2 );
      thirdButton.getOnAction().handle( new ActionEvent() );
      assertThat( systemUnderTest.isShowing(), is( true ) );
   }//End Method

}//End Class
