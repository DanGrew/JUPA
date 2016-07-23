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

import java.io.IOException;

import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * {@link FileStreamer} provides an interface to an object that can stream a file and reconstruct
 * it at a given location.
 */
public interface FileStreamer {

   /**
    * Method to stream the item found at the given download link location.
    * @param downloadLink the location to stream from.
    * @param fileProtocol the {@link JarProtocol} for the {@link java.io.File} to stream to. This will
    * be overwritten regardless of the state.
    * @throws IOException if any exception occurs during stream such that the streaming is aborted.
    */
   public void streamFile( String downloadLink, JarProtocol fileProtocol ) throws IOException;//End Method

   /**
    * Getter for the associated {@link Source} with the streamer allowing external items to receive 
    * the correct digest.
    * @return the {@link Source} associated.
    */
   public Source getSourceForProgress();

}//End Interface