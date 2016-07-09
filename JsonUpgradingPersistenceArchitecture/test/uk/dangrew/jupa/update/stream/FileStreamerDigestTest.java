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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.graphics.launch.TestApplication;
import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiver;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiverImpl;
import uk.dangrew.sd.core.lockdown.DigestProgressReceiver;
import uk.dangrew.sd.core.lockdown.DigestProgressReceiverImpl;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.progress.Progresses;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.viewer.basic.DigestViewer;

/**
 * {@link FileStreamerDigest} test.
 */
public class FileStreamerDigestTest {

   @Mock private DigestMessageReceiver messageReceiver;
   @Mock private DigestProgressReceiver progressReceiver;
   @Mock private FileStreamer subject;
   private Source expectedSource;
   private FileStreamerDigest systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      new DigestMessageReceiverImpl( messageReceiver );
      new DigestProgressReceiverImpl( progressReceiver );
      
      systemUnderTest = new FileStreamerDigest();
      systemUnderTest.attachSource( subject );
      expectedSource = new SourceImpl( subject, FileStreamerDigest.NAME );
   }//End Method
   
   @Ignore
   @Test public void manual() throws InterruptedException{
      TestApplication.launch( () -> new DigestViewer() );
      
      systemUnderTest.downloadingContentWithLength( 10000 );
      systemUnderTest.startedStreaming();
      for ( int i = 0; i < 10000; i++ ) {
         systemUnderTest.streamedByte();
         Thread.sleep( 1 );
      }
      systemUnderTest.finishedStreaming();
      
      Thread.sleep( 100000 );
   }//End Method
   
   @Test public void shouldStartProgressWhenStarted() {
      systemUnderTest.startedStreaming();
      
      verify( progressReceiver ).progress( 
               expectedSource, Progresses.simpleProgress( 0 ), Messages.simpleMessage( FileStreamerDigest.STARTED_STREAMING ) 
      );
      verify( messageReceiver ).log( 
               Mockito.any(), eq( expectedSource ), eq( Categories.information() ), eq( Messages.simpleMessage( FileStreamerDigest.STARTED_STREAMING ) ) 
      );
   }//End Method
   
   @Test public void shouldFinishedProgressWhenFinished() {
      systemUnderTest.finishedStreaming();
      
      verify( progressReceiver ).progress( 
               expectedSource, Progresses.simpleProgress( 100 ), Messages.simpleMessage( FileStreamerDigest.FINISHED_STREAMING ) 
      );
      verify( messageReceiver ).log( 
               Mockito.any(), eq( expectedSource ), eq( Categories.information() ), eq( Messages.simpleMessage( FileStreamerDigest.FINISHED_STREAMING ) ) 
      );
   }//End Method
   
   @Test public void shouldFinishProgressWhenCancelled() {
      systemUnderTest.streamingCancelled();
      
      verify( progressReceiver ).progress( 
               expectedSource, Progresses.simpleProgress( 100 ), Messages.simpleMessage( FileStreamerDigest.CANCELLED_STREAMING ) 
      );
      verify( messageReceiver ).log( 
               Mockito.any(), eq( expectedSource ), eq( Categories.error() ), eq( Messages.simpleMessage( FileStreamerDigest.CANCELLED_STREAMING ) ) 
      );
   }//End Method
   
   @Test public void shouldProvideProgressForSingleStreamedByteWhenNoLengthIdentified() {
      systemUnderTest.streamedByte();
      
      verify( progressReceiver ).progress( 
               expectedSource, Progresses.simpleProgress( 0 ), Messages.simpleMessage( 0 + FileStreamerDigest.STREAMING_PERCENTAGE ) 
      );
   }//End Method
   
   @Test public void shouldProvideProgressForSingleStreamedByte() {
      systemUnderTest.downloadingContentWithLength( 100 );
      systemUnderTest.streamedByte();
      
      verify( progressReceiver ).progress( 
               expectedSource, Progresses.simpleProgress( 1 ), Messages.simpleMessage( 1 + FileStreamerDigest.STREAMING_PERCENTAGE ) 
      );
   }//End Method
   
   @Test public void shouldHandleLessThan100ByteDownload() {
      systemUnderTest.downloadingContentWithLength( 5 );
      systemUnderTest.streamedByte();
      
      verify( progressReceiver ).progress( 
               expectedSource, Progresses.simpleProgress( 1 ), Messages.simpleMessage( 1 + FileStreamerDigest.STREAMING_PERCENTAGE ) 
      );
   }//End Method
   
   @Test public void shouldProvideProgressForStreamedBytesInSinglePercentages() {
      systemUnderTest.downloadingContentWithLength( 1000 );
      
      for ( int i = 0; i < 100; i++ ) {
         
         for ( int j = 0; j < 10; j++ ) {
            systemUnderTest.streamedByte();
            
            if ( i == 0 ) {
               continue;
            }
            verify( progressReceiver ).progress( 
                     expectedSource, 
                     Progresses.simpleProgress( i ), 
                     Messages.simpleMessage( ( i ) + FileStreamerDigest.STREAMING_PERCENTAGE ) 
            );
         }
      }
   }//End Method
   
   @Test public void shouldIgnoreInvalidDownloadLength() {
      systemUnderTest.downloadingContentWithLength( -1 );
      shouldProvideProgressForSingleStreamedByteWhenNoLengthIdentified();
   }//End Method
   
   @Test public void shouldIgnoreNoDownloadLength() {
      systemUnderTest.downloadingContentWithLength( 0 );
      shouldProvideProgressForSingleStreamedByteWhenNoLengthIdentified();
   }//End Method
   
   @Test public void shouldResetProgressWhenMultipleDownloads() {
      stream100bytes( 1 );
      stream100bytes( 2 );
   }//End Method
   
   /**
    * Method to stream 100 bytes as the {@link FileStreamer} would.
    * @param veriifcations the number of verifications expected for each percentage.
    */
   private void stream100bytes( int veriifcations ){
      systemUnderTest.downloadingContentWithLength( 100 );
      
      for ( int i = 0; i < 100; i++ ) {
         systemUnderTest.streamedByte();
         
         if ( i == 0 ) {
            continue;
         }
         verify( progressReceiver, times( veriifcations ) ).progress( 
                  expectedSource, 
                  Progresses.simpleProgress( i ), 
                  Messages.simpleMessage( ( i ) + FileStreamerDigest.STREAMING_PERCENTAGE ) 
         );
      }
   }//End Method

}//End Class
