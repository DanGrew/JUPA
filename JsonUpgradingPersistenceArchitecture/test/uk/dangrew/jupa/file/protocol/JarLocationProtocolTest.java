/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.file.protocol;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.json.io.JsonIO;

/**
 * {@link JarLocationProtocol} test.
 */
public class JarLocationProtocolTest {

   private static final String FILENAME = "any file name";
   
   @Mock private JsonIO jsonIO;
   @Captor private ArgumentCaptor< File > fileCaptor;
   @Captor private ArgumentCaptor< JSONObject > jsonCaptor;
   private JarLocationProtocol systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JarLocationProtocol( jsonIO, null, FILENAME, getClass() );
   }//End Method
   
   @Test public void publicConstructorShouldProvideJsonIO(){
      systemUnderTest = new JarLocationProtocol( FILENAME, getClass() );
      assertThat( systemUnderTest.readFromLocation(), is( nullValue() ) );
   }//End Method
   
   @Test public void publicConstructorWithSubFolderShouldProvideJsonIO(){
      systemUnderTest = new JarLocationProtocol( "subFolder", FILENAME, getClass() );
      systemUnderTest.writeToLocation( new JSONObject() );
   }//End Method
   
   @Test public void readShouldDelegateToIOWithCorrectFilePath() {
      systemUnderTest.readFromLocation();
      verify( jsonIO ).read( fileCaptor.capture() );
      verifyNoMoreInteractions( jsonIO );
      
      assertThat( fileCaptor.getValue().getAbsolutePath(), endsWith( FILENAME ) );
   }//End Method
   
   @Test public void readShouldDelegateToIOWithCorrectFilePathForClassOutsideOfProject() {
      systemUnderTest = new JarLocationProtocol( jsonIO, null, FILENAME, JSONObject.class );
      
      systemUnderTest.readFromLocation();
      verify( jsonIO ).read( fileCaptor.capture() );
      verifyNoMoreInteractions( jsonIO );
      
      assertThat( fileCaptor.getValue().getAbsolutePath(), endsWith( FILENAME ) );
      assertThat( fileCaptor.getValue().getAbsolutePath(), containsString( "json" ) );
   }//End Method
   
   @Test public void writeShouldDelegateToIOWithCorrectFilePathAndObject() {
      final JSONObject jsonObject = new JSONObject();
      
      systemUnderTest.writeToLocation( jsonObject );
      verify( jsonIO ).write( fileCaptor.capture(), jsonCaptor.capture() );
      verifyNoMoreInteractions( jsonIO );
      
      assertThat( fileCaptor.getValue().getAbsolutePath(), endsWith( FILENAME ) );
      assertThat( jsonCaptor.getValue(), is( jsonObject ) );
   }//End Method
   
   @Test public void writeShouldProvideResultBasedOnSuccess() {
      final JSONObject jsonObject = new JSONObject();
      
      when( jsonIO.write( Mockito.any(), Mockito.any() ) ).thenReturn( true );
      assertThat( systemUnderTest.writeToLocation( jsonObject ), is( true ) );
      
      when( jsonIO.write( Mockito.any(), Mockito.any() ) ).thenReturn( false );
      assertThat( systemUnderTest.writeToLocation( jsonObject ), is( false ) );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullFilename(){
      new JarLocationProtocol( null, getClass() );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullClass(){
      new JarLocationProtocol( FILENAME, null );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptClassWithNoCodeSource(){
      new JarLocationProtocol( FILENAME, String.class );
   }//End Method
   
   @Test public void shouldLookForFileInSubFolder(){
      final String subFolder = "SubFolder";
      systemUnderTest = new JarLocationProtocol( jsonIO, subFolder, FILENAME, JSONObject.class );
      
      systemUnderTest.readFromLocation();
      verify( jsonIO ).read( fileCaptor.capture() );
      verifyNoMoreInteractions( jsonIO );
      
      assertThat( fileCaptor.getValue().getAbsolutePath(), endsWith( subFolder + "/" + FILENAME ) );
      assertThat( fileCaptor.getValue().getAbsolutePath(), containsString( "json" ) );
   }//End Method
   
   @Test public void shouldProvideAbsolutePath(){
      systemUnderTest = new JarLocationProtocol( "anything", FILENAME, getClass() );
      
      File thisLocation = new File( getClass().getProtectionDomain().getCodeSource().getLocation().getPath() );
      
      String fullLocation = systemUnderTest.getLocation();
      assertThat( fullLocation, containsString( thisLocation.getParentFile().getAbsolutePath() + "/anything/" + FILENAME ) );
   }//End Method
   
}//End Class
