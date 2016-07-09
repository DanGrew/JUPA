/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.file.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * {@link IoStreaming} provides some abstraction for handling streams of data that make testing
 * and verification easier.
 */
public class IoStreaming {
   
   /**
    * Method to construct a {@link BufferedInputStream} for the given {@link InputStream}.
    * @param inputStream the {@link InputStream} to wrap.
    * @return the {@link BufferedInputStream}, or null if null parameter.
    */
   public BufferedInputStream constructBufferedInputStream( InputStream inputStream ){
      if ( inputStream == null ) {
         return null;
      }
      return new BufferedInputStream( inputStream );
   }//End Method
   
   /**
    * Method to construct a {@link BufferedOutputStream} for the given {@link File}.
    * @param file the {@link File} to wrap.
    * @return the {@link BufferedOutputStream}, or null if null parameter.
    */
   public BufferedOutputStream constructBufferedOutputStream( File file ) throws FileNotFoundException {
      if ( file == null ) {
         return null;
      }
      return new BufferedOutputStream( new FileOutputStream( file ) );
   }//End Method

}//End Class
