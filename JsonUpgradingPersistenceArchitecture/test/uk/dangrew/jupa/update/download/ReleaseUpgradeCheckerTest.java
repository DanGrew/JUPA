/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.download;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * {@link ReleaseUpgradeChecker} test.
 */
public class ReleaseUpgradeCheckerTest {
   
   private static final String DEFAULT_VERSION = "V1";
   private ReleaseUpgradeChecker systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      systemUnderTest = new ReleaseUpgradeChecker( DEFAULT_VERSION );
   }//End Method
   
   /**
    * Method to construct a test {@link ReleaseDefinition}.
    * @param releaseName the name of the release, the only part relevant for testing.
    * @return the {@link ReleaseDefinition}.
    */
   private ReleaseDefinition constructRelease( String releaseName ) {
      return new ReleaseDefinition( releaseName, "anything", "anything" );
   }//End Method

   @Test public void shouldReturnAllWhenNoneMatchDefaultVersion() {
      List< ReleaseDefinition > releases = Arrays.asList( 
               constructRelease( "one" ), constructRelease( "two" ), constructRelease( "three" ) 
      );
      assertThat( systemUnderTest.filterBasedOnCurrentVersion( releases ), is( releases ) );
   }//End Method
   
   @Test public void shouldReturnAllAboveDefaultVersion() {
      List< ReleaseDefinition > releases = Arrays.asList( 
               constructRelease( "one" ), constructRelease( "two" ), constructRelease( DEFAULT_VERSION ) 
      );
      assertThat( systemUnderTest.filterBasedOnCurrentVersion( releases ), is( releases.subList( 0, 2 ) ) );
   }//End Method
   
   @Test public void shouldReturnNoneWhenDefaultVersionIsLatest() {
      List< ReleaseDefinition > releases = Arrays.asList( 
               constructRelease( DEFAULT_VERSION ), constructRelease( "two" ), constructRelease( "three" ) 
      );
      assertThat( systemUnderTest.filterBasedOnCurrentVersion( releases ), is( Collections.emptyList() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullRelease() {
      new ReleaseUpgradeChecker( null );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptEmptyRelease() {
      new ReleaseUpgradeChecker( "    " );
   }//End Method

}//End Class
