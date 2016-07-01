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

import java.util.Timer;

/**
 * The {@link NotificationScheduler} is responsible for scheduling a {@link ReleaseAvailableTask} on
 * a {@link Timer}.
 */
public class NotificationScheduler {
   
   private final Timer timer;
   
   /**
    * Constructs a new {@link NotificationScheduler}.
    * @param task the {@link ReleaseAvailableTask} to schedule.
    * @param period the period between runs.
    */
   public NotificationScheduler( ReleaseAvailableTask task, long period ) {
      this( new Timer(), task, period );
   }//End Constructor
   
   /**
    * Constructs a new {@link NotificationScheduler}.
    * @param timer the {@link Timer} to schedule on.
    * @param task the {@link ReleaseAvailableTask} to schedule.
    * @param period the period between runs.
    */
   NotificationScheduler( Timer timer, ReleaseAvailableTask task, long period ) {
      this.timer = timer;
      
      timer.schedule( task, 0, period );
   }//End Constructor
   
   /**
    * Method to stop the {@link Timer}.
    */
   public void stop(){
      timer.cancel();
   }//End Method

}//End Class
