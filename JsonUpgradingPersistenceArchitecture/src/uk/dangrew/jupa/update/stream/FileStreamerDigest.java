/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.stream;

import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.progress.Progresses;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;

/**
 * The {@link FileStreamerDigest} provides an {@link ObjectDigestImpl} connection
 * when downloading using {@link FileStreamer}.
 */
public class FileStreamerDigest extends ObjectDigestImpl {

   static final String STARTED_STREAMING = "File streaming has begun.";
   static final String NAME = "FileStreamer";
   static final String FINISHED_STREAMING = "File streaming has completed.";
   static final String CANCELLED_STREAMING = "File streaming has failed.";
   static final String STREAMING_PERCENTAGE = "% of file streamed";
   
   private int progressInterval;
   private int progressToPercent;
   private int totalProgress;
   
   /**
    * Method to attach the {@link FileStreamer} as {@link uk.dangrew.sd.core.source.Source}.
    * @param fileStreamer the {@link FileStreamer} to attach.
    */
   void attachSource( FileStreamer fileStreamer ) {
      super.attachSource( new SourceImpl( fileStreamer, NAME ) );
   }//End Method
   
   /**
    * Method to prepare the download given the length expected.
    * @param length the length of the download in bytes.
    */
   void downloadingContentWithLength( long length ) {
      if ( length <= 0 ) {
         progressInterval = 0;
      } else if ( length < 100 ) {
         progressInterval = 1;
      } else {
         progressInterval = ( int )( length / 100.0 );
      }
      progressToPercent = 0;
      totalProgress = 0;
   }//End Method
   
   /**
    * Method to digest the start of the streaming process.
    */
   void startedStreaming() {
      progress( Progresses.simpleProgress( 0 ), Messages.simpleMessage( STARTED_STREAMING ) );
      log( Categories.information(), Messages.simpleMessage( STARTED_STREAMING ) );
   }//End Method

   /**
    * Method to digest a single byte being downloaded.
    */
   void streamedByte() {
      if ( progressInterval != 0 ) {
         progressToPercent++;
      }
      if ( progressToPercent >= progressInterval ) {
         if ( progressInterval != 0 ) {
            totalProgress++;
         }
         progress( Progresses.simpleProgress( totalProgress ), Messages.simpleMessage( totalProgress + STREAMING_PERCENTAGE ) );
         progressToPercent = 0;
      }
   }//End Method

   /**
    * Method to digest the finishing of the streaming.
    */
   void finishedStreaming() {
      progress( Progresses.simpleProgress( 100 ), Messages.simpleMessage( FINISHED_STREAMING ) );
      log( Categories.information(), Messages.simpleMessage( FINISHED_STREAMING ) );
   }//End Method

   /**
    * Method to digest the cancellation of the download.
    */
   void streamingCancelled() {
      progress( Progresses.simpleProgress( 100 ), Messages.simpleMessage( CANCELLED_STREAMING ) );
      log( Categories.error(), Messages.simpleMessage( CANCELLED_STREAMING ) );
   }//End Method
   
}//End Class
