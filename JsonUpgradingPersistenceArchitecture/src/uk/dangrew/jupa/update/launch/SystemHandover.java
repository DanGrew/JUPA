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

import java.util.function.BooleanSupplier;

import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.stream.ArtifactLocationGenerator;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * The {@link SystemHandover} is responsible for managing the handover from the current version of
 * the software to a newer version.
 */
public class SystemHandover implements ArtifactLocationGenerator {

   private final SystemLauncher launcher;
   private final ArtifactLocationGenerator generator;
   private final BooleanSupplier shutdownFunction;
   
   /**
    * Constructs a new {@link SystemHandover}.
    * @param launcher the {@link SystemLauncher} for launching the new verison.
    * @param generator the {@link ArtifactLocationGenerator} for identifying where to save the artifacts.
    * @param shutdownFunction the {@link BooleanSupplier} for shutting down the current system.
    */
   public SystemHandover( SystemLauncher launcher, ArtifactLocationGenerator generator, BooleanSupplier shutdownFunction ) {
      this.launcher = launcher;
      this.generator = generator;
      this.shutdownFunction = shutdownFunction;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public JarProtocol fetchJarLocation( ReleaseDefinition release ) {
      return generator.fetchJarLocation( release );
   }//End Method

   /**
    * Method to shutdown the current version, hodling the {@link Thread} until complete.
    * @return true if successful.
    */
   public boolean shutdown() {
      return shutdownFunction.getAsBoolean();
   }//End Method

   /**
    * Method to launch the given {@link ReleaseDefinition} that has been downloaded.
    * @param release the {@link ReleaseDefinition} to launch.
    * @return true if successful.
    */
   public boolean launch( ReleaseDefinition release ) {
      JarProtocol protocol = generator.fetchJarLocation( release );
      return launcher.launch( protocol );
   }//End Method

}//End Class
