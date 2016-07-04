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
   
   private final String location;
   private final HttpClient client;
   private final HttpGet get;
   private final HttpContext getContext;
   
   /**
    * Constructs a new {@link ReleasesDownloader}.
    * @param url the {@link String} location.
    */
   public ReleasesDownloader( String url ) {
      this( url, new DefaultHttpClient( new BasicHttpParams() ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link ReleasesDownloader}.
    * @param url the {@link String} location.
    * @param client the {@link HttpClient} to connect with.
    */
   ReleasesDownloader( String url, HttpClient client ) {
      if ( url == null ) {
         throw new IllegalArgumentException( "Must provide non null url." );
      }
      
      this.location = url;
      this.client = client;
      this.get = new HttpGet( url );
      this.getContext = new BasicHttpContext();
   }//End Constructor

   /**
    * Method to download the {@link String} content from the associated file address url.
    * @return the content downloaded, or null if failed for some reason.
    */
   public String downloadContent(){
      try {
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

   /**
    * Getter for the location to download from.
    * @return the location constructed with.
    */
   public String getDownloadLocation() {
      return location;
   }//End Method
   
}//End Class
