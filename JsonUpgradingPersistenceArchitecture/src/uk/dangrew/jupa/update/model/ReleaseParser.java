/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.model;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * The {@link ReleaseParser} is responsible for parsing csv style information from a {@link String} 
 * that describes multiple releases.
 */
public class ReleaseParser {
   
   private static final String RELEASE = "Release";
   private static final String DATE = "Date";
   private static final String DOWNLOAD = "Download";
   private static final String DESCRIPTION = "Description";

   /**
    * Method to parse the given {@link String} for {@link ReleaseDefinition}s.
    * @param releases the {@link String} to parse.
    * @return a {@link List} of {@link ReleaseDefinition}s, never null.
    */
   public List< ReleaseDefinition > parse( String releases ) {
      List< ReleaseDefinition > definitions = new ArrayList<>();
      
      if ( releases == null ) {
         throw new IllegalArgumentException( "Must supply non null text." );
      } else if ( releases.trim().length() == 0 ) {
         return definitions;
      }
      
      try ( CSVReader reader = new CSVReader( new StringReader( releases ) ) ){
      
         String[] nextLine = null;
         while ( ( nextLine = reader.readNext() ) != null ) {
            if ( nextLine.length != 2 ) {
               continue;
            }
            
            ReleaseDefinition release = parseRelease( reader, nextLine );
            if ( release != null ) {
               definitions.add( release );
            }
         }
      } catch ( IOException e ) {
         //TODO digest
         e.printStackTrace();
      }
      
      return definitions;
   }//End Method
   
   /**
    * Method to parse an individual {@link ReleaseDefinition}.
    * @param reader the {@link CSVReader} to parse from.
    * @param startingLine the starting line already verified correct.
    * @return the {@link ReleaseDefinition} constructed, or null if failed.
    * @throws IOException throw when the {@link CSVReader} fails to read.
    */
   private ReleaseDefinition parseRelease( CSVReader reader, String[] startingLine ) throws IOException {
      String[] parsingLine = startingLine;
      
      String release = parseValue( parsingLine, RELEASE );
      if ( release == null ) {
         return null;
      }
      
      parsingLine = reader.readNext();
      String date = parseValue( parsingLine, DATE );
      if ( date != null ) {
         parsingLine = reader.readNext();
      }
      
      String download = parseValue( parsingLine, DOWNLOAD );
      if ( download == null ) {
         return null;
      }
      
      parsingLine = reader.readNext();
      String description = parseValue( parsingLine, DESCRIPTION );
      if ( description == null ) {
         return null;
      }
      
      return new ReleaseDefinition( release, download, description, date );
   }//End Method
   
   /**
    * Method to parse the value from a line of csv.
    * @param csvLine the {@link String} array to parse from.
    * @param tag the tag being parsed.
    * @return the value parsed.
    */
   private String parseValue( String[] csvLine, String tag ) {
      if ( csvLine == null ) {
         return null;
      } else if ( csvLine.length != 2 ) {
         return null;
      }
      
      if ( csvLine[ 0 ].trim().equals( tag ) ) {
         return csvLine[ 1 ].trim();
      } 
      
      return null;
   }//End Method
   
}//End Class
