package uk.dangrew.jupa.file.protocol;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.dangrew.jupa.json.io.JsonIO;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ArbitraryLocationProtocolTest {

    @Mock private JsonIO jsonIO;
    @Mock private File file;
    private ArbitraryLocationProtocol systemUnderTest;

    @Before public void initialiseSystemUnderTest(){
        initMocks(this);
        systemUnderTest = new ArbitraryLocationProtocol(jsonIO, file);
    }

    @Test
    public void shouldProvideFileInformation(){
        assertThat(systemUnderTest.getSource(), is(file));
        when(file.getAbsolutePath()).thenReturn("anything");
        assertThat(systemUnderTest.getLocation(), is("anything"));
    }

    @Test public void shouldRead(){
        JSONObject jsonObject = new JSONObject();
        when(jsonIO.read(file)).thenReturn(jsonObject);

        assertThat(systemUnderTest.readFromLocation(), is(jsonObject));
    }

    @Test public void shouldWrite(){
        JSONObject jsonObject = new JSONObject();
        when(jsonIO.write(file, jsonObject)).thenReturn(true);

        assertThat(systemUnderTest.writeToLocation(jsonObject), is(true));
    }
}