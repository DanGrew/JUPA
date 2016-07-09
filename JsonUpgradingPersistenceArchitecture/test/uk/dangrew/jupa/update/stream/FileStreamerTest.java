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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.TestCommon;
import uk.dangrew.jupa.file.utility.IoStreaming;
import uk.dangrew.sd.logging.io.BasicStringIO;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * {@link FileStreamer} test.
 */
public class FileStreamerTest {

   private static final String DOWNLOAD_LOCATION = "someplace";
   
   @Mock private HttpResponse response;
   @Mock private HttpEntity entity;
   @Mock private InputStream contentStream;
   @Mock private BufferedInputStream inputStream;
   @Mock private BufferedOutputStream outputStream;
   @Mock private File fileToWriteTo;
   
   @Mock private HttpClient client;
   @Mock private FileStreamerDigest digest;
   @Mock private IoStreaming steamer;
   @Mock private JarProtocol protocol;
   private FileStreamer systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      when( protocol.getSource() ).thenReturn( fileToWriteTo );
      systemUnderTest = new FileStreamer( client, digest, steamer );
   }//End Method
   
   @Ignore
   @Test public void manual() throws ClientProtocolException, IOException {
      new FileStreamer().streamFile( 
               "https://dl.bintray.com/dangrew/JenkinsTestTracker/JenkinsTestTracker/JenkinsTestTracker/1.4.119/JenkinsTestTracker-1.4.119-runnable.jar", 
               new JarProtocol( null, "test.jar", FileStreamer.class )
      );
   }//End Method
   
   @Test public void shouldAttachSource(){
      verify( digest ).attachSource( systemUnderTest );
   }//End Method

   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullDownloadLink() throws IOException{
      systemUnderTest.streamFile( null, protocol );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullFileNameToSaveTo() throws IOException{
      systemUnderTest.streamFile( "anything", null );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptEmptyDownloadLink() throws IOException{
      systemUnderTest.streamFile( "    ", protocol );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptEmptyFileNameToSaveTo() throws IOException{
      systemUnderTest.streamFile( "anything", mock( JarProtocol.class ) );
   }//End Method
   
   @Test( expected = IOException.class ) public void shouldThrowExceptionIfRequestCannotBeExecuted() throws ClientProtocolException, IOException{
      when( client.execute( Mockito.any() ) ).thenThrow( new IOException() );
      systemUnderTest.streamFile( DOWNLOAD_LOCATION, protocol );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldThrowExceptionIfDownloadLinkIsNotValid() throws ClientProtocolException, IOException{
      when( client.execute( Mockito.any() ) ).thenThrow( new IOException() );
      systemUnderTest.streamFile( "some place", protocol );
   }//End Method
   
   @Test public void shouldRequestTheGivenDownloadLink() throws ClientProtocolException, IOException{
      ArgumentCaptor< HttpUriRequest > request = ArgumentCaptor.forClass( HttpUriRequest.class );
      
      when( client.execute( request.capture() ) ).thenReturn( mock( HttpResponse.class ) );
      streamExpectingExceptionButIgnore();
      
      assertThat( request.getValue(), is( instanceOf( HttpGet.class ) ) );
      assertThat( ( ( HttpGet )request.getValue() ).getURI().getPath(), is( DOWNLOAD_LOCATION ) );
   }//End Method
   
   @Test( expected = IOException.class ) public void shouldThrowNewExceptionIfNoResponseIsProvided() throws ClientProtocolException, IOException{
      when( client.execute( Mockito.any() ) ).thenReturn( null );
      systemUnderTest.streamFile( DOWNLOAD_LOCATION, protocol );
   }//End Method
   
   @Test( expected = IOException.class ) public void shouldThrowNewExceptionIfNoEntityIsPresentInResponse() throws ClientProtocolException, IOException{
      when( client.execute( Mockito.any() ) ).thenReturn( mock( HttpResponse.class ) );
      systemUnderTest.streamFile( DOWNLOAD_LOCATION, protocol );
   }//End Method
   
   /**
    * Method to put the conditions in place to allow the streaming part of the process to be reached.
    * @throws IOException if any call fails.
    * @throws ClientProtocolException if any call fails.
    */
   private void whenStreamFileShouldGetToStreamConstruction() throws IOException, ClientProtocolException {
      when( response.getEntity() ).thenReturn( entity );
      when( client.execute( Mockito.any() ) ).thenReturn( response );
      when( entity.getContent() ).thenReturn( contentStream );
      when( steamer.constructBufferedInputStream( contentStream ) ).thenReturn( inputStream );
      when( steamer.constructBufferedOutputStream( fileToWriteTo ) ).thenReturn( outputStream );
      when( inputStream.read() ).thenReturn( -1 );
   }//End Method
   
   @Test public void shouldStreamMultipleBytesFromInputToOutputAndDigestProgress() throws ClientProtocolException, IOException{
      whenStreamFileShouldGetToStreamConstruction();
      
      int singleByte = 3;
      when( inputStream.read() ).thenReturn( singleByte ).thenReturn( -1 );
      
      long length = 1;
      when( entity.getContentLength() ).thenReturn( length );
      
      systemUnderTest.streamFile( DOWNLOAD_LOCATION, protocol );
      
      InOrder order = inOrder( digest, inputStream, outputStream );
      order.verify( digest ).downloadingContentWithLength( length );
      order.verify( digest ).startedStreaming();
      order.verify( inputStream ).read();
      order.verify( outputStream ).write( singleByte );
      order.verify( digest ).streamedByte();
      order.verify( inputStream ).read();
      order.verify( digest ).finishedStreaming();
   }//End Method
   
   @Test public void shouldFailInputStreamButInformDigest() throws ClientProtocolException, IOException{
      whenStreamFileShouldGetToStreamConstruction();
      doThrow( new IOException() ).when( inputStream ).close();
      
      streamExpectingExceptionButIgnore();
      
      verify( digest ).streamingCancelled();
   }//End Method
   
   @Test public void shouldFailOutputStreamButInformDigest() throws ClientProtocolException, IOException{
      whenStreamFileShouldGetToStreamConstruction();
      doThrow( new IOException() ).when( outputStream ).close();
      
      streamExpectingExceptionButIgnore();
      
      verify( digest ).streamingCancelled();
   }//End Method
   
   @Test public void shouldFailReadButInformDigest() throws ClientProtocolException, IOException{
      whenStreamFileShouldGetToStreamConstruction();
      when( inputStream.read() ).thenThrow( new IOException() );
      
      streamExpectingExceptionButIgnore();
      
      verify( digest ).streamingCancelled();
   }//End Method
   
   @Test public void shouldFailWriteButInformDigest() throws ClientProtocolException, IOException{
      whenStreamFileShouldGetToStreamConstruction();
      when( inputStream.read() ).thenReturn( 4 ).thenReturn( -1 );
      doThrow( new IOException() ).when( outputStream ).write( Mockito.anyInt() );
      
      streamExpectingExceptionButIgnore();
      
      verify( digest ).streamingCancelled();
   }//End Method
   
   @Test public void shouldConstructStreamToFileWithCorrectName() throws ClientProtocolException, IOException{
      whenStreamFileShouldGetToStreamConstruction();
      
      ArgumentCaptor< File > fileCaptor = ArgumentCaptor.forClass( File.class );
      when( steamer.constructBufferedOutputStream( fileCaptor.capture() ) ).thenReturn( outputStream );
      
      streamExpectingExceptionButIgnore();
      
      assertThat( fileCaptor.getValue(), is( fileToWriteTo ) );
   }//End Method

   /**
    * Method to stream, expecting an {@link IOException}, but to catch and ignore in order
    * to verify some part of the process has been followed correctly.
    */
   private void streamExpectingExceptionButIgnore() {
      try {
         systemUnderTest.streamFile( DOWNLOAD_LOCATION, protocol );
      } catch ( IOException exception ) {
         //ignore - we dont care about this, just the digest
      }
   }//End Method
   
   @Test public void shouldPerformFullDownload() throws IOException{
      systemUnderTest = new FileStreamer();
      
      JarProtocol protocol = new JarProtocol( null, "FileStreamerTest.txt", FileStreamerTest.class );
      if ( protocol.getSource().exists() ) {
         protocol.getSource().delete();
      }
      assertThat( protocol.getSource().exists(), is( false ) );
      
      systemUnderTest.streamFile( 
               "https://raw.githubusercontent.com/DanGrew/JUPA/master/README.md", 
                protocol
      );
      
      assertThat( protocol.getSource().exists(), is( true ) );
      assertThat( new BasicStringIO().read( protocol.getSource() ), is(
               "# JUPA\nJson Upgrading and Persistence Architecture library.\n" 
      ) );
   }//End Method
   
}//End Class
