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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.event.ActionEvent;
import uk.dangrew.jupa.graphics.launch.TestApplication;
import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * {@link RestartAfterFailureConfirmation} test.
 */
public class RestartAfterFailureConfirmationTest {

   @Mock private InstallerButtonController controller;
   @Mock private ReleaseDefinition release;
   private RestartAfterFailureConfirmation systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new RestartAfterFailureConfirmation( controller, release );
   }//End Method
   
   @Test public void shouldUseSpecificMessage(){
      assertThat( systemUnderTest.confirmation().getText(), is( RestartAfterFailureConfirmation.MESSAGE ) );
   }//End Method
   
   @Test public void yesShouldPerformAction(){
      systemUnderTest.yesButton().getOnAction().handle( new ActionEvent() );
      verify( controller ).startDownload( release );
   }//End Method
   
   @Test public void noShouldPerformAction(){
      systemUnderTest.noButton().getOnAction().handle( new ActionEvent() );
      verify( controller ).cancelDownload();
   }//End Method
}//End Class
