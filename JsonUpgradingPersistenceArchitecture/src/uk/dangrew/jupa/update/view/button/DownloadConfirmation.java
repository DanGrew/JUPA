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
 * The {@link DownloadConfirmation} provides a specific {@link ConfirmationPane} that
 * confirms a download should begin.
 */
public class DownloadConfirmation extends ConfirmationWithControllerAction {
   
   static final String MESSAGE = "Would you like to download and launch this version?";

   /**
    * Constructs a new {@link DownloadConfirmation}.
    * @param controller the {@link InstallerButtonController} for performing actions.
    * @param release the {@link ReleaseDefinition} in question.
    */
   public DownloadConfirmation( InstallerButtonController controller, ReleaseDefinition release ) {
      super( MESSAGE, controller, release );
      
      setYesAction( event -> controller.checkForArtifactFileClash( release ) );
      setNoAction( event -> controller.cancelDownload() );
   }//End Constructor

}//End Class
