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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import uk.dangrew.jupa.file.utility.IoStreaming;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.logging.location.JarProtocol;

/**
 * The {@link FileStreamerImpl} is responsible for streaming a file from a download
 * location into a new file.
 */
public class FileStreamerImpl implements FileStreamer {
   
   private final FileStreamerDigest digest;
   private final IoStreaming streamer;
   private final HttpClient client;
   
   /**
    * Constructs a new {@link FileStreamer}.
    */
   public FileStreamerImpl() {
      this( new DefaultHttpClient(), new FileStreamerDigest(), new IoStreaming() );
   }//End Constructor
   
   /**
    * Constructs a new {@link FileStreamerImpl}.
    * @param client the {@link HttpClient} to execute requests with.
    * @param digest the {@link FileStreamerDigest} to use.
    * @param streamer the {@link IoStreaming} for io operations.
    */
   FileStreamerImpl( HttpClient client, FileStreamerDigest digest, IoStreaming streamer ) {
      this.digest = digest;
      this.digest.attachSource( this );
      this.streamer = streamer;
      this.client = client;
   }//End Constructor
   
   /**
    * Method to stream the item found at the given download link location.
    * @param downloadLink the location to stream from.
    * @param fileProtocol the {@link JarProtocol} for the {@link java.io.File} to stream to. This will
    * be overwritten regardless of the state.
    * @throws IOException if any exception occurs during stream such that the streaming is aborted.
    */
   @Override public void streamFile( String downloadLink, JarProtocol fileProtocol ) throws IOException  {
      if ( 
         downloadLink == null || downloadLink.trim().length() == 0 || 
         fileProtocol == null || fileProtocol.getSource() == null 
      ) {
         throw new IllegalArgumentException( "Must not provide null or empty parameters." );
      }
      
      HttpGet httpget = new HttpGet( downloadLink );
      HttpResponse response = client.execute( httpget );
      if ( response == null ) {
         throw new IOException( "No HttpResponse provided for executed request." );
      }
      HttpEntity entity = response.getEntity();
      if ( entity == null ) {
         throw new IOException( "No entity provided in HttpResponse." );
      }
      
      try ( 
         BufferedInputStream contentStream = streamer.constructBufferedInputStream( entity.getContent() );
         BufferedOutputStream outputStream = streamer.constructBufferedOutputStream( fileProtocol.getSource() ) 
      ) {
         long length = entity.getContentLength();
         digest.downloadingContentWithLength( length );
         streamToNewFile( contentStream, outputStream );
      } catch ( IOException exception ) {
         digest.streamingCancelled();
         throw exception;
      }
   }//End Method
   
   /**
    * Method to perform the streaming.
    * @param contentStream the {@link BufferedInputStream} to stream from.
    * @param outputStream the {@link BufferedOutputStream} to stream to.
    * @throws IOException if any io operation fails and can't continue.
    */
   private void streamToNewFile( BufferedInputStream contentStream, BufferedOutputStream outputStream ) throws IOException{
      digest.startedStreaming();
      
      int streamedByte;
      while ( ( streamedByte = contentStream.read() ) != -1 ) {
         outputStream.write( streamedByte );
         digest.streamedByte();
      }
      digest.finishedStreaming();
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Source getSourceForProgress(){
      return digest.getSource();
   }//End Method

}//End Class
