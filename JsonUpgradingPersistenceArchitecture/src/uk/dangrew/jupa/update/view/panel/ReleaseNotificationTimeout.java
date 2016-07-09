/*
 * ----------------------------------------
 *          Jenkins Test Tracker
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.view.panel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The {@link ReleaseNotificationTimeout} is responsible for timing a hide action on the {@link ReleaseNotificationPanel}
 * in order to stop the notification appearing indefinitely.
 */
public class ReleaseNotificationTimeout extends TimerTask {

   private ReleaseNotificationPanel releaseNotificationPanel;
   private Timer timer;
   
   /**
    * Method to schedule a hide instruction.
    * @param timer the {@link Timer} for one time use only, every call should use a new {@link Timer}.
    * @param timeout the timeout of the notification in milliseconds.
    * @param releaseNotificationPanel the {@link ReleaseNotificationPanel} to hide.
    */
   public void schedule( Timer timer, long timeout, ReleaseNotificationPanel releaseNotificationPanel ) {
      this.releaseNotificationPanel = releaseNotificationPanel;
      this.timer = timer;
      this.timer.schedule( this, timeout );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void run() {
      releaseNotificationPanel.hide();
      timer.cancel();
   }//End Method

}//End Class
