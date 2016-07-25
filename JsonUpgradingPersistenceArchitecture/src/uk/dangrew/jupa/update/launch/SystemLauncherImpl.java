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

import java.io.IOException;

import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * The {@link SystemLauncherImpl} is rsponsible for launching a new executable jar that has been downloaded.
 */
public class SystemLauncherImpl implements SystemLauncher {
   
   static final String COMMAND = "java";
   static final String AS_JAR = "-jar";
   
   /**
    * {@inheritDoc}
    */
   @Override public boolean launch( JarProtocol protocol ) {
      try {
         String artifactRunnable = protocol.getLocation();
         launch( new String[]{ COMMAND, AS_JAR, artifactRunnable } );
         return true;
      } catch ( IOException exception ) {
         return false;
      }
   }//End Method
   
   /**
    * Method factored out to be stubbed that cannot be tested.
    * @param arguments the process arguments.
    * @return the {@link Process} constructed.
    * @throws IOException if the process fails to launch.
    */
   Process launch( String[] arguments ) throws IOException {
      return Runtime.getRuntime().exec( arguments );
   }//End Method

}//End Class
