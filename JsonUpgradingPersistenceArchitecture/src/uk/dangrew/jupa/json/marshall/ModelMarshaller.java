/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.marshall;

import org.json.JSONObject;

import uk.dangrew.jupa.file.protocol.FileLocationProtocol;
import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.write.JsonStructure;

/**
 * The {@link ModelMarshaller} is responsible for marshalling a model, defined in terms of
 * {@link JsonParser}s to a {@link FileLocationProtocol}.
 */
public class ModelMarshaller {

   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final JsonParser parserWithWriteHandles;
   private final FileLocationProtocol fileProtocol;

   /**
    * Constructs a new {@link ModelMarshaller}.
    * @param structure the {@link JsonStructure} used to build a blank {@link JSONObject} into the 
    * required structure.
    * @param parserWithReadHandles the {@link JsonParser} for reading.
    * @param parserWithWriteHandles the {@link JsonParser} for writing.
    * @param fileProtocol the {@link FileLocationProtocol} for reading and writing.
    */
   public ModelMarshaller( 
            JsonStructure structure, 
            JsonParser parserWithReadHandles, 
            JsonParser parserWithWriteHandles,
            FileLocationProtocol fileProtocol
   ) {
      if ( structure == null || parserWithReadHandles == null || parserWithWriteHandles == null || fileProtocol == null ) {
         throw new NullPointerException( "No parameters are allowed to be null." );
      }
      
      this.structure = structure;
      this.parserWithReadHandles = parserWithReadHandles;
      this.parserWithWriteHandles = parserWithWriteHandles;
      this.fileProtocol = fileProtocol;
   }//End Constructor

   /**
    * Method to write to the associated {@link java.io.File}.
    */
   public void write() {
      JSONObject objectToWrite = new JSONObject();
      structure.build( objectToWrite );
      parserWithWriteHandles.parse( objectToWrite );
      fileProtocol.writeToLocation( objectToWrite );
   }//End Method

   /**
    * Method to read from the associated {@link java.io.File}.
    */
   public void read() {
      JSONObject readObject = fileProtocol.readFromLocation();
      parserWithReadHandles.parse( readObject );
   }//End Method

}//End Class
