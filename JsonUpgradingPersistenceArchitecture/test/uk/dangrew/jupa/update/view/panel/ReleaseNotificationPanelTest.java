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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.controlsfx.control.NotificationPane;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.event.ActionEvent;
import uk.dangrew.jupa.graphics.launch.TestApplication;

/**
 * {@link ReleaseNotificationPanel} test.
 */
public class ReleaseNotificationPanelTest {

   private static final String NOTIFICATION_TEXT = "this is the text";
   
   @Mock private Runnable runnable;
   private ReleaseNotificationPanel systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ReleaseNotificationPanel( NOTIFICATION_TEXT, runnable );
   }//End Method
   
   private void triggerActionOnInstallButton(){
      PlatformImpl.runAndWait( () -> systemUnderTest.install().handle( new ActionEvent() ) );
   }//End Method
   
   @Test public void notificationShouldUseDarkTheme(){
      assertThat( systemUnderTest.getStyleClass().contains( NotificationPane.STYLE_CLASS_DARK ), is( true ) );
   }//End Method
   
   @Test public void notificationShouldUseCorrectText(){
      assertThat( systemUnderTest.getText(), is( NOTIFICATION_TEXT ) );
   }//End Method
   
   @Test public void buttonShouldTriggerRunnable(){
      triggerActionOnInstallButton();
      verify( runnable ).run();
   }//End Method
   
}//End Class
