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

/**
 * {@link JsonIO} provides basic read and write functionality for files.
 */
public class JsonIO {

   /**
    * Method to read json data from the given {@link File}.
    * @param file the {@link File} to read from.
    * @return the {@link JSONObject} read, or null if anything goes wrong.
    */
   public JSONObject read( File file ) {
      if ( !file.exists() ) {
         return null;
      }
      String parsedString = readFileIntoString( file );
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
    * Method to read the given {@link Scanner} and extract a {@link String}.
    * @param scanner the {@link Scanner} to read.
    * @return the {@link String} containing all text from the {@link Scanner}.
    */
   String readScannerContentAndClose( Scanner scanner ) {
      String content = scanner.useDelimiter( "//Z" ).next();
      scanner.close();
      return content;
   }//End Method
   
   /**
    * Method to read a text file into a {@link String}.
    * @param file the {@link File} to read into a {@link String}.
    * @return the {@link String} containing all text from the {@link File}.
    */
   String readFileIntoString( File file ) {
      try ( InputStream stream = new FileInputStream( file ) ){
         Scanner scanner = new Scanner( stream );
         return readScannerContentAndClose( scanner );
      } catch ( IOException e ) {
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
      if ( object == null ) {
         throw new NullPointerException( "Write object must not be null." );
      }
      
      if ( !file.exists() ) {
         try {
            file.createNewFile();
         } catch ( IOException e ) {
            //TODO - digest.
            return false;
         }
      }
      
      try ( FileWriter writer = new FileWriter( file ) ) {
         writer.write( object.toString( 3 ) );
         return true;
      } catch ( IOException e ) {
         //TODO - digest.
         return false;
      }
   }//End Method

}//End Class
