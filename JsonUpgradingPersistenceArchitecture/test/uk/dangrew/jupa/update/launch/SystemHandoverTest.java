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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.BooleanSupplier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.stream.ArtifactLocationGenerator;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * {@link SystemHandover} test.
 */
public class SystemHandoverTest {
   
   @Mock private SystemLauncher launcher;
   @Mock private BooleanSupplier shutdownFunction;
   @Mock private ArtifactLocationGenerator generator;
   private SystemHandover systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new SystemHandover( launcher, generator, shutdownFunction );
   }//End Method

   @Test public void shouldUseGivenGenerator() {
      ReleaseDefinition release = mock( ReleaseDefinition.class );
      JarProtocol protocol = mock( JarProtocol.class );
      when( generator.fetchJarLocation( release ) ).thenReturn( protocol );
      
      assertThat( systemUnderTest.fetchJarLocation( release ), is( protocol ) );
   }//End Method
   
   @Test public void shutdownShouldUseGivenFunction(){
      when( shutdownFunction.getAsBoolean() ).thenReturn( true );
      assertThat( systemUnderTest.shutdown(), is( true ) );
      
      when( shutdownFunction.getAsBoolean() ).thenReturn( false );
      assertThat( systemUnderTest.shutdown(), is( false ) );
   }//End Method
   
   @Test public void launchShouldUseGivenSystemLauncher(){
      ReleaseDefinition release = mock( ReleaseDefinition.class );
      JarProtocol protocol = mock( JarProtocol.class );
      when( generator.fetchJarLocation( release ) ).thenReturn( protocol );
      
      when( launcher.launch( protocol ) ).thenReturn( true );
      assertThat( systemUnderTest.launch( release ), is( true ) );
      
      when( launcher.launch( protocol ) ).thenReturn( false );
      assertThat( systemUnderTest.launch( release ), is( false ) );
   }//End Method

}//End Class
