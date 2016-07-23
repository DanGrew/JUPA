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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.graphics.launch.TestApplication;
import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * {@link ConfirmationWithControllerAction} test.
 */
public class ConfirmationWithControllerActionTest {

   private static final String MESSAGE = "controller action message";
   @Mock private InstallerButtonController controller;
   @Mock private ReleaseDefinition release;
   private ConfirmationWithControllerAction systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ConfirmationWithControllerAction( MESSAGE, controller, release );
   }//End Method
   
   @Test public void shouldUseGivenMessage() {
      assertThat( systemUnderTest.confirmation().getText(), is( MESSAGE ) );
   }//End Method

   @Test public void shouldMatchCorrectController() {
      assertThat( systemUnderTest.hasController( controller ), is( true ) );
      assertThat( systemUnderTest.hasController( mock( InstallerButtonController.class ) ), is( false ) );
   }//End Method
   
   @Test public void shouldMatchCorrectRelease() {
      assertThat( systemUnderTest.hasRelease( release ), is( true ) );
      assertThat( systemUnderTest.hasRelease( mock( ReleaseDefinition.class ) ), is( false ) );
   }//End Method
   
   @Test public void shouldProvideNoDefaultActions() {
      assertThat( systemUnderTest.yesButton().getOnAction(), is( nullValue() ) );
      assertThat( systemUnderTest.noButton().getOnAction(), is( nullValue() ) );
   }//End Method
}//End Class
