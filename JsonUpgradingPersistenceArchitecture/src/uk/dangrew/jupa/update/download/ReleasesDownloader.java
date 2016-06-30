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

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * The {@link ReleasesDownloader} is responsible for connecting to a public file location
 * and downloading the {@link String} content.
 */
public class ReleasesDownloader {
   
   private final HttpClient client;
   
   /**
    * Constructs a new {@link ReleasesDownloader}.
    */
   public ReleasesDownloader() {
      this( new DefaultHttpClient( new BasicHttpParams() ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link ReleasesDownloader}.
    * @param client the {@link HttpClient} to connect with.
    */
   ReleasesDownloader( HttpClient client ) {
      this.client = client;
   }//End Constructor

   /**
    * Method to download the {@link String} content from the given file address url.
    * @param url the {@link String} location.
    * @return the content downloaded, or null if failed for some reason.
    */
   public String downloadContent( String url ){
      if ( url == null ) {
         throw new IllegalArgumentException( "Must provide non null url." );
      }
      
      try {
         HttpGet get = new HttpGet( url );
         HttpContext getContext = new BasicHttpContext();
         HttpResponse response = client.execute( get, getContext );
         return handleResponse( response );
      } catch ( HttpResponseException exception ) {
         exception.printStackTrace();
      } catch ( ClientProtocolException exception ) {
         exception.printStackTrace();
      } catch ( IOException exception ) {
         exception.printStackTrace();
      } catch ( IllegalStateException exception ) {
         //bad practice but I don't want the program to crash with something I can't check for!
         exception.printStackTrace();
      }
      return null;
   }//End Method
   
   /**
    * Method to handle the {@link HttpResponse} and close the stream correctly.
    * @param response the {@link HttpResponse} to handle.
    * @return the handled {@link String} response.
    * @throws IOException if there a problem or the connection is aborted.
    */
   private String handleResponse( HttpResponse response ) throws IOException {
      try {
         return new BasicResponseHandler().handleResponse( response );
      } finally {
         if ( response.getEntity() != null ) {
            EntityUtils.consume( response.getEntity() );
         }
      }
   }//End Method
   
}//End Class
