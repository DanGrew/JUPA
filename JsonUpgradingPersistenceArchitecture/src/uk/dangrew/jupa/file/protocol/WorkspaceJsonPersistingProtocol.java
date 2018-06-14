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

import uk.dangrew.kode.utility.io.IoCommon;

public class WorkspaceJsonPersistingProtocol implements JsonPersistingProtocol {
   
   private final IoCommon ioCommon;
   private final String filename;
   private final Class< ? > relativeTo;
   
   public WorkspaceJsonPersistingProtocol( String filename, Class< ? > relativeTo ) {
      this( new IoCommon(), filename, relativeTo );
   }//End Constructor
   
   WorkspaceJsonPersistingProtocol( IoCommon ioCommon, String filename, Class< ? > relativeTo ) {
      this.ioCommon = ioCommon;
      this.filename = filename;
      this.relativeTo = relativeTo;
   }//End Constructor
   
   @Override public JSONObject readFromLocation() {
      return new JSONObject( ioCommon.readFileIntoString( relativeTo, filename ) );
   }//End Method

   @Override public boolean writeToLocation( JSONObject object ) {
      throw new UnsupportedOperationException( "Not implemented. Current practice is to write into new JSON Objects rather than files." );
   }//End Method
   
   @Override public String getLocation() {
      return filename;
   }//End Method

}//End Class
