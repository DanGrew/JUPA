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

import java.util.HashMap;
import java.util.Map;

import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * {@link ArtifactLocationGenerator} is responsible for constructing the appropriate {@link JarProtocol}s
 * and managing them for downloading {@link ReleaseDefinition}s.
 */
public class ArtifactLocationGenerator {
   
   private final Class< ? > downloadRelativeTo;
   private Map< ReleaseDefinition, JarProtocol > releaseProtocols;
   
   /**
    * Constructs a new {@link ArtifactLocationGenerator}.
    * @param downloadRelativeTo the {@link Class} relative for the download location.
    */
   public ArtifactLocationGenerator( Class< ? > downloadRelativeTo ) {
      this.downloadRelativeTo = downloadRelativeTo;
      this.releaseProtocols = new HashMap<>();
   }//End Constructor
   
   /**
    * Method to fetch a {@link JarProtocol} for the given {@link ReleaseDefinition}, constructing
    * a new one if one doesn't exist.
    * @param release the {@link ReleaseDefinition} to fetch for.
    * @return the {@link JarProtocol} that exists or a new one if not. 
    */
   public JarProtocol fetchJarLocation( ReleaseDefinition release ){
      JarProtocol protocol = releaseProtocols.get( release );
      if ( protocol == null ) {
         protocol = new JarProtocol( null, release.getArtifactName(), downloadRelativeTo );
         releaseProtocols.put( release, protocol );
      }
      return protocol;
   }//End Method

}//End Class
