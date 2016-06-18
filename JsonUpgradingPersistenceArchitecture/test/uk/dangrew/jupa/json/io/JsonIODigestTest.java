/*
 * ----------------------------------------
 *             System Digest
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.io;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.sd.core.category.Categories;
import uk.dangrew.sd.core.category.Category;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiver;
import uk.dangrew.sd.core.lockdown.DigestMessageReceiverImpl;
import uk.dangrew.sd.core.message.Message;
import uk.dangrew.sd.core.source.Source;

/**
 * {@link JsonIODigest} test.
 */
public class JsonIODigestTest {
   
   private static final String SOME_SPECIFIC_FILE_VALUE = "a specific message for the file";
   
   @Mock private File file;
   
   @Captor private ArgumentCaptor< LocalDateTime > timestampCaptor;
   @Captor private ArgumentCaptor< Source > sourceCaptor;
   @Captor private ArgumentCaptor< Category > categoryCaptor;
   @Captor private ArgumentCaptor< Message > messageCaptor;
   
   @Mock private JsonIO jsonIO;
   @Mock private DigestMessageReceiver receiver;
   private JsonIODigest systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      new DigestMessageReceiverImpl( receiver );
      
      systemUnderTest = new JsonIODigest();
      systemUnderTest.attachSource( jsonIO );
      
      when( file.getAbsolutePath() ).thenReturn( SOME_SPECIFIC_FILE_VALUE );
   }//End Method
   
   @Test public void shouldReportFailedParse() {
      systemUnderTest.failedToParseInput( file );
      
      verify( receiver ).log( 
               timestampCaptor.capture(), sourceCaptor.capture(), categoryCaptor.capture(), messageCaptor.capture() 
      );
      
      assertThat( timestampCaptor.getValue(), is( not( nullValue() ) ) );
      assertThat( sourceCaptor.getValue().getIdentifier(), is( JsonIODigest.BASIC_STRING_IO_NAME ) );
      assertThat( categoryCaptor.getValue(), is( Categories.error() ) );
      assertThat( 
               messageCaptor.getValue().getMessage(), 
               is( JsonIODigest.format( JsonIODigest.FAILED_TO_PARSE, file ) ) 
      );
   }//End Method
   
   @Test public void shouldFormatFailureAndFile(){
      assertThat( JsonIODigest.format( "some reason", file ), is( 
               "some reason\nFile: a specific message for the file"
      ) );
   }//End Method
   
   @Test public void formatShouldIgnoreNullFile(){
      assertThat( JsonIODigest.format( JsonIODigest.FAILED_TO_PARSE, null ), is( 
               "Failed to parse Json found in: \nFile: File not available"
      ) );
   }//End Method
   
   @Test public void formatShouldIgnoreFileWithNoPath(){
      when( file.getAbsolutePath() ).thenReturn( null );
      assertThat( JsonIODigest.format( JsonIODigest.FAILED_TO_PARSE, file ), is( 
               "Failed to parse Json found in: \nFile: File not available"
      ) );
   }//End Method
}//End Class
