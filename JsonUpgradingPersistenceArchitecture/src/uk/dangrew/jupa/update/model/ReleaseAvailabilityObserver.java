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

import java.util.List;

/**
 * The {@link ReleaseAvailabilityObserver} provides an interface for interested objects that
 * want to know when new {@link ReleaseDefinition}s are available.
 */
public interface ReleaseAvailabilityObserver {
   
   /**
    * Method to notify that {@link ReleaseDefinition}s are now available.
    * @param releases the {@link ReleaseDefinition}s.
    */
   public void releasesAreNowAvailable( List< ReleaseDefinition > releases );

}//End Class
