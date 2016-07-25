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

import uk.dangrew.jupa.update.stream.ArtifactLocationGeneratorImpl;

/**
 * The {@link BasicSystemHandover} provides a simple method of constructing a {@link SystemHandover}
 * where no other functions are needed.
 */
public class BasicSystemHandover extends SystemHandover {

   public BasicSystemHandover( Class< ? > relativeTo ) {
      super( new SystemLauncherImpl(), new ArtifactLocationGeneratorImpl( relativeTo ), () -> true );
   }//End Constructor

}//End Class
