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

/**
 * Basic model for handling parsing of {@link String} values.
 */
public class StringModel {
   
   static final String FIRST_NAME = "firstName";
   static final String LAST_NAME = "lastName";
   static final String SKILLS = "skills";
   static final String PROJECTS = "projects";
   static final String PROJECT_NAME = "projectName";
   static final String VCS = "vcs";
   static final String PROJECT_FULL_NAME = "projectFullName";
   static final String PROJECT = "project";
   
   Developer developer;
   
   private int projectIndex;
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
    * Method to get the first name value.
    * @param key the first name key.
    * @return the first name.
    */
   String getFirstName( String key ) {
      return developer.firstName;
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
    * Getter for the last name.
    * @param key the last name key.
    * @return the last name.
    */
   String getLastName( String key ) {
      return developer.lastName;
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
    * Getter for a skill at a particular index.
    * @param key the skill key.
    * @param index the index to get the value for.
    * @return the skill at that index.
    */
   String getSkill( String key, Integer index ) {
      return developer.skills.get( index );
   }//End Method
   
   /**
    * Method the get the number of skills in the model.
    * @param key the skills key.
    * @return the number of skills.
    */
   Integer getSkillCount( String key ){
      return developer.skills.size();
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
    * Method to get the number of project held.
    * @param key the projects key.
    * @return the number of projects held.
    */
   Integer getProjectCount( String key ){
      return developer.projects.size();
   }//End Method
   
   /**
    * Begin writing a new {@link Project}.
    * @param key the project key.
    */
   void beginProjectWrite( String key ){
      projectInProgress = developer.projects.get( projectIndex );
   }//End Method
   
   /**
    * End writing the current {@link Project}.
    * @param key the project key.
    */
   void endProjectWrite( String key ){
      projectInProgress = null;
      projectIndex++;
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
    * Getter for the current {@link Project} name.
    * @param key the project name key.
    */
   Object getProjectName( String key ) {
      return projectInProgress.projectName;
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
    * Getter for the current {@link Project} full name.
    * @param key the project full name key.
    */
   Object getProjectFullName( String key ) {
      return projectInProgress.fullProjectName;
   }//End Method
   
   /**
    * Method to handle the parsing of the vcs.
    * @param key the key parsed.
    * @param value the value parsed.
    */
   void vcs( String key, String value ){
      projectInProgress.vcs = value;
   }//End Method
   
   Object getVcs( String key ) {
      return projectInProgress.vcs;
   }
   
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
