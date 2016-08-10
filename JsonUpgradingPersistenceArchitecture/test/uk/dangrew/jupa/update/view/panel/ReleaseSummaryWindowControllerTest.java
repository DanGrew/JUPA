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
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.graphics.launch.TestApplication;
import uk.dangrew.jupa.update.launch.BasicSystemHandover;
import uk.dangrew.jupa.update.launch.SystemHandover;
import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * {@link ReleaseSummaryWindowController} test.
 */
public class ReleaseSummaryWindowControllerTest {

   private SystemHandover handover;
   private ReleaseSummaryWindowController systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      handover = new BasicSystemHandover( getClass() );
      systemUnderTest = new ReleaseSummaryWindowController( handover );
   }//End Method
   
   @Test public void summaryShouldInitiallyBeHidden(){
      assertThat( systemUnderTest.summaryStage().isShowing(), is( false ) );
   }//End Method

   @Test public void shouldDisplayCorrectReleasesWhenGiven(){
      List< ReleaseDefinition > releases = new ArrayList<>();
      systemUnderTest.releasesAreNowAvailable( releases );
      
      assertThat( systemUnderTest.summaryStage().getScene().getRoot(), is( systemUnderTest.summaryPanel() ) );
      
      releases.add( new ReleaseDefinition( "a", "b", "c" ) );
      systemUnderTest.releasesAreNowAvailable( releases );
      
      assertThat( systemUnderTest.summaryPanel().hasReleases( releases ), is( true ) );
   }//End Method
   
   @Test public void shouldPassArtifactGeneratorThroughToSummaryPanel() {
      assertThat( systemUnderTest.summaryPanel().artifactLocationGenerator(), is( handover ) );
   }// End Method
   
   @Test public void showShouldShowStage(){
      assertThat( systemUnderTest.summaryStage().isShowing(), is( false ) );
      systemUnderTest.show();
      assertThat( systemUnderTest.summaryStage().isShowing(), is( true ) );
   }//End Method
   
   @Test public void hideShouldHideStage(){
      assertThat( systemUnderTest.summaryStage().isShowing(), is( false ) );
      systemUnderTest.show();
      assertThat( systemUnderTest.summaryStage().isShowing(), is( true ) );
      systemUnderTest.hide();
      assertThat( systemUnderTest.summaryStage().isShowing(), is( false ) );
   }//End Method

}//End Class
