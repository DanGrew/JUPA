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

   private final String location;
   private final ReleasesDownloader downloader;
   private final ReleaseParser parser;
   private final ReleaseAvailabilityObserver observer;
   
   private final List< ReleaseDefinition > releases;
   
   /**
    * Constructs a new {@link ReleaseAvailableTask}.
    * @param location the {@link String} location of the releases.
    * @param observer the {@link ReleaseAvailabilityObserver} to notify when the releases change.
    */
   public ReleaseAvailableTask( String location, ReleaseAvailabilityObserver observer ) {
      this( location, new ReleasesDownloader(), new ReleaseParser(), observer );
   }//End Constructor
   
   /**
    * Constructs a new {@link ReleaseAvailableTask}.
    * @param location the {@link String} location of the releases.
    * @param downloader the {@link ReleasesDownloader} to use.
    * @param parser the {@link ReleaseParser} to use.
    * @param observer the {@link ReleaseAvailabilityObserver} to notify when the releases change.
    */
   public ReleaseAvailableTask( String location, ReleasesDownloader downloader, ReleaseParser parser, ReleaseAvailabilityObserver observer ){
      this.location = location;
      this.downloader = downloader;
      this.parser = parser;
      this.observer = observer;
      
      this.releases = new ArrayList<>();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void run() {
      String downloaded = downloader.downloadContent( location );
      if ( downloaded == null ) {
         return;
      }
      
      List< ReleaseDefinition > parsedReleases = parser.parse( downloaded );
      if ( parsedReleases.equals( releases ) ) {
         return;
      }
      
      releases.clear();
      releases.addAll( parsedReleases );
      observer.releasesAreNowAvailable( parsedReleases );
   }//End Method

}//End Class
