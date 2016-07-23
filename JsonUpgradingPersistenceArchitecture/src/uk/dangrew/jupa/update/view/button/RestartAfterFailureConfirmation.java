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
 * The {@link RestartAfterFailureConfirmation} provides a specific {@link ConfirmationPane} that
 * confirms a download should be tried again.
 */
public class RestartAfterFailureConfirmation extends ConfirmationWithControllerAction {
   
   static final String MESSAGE = "Download of the new release has failed. Would you like to try again? "
            + "If this problem persists file an issue on the GitHub page having looked "
            + "at the logs produced from this session.";

   /**
    * Constructs a new {@link RestartAfterFailureConfirmation}.
    * @param controller the {@link InstallerButtonController} for performing actions.
    * @param release the {@link ReleaseDefinition} in question.
    */
   public RestartAfterFailureConfirmation( InstallerButtonController controller, ReleaseDefinition release ) {
      super( MESSAGE, controller, release );
      
      setYesAction( event -> controller.startDownload( release ) );
      setNoAction( event -> controller.cancelDownload() );
   }//End Constructor

}//End Class
