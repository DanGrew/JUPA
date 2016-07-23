/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.view.button;

import javafx.scene.layout.BorderPane;
import uk.dangrew.sd.core.lockdown.DigestProgressReceiver;
import uk.dangrew.sd.core.lockdown.DigestProgressReceiverImpl;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.progress.Progress;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.progressbar.model.DigestProgressBar;

/**
 * The {@link DownloadProgress} provides a {@link DigestProgressBar} that receives digest 
 * information from the given {@link Source}.
 */
public class DownloadProgress extends BorderPane implements DigestProgressReceiver {
   
   private final Source source;
   private final DigestProgressBar bar;
   
   /**
    * Constructs a new {@link DownloadProgress}.
    * @param progressSource the associated {@link Source}.
    */
   public DownloadProgress( Source progressSource ) {
      this.source = progressSource;
      this.bar = new DigestProgressBar( this.source );
      setCenter( this.bar );
      
      new DigestProgressReceiverImpl( this );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void progress( Source source, Progress progress, Message message ) {
      bar.handleProgress( source, progress, message );
   }//End Method 
   
   /**
    * Method to determine whether the {@link DownloadProgress} is associated with the given {@link Source}.
    * @param source the {@link Source} in question.
    * @return true if associated.
    */
   boolean hasSource( Source source ) {
      return this.source.equals( source );
   }//End Method

   DigestProgressBar bar() {
      return bar;
   }//End Method

}//End Class
