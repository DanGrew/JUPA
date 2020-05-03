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
import uk.dangrew.jupa.file.protocol.JsonPersistingProtocol;
import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.structure.JsonStructure;

import java.util.function.Supplier;

/**
 * The {@link uk.dangrew.jupa.json.marshall.ModelMarshaller} is responsible for marshalling a model, defined in terms of
 * {@link JsonParser}s to a {@link JsonPersistingProtocol}.
 */
public class DynamicModelMarshaller {

    private final Supplier<JsonStructure> structureProvider;
    private final JsonParser parserWithReadHandles;
    private final JsonParser parserWithWriteHandles;

    /**
     * Constructs a new {@link uk.dangrew.jupa.json.marshall.ModelMarshaller}.
     *
     * @param structureProvider      provider of the {@link JsonStructure} used to build a blank {@link JSONObject} into
     *                               the required structure.
     * @param parserWithReadHandles  the {@link JsonParser} for reading.
     * @param parserWithWriteHandles the {@link JsonParser} for writing.
     */
    public DynamicModelMarshaller(
            Supplier<JsonStructure> structureProvider,
            JsonParser parserWithReadHandles,
            JsonParser parserWithWriteHandles
    ) {
        if (structureProvider == null || parserWithReadHandles == null || parserWithWriteHandles == null) {
            throw new NullPointerException("No parameters are allowed to be null.");
        }

        this.structureProvider = structureProvider;
        this.parserWithReadHandles = parserWithReadHandles;
        this.parserWithWriteHandles = parserWithWriteHandles;
    }//End Constructor

    /**
     * Method to write to the associated {@link java.io.File}.
     */
    public void write(JsonPersistingProtocol fileProtocol) {
        JSONObject objectToWrite = new JSONObject();
        structureProvider.get().build(objectToWrite);
        parserWithWriteHandles.parse(objectToWrite);
        fileProtocol.writeToLocation(objectToWrite);
    }//End Method

    /**
     * Method to read from the associated {@link java.io.File}.
     */
    public void read(JsonPersistingProtocol fileProtocol) {
        JSONObject readObject = fileProtocol.readFromLocation();
        if (readObject == null) {
            return;
        }

        parserWithReadHandles.parse(readObject);
    }//End Method

}//End Class
