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

import uk.dangrew.jupa.json.io.JsonIO;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * The {@link JarJsonPersistingProtocol} provides a method of holding a {@link File} in the
 * same place as the jar and providing read and write access to it using {@link JSONObject}s.
 */
public class JarJsonPersistingProtocol implements JsonPersistingProtocol {
   
   private final JarProtocol protocol;
   private final JsonIO jsonIO;
   
   /**
    * Constructs a new {@link JarJsonPersistingProtocol}.
    * @param filename the filename to read/write to.
    * @param relativeTo the {@link Class} providing the {@link CodeSource} to place
    * the {@link File} relative to.
    */
   public JarJsonPersistingProtocol( String filename, Class< ? > relativeTo ) {
      this( new JsonIO(), null, filename, relativeTo );
   }//End Constructor
   
   /**
    * Constructs a new {@link JarJsonPersistingProtocol}.
    * @param subFolder the name of the sub folder the file will be placed in.
    * @param filename the filename to read/write to.
    * @param relativeTo the {@link Class} providing the {@link CodeSource} to place
    * the {@link File} relative to.
    */
   public JarJsonPersistingProtocol( String subFolder, String filename, Class< ? > relativeTo ) {
      this( new JsonIO(), subFolder, filename, relativeTo );
   }//End Constructor
   
   /**
    * Constructs a new {@link JarJsonPersistingProtocol}.
    * @param jsonIO the {@link JsonIO} for read and writing.
    * @param subFolder the name of the sub folder the file will be placed in.
    * @param filename the filename to read/write to.
    * @param relativeTo the {@link Class} providing the {@link CodeSource} to place
    * the {@link File} relative to.
    */
   JarJsonPersistingProtocol( JsonIO jsonIO, String subFolder, String filename, Class< ? > relativeTo ) {
      this.protocol = new JarProtocol( subFolder, filename, relativeTo );
      this.jsonIO = jsonIO;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public JSONObject readFromLocation() {
      return jsonIO.read( protocol.getSource() );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean writeToLocation( JSONObject object ) {
      return jsonIO.write( protocol.getSource(), object );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String getLocation() {
      return protocol.getSource().getAbsolutePath();
   }//End Method

}//End Class
