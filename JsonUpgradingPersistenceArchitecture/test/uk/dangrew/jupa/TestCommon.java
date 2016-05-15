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
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Assert;

public class TestCommon {

   /**
    * Method to read a text file into a {@link String}.
    * @param locationClass the {@link Class} in the package to load the {@link File} from.
    * @param filename the name of the {@link File}.
    * @return the {@link String} containing all text from the {@link File}.
    */
   public static final String readFileIntoString( Class< ? > locationClass, String filename ) {
      InputStream stream = locationClass.getResourceAsStream( filename );
      Assert.assertNotNull( stream );
      
      Scanner scanner = new Scanner( stream );
      String content = scanner.useDelimiter( "//Z" ).next();
      scanner.close();
      return content;
   }//End Method
   
}//End Class
