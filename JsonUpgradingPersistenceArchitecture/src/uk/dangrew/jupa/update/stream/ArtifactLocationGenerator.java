/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.stream;

import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * The {@link ArtifactLocationGenerator} provides a method of getting {@link JarProtocol}s for
 * different {@link ReleaseDefinition}s describing where their artifacts should be saved.
 */
public interface ArtifactLocationGenerator {

   /**
    * Method to fetch a {@link JarProtocol} for the given {@link ReleaseDefinition}, constructing
    * a new one if one doesn't exist.
    * @param release the {@link ReleaseDefinition} to fetch for.
    * @return the {@link JarProtocol} that exists or a new one if not. 
    */
   public JarProtocol fetchJarLocation( ReleaseDefinition release );
}//End Interface