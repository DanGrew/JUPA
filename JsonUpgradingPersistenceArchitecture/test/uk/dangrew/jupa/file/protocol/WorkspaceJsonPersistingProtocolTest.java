package uk.dangrew.jupa.file.protocol;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.utility.io.IoCommon;

public class WorkspaceJsonPersistingProtocolTest {

   private static final String FILENAME = "anything";
   
   @Mock private IoCommon ioCommon;
   private WorkspaceJsonPersistingProtocol systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      uk.dangrew.kode.launch.TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new WorkspaceJsonPersistingProtocol( ioCommon, FILENAME, getClass() );
   }//End Method

   @Test public void shouldReadFromLocation() {
      when( ioCommon.readFileIntoString( getClass(), FILENAME ) ).thenReturn( "{}" );
      JSONObject object = systemUnderTest.readFromLocation();
      assertThat( object, is( notNullValue() ) );
   }//End Method

}//End Class
