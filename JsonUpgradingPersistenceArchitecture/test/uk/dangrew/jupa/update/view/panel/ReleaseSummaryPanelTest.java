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

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.controlsfx.control.NotificationPane;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import uk.dangrew.jupa.update.launch.BasicSystemHandover;
import uk.dangrew.jupa.update.launch.SystemHandover;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.view.button.InstallerButton;
import uk.dangrew.kode.launch.TestApplication;

/**
 * {@link ReleaseSummaryPanel} test.
 */
public class ReleaseSummaryPanelTest {
   
   private ReleaseDefinition releaseA;
   private ReleaseDefinition releaseB;
   private ReleaseDefinition releaseC;
   private SystemHandover handover;
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
      
      handover = new BasicSystemHandover( getClass() );
      systemUnderTest = new ReleaseSummaryPanel( handover );
      systemUnderTest.setReleases( Arrays.asList( releaseA, releaseB, releaseC ) );
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
   
   @Test public void shouldProvideInstallerButtonForEachRelease(){
      assertThat( systemUnderTest.releaseContainer().getChildren(), hasSize( 3 ) );
      
      assertThat( systemUnderTest.releaseContainer().getChildren().get( 0 ), is( instanceOf( InstallerButton.class ) ) );
      InstallerButton firstButton = ( InstallerButton ) systemUnderTest.releaseContainer().getChildren().get( 0 );
      assertThat( firstButton.hasRelease( releaseA ), is( true ) );
      
      assertThat( systemUnderTest.releaseContainer().getChildren().get( 1 ), is( instanceOf( InstallerButton.class ) ) );
      InstallerButton secondButton = ( InstallerButton ) systemUnderTest.releaseContainer().getChildren().get( 1 );
      assertThat( secondButton.hasRelease( releaseB ), is( true ) );
      
      assertThat( systemUnderTest.releaseContainer().getChildren().get( 2 ), is( instanceOf( InstallerButton.class ) ) );
      InstallerButton thirdButton = ( InstallerButton ) systemUnderTest.releaseContainer().getChildren().get( 2 );
      assertThat( thirdButton.hasRelease( releaseC ), is( true ) );
   }//End Method
   
   @Test public void shouldHaveGivenReleases(){
      assertThat( systemUnderTest.hasReleases( Arrays.asList( releaseA, releaseB, releaseC ) ), is( true ) );
      assertThat( systemUnderTest.hasReleases( Arrays.asList( releaseA, releaseC ) ), is( false ) );
      assertThat( systemUnderTest.hasReleases( Arrays.asList( releaseA, releaseC, releaseB ) ), is( false ) );
   }//End Method
   
   @Test public void shouldReplaceReleasesWhenSetAgain(){
      ReleaseDefinition newRelease = new ReleaseDefinition( "me", "you", "irene" );
      systemUnderTest.setReleases( Arrays.asList( newRelease ) );
      
      assertThat( systemUnderTest.releaseContainer().getChildren(), hasSize( 1 ) );
      
      assertThat( systemUnderTest.releaseContainer().getChildren().get( 0 ), is( instanceOf( InstallerButton.class ) ) );
      InstallerButton firstButton = ( InstallerButton ) systemUnderTest.releaseContainer().getChildren().get( 0 );
      assertThat( firstButton.hasRelease( newRelease ), is( true ) );
   }//End Method
   
   @Test public void releaseContentShouldBeInScrollPane(){
      assertThat( systemUnderTest.scroller().getContent(), is( systemUnderTest.releaseContainer() ) );
      assertThat( systemUnderTest.scroller().getVbarPolicy(), is( ScrollBarPolicy.AS_NEEDED ) );
      assertThat( systemUnderTest.scroller().getHbarPolicy(), is( ScrollBarPolicy.NEVER ) );
   }//End Method
   
   @Test public void shouldProvideTextTitleAboveReleasesContent(){
      assertThat( systemUnderTest.panelStructure().getCenter(), is( systemUnderTest.scroller() ) );
      assertThat( systemUnderTest.panelStructure().getTop(), is( systemUnderTest.textContent() ) );
   }//End Method
   
   @Test public void shouldProvideTextTitleDescribingContent(){
      assertThat( systemUnderTest.textContent().getChildren(), contains( systemUnderTest.headerLabel(), systemUnderTest.descriptionLabel() ) );
      assertThat( systemUnderTest.headerLabel().getText(), is( ReleaseSummaryPanel.HEADER_TEXT ) );
      assertThat( systemUnderTest.headerLabel().isWrapText(), is( true ) );
      assertThat( systemUnderTest.descriptionLabel().getText(), is( ReleaseSummaryPanel.DESCRIPTION_TEXT ) );
      assertThat( systemUnderTest.descriptionLabel().isWrapText(), is( true ) );
   }//End Method
   
   @Test public void contentShouldBeStructure(){
      assertThat( systemUnderTest.getContent(), is( systemUnderTest.panelStructure() ) );
   }//End Method
   
   @Test public void shouldPassGneratorThroughToInstallerButtons(){
      assertThat( systemUnderTest.releaseContainer().getChildren().get( 0 ), is( instanceOf( InstallerButton.class ) ) );
      InstallerButton firstButton = ( InstallerButton ) systemUnderTest.releaseContainer().getChildren().get( 0 );
      assertThat( firstButton.hasSystemHandover( handover ), is( true ) );
   }//End Method

   @Test public void shouldLeaveInstallerButtonsInTactForExistingReleasesWhenSet(){
      InstallerButton firstButton = ( InstallerButton ) systemUnderTest.releaseContainer().getChildren().get( 0 );
      systemUnderTest.setReleases( Arrays.asList( releaseA ) );
      
      InstallerButton nextButton = ( InstallerButton ) systemUnderTest.releaseContainer().getChildren().get( 0 );
      assertThat( nextButton, is( firstButton ) );
      
      systemUnderTest.setReleases( Arrays.asList( releaseB ) );
      InstallerButton finalButton = ( InstallerButton ) systemUnderTest.releaseContainer().getChildren().get( 0 );
      assertThat( finalButton, is( not( firstButton ) ) );
   }//End Method
}//End Class
