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
import java.security.CodeSource;

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
    * @param relativeTo the {@link Class} providing the {@link CodeSource} to place
    * the {@link File} relative to.
    */
   public JarLocationProtocol( String filename, Class< ? > relativeTo ) {
      this( new JsonIO(), filename, relativeTo );
   }//End Constructor
   
   /**
    * Constructs a new {@link JarLocationProtocol}.
    * @param jsonIO the {@link JsonIO} for read and writing.
    * @param filename the filename to read/write to.
    * @param relativeTo the {@link Class} providing the {@link CodeSource} to place
    * the {@link File} relative to.
    */
   JarLocationProtocol( JsonIO jsonIO, String filename, Class< ? > relativeTo ) {
      if ( filename == null || relativeTo == null ) {
         throw new NullPointerException( "Parameters cannot be null." );
      }
      
      CodeSource codeSource = relativeTo.getProtectionDomain().getCodeSource();
      if ( codeSource == null ) {
         throw new IllegalArgumentException( "Can only use class that has a code source. Its not clear what"
                  + "determines this. Current project classes and dependencies appear to be acceptable. Java"
                  + "source such as String.class is not acceptable." );
      }
      
      this.jsonIO = jsonIO;
      
      File jarPath = new File( codeSource.getLocation().getPath() );
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
