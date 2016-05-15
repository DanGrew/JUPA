/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse.poc.string;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.TestCommon;
import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayHandler;
import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandler;
import uk.dangrew.jupa.json.parse.handle.type.StringTypeHandle;

/**
 * Proof of concept test to prove that a simple string model can be parsed in.
 */
public class StringModelParseTest {
   
   private static final String FIRST_NAME = "firstName";
   private static final String LAST_NAME = "lastName";
   private static final String SKILLS = "skills";
   private static final String PROJECTS = "projects";
   private static final String PROJECT_NAME = "projectName";
   private static final String VCS = "vcs";
   private static final String PROJECT_FULL_NAME = "projectFullName";
   
   private StringModel model;
   private JsonParser parser;

   @Before public void initialiseSystemUnderTest(){
      model = new StringModel();
      
      parser = new JsonParser();
      parser.when( FIRST_NAME, new StringTypeHandle( model::firstName ) );
      parser.when( LAST_NAME, new StringTypeHandle( model::lastName ) );
      
      JsonArrayHandler< String > skillsHandler = new JsonArrayHandler<>( 
               model::skill, model::skillsFound, model::skillsComplete 
      );
      parser.when( SKILLS, new StringTypeHandle( skillsHandler ) );
      
      JsonKeyHandler< String > projectsHandler = new JsonKeyHandler<>( 
               null, model::projectFound, model::projectComplete, model::projectsFound, model::projectsComplete 
      );
      parser.when( PROJECTS, new StringTypeHandle( projectsHandler ) );
      
      parser.when( PROJECT_NAME, new StringTypeHandle( model::projectName ) );
      parser.when( PROJECT_FULL_NAME, new StringTypeHandle( model::projectFullName ) );
      parser.when( VCS, new StringTypeHandle( model::vcs ) );
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
