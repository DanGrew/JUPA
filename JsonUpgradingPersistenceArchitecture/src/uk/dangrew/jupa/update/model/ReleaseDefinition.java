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

/**
 * {@link ReleaseDefinition} defines an individual release as a set of {@link String} values
 * parsed from a releases file.
 */
public class ReleaseDefinition {

   private final String identification;
   private final String downloadLocation;
   private final String description;
   private final String date;
   private final String artifactName;
   
   /**
    * Constructs a new {@link ReleaseDefinition}.
    * @param identification the identification of the release, or the version number.
    * @param downloadLocation the location that the release can be downloaded from.
    * @param description the description of the release, maybe containing changelog information.
    */
   public ReleaseDefinition( String identification, String downloadLocation, String description ) {
      this( identification, downloadLocation, description, null );
   }//End Constructor
   
   /**
    * Constructs a new {@link ReleaseDefinition}.
    * @param identification the identification of the release, or the version number.
    * @param downloadLocation the location that the release can be downloaded from.
    * @param description the description of the release, maybe containing changelog information.
    * @param date the date associated, can be null.
    */
   public ReleaseDefinition( String identification, String downloadLocation, String description, String date ) {
      if ( identification == null || downloadLocation == null || description == null ) {
         throw new IllegalArgumentException( "Must not provide null arguments." );
      }
      
      this.identification = identification;
      this.downloadLocation = downloadLocation;
      this.description = description;
      this.date = date;
      this.artifactName = extractArtifactName( downloadLocation );
   }//End Constructor
   
   /**
    * Method to extract the artifact from the download location.
    * @param downloadLocation the downloadLocation to extract from.
    * @return the artifact name.
    */
   private static String extractArtifactName( String downloadLocation ){
      String[] webLinkParts = downloadLocation.split( "//" );
      if ( webLinkParts.length < 2 ) {
         return downloadLocation;
      }
      
      String parsingPart = webLinkParts[ webLinkParts.length - 1 ];
      String[] subFolderSplit = parsingPart.split( "/" );
      if ( subFolderSplit.length < 2 ) {
         return downloadLocation;
      }
      
      return subFolderSplit[ subFolderSplit.length - 1 ];
   }//End Method

   /**
    * Getter for the identification of the release, or the version number.
    * @return the value.
    */
   public String getIdentification() {
      return identification;
   }//End Method

   /**
    * Getter for the location that the release can be downloaded from.
    * @return the value.
    */
   public String getDownloadLocation() {
      return downloadLocation;
   }//End Method

   /**
    * Getter for the description of the release, maybe containing changelog information.
    * @return the value.
    */
   public String getDescription() {
      return description;
   }//End Method
   
   /**
    * Getter for the date associated with the release.
    * @return the date in no particular format, can be null.
    */
   public String getDate() {
      return date;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ( ( date == null ) ? 0 : date.hashCode() );
      result = prime * result + ( ( description == null ) ? 0 : description.hashCode() );
      result = prime * result + ( ( downloadLocation == null ) ? 0 : downloadLocation.hashCode() );
      result = prime * result + ( ( identification == null ) ? 0 : identification.hashCode() );
      return result;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean equals( Object obj ) {
      if ( this == obj ) {
         return true;
      }
      if ( obj == null ) {
         return false;
      }
      if ( !( obj instanceof ReleaseDefinition ) ) {
         return false;
      }
      ReleaseDefinition other = ( ReleaseDefinition ) obj;
      if ( date != other.date ) {
         if ( date != null && date.equals( other.date ) ) {
            //equals
         } else {
            return false;
         }
      }
      if ( !description.equals( other.description ) ) {
         return false;
      }
      if ( !downloadLocation.equals( other.downloadLocation ) ) {
         return false;
      }
      if ( !identification.equals( other.identification ) ) {
         return false;
      }
      return true;
   }//End Method
   
   /**
    * Method to get the artifact name, extracted from the download location.
    * @return the artifact name.
    */
   public String getArtifactName(){
      return artifactName;
   }//End Method

}//End Class
