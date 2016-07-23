/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.stream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * {@link ArtifactLocationGenerator} test.
 */
public class ArtifactLocationGeneratorTest {

   private ReleaseDefinition release;
   private ArtifactLocationGenerator systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      release = new ReleaseDefinition( "identification", "downloadLocation", "description" );
      systemUnderTest = new ArtifactLocationGenerator( getClass() );
   }//End Method
   
   @Test public void shouldProduceProtocolAtCorrectLocation() {
      JarProtocol protocol = new JarProtocol( null, release.getArtifactName(), getClass() );
      assertThat( systemUnderTest.fetchJarLocation( release ).getLocation(), is( protocol.getLocation() ) );
   }//End Method
   
   @Test public void shoudlCacheProtocolsForReleases(){
      JarProtocol firstProtocol = systemUnderTest.fetchJarLocation( release );
      JarProtocol secondProtocol = systemUnderTest.fetchJarLocation( release );
      assertThat( firstProtocol, is( secondProtocol ) );
   }//End Method
   
   @Test public void shouldUseDifferentProtocolsForDifferentReleases(){
      JarProtocol firstProtocol = systemUnderTest.fetchJarLocation( release );
      JarProtocol secondProtocol = systemUnderTest.fetchJarLocation( new ReleaseDefinition( "a", "b", "c" ) );
      assertThat( firstProtocol, is( not( secondProtocol ) ) );
   }//End Method

}//End Class
