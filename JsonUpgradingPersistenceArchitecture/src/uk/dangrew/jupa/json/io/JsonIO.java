/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import uk.dangrew.sd.logging.io.BasicStringIO;

/**
 * {@link JsonIO} provides basic read and write functionality for files.
 */
public class JsonIO {
   
   private final BasicStringIO stringIO;
   
   /**
    * Constructs a new {@link JsonIO}.
    */
   public JsonIO() {
      stringIO = new BasicStringIO();
   }//End Constructor

   /**
    * Method to read json data from the given {@link File}.
    * @param file the {@link File} to read from.
    * @return the {@link JSONObject} read, or null if anything goes wrong.
    */
   public JSONObject read( File file ) {
      String parsedString = stringIO.read( file );
      if ( parsedString == null ) {
         return null;
      }
      try {
         return new JSONObject( parsedString );
      } catch ( JSONException exception ) {
         return null;
      }
   }//End Method
   
   /**
    * Method to write the given {@link JSONObject} to the given {@link File}.
    * @param file the {@link File} to write to.
    * @param object the {@link JSONObject} to write.
    * @return true if written successfully.
    */
   public boolean write( File file, JSONObject object ) {
      if ( file == null ) {
         throw new NullPointerException( "File must not be null." );
      }
      String stringToWrite = object.toString( 3 );
      return stringIO.write( file, stringToWrite, false );
   }//End Method

}//End Class
