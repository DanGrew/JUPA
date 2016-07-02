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

import static org.mockito.Mockito.verify;

import java.util.Timer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * {@link NotificationScheduler} test.
 */
public class NotificationSchedulerTest {

   private static final long PERIOD = 2487563;
   
   @Mock private Timer timer;
   @Mock private ReleaseAvailableTask task;
   private NotificationScheduler systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new NotificationScheduler( timer, task, PERIOD );
   }//End Method
   
   @Test public void shouldScheduleTaskAppropriately() {
      verify( timer ).schedule( task, 0, PERIOD );
   }//End Method

   @Test public void shouldStopCorrectlyWhenRequested(){
      systemUnderTest.stop();
      verify( timer ).cancel();
   }//End Method
}//End Class
