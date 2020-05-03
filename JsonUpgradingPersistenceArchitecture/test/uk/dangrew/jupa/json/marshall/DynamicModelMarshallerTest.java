package uk.dangrew.jupa.json.marshall;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.dangrew.jupa.file.protocol.JsonPersistingProtocol;
import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.structure.JsonStructure;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DynamicModelMarshallerTest {

    @Mock private JsonPersistingProtocol protocol;

    @Mock private JsonStructure jsonStructure;
    @Mock private JsonParser parserWithReadHandles;
    @Mock private JsonParser parserWithWriteHandles;
    private DynamicModelMarshaller systemUnderTest;

    @Before
    public void initialiseSystemUnderTest() {
        initMocks(this);
        systemUnderTest = new DynamicModelMarshaller(
                () -> jsonStructure,
                parserWithReadHandles,
                parserWithWriteHandles
        );
    }

    @Test public void shouldWrite(){
        systemUnderTest.write(protocol);
        verify(jsonStructure).build(any());
        verify(parserWithWriteHandles).parse(any());
        verify(protocol).writeToLocation(any());
    }

    @Test
    public void shouldRead(){
        when(protocol.readFromLocation()).thenReturn(null);
        systemUnderTest.read(protocol);
        verify(parserWithReadHandles, never()).parse(any());

        JSONObject jsonObject = new JSONObject();
        when(protocol.readFromLocation()).thenReturn(jsonObject);
        systemUnderTest.read(protocol);
        verify(parserWithReadHandles).parse(jsonObject);
    }

}