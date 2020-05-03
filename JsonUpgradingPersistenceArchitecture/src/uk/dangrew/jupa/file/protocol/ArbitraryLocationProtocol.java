/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */

package uk.dangrew.jupa.file.protocol;

import org.json.JSONObject;
import uk.dangrew.jupa.file.protocol.JsonPersistingProtocol;
import uk.dangrew.jupa.json.io.JsonIO;
import uk.dangrew.sd.logging.location.FileLocationProtocol;

import java.io.File;
import java.security.CodeSource;

/**
 * The {@link uk.dangrew.sd.logging.location.JarProtocol} is a {@link FileLocationProtocol} for establishing a {@link File}
 * in the same location as the jar, optionally in a sub folder.
 */
public class ArbitraryLocationProtocol implements JsonPersistingProtocol {
   
   private static final String FILE_SEPARATOR = "/";
   private final File source;
   private final JsonIO jsonIO;
   
   /**
    * Constructs a new {@link uk.dangrew.sd.logging.location.JarProtocol}.
    * @param file the file to read/write to.
    */
   public ArbitraryLocationProtocol(File file) {
      this(new JsonIO(), file);
   }

   ArbitraryLocationProtocol(JsonIO jsonIO, File file){
      if ( file == null){
         throw new NullPointerException( "Parameters cannot be null." );
      }

      this.source = file;
      this.jsonIO = jsonIO;
   }//End Constructor
   
   /**
    * Method to get the {@link File} source.
    * @return the {@link File}.
    */
   public File getSource(){
      return source;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String getLocation() {
      return source.getAbsolutePath();
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public JSONObject readFromLocation() {
      return jsonIO.read( getSource() );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean writeToLocation( JSONObject object ) {
      return jsonIO.write( getSource(), object );
   }//End Method

}//End Class
