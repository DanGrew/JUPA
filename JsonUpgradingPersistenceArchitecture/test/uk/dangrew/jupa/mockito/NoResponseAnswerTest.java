/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;

/**
 * {@link NoResponseAnswer} test.
 */
public class NoResponseAnswerTest {

   @Test public void shouldRunRunnableWhenInvoked() throws Throwable {
      Runnable runnable = mock( Runnable.class );
      NoResponseAnswer< Object > systemUnderTest = new NoResponseAnswer<>( runnable );
      
      verifyZeroInteractions( runnable );
      systemUnderTest.answer( null );
      verify( runnable ).run();
   }//End Method

}//End Class
