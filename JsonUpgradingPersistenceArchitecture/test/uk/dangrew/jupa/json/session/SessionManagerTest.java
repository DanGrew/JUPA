/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.session;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import uk.dangrew.jupa.json.marshall.ModelMarshaller;
import uk.dangrew.jupa.mockito.NoResponseAnswer;

/**
 * {@link SessionManager} test.
 */
public class SessionManagerTest {
   
   private CountDownLatch writeLatch;
   @Mock private ModelMarshaller marshaller;
   private SessionManager systemUnderTest;

   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new SessionManager( marshaller );
   }//End Method
   
   @After public void teardown(){
      systemUnderTest.stop();
   }//End Method
   
   @Test public void shouldProvideObjectPropertyTriggerAndInformMarshallerWhenTriggered() throws InterruptedException {
      ObjectProperty< String > property = new SimpleObjectProperty< String >( "anything" );
      systemUnderTest.triggerWriteOnChange( property );
      verifyZeroInteractions( marshaller );
      
      writeLatch = new CountDownLatch( 1 );
      doAnswer( new NoResponseAnswer<>( writeLatch::countDown ) ).when( marshaller ).write();
      
      property.set( "something else" );
      writeLatch.await();
      
      verify( marshaller ).write();
   }//End Method
   
   @Test public void shouldTriggerEachOfMultipleTriggers() throws InterruptedException {
      ObjectProperty< String > property1 = new SimpleObjectProperty< String >( "anything" );
      ObjectProperty< String > property2 = new SimpleObjectProperty< String >( "anything" );
      ObservableMap< String, String > property3 = FXCollections.observableHashMap();
      ObjectProperty< String > property4 = new SimpleObjectProperty< String >( "anything" );
      systemUnderTest.triggerWriteOnChange( property1 );
      systemUnderTest.triggerWriteOnChange( property2 );
      systemUnderTest.triggerWriteOnChange( property3 );
      systemUnderTest.triggerWriteOnChange( property4 );
      verifyZeroInteractions( marshaller );
      
      writeLatch = new CountDownLatch( 1 );
      doAnswer( new NoResponseAnswer<>( writeLatch::countDown ) ).when( marshaller ).write();
      property1.set( "something else" );
      writeLatch.await();
      
      writeLatch = new CountDownLatch( 1 );
      doAnswer( new NoResponseAnswer<>( writeLatch::countDown ) ).when( marshaller ).write();
      property2.set( "another thing" );
      writeLatch.await();
      
      writeLatch = new CountDownLatch( 1 );
      doAnswer( new NoResponseAnswer<>( writeLatch::countDown ) ).when( marshaller ).write();
      property3.put( "whatever", "I want" );
      writeLatch.await();
      
      writeLatch = new CountDownLatch( 1 );
      doAnswer( new NoResponseAnswer<>( writeLatch::countDown ) ).when( marshaller ).write();
      property4.set( "chillis" );
      writeLatch.await();
      
      verify( marshaller, times( 4 ) ).write();
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullValueTrigger(){
      systemUnderTest.triggerWriteOnChange( ( ObservableValue< ? > )null );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullMapTrigger(){
      systemUnderTest.triggerWriteOnChange( ( ObservableMap< ?, ? > )null );
   }//End Method
   
   @Test public void shouldIgnoreSameValueTriggerMultipleTimes() throws InterruptedException{
      ObjectProperty< String > property = new SimpleObjectProperty< String >( "anything" );
      systemUnderTest.triggerWriteOnChange( property );
      systemUnderTest.triggerWriteOnChange( property );
      systemUnderTest.triggerWriteOnChange( property );
      verifyZeroInteractions( marshaller );
      
      writeLatch = new CountDownLatch( 1 );
      doAnswer( new NoResponseAnswer<>( writeLatch::countDown ) ).when( marshaller ).write();
      property.set( "something else" );
      writeLatch.await();
      
      verify( marshaller ).write();
   }//End Method
   
   @Test public void shouldIgnoreSameMapTriggerMultipleTimes() throws InterruptedException{
      ObservableMap< String, String > property = FXCollections.observableHashMap();
      systemUnderTest.triggerWriteOnChange( property );
      systemUnderTest.triggerWriteOnChange( property );
      systemUnderTest.triggerWriteOnChange( property );
      verifyZeroInteractions( marshaller );
      
      writeLatch = new CountDownLatch( 1 );
      doAnswer( new NoResponseAnswer<>( writeLatch::countDown ) ).when( marshaller ).write();
      property.put( "something", "else" );
      writeLatch.await();
      
      verify( marshaller ).write();
   }//End Method
   
   @Test public void shouldHaveNoFurtherInteractionsWithAlreadyRegisteredValue(){
      @SuppressWarnings("unchecked") //mocking genericized objects
      ObjectProperty< Object > property = mock( ObjectProperty.class );
      
      systemUnderTest.triggerWriteOnChange( property );
      systemUnderTest.triggerWriteOnChange( property );
      systemUnderTest.triggerWriteOnChange( property );
      verify( property, times( 1 ) ).addListener( Mockito.< ChangeListener< Object > >any() ); 
   }//End Method
   
   @Test public void shouldHaveNoFurtherInteractionsWithAlreadyRegisteredMap(){
      @SuppressWarnings("unchecked") //mocking genericized objects
      ObservableMap< Object, Object > property = mock( ObservableMap.class );
      
      systemUnderTest.triggerWriteOnChange( property );
      systemUnderTest.triggerWriteOnChange( property );
      systemUnderTest.triggerWriteOnChange( property );
      verify( property, times( 1 ) ).addListener( Mockito.< MapChangeListener< Object, Object > >any() ); 
   }//End Method
   
   @Test public void shouldQueueOnlyOneWriteToAvoidRepeatingTheSameWrite() throws InterruptedException{
      CountDownLatch waitForFirstCallToReachWrite = new CountDownLatch( 1 );
      CountDownLatch waitForMultipleCallsToBeReceived = new CountDownLatch( 1 );
      CountDownLatch waitForSecondWrite = new CountDownLatch( 2 );
      
      doAnswer( new NoResponseAnswer<>( () -> {
         try {
            waitForFirstCallToReachWrite.countDown();
            waitForSecondWrite.countDown();
            waitForMultipleCallsToBeReceived.await();
         } catch ( Exception e ) {
            fail();
         }  
      } ) ).when( marshaller ).write();
      
      ObjectProperty< String > property = new SimpleObjectProperty< String >( "anything" );
      systemUnderTest.triggerWriteOnChange( property );
      
      verifyZeroInteractions( marshaller );
      property.set( "something else" );
      waitForFirstCallToReachWrite.await();
      verify( marshaller, times( 1 ) ).write();
      
      property.set( "another" );
      property.set( "my bad" );
      property.set( "Nooo!" );
      waitForMultipleCallsToBeReceived.countDown();
      
      waitForSecondWrite.await();
      verify( marshaller, times( 2 ) ).write();
   }//End Method
   
   @Test public void shouldStopThreading(){
      ObjectProperty< String > property = new SimpleObjectProperty< String >( "anything" );
      systemUnderTest.triggerWriteOnChange( property );
      verifyZeroInteractions( marshaller );
      
      systemUnderTest.stop();
      property.set( "something else" );
      
      verify( marshaller, never() ).write();
   }//End Method

}//End Class
