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
import java.util.TimerTask;

import uk.dangrew.jupa.update.model.ReleaseAvailabilityObserver;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.jupa.update.model.ReleaseParser;

/**
 * The {@link ReleaseAvailableTask} provides a {@link TimerTask} that downloads {@link ReleaseDefinition}s
 * from a given location and notifies an observer when they change.
 */
public class ReleaseAvailableTask extends TimerTask {

   private final ReleasesDownloader downloader;
   private final ReleaseParser parser;
   private final ReleaseUpgradeChecker checker;
   private final ReleaseAvailabilityObserver observer;
   
   private final List< ReleaseDefinition > releases;
   
   /**
    * Constructs a new {@link ReleaseAvailableTask}.
    * @param currentVersion the current version of the software.
    * @param downloader the {@link ReleasesDownloader} to use.
    * @param observer the {@link ReleaseAvailabilityObserver} to notify when the releases change.
    */
   public ReleaseAvailableTask( String currentVersion, ReleasesDownloader downloader, ReleaseAvailabilityObserver observer ) {
      this( downloader, new ReleaseParser(), new ReleaseUpgradeChecker( currentVersion ), observer );
   }//End Constructor
   
   /**
    * Constructs a new {@link ReleaseAvailableTask}.
    * @param downloader the {@link ReleasesDownloader} to use.
    * @param parser the {@link ReleaseParser} to use.
    * @param checker the {@link ReleaseUpgradeChecker} to use.
    * @param observer the {@link ReleaseAvailabilityObserver} to notify when the releases change.
    */
   ReleaseAvailableTask( ReleasesDownloader downloader, ReleaseParser parser, ReleaseUpgradeChecker checker, ReleaseAvailabilityObserver observer ){
      this.downloader = downloader;
      this.parser = parser;
      this.checker = checker;
      this.observer = observer;
      
      this.releases = new ArrayList<>();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void run() {
      String downloaded = downloader.downloadContent();
      if ( downloaded == null ) {
         return;
      }
      
      List< ReleaseDefinition > parsedReleases = parser.parse( downloaded );
      if ( parsedReleases.equals( releases ) ) {
         return;
      }
      
      releases.clear();
      releases.addAll( parsedReleases );
      
      List< ReleaseDefinition > filtered = checker.filterBasedOnCurrentVersion( parsedReleases );
      observer.releasesAreNowAvailable( filtered );
   }//End Method
   
}//End Class
