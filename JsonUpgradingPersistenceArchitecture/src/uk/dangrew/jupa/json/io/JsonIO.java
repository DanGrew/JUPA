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

import org.json.JSONException;
import org.json.JSONObject;

import uk.dangrew.sd.logging.io.BasicStringIO;

/**
 * {@link JsonIO} provides basic read and write functionality for files.
 */
public class JsonIO {
   
   private final BasicStringIO stringIO;
   private final JsonIODigest digest;
   
   /**
    * Constructs a new {@link JsonIO}.
    */
   public JsonIO() {
      this( new JsonIODigest() );
   }//End Constructor
   
   /**
    * Constructs a new {@link JsonIO} with the given {@link JsonIODigest}.
    * @param digest the {@link JsonIODigest} to use.
    */
   JsonIO( JsonIODigest digest ) {
      this.stringIO = new BasicStringIO();
      this.digest = digest;
      this.digest.attachSource( this );
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
         digest.failedToParseInput( file );
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
