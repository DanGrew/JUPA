/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.download;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.graphics.launch.TestApplication;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.model.ReleaseParser;
import uk.dangrew.jupa.update.view.panel.ReleaseSummaryPanel;

/**
 * {@link ReleasesDownloader} test.
 */
public class ReleasesDownloaderTest {
   
   @Mock private HttpClient client;
   private ReleasesDownloader systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ReleasesDownloader( "anything", client );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
       String content = new ReleasesDownloader(
                "https://raw.githubusercontent.com/DanGrew/JenkinsTestTracker/master/RELEASES"
       ).downloadContent();
       List< ReleaseDefinition > releases = new ReleaseParser().parse( content );
       TestApplication.launch( () -> {
          ReleaseSummaryPanel summary = new ReleaseSummaryPanel();
          summary.setReleases( releases );
          return summary;
       } );
       
       Thread.sleep( 1000000 );
   }//End Method
   
   @Test public void shouldProvideGivenLocation(){
      assertThat( systemUnderTest.getDownloadLocation(), is( "anything" ) );
      assertThat( systemUnderTest.getDownloadLocation(), is( not( "anything else " ) ) );
   }//End Method
   
   @Test public void shouldHandleInvalidPath(){
      assertThat( new ReleasesDownloader( "anything" ).downloadContent(), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullPath(){
      new ReleasesDownloader( null );
   }//End Method
   
   @Test public void shouldConnectToValidLinkInRepo(){
      String result = new ReleasesDownloader( 
               "https://raw.githubusercontent.com/DanGrew/JUPA/master/README.md" 
      ).downloadContent();
      System.out.println( result );
      assertThat( result, is( not( nullValue() ) ) );
   }//End Method
   
   @Test public void shouldHandleHttpResponseException() throws ClientProtocolException, IOException{
      when( client.execute( Mockito.< HttpUriRequest >any(), Mockito.< HttpContext >any() ) ).thenThrow( new HttpResponseException( 0, "anything" ) );
      assertThat( systemUnderTest.downloadContent(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldHandleClientProtocolException() throws ClientProtocolException, IOException{
      when( client.execute( Mockito.< HttpUriRequest >any(), Mockito.< HttpContext >any() ) ).thenThrow( new ClientProtocolException() );
      assertThat( systemUnderTest.downloadContent(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldHandleIOException() throws ClientProtocolException, IOException{
      when( client.execute( Mockito.< HttpUriRequest >any(), Mockito.< HttpContext >any() ) ).thenThrow( new IOException() );
      assertThat( systemUnderTest.downloadContent(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldHandleIllegalStateException() throws ClientProtocolException, IOException{
      when( client.execute( Mockito.< HttpUriRequest >any(), Mockito.< HttpContext >any() ) ).thenThrow( new IllegalStateException() );
      assertThat( systemUnderTest.downloadContent(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldTidyUpResponseBeforeFinishing() throws ClientProtocolException, IOException{
      HttpResponse response = mock( HttpResponse.class );
      when( response.getStatusLine() ).thenReturn( mock( StatusLine.class ) );
      HttpEntity entity = mock( HttpEntity.class );
      when( response.getEntity() ).thenReturn( entity );
      InputStream stream = mock( InputStream.class );
      when( entity.getContent() ).thenReturn( stream );
      
      when( client.execute( Mockito.< HttpUriRequest >any(), Mockito.< HttpContext >any() ) ).thenReturn( response );
      
      assertThat( systemUnderTest.downloadContent(), is( nullValue() ) );
      verify( entity ).isStreaming();
      verify( stream ).close();
   }//End Method
   
   @Test public void shouldNotTidyUpResponseIfNoEntityBeforeFinishing() throws ClientProtocolException, IOException{
      HttpResponse response = mock( HttpResponse.class );
      when( response.getStatusLine() ).thenReturn( mock( StatusLine.class ) );
      
      when( client.execute( Mockito.< HttpUriRequest >any(), Mockito.< HttpContext >any() ) ).thenReturn( response );
      
      assertThat( systemUnderTest.downloadContent(), is( nullValue() ) );
   }//End Method
}//End Class
