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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.Label;
import uk.dangrew.jupa.graphics.launch.TestApplication;
import uk.dangrew.jupa.update.launch.BasicSystemHandover;
import uk.dangrew.jupa.update.launch.SystemHandover;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.sd.core.source.Source;

/**
 * {@link InstallerButton} test.
 */
public class InstallerButtonTest {

   private ReleaseDefinition release;
   @Mock private SystemHandover handover;
   private InstallerButton systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      release = new ReleaseDefinition( "a", "b", "c" );
      systemUnderTest = new InstallerButton( release, handover );
      
      systemUnderTest.setCenter( new Label() );
   }//End Method
   
   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new InstallerButton( new ReleaseDefinition( 
               "1.4.119", 
               "https://dl.bintray.com/dangrew/JenkinsTestTracker/JenkinsTestTracker/JenkinsTestTracker/1.4.119/JenkinsTestTracker-1.4.119-runnable.jar", 
               "Testing release" 
      ), new BasicSystemHandover( getClass() ) ) );
      
      Thread.sleep( 1000000 );
   }//End Method
   
   @Test public void shouldStartWithReleaseButtonInCenterWithMinHeight(){
      systemUnderTest = new InstallerButton( release, handover );
      
      assertThat( systemUnderTest.getCenter(), is( instanceOf( ReleaseButton.class ) ) );
      ReleaseButton releaseButton = ( ReleaseButton ) systemUnderTest.getCenter();
      assertThat( releaseButton.hasController( systemUnderTest.controller() ), is( true ) );
      assertThat( releaseButton.hasRelease( release ), is( true ) );
      assertThat( systemUnderTest.getMinHeight(), is( InstallerButton.MIN_HEIGHT ) );
   }//End Method
   
   @Test public void downloadInitiatedShouldDisplayDownloadConfirmation(){
      systemUnderTest.downloadInitiated();
      assertThat( systemUnderTest.getCenter(), is( instanceOf( DownloadConfirmation.class ) ) );
      DownloadConfirmation confirmation = ( DownloadConfirmation ) systemUnderTest.getCenter();
      assertThat( confirmation.hasController( systemUnderTest.controller() ), is( true ) );
      assertThat( confirmation.hasRelease( release ), is( true ) );
   }//End Method
   
   @Test public void downloadConfirmedShouldDisplayDownloadProgress(){
      Source source = mock( Source.class );
      systemUnderTest.downloadConfirmed( source );
      
      assertThat( systemUnderTest.getCenter(), is( instanceOf( DownloadProgress.class ) ) );
      DownloadProgress confirmation = ( DownloadProgress ) systemUnderTest.getCenter();
      assertThat( confirmation.hasSource( source ), is( true ) );
   }//End Method
   
   @Test public void downloadCancelledShouldDisplayReleaseButton(){
      systemUnderTest.downloadCancelled();
      assertThat( systemUnderTest.getCenter(), is( instanceOf( ReleaseButton.class ) ) );
      ReleaseButton button = ( ReleaseButton ) systemUnderTest.getCenter();
      assertThat( button.hasController( systemUnderTest.controller() ), is( true ) );
      assertThat( button.hasRelease( release ), is( true ) );
   }//End Method
   
   @Test public void downloadFinishedShouldDisplayLaunchConfirmation(){
      systemUnderTest.downloadFinished();
      assertThat( systemUnderTest.getCenter(), is( instanceOf( LaunchConfirmation.class ) ) );
      LaunchConfirmation button = ( LaunchConfirmation ) systemUnderTest.getCenter();
      assertThat( button.hasController( systemUnderTest.controller() ), is( true ) );
      assertThat( button.hasRelease( release ), is( true ) );
   }//End Method
   
   @Test public void launchConfirmedShouldDisplayLaunchingLabel(){
      systemUnderTest.launchConfirmed();
      assertThat( systemUnderTest.getCenter(), is( instanceOf( Label.class ) ) );
      Label label = ( Label ) systemUnderTest.getCenter();
      assertThat( label.getText(), is( InstallerButton.LAUNCHING_LABEL ) );
      assertThat( label.isWrapText(), is( true ) );
   }//End Method
   
   @Test public void launchCancelledShouldDisplayReleaseButton(){
      systemUnderTest.launchCancelled();
      assertThat( systemUnderTest.getCenter(), is( instanceOf( ReleaseButton.class ) ) );
      ReleaseButton button = ( ReleaseButton ) systemUnderTest.getCenter();
      assertThat( button.hasController( systemUnderTest.controller() ), is( true ) );
      assertThat( button.hasRelease( release ), is( true ) );
   }//End Method
   
   @Test public void launchFailedShouldDisplayMessage(){
      systemUnderTest.launchFailed();
      assertThat( systemUnderTest.getCenter(), is( instanceOf( Label.class ) ) );
      Label label = ( Label ) systemUnderTest.getCenter();
      assertThat( label.getText(), is( InstallerButton.FAILED_LAUNCH_LABEL ) );
      assertThat( label.isWrapText(), is( true ) );
   }//End Method
   
   @Test public void fileExistsShouldDisplayOverwriteConfirmation(){
      systemUnderTest.fileExists();
      assertThat( systemUnderTest.getCenter(), is( instanceOf( OverwriteConfirmation.class ) ) );
      OverwriteConfirmation confirmation = ( OverwriteConfirmation ) systemUnderTest.getCenter();
      assertThat( confirmation.hasController( systemUnderTest.controller() ), is( true ) );
      assertThat( confirmation.hasRelease( release ), is( true ) );
   }//End Method
   
   @Test public void downloadFailedShouldDisplayRestartConfirmation(){
      systemUnderTest.downloadFailed();
      assertThat( systemUnderTest.getCenter(), is( instanceOf( RestartAfterFailureConfirmation.class ) ) );
      RestartAfterFailureConfirmation confirmation = ( RestartAfterFailureConfirmation ) systemUnderTest.getCenter();
      assertThat( confirmation.hasController( systemUnderTest.controller() ), is( true ) );
      assertThat( confirmation.hasRelease( release ), is( true ) );
   }//End Method
   
   @Test public void shouldBeAssociatedWithCorrectRelease(){
      assertThat( systemUnderTest.hasRelease( release ), is( true ) );
      assertThat( systemUnderTest.hasRelease( mock( ReleaseDefinition.class) ), is( false ) );
   }//End Method
   
   @Test public void shouldBeAssociatedWithCorrectGenerator(){
      assertThat( systemUnderTest.hasSystemHandover( handover ), is( true ) );
      assertThat( systemUnderTest.hasSystemHandover( mock( SystemHandover.class ) ), is( false ) );
   }//End Method
}//End Class
