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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
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
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullTask(){
      new NotificationScheduler( null, PERIOD );
   }//End Method
   
   @Test public void shouldProvideGivenPeriod(){
      assertThat( systemUnderTest.getNotificationPeriod(), is( PERIOD ) );
      assertThat( systemUnderTest.getNotificationPeriod(), is( not( 102L ) ) );
   }//End Method
   
   @Test public void shouldHaveTaskAssociated(){
      assertThat( systemUnderTest.isTask( task ), is( true ) );
      assertThat( systemUnderTest.isTask( mock( ReleaseAvailableTask.class ) ), is( false ) );
   }//End Method
   
   @Test public void shouldScheduleTaskAppropriately() {
      verify( timer ).schedule( task, 0, PERIOD );
   }//End Method

   @Test public void shouldStopCorrectlyWhenRequested(){
      systemUnderTest.stop();
      verify( timer ).cancel();
   }//End Method
}//End Class
