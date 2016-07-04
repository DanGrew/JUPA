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
   private final ReleaseAvailableTask task;
   private final long period;
   
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
      if ( task == null ) {
         throw new IllegalArgumentException( "Must provide non null task." );
      }
      
      this.timer = timer;
      this.task = task;
      this.period = period;
      timer.schedule( task, 0, period );
   }//End Constructor
   
   /**
    * Method to stop the {@link Timer}.
    */
   public void stop(){
      timer.cancel();
   }//End Method

   /**
    * Method to determine whether the given {@link ReleaseAvailableTask} is associated.
    * @param task the {@link ReleaseAvailableTask} in question.
    * @return true if equal, false if different.
    */
   public boolean isTask( ReleaseAvailableTask task ) {
      return this.task.equals( task );
   }//End Method

   /**
    * Getter for the period in milliseconds between notifications.
    * @return the notification period in milliseconds.
    */
   public long getNotificationPeriod() {
      return period;
   }//End Method

}//End Class
