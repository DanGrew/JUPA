/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.locator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * {@link JsonPathNavigator} test.
 */
public class JsonPathNavigatorTest {
   
   @Mock private JSONObject jsonObject;
   @Mock private JsonNavigable first;
   @Mock private JsonNavigable second;
   @Mock private JsonNavigable third;
   @Mock private JsonNavigable fourth;
   
   private List< JsonNavigable > path;
   @Mock private BiFunction< JsonNavigable, Object, Object > navigationHandler;
   @Mock private BiFunction< JsonNavigable, Object, Object > destinationHandler;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      path = new ArrayList<>();
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullParent(){
      new JsonPathNavigator( null, path, navigationHandler, destinationHandler );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullPath(){
      new JsonPathNavigator( jsonObject, null, navigationHandler, destinationHandler );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullNavigationHandler(){
      new JsonPathNavigator( jsonObject, path, null, destinationHandler );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullDestinationHandler(){
      new JsonPathNavigator( jsonObject, path, navigationHandler, null );
   }//End Method

   @Test public void shouldSafelyIgnoreEmptyPath() {
      new JsonPathNavigator( jsonObject, path, navigationHandler, destinationHandler ).navigate();
      verifyZeroInteractions( navigationHandler, destinationHandler );
   }//End Method
   
   @Test public void shouldNotNavigateAndOnlyHandleDestinationOnSingleNavigable() {
      path.add( first );
      new JsonPathNavigator( jsonObject, path, navigationHandler, destinationHandler ).navigate();
      verifyZeroInteractions( navigationHandler );
      verify( destinationHandler ).apply( first, jsonObject );
   }//End Method
   
   @Test public void shouldNavigateThroughOneNavigableAndHandleDestination() {
      path.add( first );
      path.add( second );
      JSONObject nested = new JSONObject();
      when( navigationHandler.apply( first, jsonObject ) ).thenReturn( nested );
      
      new JsonPathNavigator( jsonObject, path, navigationHandler, destinationHandler ).navigate();
      verify( navigationHandler ).apply( first, jsonObject );
      verify( destinationHandler ).apply( second, nested );
   }//End Method
   
   @Test public void shouldNavigateThroughMultipleNavigableAndHandleDestination() {
      path.add( first );
      path.add( second );
      path.add( third );
      path.add( fourth );
      
      JSONObject firstNested = new JSONObject();
      JSONObject secondNested = new JSONObject();
      JSONObject thirdNested = new JSONObject();
      
      when( navigationHandler.apply( first, jsonObject ) ).thenReturn( firstNested );
      when( navigationHandler.apply( second, firstNested ) ).thenReturn( secondNested );
      when( navigationHandler.apply( third, secondNested ) ).thenReturn( thirdNested );
      
      new JsonPathNavigator( jsonObject, path, navigationHandler, destinationHandler ).navigate();
      verify( navigationHandler ).apply( first, jsonObject );
      verify( navigationHandler ).apply( second, firstNested );
      verify( navigationHandler ).apply( third, secondNested );
      verify( destinationHandler ).apply( fourth, thirdNested );
   }//End Method

   @Test public void shouldNavigateThroughMultipleButNotReachDestination(){
      path.add( first );
      path.add( second );
      path.add( third );
      path.add( fourth );
      
      JSONObject firstNested = new JSONObject();
      JSONObject secondNested = new JSONObject();
      
      when( navigationHandler.apply( first, jsonObject ) ).thenReturn( firstNested );
      when( navigationHandler.apply( second, firstNested ) ).thenReturn( secondNested );
      
      new JsonPathNavigator( jsonObject, path, navigationHandler, destinationHandler ).navigate();
      verify( navigationHandler ).apply( first, jsonObject );
      verify( navigationHandler ).apply( second, firstNested );
      verify( navigationHandler ).apply( third, secondNested );
      verifyNoMoreInteractions( destinationHandler );
   }//End Method
   
   @Test public void desintationShouldProvideResult(){
      path.add( first );
      final Object returnValue = new Object();
      when( destinationHandler.apply( first, jsonObject ) ).thenReturn( returnValue );
      
      Object result = new JsonPathNavigator( jsonObject, path, navigationHandler, destinationHandler ).navigate();
      assertThat( result, is( returnValue ) );
      
      verifyZeroInteractions( navigationHandler );
      verify( destinationHandler ).apply( first, jsonObject );
   }//End Method
}//End Class
