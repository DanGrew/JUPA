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
   
   /**
    * Constructs a new {@link ReleaseDefinition}.
    * @param identification the identification of the release, or the version number.
    * @param downloadLocation the location that the release can be downloaded from.
    * @param description the description of the release, maybe containing changelog information.
    */
   public ReleaseDefinition( String identification, String downloadLocation, String description ) {
      if ( identification == null || downloadLocation == null || description == null ) {
         throw new IllegalArgumentException( "Must not provide null arguments." );
      }
      
      this.identification = identification;
      this.downloadLocation = downloadLocation;
      this.description = description;
   }//End Constructor

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
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
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

}//End Class
