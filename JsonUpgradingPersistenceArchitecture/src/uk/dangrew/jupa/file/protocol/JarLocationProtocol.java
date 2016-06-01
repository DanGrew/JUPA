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

import java.io.File;

import org.json.JSONObject;

import uk.dangrew.jupa.json.io.JsonIO;

/**
 * The {@link JarLocationProtocol} provides a method of holding a {@link File} in the
 * same place as the jar and providing read and write access to it.
 */
public class JarLocationProtocol implements FileLocationProtocol {
   
   private final JsonIO jsonIO;
   private final File source;
   
   /**
    * Constructs a new {@link JarLocationProtocol}.
    * @param filename the filename to read/write to.
    */
   public JarLocationProtocol( String filename ) {
      this( new JsonIO(), filename );
   }//End Constructor
   
   /**
    * Constructs a new {@link JarLocationProtocol}.
    * @param jsonIO the {@link JsonIO} for read and writing.
    * @param filename the filename to read/write to.
    */
   JarLocationProtocol( JsonIO jsonIO, String filename ) {
      if ( filename == null ) {
         throw new NullPointerException( "Filename cannot be null." );
      }
      
      this.jsonIO = jsonIO;
      
      File jarPath = new File( this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() );
      String propertiesPath = jarPath.getParentFile().getAbsolutePath();
      source = new File( propertiesPath + "/" + filename );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public JSONObject readFromLocation() {
      return jsonIO.read( source );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean writeToLocation( JSONObject object ) {
      return jsonIO.write( source, object );
   }//End Method

}//End Class
