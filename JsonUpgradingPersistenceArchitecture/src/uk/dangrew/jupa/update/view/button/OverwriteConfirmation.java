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
 * The {@link OverwriteConfirmation} provides a specific {@link ConfirmationPane} that
 * confirms a file should be overwritten.
 */
public class OverwriteConfirmation extends ConfirmationWithControllerAction {
   
   static final String MESSAGE_SUFFIX = " already exists, overwrite?";

   /**
    * Constructs a new {@link OverwriteConfirmation}.
    * @param controller the {@link InstallerButtonController} for performing actions.
    * @param release the {@link ReleaseDefinition} in question.
    */
   public OverwriteConfirmation( InstallerButtonController controller, ReleaseDefinition release ) {
      super( release.getArtifactName() + MESSAGE_SUFFIX, controller, release );
      
      setYesAction( event -> controller.startDownload( release ) );
      setNoAction( event -> controller.cancelDownload() );
   }//End Constructor

}//End Class
