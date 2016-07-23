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

/**
 * The {@link LaunchConfirmation} provides a specific {@link ConfirmationPane} that
 * confirms that the downloaded should be launched.
 */
public class LaunchConfirmation extends ConfirmationWithControllerAction {
   
   static final String MESSAGE = "Would you like to launch the downloaded version? This will "
            + "terminate this version of the software.";
   
   /**
    * Constructs a new {@link LaunchConfirmation}.
    * @param controller the {@link InstallerButtonController} for performing actions.
    */
   public LaunchConfirmation( InstallerButtonController controller ) {
      super( MESSAGE, controller, null );
      
      setYesAction( event -> controller.launchConfirmed() );
      setNoAction( event -> controller.launchCancelled() );
   }//End Constructor

}//End Class
