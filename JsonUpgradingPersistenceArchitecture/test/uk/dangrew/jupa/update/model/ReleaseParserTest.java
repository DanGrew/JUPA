/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.model;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.TestCommon;

/**
 * {@link ReleaseParser} test.
 */
public class ReleaseParserTest {
   
   private ReleaseParser systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      systemUnderTest = new ReleaseParser();
   }//End Method

   @Test public void shouldExtractSingleReleaseDefinition() {
      String testCase = TestCommon.readFileIntoString( getClass(), "single-release.txt" );
      List< ReleaseDefinition > definitions = systemUnderTest.parse( testCase );
      
      assertThat( definitions, hasSize( 1 ) );
      assertThat( definitions.get( 0 ), is( new ReleaseDefinition( "release-value", "download-value", "description-value" ) ) );
   }//End Method
   
   @Test public void shouldExtractSingleReleaseDefinitionWithCommas() {
      String testCase = TestCommon.readFileIntoString( getClass(), "single-release-using-commas.txt" );
      List< ReleaseDefinition > definitions = systemUnderTest.parse( testCase );
      
      assertThat( definitions, hasSize( 1 ) );
      assertThat( definitions.get( 0 ), is( new ReleaseDefinition( 
               "3,0,1",
               "some-site/mine,yours,theirs",
               "Ohh where do I being... this is an awesome release, and you should all use it,,,," 
      ) ) );
   }//End Method
   
   @Test public void shouldIgnoreSingleReleaseDefinitionWithMoreThanTwoItemsOnALine() {
      String testCase = TestCommon.readFileIntoString( getClass(), "single-release-invalid-description.txt" );
      List< ReleaseDefinition > definitions = systemUnderTest.parse( testCase );
      
      assertThat( definitions, is( empty() ) );
   }//End Method
   
   @Test public void shouldExtractMultipleReleaseDefinitions() {
      String testCase = TestCommon.readFileIntoString( getClass(), "multiple-releases.txt" );
      List< ReleaseDefinition > definitions = systemUnderTest.parse( testCase );
      
      assertThat( definitions, hasSize( 3 ) );
      assertThat( definitions.get( 0 ), is( new ReleaseDefinition( "1.4.56", "some-location/1.4.56", "this is the first release" ) ) );
      assertThat( definitions.get( 1 ), is( new ReleaseDefinition( "1.4.57", "some-location/1.4.57", "this is the next release" ) ) );
      assertThat( definitions.get( 2 ), is( new ReleaseDefinition( "1.4.60", "some-location/1.4.60", "this is the final release" ) ) );
   }//End Method
   
   @Test public void shouldIgnoreMissingReleaseIdentification() {
      String testCase = TestCommon.readFileIntoString( getClass(), "single-release-missing-identification.txt" );
      List< ReleaseDefinition > definitions = systemUnderTest.parse( testCase );
      
      assertThat( definitions, is( empty() ) );
   }//End Method
   
   @Test public void shouldIgnoreMissingDownloadLocation() {
      String testCase = TestCommon.readFileIntoString( getClass(), "single-release-missing-download.txt" );
      List< ReleaseDefinition > definitions = systemUnderTest.parse( testCase );
      
      assertThat( definitions, is( empty() ) );
   }//End Method
   
   @Test public void shouldIgnoreMissingDescription() {
      String testCase = TestCommon.readFileIntoString( getClass(), "single-release-missing-description.txt" );
      List< ReleaseDefinition > definitions = systemUnderTest.parse( testCase );
      
      assertThat( definitions, is( empty() ) );
   }//End Method
   
   @Test public void shouldIgnoreInvalidReleasesAndParseValid() {
      String testCase = TestCommon.readFileIntoString( getClass(), "complete-release-list-with-invalid.txt" );
      List< ReleaseDefinition > definitions = systemUnderTest.parse( testCase );
      
      assertThat( definitions, hasSize( 1 ) );
      assertThat( definitions.get( 0 ), is( new ReleaseDefinition( "1.4.61", "some-location/1.4.61", "this is the only valid release" ) ) );
   }//End Method
   
   @Test public void shouldIgnoreInvalidEmptyString() {
      List< ReleaseDefinition > definitions = systemUnderTest.parse( "    " );
      assertThat( definitions, is( empty() ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullString() {
      systemUnderTest.parse( null );
   }//End Method
   
   @Test public void shouldIgnoreIrrelevantData() {
      List< ReleaseDefinition > definitions = systemUnderTest.parse( "some not relevant" );
      assertThat( definitions, is( empty() ) );
   }//End Method
   
}//End Class
