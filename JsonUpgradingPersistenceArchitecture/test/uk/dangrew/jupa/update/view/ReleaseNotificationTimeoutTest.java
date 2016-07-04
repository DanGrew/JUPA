/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Timer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.graphics.launch.TestApplication;

/**
 * {@link ReleaseNotificationTimeout} test.
 */
public class ReleaseNotificationTimeoutTest {
   
   private static final long TIMEOUT = 5000L;
   
   @Mock private ReleaseNotificationPanel panel;
   @Mock private Timer timer;
   private ReleaseNotificationTimeout systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ReleaseNotificationTimeout();
      systemUnderTest.schedule( timer, TIMEOUT, panel );
   }//End Method
   
   @Test public void shouldInitiallyScheduleIteselfOnTimer() {
      verify( timer ).schedule( systemUnderTest, TIMEOUT );
   }//End Method
   
   @Test public void whenRunShouldHideAndCancelTimer(){
      systemUnderTest.run();
      verify( panel ).hide();
      verify( timer ).cancel();
   }//End Method
   
   @Test public void shouldSupportReschduling(){
      systemUnderTest.run();
      
      Timer otherTimer = mock( Timer.class );
      ReleaseNotificationPanel otherPanel = mock( ReleaseNotificationPanel.class );
      systemUnderTest.schedule( otherTimer, 123, otherPanel );
      verify( otherTimer ).schedule( systemUnderTest, 123 );
      
      systemUnderTest.run();
      verify( otherPanel ).hide();
      verify( otherTimer ).cancel();
   }//End Method

}//End Class
