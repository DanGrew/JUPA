/*
 * ----------------------------------------
 *          Jenkins Test Tracker
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.mockito;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;

/**
 * {@link ParameterReturner} test.
 */
public class ParameterReturnerTest {
   
   private ParameterReturner< String > systemUnderTest;
   
   @Test public void shouldRetrieveParameterAsType() throws Throwable {
      InvocationOnMock invocation = mock( InvocationOnMock.class );
      Object[] parameters = new Object[]{ 100, "100" };
      when( invocation.getArguments() ).thenReturn( parameters );
      
      systemUnderTest = new ParameterReturner<>( String.class, 1 );
      String value = systemUnderTest.answer( invocation );
      assertThat( value, is( parameters[ 1 ] ) );
   }//End Method
   
   @Test public void shouldRetrieveParameterAsAssignableType() throws Throwable {
      InvocationOnMock invocation = mock( InvocationOnMock.class );
      Object[] parameters = new Object[]{ 100, Arrays.asList( "a", "b", "c" ) };
      when( invocation.getArguments() ).thenReturn( parameters );
      
      @SuppressWarnings("rawtypes") 
      ParameterReturner< List > systemUnderTest = new ParameterReturner< List >( List.class, 1 );
      
      @SuppressWarnings("unchecked") 
      List< String > value = systemUnderTest.answer( invocation );
      
      assertThat( value, is( parameters[ 1 ] ) );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailWithClassCastIfWrongParameterType() throws Throwable{
      InvocationOnMock invocation = mock( InvocationOnMock.class );
      Object[] parameters = new Object[]{ "String", 100 };
      when( invocation.getArguments() ).thenReturn( parameters );
      
      systemUnderTest = new ParameterReturner<>( String.class, 1 );
      systemUnderTest.answer( invocation );
   }//End Method
   
   @Test( expected = AssertionError.class ) public void shouldFailWithInvalidParameterIfNotEnoughParameters() throws Throwable{
      InvocationOnMock invocation = mock( InvocationOnMock.class );
      Object[] parameters = new Object[]{ "String", 100 };
      when( invocation.getArguments() ).thenReturn( parameters );
      
      systemUnderTest = new ParameterReturner<>( String.class, 2 );
      systemUnderTest.answer( invocation );
   }//End Method

}//End Class
