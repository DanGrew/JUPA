/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Scanner;

import org.junit.Test;

/**
 * {@link TestCommon} test.
 */
public class TestCommonTest {
   
   @Test public void constructorShouldBePrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
     Constructor<TestCommon> constructor = TestCommon.class.getDeclaredConstructor();
     assertThat( Modifier.isPrivate( constructor.getModifiers() ), is( true ) );
     constructor.setAccessible(true);
     assertThat( constructor.newInstance(), is( notNullValue() ) );
   }//End Method

   @Test( expected = AssertionError.class ) public void shouldFailAssertionIfNoFileFound() {
      TestCommon.readFileIntoString( getClass(), "this cannot exist as a file." );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void readingFromScannerShouldCloseScannerAferwards(){
      Scanner scanner = new Scanner( new StringReader( "anything" ) );
      TestCommon.readScannerContentAndClose( scanner );
      scanner.next();
   }//End Method

}//End Class
