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

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * {@link BasicSystemHandover} test.
 */
public class BasicSystemHandoverTest {

   private BasicSystemHandover systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      systemUnderTest = new BasicSystemHandover( getClass() );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullClass() {
      new BasicSystemHandover( null );
   }//End Method
   
   @Test public void shouldProvideGeneratorForRelativeClass() {
      ReleaseDefinition release = new ReleaseDefinition( "a", "b", "c" );
      assertThat( systemUnderTest.fetchJarLocation( release ).getLocation(), is( new JarProtocol( null, "b", getClass() ).getLocation() ) );
   }//End Method
   
   @Test public void shouldProvideEmptyShutdownFunction() {
      assertThat( systemUnderTest.launch( new ReleaseDefinition( "a", "b", "c" ) ), is( true ) );
   }//End Method

}//End Class
