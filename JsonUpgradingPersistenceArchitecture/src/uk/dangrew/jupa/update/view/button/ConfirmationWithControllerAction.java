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
 * The {@link ConfirmationWithControllerAction} provides a specific {@link ConfirmationPane} that
 * is associated with an {@link InstallerButtonController} and {@link ReleaseDefinition}.
 */
public class ConfirmationWithControllerAction extends ConfirmationPane {
   
   private final InstallerButtonController controller;
   private final ReleaseDefinition release;
   
   /**
    * Constructs a new {@link ConfirmationWithControllerAction}.
    * @param message the message associated with the confirmation.
    * @param controller the {@link InstallerButtonController} for performing actions.
    * @param release the {@link ReleaseDefinition} in question.
    */
   public ConfirmationWithControllerAction( String message, InstallerButtonController controller, ReleaseDefinition release ) {
      super( message );
      this.controller = controller;
      this.release = release;
   }//End Constructor
   
   /**
    * Method to determine whether this {@link ConfirmationWithControllerAction} is associated with the 
    * given {@link InstallerButtonController}.
    * @param controller {@link InstallerButtonController} in question.
    * @return true if is associated with, false otherwise.
    */
   boolean hasController( InstallerButtonController controller ) {
      return this.controller == controller;
   }//End Method
   
   /**
    * Method to determine whether this {@link ConfirmationWithControllerAction} is associated with the 
    * given {@link ReleaseDefinition}.
    * @param release {@link ReleaseDefinition} in question.
    * @return true if is associated with, false otherwise.
    */
   boolean hasRelease( ReleaseDefinition release ) {
      return this.release == release;
   }//End Method

}//End Class
