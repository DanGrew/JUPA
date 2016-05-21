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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * {@link JsonValueSetterFunction} test.
 */
public class JsonValueSetterFunctionTest {
   
   private static final Object VALUE = new Object();
   
   @Mock private JsonNavigable navigable;
   @Mock private Object parent;
   private JsonValueSetterFunction systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JsonValueSetterFunction( VALUE );
   }//End Method
   
   @Test public void functionShouldInformNavigableToPut() {
      assertThat( systemUnderTest.apply( navigable, parent ), is( nullValue() ) );
      verify( navigable ).put( parent, VALUE );
   }//End Method
   
   @Test public void functionShouldInformNavigableToPutNull() {
      systemUnderTest = new JsonValueSetterFunction( null );
      assertThat( systemUnderTest.apply( navigable, parent ), is( nullValue() ) );
      verify( navigable ).put( parent, null );
   }//End Method

}//End Class
