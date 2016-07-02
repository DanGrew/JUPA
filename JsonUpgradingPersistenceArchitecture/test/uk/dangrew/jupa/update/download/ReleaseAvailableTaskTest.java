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

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.mockito.ParameterReturner;
import uk.dangrew.jupa.update.model.ReleaseAvailabilityObserver;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.model.ReleaseParser;

/**
 * {@link ReleaseAvailableTask} test.
 */
public class ReleaseAvailableTaskTest {

   private static final String DOWNLOADED_CONTENT = "anything downloaded";
   
   @Mock private ReleaseDefinition releaseA;
   @Mock private ReleaseDefinition releaseB;
   @Mock private ReleaseDefinition releaseC;
   
   @Mock private ReleaseAvailabilityObserver observer;
   @Mock private ReleasesDownloader downloader;
   @Mock private ReleaseParser parser;
   @Mock private ReleaseUpgradeChecker checker;
   private ReleaseAvailableTask systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      
      when( downloader.downloadContent() ).thenReturn( DOWNLOADED_CONTENT );
      when( checker.filterBasedOnCurrentVersion( Mockito.anyListOf( ReleaseDefinition.class ) ) ).then( new ParameterReturner<>( List.class, 0 ) );
      systemUnderTest = new ReleaseAvailableTask( downloader, parser, checker, observer );
   }//End Method

   /**
    * Method to put the mocking in place to provide the given {@link ReleaseDefinition}s.
    * @param releases the {@link ReleaseDefinition}s to provide.
    */
   private void whenParsedReturn( List< ReleaseDefinition > releases ){
      when( parser.parse( DOWNLOADED_CONTENT ) ).thenReturn( releases );
   }//End Method
   
   @Test public void shouldNotifyObserverWithReleasesWhenNonHeld() {
      List< ReleaseDefinition > expectedReleases = Arrays.asList( releaseA, releaseB, releaseC );
      whenParsedReturn( expectedReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( expectedReleases );
   }//End Method
   
   @Test public void shouldNotifyObserverWithNewReleasesWhenMoreThanHeld() {
      List< ReleaseDefinition > firstReleases = Arrays.asList( releaseA, releaseB );
      whenParsedReturn( firstReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( firstReleases );
      
      List< ReleaseDefinition > newReleases = Arrays.asList( releaseA, releaseB, releaseC );
      whenParsedReturn( newReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( newReleases );
   }//End Method
   
   @Test public void shouldNotifyObserverWithUpdatedReleasesWhenLessHeld() {
      List< ReleaseDefinition > firstReleases = Arrays.asList( releaseA, releaseB, releaseC );
      whenParsedReturn( firstReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( firstReleases );
      
      List< ReleaseDefinition > newReleases = Arrays.asList( releaseA, releaseB );
      whenParsedReturn( newReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( newReleases );
   }//End Method
   
   @Test public void shouldNotNotifyObserverWhenReleasesHaveNotChanged() {
      List< ReleaseDefinition > expectedReleases = Arrays.asList( releaseA, releaseB );
      whenParsedReturn( expectedReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( expectedReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( expectedReleases );
   }//End Method
   
   @Test public void shouldNotifyObserverWhenReleasesHaveChangedData(){
      List< ReleaseDefinition > firstReleases = Arrays.asList( releaseA, releaseB );
      whenParsedReturn( firstReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( firstReleases );
      
      List< ReleaseDefinition > newReleases = Arrays.asList( releaseA, releaseC );
      whenParsedReturn( newReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( newReleases );
   }//End Method
   
   @Test public void shouldClearReleasesFoundWithEachUpdate(){
      List< ReleaseDefinition > firstReleases = Arrays.asList( releaseA );
      whenParsedReturn( firstReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( firstReleases );
      
      List< ReleaseDefinition > newReleases = Arrays.asList( releaseB );
      whenParsedReturn( newReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( newReleases );
      
      List< ReleaseDefinition > lastReleases = Arrays.asList( releaseA, releaseB );
      whenParsedReturn( lastReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( lastReleases );
   }//End Method
   
   @Test public void shouldIgnoreNullDownloadedContent(){
      when( downloader.downloadContent() ).thenReturn( null );
      systemUnderTest.run();
      verify( observer, never() ).releasesAreNowAvailable( Mockito.anyListOf( ReleaseDefinition.class ) );
   }//End Method
   
   @Test public void shouldFilterReleasesUsesCheckerBeforePassingOn(){
      List< ReleaseDefinition > firstReleases = Arrays.asList( releaseA, releaseB, releaseC );
      whenParsedReturn( firstReleases );
      List< ReleaseDefinition > filteredReleases = Arrays.asList( releaseB, releaseC );
      when( checker.filterBasedOnCurrentVersion( firstReleases ) ).thenReturn( filteredReleases );
      
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( filteredReleases );
   }//End Method
   
   @Test public void shouldUseVersionNumberToFilterReleases(){
      when( downloader.downloadContent() ).thenReturn( 
               "Release, \"V2\"\n"
             + "Download, \"somewhere\"\n"
             + "Description, \"anything\"\n"
             
             + "Release, \"V1\"\n"
             + "Download, \"somewhere\"\n"
             + "Description, \"anything\"\n"
             
             + "Release, \"V0\"\n"
             + "Download, \"somewhere\"\n"
             + "Description, \"anything\"\n"
     );
      systemUnderTest = new ReleaseAvailableTask( "V1", downloader, observer );
      systemUnderTest.run();
      verify( observer ).releasesAreNowAvailable( Arrays.asList( new ReleaseDefinition( "V2", "somewhere", "anything" ) ) );
   }//End Method

}//End Class
