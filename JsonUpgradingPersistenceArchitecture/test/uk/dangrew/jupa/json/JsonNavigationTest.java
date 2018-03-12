package uk.dangrew.jupa.json;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

public class JsonNavigationTest {

   @Test public void shouldConsumeKeyForRunnable() {
      Runnable runnable = mock( Runnable.class );
      Consumer< String > consumer = JsonNavigation.consumeKey( runnable );
      consumer.accept( "anything" );
      verify( runnable ).run();
   }//End Method
   
   @Test public void shouldConsumeKeyForConsumer() {
      Consumer< Object > consumer = mock( Consumer.class );
      BiConsumer< String, Object > biConsumer = JsonNavigation.consumeKey( consumer );
      
      Object value = new Object();
      biConsumer.accept( "anything", value );
      verify( consumer ).accept( value );
   }//End Method
   
   @Test public void shouldConsumeKeyForSupplier() {
      Object result = new Object();
      Supplier< Object > supplier = () -> result;
      Function< String, Object > function = JsonNavigation.consumeKey( supplier );
      
      assertThat( function.apply( "anything" ), is( result ) );
   }//End Method
   
   @Test public void shouldConsumeKeyForFunction() {
      Object result = new Object();
      Function< Integer, Object > function = i -> result;
      BiFunction< String, Integer, Object > biFunction = JsonNavigation.consumeKey( function );
      
      assertThat( biFunction.apply( "anything", 12 ), is( result ) );
   }//End Method
   
   @Test public void shouldConsumeKeyForNullRunnable() {
      Runnable runnable = null;
      Consumer< String > consumer = JsonNavigation.consumeKey( runnable );
      consumer.accept( "anything" );
   }//End Method
   
   @Test public void shouldConsumeKeyForNullConsumer() {
      Consumer< Object > consumer = null;
      BiConsumer< String, Object > biConsumer = JsonNavigation.consumeKey( consumer );
      
      Object value = new Object();
      biConsumer.accept( "anything", value );
   }//End Method
   
   @Test public void shouldConsumeKeyForNullSupplier() {
      Supplier< Object > supplier = null;
      Function< String, Object > function = JsonNavigation.consumeKey( supplier );
      
      assertThat( function.apply( "anything" ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldConsumeKeyForNullFunction() {
      Function< Integer, Object > function = null;
      BiFunction< String, Integer, Object > biFunction = JsonNavigation.consumeKey( function );
      
      assertThat( biFunction.apply( "anything", 12 ), is( nullValue() ) );
   }//End Method

}//End Class
