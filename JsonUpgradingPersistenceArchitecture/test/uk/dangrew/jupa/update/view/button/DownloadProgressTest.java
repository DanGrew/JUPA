/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.view.button;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.sd.core.message.Messages;
import uk.dangrew.sd.core.progress.Progresses;
import uk.dangrew.sd.core.source.Source;
import uk.dangrew.sd.core.source.SourceImpl;
import uk.dangrew.sd.digest.object.ObjectDigestImpl;

/**
 * {@link DownloadProgress} test.
 */
public class DownloadProgressTest {

   private Source source;
   private DownloadProgress systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      source = new SourceImpl( "source" );
      systemUnderTest = new DownloadProgress( source );
   }//End Method
   
   @Test public void shouldDisplayProgressBarInCenter(){
      assertThat( systemUnderTest.getCenter(), is( systemUnderTest.bar() ) );
   }//End Method
   
   @Test public void shouldDisplayProgressInBarFromDigest(){
      new ObjectDigestImpl( source ).progress( Progresses.simpleProgress( 10 ), Messages.simpleMessage( "anything" ) );
      assertThat( systemUnderTest.bar().getProgress(), is( 0.1 ) );
      assertThat( systemUnderTest.bar().getText(), is( "source: anything" ) );
   }//End Method
   
   @Test public void shouldNotDisplayProgressInBarFromDigestWithDifferentSource(){
      new ObjectDigestImpl( new SourceImpl( new Object() ) ).progress( Progresses.simpleProgress( 10 ), Messages.simpleMessage( "anything" ) );
      assertThat( systemUnderTest.bar().getProgress(), is( not( 0.1 ) ) );
      assertThat( systemUnderTest.bar().getText(), is( not( "source: anything" ) ) );
   }//End Method
   
   @Test public void shouldBeAssociatedWithCorrectSource(){
      assertThat( systemUnderTest.hasSource( source ), is( true ) );
      assertThat( systemUnderTest.hasSource( mock( Source.class ) ), is( false ) );
   }//End Method
}//End Class
