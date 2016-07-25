/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.launch;

import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * The {@link SystemLauncher} is responsible for launching the new version of the software.
 */
public interface SystemLauncher {

   /**
    * Method to launch the {@link uk.dangrew.jupa.update.model.ReleaseDefinition} associated with the
    * given {@link JarProtocol} location.
    * @param protocol the {@link JarProtocol} to find the {@link uk.dangrew.jupa.update.model.ReleaseDefinition} artifact.
    * @return true if successfully launched.
    */
   public boolean launch( JarProtocol protocol );

}//End Interface