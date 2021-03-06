/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.file.protocol;

import org.json.JSONObject;

import uk.dangrew.sd.logging.location.FileLocationProtocol;

/**
 * The {@link FileLocationProtocol} is responsible for determining where a {@link java.io.File} should
 * be placed and how it should be written.
 */
public interface JsonPersistingProtocol extends FileLocationProtocol {
   
   /**
    * Method to read from the associated location into a {@link JSONObject}.
    * @return the parsed {@link JSONObject}, null if reasd failed.
    */
   public JSONObject readFromLocation();
   
   /**
    * Method to write the given {@link JSONObject} to the associated location.
    * @param object the {@link JSONObject} to write.
    * @return true if successfully written.
    */
   public boolean writeToLocation( JSONObject object );
   
}//End Interface
