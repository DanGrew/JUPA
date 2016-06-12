/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
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
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import uk.dangrew.jupa.TestCommon;

/**
 * {@link JsonIO} test,
 */
@RunWith( JUnitParamsRunner.class )
public class JsonIOTest {

   private static final String EXISITNG_FILE = "existing-file.json";
   private static final String POPULATING_FILE = "populating-file.json";
   private static final String SUB_FOLDER_FILE = "testing/populating-file.json";
   private static final String INVALID_FILE = "invalid-file.json";
   
   private JSONObject jsonObject;
   private JsonIO systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      jsonObject = new JSONObject();
      systemUnderTest = new JsonIO();
      
      File file = constructFileFor( POPULATING_FILE );
      if ( file.exists() ) {
         file.delete();
      }
      
      File subfolderFile = constructFileFor( SUB_FOLDER_FILE );
      if ( subfolderFile.exists() ) {
         subfolderFile.delete();
      }
   }//End Method
   
   @Test public void shouldParseTestableFile() {
      final File file = constructFileFor( EXISITNG_FILE );
      assertThat( file, is( not( nullValue() ) ) );
      assertThat( file.exists(), is( true ) );
      
      jsonObject = systemUnderTest.read( file );
      assertThat( jsonObject, is( not( nullValue() ) ) );
      assertThat( jsonObject.get( "SomeKey" ), is( "SomeValue" ) );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void readShouldNotAcceptNullFile(){
      systemUnderTest.read( null );
   }//End Method
   
   @Test public void readShouldIgnoreNonExistentFile(){
      assertThat( systemUnderTest.read( new File( "something that does not exist" ) ), is( nullValue() ) );
   }//End Method
   
   @Test public void readShouldIgnoreInvalidFileData(){
      File invalidFile = constructFileFor( INVALID_FILE );
      assertThat( invalidFile, is( not( nullValue() ) ) );
      assertThat( invalidFile.exists(), is( true ) );
      assertThat( systemUnderTest.read( invalidFile ), is( nullValue() ) );
   }//End Method
   
   @Test public void writeShouldNotAcceptNullFile(){
      final JSONObject input = mock( JSONObject.class );
      try {
         assertThat( systemUnderTest.write( null, input ), is( false ) );
         fail( "No null pointer exception thrown." );
      } catch ( NullPointerException npe ) {
         verifyZeroInteractions( input );
      }
   }//End Method
   
   @Test public void writeShouldNotAcceptNullJsonObject(){
      final File file = mock( File.class );
      try {
         assertThat( systemUnderTest.write( file, null ), is( false ) );
         fail( "No null pointer exception thrown." );
      } catch ( NullPointerException npe ) {
         verifyZeroInteractions( file );
      }
   }//End Method
   
   @Parameters( { POPULATING_FILE, SUB_FOLDER_FILE } )
   @Test public void writeShouldConstructNonExistentFile( String file ){
      assertThat( JsonIOTest.class.getResource( file ), is( nullValue() ) );
      shouldWriteTestableFile( file );
   }//End Method
   
   @Parameters( { POPULATING_FILE, SUB_FOLDER_FILE } )
   @Test public void shouldWriteTestableFile( String filename ) {
      final String keyA = "something very specific";
      final String keyB = "anotherkey";
      final String value = "another thing but mispelt";
      
      JSONObject writeObject = new JSONObject();
      writeObject.put( keyA, value );
      writeObject.put( keyB, value );
      
      final File file = constructFileFor( filename );
      assertThat( systemUnderTest.write( file, writeObject ), is( true ) );
      
      assertThat( file.exists(), is( true ) );
      jsonObject = systemUnderTest.read( file );
      assertThat( jsonObject.toString(), is( writeObject.toString() ) );
      
      assertThat( TestCommon.readFileIntoString( getClass(), filename ), is( writeObject.toString( 3 ) ) );
   }//End Method
   
   @Test public void shouldCatchNullStringWhenReadAndAvoidExceptions(){
      final File file = constructFileFor( EXISITNG_FILE );
      assertThat( file, is( not( nullValue() ) ) );
      assertThat( file.exists(), is( true ) );
      
      //this should not happen because file issues are handled before - spying to make sure
      systemUnderTest = spy( systemUnderTest );
      when( systemUnderTest.readFileIntoString( file ) ).thenReturn( null );
      
      assertThat( systemUnderTest.read( file ), is( nullValue() ) );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void readScannerShouldClose(){
      Scanner scanner = new Scanner( new StringReader( "anything" ) );
      systemUnderTest.readScannerContentAndClose( scanner );
      scanner.next();
   }//End Method
   
   @Test public void readFileIntoStringShouldHandleIoExceptionsEvenThoughDefendedAgainst(){
      systemUnderTest = spy( systemUnderTest );
      doAnswer( invocation -> { throw new IOException(); } ).when( systemUnderTest ).readScannerContentAndClose( Mockito.any() );
      
      final File file = constructFileFor( EXISITNG_FILE );
      assertThat( file, is( not( nullValue() ) ) );
      assertThat( file.exists(), is( true ) );
      assertThat( systemUnderTest.readFileIntoString( file ), is( nullValue() ) );
   }//End Method
   
   @Parameters( { POPULATING_FILE, SUB_FOLDER_FILE } )
   @Test public void writeShouldUseFileThatAlreadyExists( String filename ) throws IOException{
      final File file = constructFileFor( filename );
      assertThat( file, is( not( nullValue() ) ) );
      assertThat( file.exists(), is( false ) );
      
      file.getParentFile().mkdirs();
      file.createNewFile();
      
      shouldWriteTestableFile( filename );
   }//End Method
   
   @Test public void writeShouldFailGracefullyIfFileCannotBeCreated() throws IOException{
      File file = mock( File.class );
      when( file.exists() ).thenReturn( false );
      when( file.createNewFile() ).thenThrow( new IOException() );
      
      assertThat( systemUnderTest.write( file, jsonObject ), is( false ) );
   }//End Method
   
   @Test public void writeShouldFailGracefullyIfFileWriterCannotBeCreated() throws IOException{
      File file = mock( File.class );
      when( file.exists() ).thenReturn( true );
      doAnswer( invocation -> { throw new IOException(); } ).when( file ).getPath();
      
      assertThat( systemUnderTest.write( file, jsonObject ), is( false ) );
   }//End Method
   
   /**
    * Method to construct the {@link File} for the given filename in the current package.
    * @param fileName the filename to construct for.
    * @return the {@link File}, not necessarily one that exists.
    */
   private File constructFileFor( String fileName ) {
      URL knownResource = JsonIOTest.class.getResource( EXISITNG_FILE );
      if ( knownResource == null ) {
         fail( EXISITNG_FILE + " shold be present but cannot be found." );
      }
      
      String knownResourcePath = knownResource.getFile();
      if ( !knownResourcePath.contains( fileName ) ) {
         knownResourcePath = knownResourcePath.replace( EXISITNG_FILE, fileName );
      }
      
      return new File( knownResourcePath );
   }//End Method

}//End Class
