/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.write.poc.string;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.TestCommon;
import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayParseHandler;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayWithObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;

/**
 * Proof of concept test to prove that a simple string model can be parsed in.
 */
public class StringModelParseTest {
   
   private StringModel model;
   private JsonParser parser;

   @Before public void initialiseSystemUnderTest(){
      model = new StringModel();
      
      parser = new JsonParser();
      parser.when( StringModel.FIRST_NAME, new StringParseHandle( model::firstName ) );
      parser.when( StringModel.LAST_NAME, new StringParseHandle( model::lastName ) );
      
      JsonArrayParseHandler< String > skillsHandler = new JsonArrayParseHandler<>( 
               model::skill, model::skillsFound, model::skillsComplete 
      );
      parser.when( StringModel.SKILLS, new StringParseHandle( skillsHandler ) );
      
      JsonArrayWithObjectParseHandler< String > projectsHandler = new JsonArrayWithObjectParseHandler<>( 
               model::projectFound, model::projectComplete, model::projectsFound, model::projectsComplete 
      );
      parser.when( StringModel.PROJECTS, new StringParseHandle( projectsHandler ) );
      
      parser.when( StringModel.PROJECT_NAME, new StringParseHandle( model::projectName ) );
      parser.when( StringModel.PROJECT_FULL_NAME, new StringParseHandle( model::projectFullName ) );
      parser.when( StringModel.VCS, new StringParseHandle( model::vcs ) );
   }//End Method
   
   @Test public void proofOfConceptTest() {
      String input = TestCommon.readFileIntoString( getClass(), "string-model.json" );
      JSONObject inputObject = new JSONObject( input );
      parser.parse( inputObject );
      
      Developer developer = model.developer;
      assertThat( developer.firstName, is( "Dan" ) );
      assertThat( developer.lastName, is( "Grew" ) );
      
      assertThat( developer.skills, is( Arrays.asList( "Java", "Testing", "Maven", "Gradle" ) ) );
      assertThat( developer.projects, hasSize( 3 ) );
      
      Project jtt = developer.projects.get( 0 );
      assertThat( jtt.projectName, is( "JenkinsTestTracker" ) );
      assertThat( jtt.fullProjectName, is( jtt.projectName ) );
      assertThat( jtt.vcs, is( "GitHub" ) );
      
      Project sd = developer.projects.get( 1 );
      assertThat( sd.projectName, is( "SystemDigest" ) );
      assertThat( sd.fullProjectName, is( sd.projectName ) );
      assertThat( sd.vcs, is( "GitHub" ) );
      
      Project jupa = developer.projects.get( 2 );
      assertThat( jupa.projectName, is( "JUPA" ) );
      assertThat( jupa.fullProjectName, is( "JsonUpgradingPersistenceArchitecture" ) );
      assertThat( jupa.vcs, is( "GitHub" ) );
   }//End Method

}//End Class
