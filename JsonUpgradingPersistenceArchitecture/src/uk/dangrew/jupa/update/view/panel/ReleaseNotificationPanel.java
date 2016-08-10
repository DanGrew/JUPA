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

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;

/**
 * The {@link ReleaseNotificationPanel} is a wrapper that provides a basic notification of {@link ReleaseDefinition}s
 * being made available. To nest something inside, simple call {@link #setContent(javafx.scene.Node)}.
 */
public class ReleaseNotificationPanel extends NotificationPane {

   static final String INSTALL_BUTTON_TEXT = "Install";
   
   private final Action installButton;
   
   /**
    * Constructs a new {@link ReleaseNotificationPanel}.
    * @param notificationText the {@link String} text to display.
    * @param installRequestedHandler the {@link Runnable} to execute when the button is pressed.
    */
   public ReleaseNotificationPanel( String notificationText, Runnable installRequestedHandler ) {
      getStyleClass().add( NotificationPane.STYLE_CLASS_DARK );
      
      setText( notificationText );
      installButton = new Action( INSTALL_BUTTON_TEXT, event -> installRequestedHandler.run() );
      getActions().add( installButton );
   }//End Constructor
   
   Action install(){
      return installButton;
   }//End Method

}//End Class
