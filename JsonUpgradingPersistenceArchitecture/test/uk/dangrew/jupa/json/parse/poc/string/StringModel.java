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

import java.util.ArrayList;

/**
 * Basic model for handling parsing of {@link String} values.
 */
public class StringModel {
   
   Developer developer;
   private Project projectInProgress;
   
   /**
    * Constructs a new {@link StringModel}.
    */
   StringModel() {
      developer = new Developer();
   }//End Constructor

   /**
    * Method to handle the parsing of the first name.
    * @param key the key parsed.
    * @param value the value parsed.
    */
   void firstName( String key, String value ){
      developer.firstName = value;
   }//End Method
   
   /**
    * Method to handle the parsing of the last name.
    * @param key the key parsed.
    * @param value the value parsed.
    */
   void lastName( String key, String value ){
      developer.lastName = value;
   }//End Method
   
   /**
    * Method to handle the start of skills parsing.
    * @param key the key parsed.
    */
   void skillsFound( String key ) {
      developer.skills = new ArrayList<>();
   }//End Method
   
   /**
    * Method to handle the parsing of a skill.
    * @param key the key parsed.
    * @param value the value parsed.
    */
   void skill( String key, String value ){
      developer.skills.add( value );
   }//End Method
   
   /**
    * Method to handle the completion of the skills.
    * @param key the key parsed.
    */
   void skillsComplete( String key ) {
      //do nothing
   }//End Method
   
   /**
    * Method to handle the start of the projects.
    * @param key the key parsed.
    */
   void projectsFound( String key ) {
      developer.projects = new ArrayList<>();
   }//End Method
   
   /**
    * Method to handle the start of a project.
    * @param key the key parsed.
    */
   void projectFound( String key ){
      projectInProgress = new Project();
   }//End Method
   
   /**
    * Method to handle the parsing of the project name.
    * @param key the key parsed.
    * @param value the value parsed.
    */
   void projectName( String key, String value ){
      projectInProgress.projectName = value;
      if ( projectInProgress.fullProjectName == null ) {
         projectInProgress.fullProjectName = value;
      }
   }//End Method
   
   /**
    * Method to handle the parsing of the full project name.
    * @param key the key parsed.
    * @param value the value parsed.
    */
   void projectFullName( String key, String value ){
      projectInProgress.fullProjectName = value;
   }//End Method
   
   /**
    * Method to handle the parsing of the vcs.
    * @param key the key parsed.
    * @param value the value parsed.
    */
   void vcs( String key, String value ){
      projectInProgress.vcs = value;
   }//End Method
   
   /**
    * Method to handle the completion of a project.
    * @param key the key parsed.
    * @param value the value parsed.
    */
   void projectComplete( String key ) {
      developer.projects.add( projectInProgress );
      projectInProgress = null;
   }//End Method
   
   /**
    * Method to handle the completion of the projects.
    * @param key the key parsed.
    * @param value the value parsed.
    */
   void projectsComplete( String key ) {
      //do nothing
   }//End Method
}//End Class
