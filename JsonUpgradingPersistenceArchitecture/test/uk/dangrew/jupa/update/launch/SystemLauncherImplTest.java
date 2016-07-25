/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.launch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * {@link SystemLauncherImpl} test.
 */
public class SystemLauncherImplTest {

   private static final String PATH = "something very specific";
   
   @Mock private JarProtocol protocol;
   @Spy private SystemLauncherImpl systemUnderTest;
   
   @Before public void initialiseSystemUnderTest() throws IOException{
      MockitoAnnotations.initMocks( this );
      doReturn( null ).when( systemUnderTest ).launch( Mockito.< String[ ] >any() );
      when( protocol.getLocation() ).thenReturn( PATH );
   }//End Method
   
   @Test public void shouldLaunchWithCorrectArgumentsAndProvidePositiveResult() throws IOException {
      assertThat( systemUnderTest.launch( protocol ), is( true ) );
      verify( systemUnderTest ).launch( new String[]{ SystemLauncherImpl.COMMAND, SystemLauncherImpl.AS_JAR, PATH } );
   }//End Method
   
   @Test public void shouldLaunchWithCorrectArgumentsAndProvideNegativeResult() throws IOException {
      when( systemUnderTest.launch( new String[]{ SystemLauncherImpl.COMMAND, SystemLauncherImpl.AS_JAR, PATH } ) ).thenThrow( new IOException() );
      assertThat( systemUnderTest.launch( protocol ), is( false ) );
   }//End Method

}//End Class
