/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.io;

import java.io.File;

import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;

/**
 * The {@link JsonIODigest} provides the parsing options that the {@link JsonIO}
 * can use when processing.
 */
class JsonIODigest extends ObjectDigestImpl {
   
   static final String BASIC_STRING_IO_NAME = "Json IO";
   static final String FAILED_TO_PARSE = "Failed to parse Json found in: ";
   static final String FILE_NOT_AVAILABLE = "File not available";

   /**
    * Method to attach the given {@link JsonIO} as {@link uk.dangrew.sd.core.source.Source}
    * to this {@link ObjectDigestImpl}.
    * @param source the {@link BasicStringIO}.
    */
   void attachSource( JsonIO source ) {
      super.attachSource( new SourceImpl( source, BASIC_STRING_IO_NAME ) );
   }//End Method
   
   /**
    * Method to log that the {@link JsonIO} has failed to parse the json in the {@link File}.
    * @param file the {@link File} being worked with.
    */
   void failedToParseInput( File file ) {
      log( Categories.error(), Messages.simpleMessage( format( FAILED_TO_PARSE, file ) ) );
   }//End Method

   /**
    * Method to format the input into a readable, loggable {@link String}.
    * @param error the error description.
    * @param file the {@link File} being worked with.
    * @return a formatted {@link String}.
    */
   static String format( String error, File file ) {
      StringBuilder builder = new StringBuilder();
      builder.append( error ).append( "\n" );
      builder.append( "File: " );
      if ( file == null || file.getAbsolutePath() == null ) {
         builder.append( FILE_NOT_AVAILABLE );
      } else {
         builder.append( file.getAbsolutePath() ); 
      }
      return builder.toString();
   }//End Method

}//End Class
