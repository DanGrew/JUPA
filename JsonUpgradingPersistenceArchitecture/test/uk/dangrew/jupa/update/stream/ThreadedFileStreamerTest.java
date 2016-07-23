/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.stream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * {@link ThreadedFileStreamer} test.
 */
public class ThreadedFileStreamerTest {

   private static final String DOWNLOAD = "anything";
   
   @Mock private JarProtocol protocol;
   @Mock private FileStreamer streamer;
   private CountDownLatch latch;
   private ThreadedFileStreamer systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      latch = new CountDownLatch( 1 );
      systemUnderTest = new ThreadedFileStreamer( streamer );
      systemUnderTest.setOnCompletion( latch::countDown );
   }//End Method
   
   @Test public void shouldNotifyWhenComplete() throws IOException, InterruptedException {
      systemUnderTest.streamFile( DOWNLOAD, protocol );
      
      latch.await();
      assertThat( systemUnderTest.wasSuccessful(), is( true ) );
   }//End Method
   
   @Test public void shouldNotifyWhenFailed() throws IOException, InterruptedException {
      doThrow( new IOException() ).when( streamer ).streamFile( DOWNLOAD, protocol );
      
      systemUnderTest.streamFile( DOWNLOAD, protocol );
      
      latch.await();
      assertThat( systemUnderTest.wasSuccessful(), is( false ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullStreamer() {
      new ThreadedFileStreamer( null );
   }//End Method
   
   @Test public void shouldProvideSourceFromSubject() {
      Source source = new SourceImpl( this );
      when( streamer.getSourceForProgress() ).thenReturn( source );
      
      assertThat( systemUnderTest.getSourceForProgress(), is( source ) );
   }//End Method

   @Test public void shouldAcceptNullOnCompletionRunnable() throws IOException, InterruptedException{
      systemUnderTest.setOnCompletion( null );
      systemUnderTest.streamFile( DOWNLOAD, protocol );
   }//End Method
   
}//End Class
