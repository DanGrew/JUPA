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
 * {@link ArtifactLocationGeneratorImpl} provides a basic implementation for {@link ArtifactLocationGenerator}.
 */
public class ArtifactLocationGeneratorImpl implements ArtifactLocationGenerator {
   
   private final Class< ? > downloadRelativeTo;
   private Map< ReleaseDefinition, JarProtocol > releaseProtocols;
   
   /**
    * Constructs a new {@link ArtifactLocationGenerator}.
    * @param downloadRelativeTo the {@link Class} relative for the download location.
    */
   public ArtifactLocationGeneratorImpl( Class< ? > downloadRelativeTo ) {
      if ( downloadRelativeTo == null ) {
         throw new IllegalArgumentException( "Argument must not be null." );
      }
      
      this.downloadRelativeTo = downloadRelativeTo;
      this.releaseProtocols = new HashMap<>();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public JarProtocol fetchJarLocation( ReleaseDefinition release ){
      JarProtocol protocol = releaseProtocols.get( release );
      if ( protocol == null ) {
         protocol = new JarProtocol( null, release.getArtifactName(), downloadRelativeTo );
         releaseProtocols.put( release, protocol );
      }
      return protocol;
   }//End Method

}//End Class
