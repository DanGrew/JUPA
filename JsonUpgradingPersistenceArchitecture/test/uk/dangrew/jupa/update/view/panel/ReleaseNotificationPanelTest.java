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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.controlsfx.control.NotificationPane;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.javafx.application.PlatformImpl;

import javafx.event.ActionEvent;
import uk.dangrew.jupa.graphics.launch.TestApplication;
import uk.dangrew.jupa.update.download.NotificationScheduler;
import uk.dangrew.jupa.update.download.ReleaseAvailableTask;
import uk.dangrew.jupa.update.download.ReleasesDownloader;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.stream.ArtifactLocationGenerator;

/**
 * {@link ReleaseNotificationPanel} test.
 */
public class ReleaseNotificationPanelTest {

   private ArtifactLocationGenerator generator;
   private ReleaseNotificationPanel systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      generator = new ArtifactLocationGenerator( getClass() );
      systemUnderTest = new ReleaseNotificationPanel( generator );
   }//End Method
   
   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      ReleasesDownloader downloader = mock( ReleasesDownloader.class );
      ReleaseAvailableTask task = new ReleaseAvailableTask( "1.3.1", downloader, systemUnderTest );
      new NotificationScheduler( task, 3000 );
      
      Thread.sleep( 1000 );
      
      when( downloader.downloadContent() ).thenReturn( 
          "Release, \"1.4.103\"\n"
        + "Download, \"somewhere\"\n"
        + "Description, \"This is the first downloadable for testing purposes.\"\n"
      );
      
      Thread.sleep( 6000 );
      when( downloader.downloadContent() ).thenReturn( 
            "Release, \"1.4.103\"\n"
          + "Download, \"somewhere\"\n"
          + "Description, \"This is the first downloadable for testing purposes.\"\n"
          + "Release, \"1.4.104\"\n"
          + "Date, \"Just Now!\"\n"
          + "Download, \"somewhere\"\n"
          + "Description, \"This is the first downloadable for testing purposes.\"\n"
      );
      
      Thread.sleep( 1000000 );
   }//End Method
   
   private void triggerActionOnInstallButton(){
      PlatformImpl.runAndWait( () -> systemUnderTest.install().handle( new ActionEvent() ) );
   }//End Method
   
   @Test public void summaryShouldInitiallyBeHidden(){
      assertThat( systemUnderTest.summaryStage().isShowing(), is( false ) );
   }//End Method
   
   @Test public void notificationShouldUseDarkTheme(){
      assertThat( systemUnderTest.getStyleClass().contains( NotificationPane.STYLE_CLASS_DARK ), is( true ) );
   }//End Method
   
   @Test public void notificationShouldUseCorrectText(){
      assertThat( systemUnderTest.getText(), is( ReleaseNotificationPanel.NEW_VERSIONS_MESSAGE ) );
   }//End Method
   
   @Test public void installButtonShouldBePresentAndShouldTriggerSummaryPanelToShow(){
      assertThat( systemUnderTest.getActions().contains( systemUnderTest.install() ), is( true ) );
      
      triggerActionOnInstallButton();
      assertThat( systemUnderTest.summaryStage().isShowing(), is( true ) );
   }//End Method
   
   @Test public void installButtonShouldHideNotification(){
      systemUnderTest.show();
      assertThat( systemUnderTest.isShowing(), is( true ) );
      triggerActionOnInstallButton();
      assertThat( systemUnderTest.isShowing(), is( false ) );
   }//End Method

   @Test public void whenReleasesAreAvailableShouldShowNotificationAndClearWhenNoneAreAvailable(){
      List< ReleaseDefinition > releases = new ArrayList<>();
      systemUnderTest.releasesAreNowAvailable( releases );
      
      assertThat( systemUnderTest.isShowing(), is( false ) );
      
      releases.add( new ReleaseDefinition( "a", "b", "c" ) );
      systemUnderTest.releasesAreNowAvailable( releases );
      
      triggerActionOnInstallButton();
      assertThat( systemUnderTest.summaryPanel().hasReleases( releases ), is( true ) );
      assertThat( systemUnderTest.isShowing(), is( false ) );
      
      releases.clear();
      systemUnderTest.releasesAreNowAvailable( releases );
      
      assertThat( systemUnderTest.summaryPanel().hasReleases( releases ), is( true ) );
      assertThat( systemUnderTest.isShowing(), is( false ) );
   }//End Method
   
   @Test public void shouldDisplayCorrectReleasesWhenGiven(){
      List< ReleaseDefinition > releases = new ArrayList<>();
      systemUnderTest.releasesAreNowAvailable( releases );
      
      assertThat( systemUnderTest.summaryStage().getScene().getRoot(), is( systemUnderTest.summaryPanel() ) );
      
      releases.add( new ReleaseDefinition( "a", "b", "c" ) );
      systemUnderTest.releasesAreNowAvailable( releases );
      
      triggerActionOnInstallButton();
      assertThat( systemUnderTest.summaryPanel().hasReleases( releases ), is( true ) );
   }//End Method
   
   @Test public void shouldHideNotificationWhenThereAreNoNewReleases(){
      systemUnderTest.show();
      assertThat( systemUnderTest.isShowing(), is( true ) );
      systemUnderTest.releasesAreNowAvailable( new ArrayList<>() );
      assertThat( systemUnderTest.isShowing(), is( false ) );
   }//End Method
   
   @Test public void shouldHideNotificationWhenThereAreNewReleasesAndSummaryIsShowing(){
      systemUnderTest.show();
      PlatformImpl.runAndWait( () -> {
         systemUnderTest.summaryStage().show();
         systemUnderTest.releasesAreNowAvailable( Arrays.asList( new ReleaseDefinition( "a", "b", "c" ) ) );
      } );
      assertThat( systemUnderTest.isShowing(), is( false ) );
   }//End Method
   
   @Test public void shouldShowNotificationWhenThereAreNewReleasesAndSummaryIsHiding(){
      systemUnderTest.show();
      systemUnderTest.releasesAreNowAvailable( Arrays.asList( new ReleaseDefinition( "a", "b", "c" ) ) );
      assertThat( systemUnderTest.isShowing(), is( true ) );
   }//End Method
   
   @Test public void shouldPassArtifactGeneratorThroughToSummaryPanel(){
      assertThat( systemUnderTest.summaryPanel().artifactLocationGenerator(), is( generator ) );
   }//End Method
}//End Class
