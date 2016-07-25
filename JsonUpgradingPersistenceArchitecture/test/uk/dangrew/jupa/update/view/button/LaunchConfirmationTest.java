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
 * {@link LaunchConfirmation} test.
 */
public class LaunchConfirmationTest {

   @Mock private ReleaseDefinition release;
   @Mock private InstallerButtonController controller;
   private LaunchConfirmation systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new LaunchConfirmation( controller, release );
   }//End Method
   
   @Test public void shouldUseSpecificMessage(){
      assertThat( systemUnderTest.confirmation().getText(), is( LaunchConfirmation.MESSAGE ) );
   }//End Method
   
   @Test public void yesShouldPerformAction(){
      systemUnderTest.yesButton().getOnAction().handle( new ActionEvent() );
      verify( controller ).launchConfirmed( release );
   }//End Method
   
   @Test public void noShouldPerformAction(){
      systemUnderTest.noButton().getOnAction().handle( new ActionEvent() );
      verify( controller ).launchCancelled();
   }//End Method
}//End Class
