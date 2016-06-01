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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayParseHandler;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayWithObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.write.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonArrayWithObjectWriteHandler;
import uk.dangrew.jupa.json.write.handle.key.JsonArrayWriteHandler;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;

/**
 * Convenience object for creating test structures.
 */
class StringModelParsing {
   
   static final String FIRST_NAME_VALUE = "Dan";
   static final String LAST_NAME_VALUE = "Grew";
   static final List< String > SKILLS_VALUE = Arrays.asList( "Java", "Testing", "Jenkins", "Design" );
   static final List< Project > PROJECTS_VALUE = Arrays.asList( 
            new Project( "JenkinsTestTracker", "JenkinsTestTracker", "GitHub" ),
            new Project( "SystemDigest", "SystemDigest", "GitHub" ),
            new Project( "JUPA", "JsonUpgradingPersistenceArchitecture", "GitHub" )
   );
   
   /**
    * Method to construct a {@link StringModel} with sample data to test against.
    * @return the {@link StringModel}.
    */
   StringModel constructStringModelWithData(){
      StringModel model = new StringModel();
      model.developer = new Developer();
      model.developer.firstName = FIRST_NAME_VALUE;
      model.developer.lastName = LAST_NAME_VALUE;
      model.developer.skills = new ArrayList<>();
      model.developer.skills.addAll( SKILLS_VALUE );
      model.developer.projects = new ArrayList<>();
      model.developer.projects.addAll( PROJECTS_VALUE );
      
      return model;
   }//End Method

   /**
    * Method to construct a {@link JsonParser} with read handles.
    * @param model the {@link StringModel} to handle read.
    * @return the {@link JsonParser}.
    */
   JsonParser constructParserWithReadHandles( StringModel model ){
      JsonParser parser = new JsonParser();
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
      
      return parser;
   }//End Method
   
   /**
    * Method to construct the {@link JsonStructure} to build {@link org.json.JSONObject}s.
    * @param skillsSize the number of skills.
    * @param projectsSize the number of projects.
    * @return the {@link JsonStructure}.
    */
   JsonStructure constructStructure( int skillsSize, int projectsSize ) {
      JsonStructure structure = new JsonStructure();
      structure.child( StringModel.FIRST_NAME, structure.root() );
      structure.child( StringModel.LAST_NAME, structure.root() );
      structure.array( StringModel.SKILLS, structure.root() );
      structure.array( StringModel.PROJECTS, structure.root() );
      structure.child( StringModel.PROJECT, StringModel.PROJECTS );
      structure.child( StringModel.PROJECT_NAME, StringModel.PROJECT );
      structure.child( StringModel.PROJECT_FULL_NAME, StringModel.PROJECT );
      structure.child( StringModel.VCS, StringModel.PROJECT );
      
      structure.arraySize( StringModel.SKILLS, skillsSize );
      structure.arraySize( StringModel.PROJECTS, projectsSize );
      
      return structure;
   }//End Method
   
   /**
    * Method to construct a {@link JsonParser} with write handles using the {@link StringModel}.
    * @param model the {@link StringModel} providing the handles.
    * @return the {@link JsonParser}.
    */
   JsonParser constructParserWithWriteHandles( StringModel model ){
      JsonParser parser = new JsonParser();
      
      parser.when( StringModel.FIRST_NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( model::getFirstName ) ) );
      parser.when( StringModel.LAST_NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( model::getLastName ) ) );
      parser.when( StringModel.SKILLS, new JsonWriteHandleImpl( new JsonArrayWriteHandler( model::getSkill ) ) );
      parser.when( StringModel.PROJECTS, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               model::beginProjectWrite, model::endProjectWrite, null, null 
      ) ) );
      parser.when( StringModel.PROJECT_NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( model::getProjectName ) ) );
      parser.when( StringModel.PROJECT_FULL_NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( model::getProjectFullName ) ) );
      parser.when( StringModel.VCS, new JsonWriteHandleImpl( new JsonValueWriteHandler( model::getVcs ) ) );
      return parser;
   }//End Method
   
}//End Class
