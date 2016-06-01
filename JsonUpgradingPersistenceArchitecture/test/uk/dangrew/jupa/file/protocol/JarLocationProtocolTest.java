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

   @Mock private JsonIO jsonIO;
   @Captor private ArgumentCaptor< File > fileCaptor;
   @Captor private ArgumentCaptor< JSONObject > jsonCaptor;
   private JarLocationProtocol systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JarLocationProtocol( jsonIO );
   }//End Method
   
   @Test public void publicConstructorShouldProvideJsonIO(){
      systemUnderTest = new JarLocationProtocol();
      assertThat( systemUnderTest.readFromLocation(), is( nullValue() ) );
   }//End Method
   
   @Test public void readShouldDelegateToIOWithCorrectFilePath() {
      systemUnderTest.readFromLocation();
      verify( jsonIO ).read( fileCaptor.capture() );
      verifyNoMoreInteractions( jsonIO );
      
      assertThat( fileCaptor.getValue().getAbsolutePath(), endsWith( JarLocationProtocol.FILENAME ) );
   }//End Method
   
   @Test public void writeShouldDelegateToIOWithCorrectFilePathAndObject() {
      final JSONObject jsonObject = new JSONObject();
      
      systemUnderTest.writeToLocation( jsonObject );
      verify( jsonIO ).write( fileCaptor.capture(), jsonCaptor.capture() );
      verifyNoMoreInteractions( jsonIO );
      
      assertThat( fileCaptor.getValue().getAbsolutePath(), endsWith( JarLocationProtocol.FILENAME ) );
      assertThat( jsonCaptor.getValue(), is( jsonObject ) );
   }//End Method
   
   @Test public void writeShouldProvideResultBasedOnSuccess() {
      final JSONObject jsonObject = new JSONObject();
      
      when( jsonIO.write( Mockito.any(), Mockito.any() ) ).thenReturn( true );
      assertThat( systemUnderTest.writeToLocation( jsonObject ), is( true ) );
      
      when( jsonIO.write( Mockito.any(), Mockito.any() ) ).thenReturn( false );
      assertThat( systemUnderTest.writeToLocation( jsonObject ), is( false ) );
   }//End Method

}//End Class