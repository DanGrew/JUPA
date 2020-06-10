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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.javafx.platform.PlatformLifecycle;
import uk.dangrew.kode.javafx.platform.PlatformLifecycleImpl;
import uk.dangrew.jupa.update.launch.SystemHandover;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.stream.ThreadedFileStreamer;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * {@link InstallerButtonController} test.
 */
public class InstallerButtonControllerTest {
   
   private static final String RELEASE = "1.4.5";
   private static final String DOWNLOAD = "http://oh-yeah.com";
   private static final String DESCRIPTION = "An exciting release";
   
   private ReleaseDefinition release;
   @Mock private InstallerButton subject;
   @Mock private ThreadedFileStreamer streamer;
   @Mock private SystemHandover handover;
   @Mock private JarProtocol protocol;
   @Mock private File sourceFile;
   @Mock private PlatformLifecycleImpl lifecyle;
   private InstallerButtonController systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      PlatformLifecycle.setInstance( lifecyle );
      release = new ReleaseDefinition( RELEASE, DOWNLOAD, DESCRIPTION );
      
      when( protocol.getSource() ).thenReturn( sourceFile );
      when( handover.fetchJarLocation( release ) ).thenReturn( protocol );
      
      systemUnderTest = new InstallerButtonController( subject, streamer, handover );
   }//End Method

   @Test public void startUpdateShouldCallDownloadInitialised() {
      systemUnderTest.startUpdate();
      verify( subject ).downloadInitiated();
   }//End Method
   
   @Test public void shouldCheckForFileClashAndInformSubjectIfItDoes(){
      when( sourceFile.exists() ).thenReturn( true );
      
      systemUnderTest.checkForArtifactFileClash( release );
      verify( subject ).fileExists();
   }//End Method
   
   @Test public void shouldCheckForFileClashAndContinueIfItDoesnt(){
      systemUnderTest = spy( systemUnderTest );
      
      when( sourceFile.exists() ).thenReturn( false );
      
      systemUnderTest.checkForArtifactFileClash( release );
      verify( systemUnderTest ).startDownload( release );
   }//End Method
   
   @Test public void startDownloadShouldPerformDownloadAndUpdateSubject() throws IOException {
      Source source = mock( Source.class );
      when( streamer.getSourceForProgress() ).thenReturn( source );
      
      systemUnderTest.startDownload( release );
      verify( subject ).downloadConfirmed( source );
      verify( streamer ).streamFile( DOWNLOAD, protocol );
   }//End Method
   
   @Test public void startDownloadShouldFailedDownloadIfExceptionOccurs() throws IOException {
      doThrow( new IOException() ).when( streamer ).streamFile( Mockito.anyString(), Mockito.any() );;
      
      systemUnderTest.startDownload( release );
      verify( subject ).downloadFailed();
   }//End Method
   
   @Test public void shouldFinishStreamingWhenStreamerCompletes(){
      ArgumentCaptor< Runnable > completionHandlerCaptor = ArgumentCaptor.forClass( Runnable.class );
      verify( streamer ).setOnCompletion( completionHandlerCaptor.capture() );
      assertThat( completionHandlerCaptor.getValue(), is( notNullValue() ) );
      
      when( streamer.wasSuccessful() ).thenReturn( true );
      completionHandlerCaptor.getValue().run();
      verify( subject ).downloadFinished();
      
      when( streamer.wasSuccessful() ).thenReturn( false );
      completionHandlerCaptor.getValue().run();
      verify( subject ).downloadFailed();
   }//End Method
   
   @Test public void cancelDownloadShouldCallDownloadCancelled() {
      systemUnderTest.cancelDownload();
      verify( subject ).downloadCancelled();
   }//End Method
   
   @Test public void launchConfirmedShouldAttemptToLaunchBeforeShuttingDown() {
      when( handover.launch( release ) ).thenReturn( true );
      
      systemUnderTest.launchConfirmed( release );
      verify( subject ).launchConfirmed();
      
      verify( handover ).launch( release );
      verify( handover ).shutdown();
      verify( lifecyle ).shutdownPlatform();
   }//End Method
   
   @Test public void launchConfirmedShouldNotShutdownIfLaunchFails() {
      when( handover.launch( release ) ).thenReturn( false );
      
      systemUnderTest.launchConfirmed( release );
      verify( subject ).launchConfirmed();
      
      verify( handover ).launch( release );
      verify( handover, never() ).shutdown();
      verify( lifecyle, never() ).shutdownPlatform();
      verify( subject ).launchFailed();
   }//End Method
   
   @Test public void launchCancelledShouldCallLaunchCancelled() {
      systemUnderTest.launchCancelled();
      verify( subject ).launchCancelled();
   }//End Method
   
   @Test public void shouldBeAssociatedWithCorrectGenerator(){
      assertThat( systemUnderTest.hasSystemHandover( handover ), is( true ) );
      assertThat( systemUnderTest.hasSystemHandover( mock( SystemHandover.class ) ), is( false ) );
   }//End Method

}//End Class
