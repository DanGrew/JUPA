/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.download;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * The {@link ReleaseUpgradeChecker} is responsible for determining whether the identified
 * {@link ReleaseDefinition}s are relevant given the current version of the software.
 */
class ReleaseUpgradeChecker {

   private final String currentVersion;
   
   /**
    * Constructs a new {@link ReleaseUpgradeChecker}.
    * @param currentVersion the current software version.
    */
   ReleaseUpgradeChecker( String currentVersion ) {
      if ( currentVersion == null || currentVersion.trim().length() == 0 ) {
         throw new IllegalArgumentException( "Must supply valid, non-null, non-empty, version." );
      }
      
      this.currentVersion = currentVersion;
   }//End Constructor
   
   /**
    * Method to filter the {@link ReleaseDefinition}s given based on the current software version number.
    * @param releases the {@link ReleaseDefinition}s found to filter.
    * @return the {@link ReleaseDefinition}s that are newer than the current software. This assumes
    * that the given {@link ReleaseDefinition}s are ordered newest to oldest.
    */
   List< ReleaseDefinition > filterBasedOnCurrentVersion( List< ReleaseDefinition > releases ) {
      List< ReleaseDefinition > newReleases = new ArrayList<>();
      
      for ( ReleaseDefinition release : releases ) {
         if ( release.getIdentification().equals( currentVersion ) ) {
            break;
         }
         
         newReleases.add( release );
      }
      
      return newReleases;
   }//End Method

}//End Class
