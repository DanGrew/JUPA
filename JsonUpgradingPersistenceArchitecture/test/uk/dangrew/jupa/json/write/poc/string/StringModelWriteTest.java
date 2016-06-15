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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.dangrew.jupa.json.write.poc.string.StringModelParsing.FIRST_NAME_VALUE;
import static uk.dangrew.jupa.json.write.poc.string.StringModelParsing.LAST_NAME_VALUE;
import static uk.dangrew.jupa.json.write.poc.string.StringModelParsing.PROJECTS_VALUE;
import static uk.dangrew.jupa.json.write.poc.string.StringModelParsing.SKILLS_VALUE;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.structure.JsonStructure;

/**
 * Proof of concept test to prove a string model can be written.
 */
public class StringModelWriteTest {

   private StringModel model;
   private StringModelParsing parsing;
   private JSONObject jsonObject;
   
   @Before public void initialiseSystemUnderTest(){
      jsonObject = new JSONObject();
      
      parsing = new StringModelParsing();
      
      model = parsing.constructStringModelWithData();
      JsonStructure structure = parsing.constructStructure( model );
      structure.build( jsonObject );
   }//End Method
   
   @Test public void proofOfConceptTest() {
      JsonParser parser = parsing.constructParserWithWriteHandles( model );
      parser.parse( jsonObject );
      
      assertThat( jsonObject.get( StringModel.FIRST_NAME ), is( FIRST_NAME_VALUE ) );
      assertThat( jsonObject.get( StringModel.LAST_NAME ), is( LAST_NAME_VALUE ) );
      
      JSONArray skills = jsonObject.getJSONArray( StringModel.SKILLS );
      assertThat( skills.length(), is( SKILLS_VALUE.size() ) );
      for ( int i = 0; i < SKILLS_VALUE.size(); i++ ) {
         assertThat( skills.get( i ), is( SKILLS_VALUE.get( i ) ) );
      }
      
      JSONArray projects = jsonObject.getJSONArray( StringModel.PROJECTS );
      assertThat( projects.length(), is( PROJECTS_VALUE.size() ) );
      for ( int i = 0; i < PROJECTS_VALUE.size(); i++ ) {
         JSONObject project = projects.getJSONObject( i );
         assertThat( project.get( StringModel.PROJECT_NAME ), is( PROJECTS_VALUE.get( i ).projectName ) );
         assertThat( project.get( StringModel.PROJECT_FULL_NAME ), is( PROJECTS_VALUE.get( i ).fullProjectName ) );
         assertThat( project.get( StringModel.VCS ), is( PROJECTS_VALUE.get( i ).vcs ) );
      }
   }//End Method

}//End Class
