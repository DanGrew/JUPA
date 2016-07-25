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

import uk.dangrew.jupa.update.model.ReleaseDefinition;

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
    * @param release the {@link ReleaseDefinition} to launch.
    */
   public LaunchConfirmation( InstallerButtonController controller, ReleaseDefinition release ) {
      super( MESSAGE, controller, release );
      
      setYesAction( event -> controller.launchConfirmed( release ) );
      setNoAction( event -> controller.launchCancelled() );
   }//End Constructor

}//End Class
