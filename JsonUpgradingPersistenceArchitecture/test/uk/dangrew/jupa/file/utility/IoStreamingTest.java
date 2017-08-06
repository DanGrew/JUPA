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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.TestCommon;

/**
 * {@link IoStreaming} test.
 */
public class IoStreamingTest {

   private IoStreaming systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new IoStreaming();
   }// End Method

   @Test public void shouldConstructBufferedInputStream() throws IOException {
      InputStream firstStream = new ByteArrayInputStream( "anything".getBytes( StandardCharsets.UTF_8 ) );
      InputStream secondStream = new ByteArrayInputStream( "anything".getBytes( StandardCharsets.UTF_8 ) );
      
      BufferedInputStream actualStream = new BufferedInputStream( firstStream );
      BufferedInputStream streamUnderTest = systemUnderTest.constructBufferedInputStream( secondStream );
      
      int readByte = actualStream.read();
      assertThat( readByte, is( not( -1 ) ) );
      assertThat( streamUnderTest.read(), is( readByte ) );
      
      while( readByte != -1 ) {
         readByte = actualStream.read();
         assertThat( streamUnderTest.read(), is( readByte ) );   
      }
   }// End Method
   
   @Test public void shouldIgnoreNullInputStream(){
      assertThat( systemUnderTest.constructBufferedInputStream( null ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldConstructStreamToFile() throws FileNotFoundException, IOException{
      File file = new File( getClass().getResource( "IoStreamingTest.txt" ).getFile() );
      assertThat( file.exists(), is( true ) );
      
      BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( systemUnderTest.constructBufferedOutputStream( file ) ) );
      writer.write( 
               "lots of streams and writers" 
      );
      writer.close();
      
      String data = TestCommon.readFileIntoString( getClass(), "IoStreamingTest.txt" );
      assertThat( data, is( "lots of streams and writers" ) );
   }//End Method
   
   @Test public void shouldIgnoreNullFile() throws FileNotFoundException{
      assertThat( systemUnderTest.constructBufferedOutputStream( null ), is( nullValue() ) );
   }//End Method

}//End Class
