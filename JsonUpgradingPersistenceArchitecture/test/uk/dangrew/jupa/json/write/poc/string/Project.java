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

/** Simple data object for testing.**/
public final class Project {
   
   String projectName;
   String fullProjectName;
   String vcs;
   
   /** Blank constructor. */
   Project() {}
   
   /**
    * Constructs a new {@link Project}.
    * @param projectName the name of the project.
    * @param fullProjectName the full name of the project.
    * @param vcs the vcs.
    */
   Project( String projectName, String fullProjectName, String vcs ) {
      this.projectName = projectName;
      this.fullProjectName = fullProjectName;
      this.vcs = vcs;
   }//End Constructor

}//End Class
