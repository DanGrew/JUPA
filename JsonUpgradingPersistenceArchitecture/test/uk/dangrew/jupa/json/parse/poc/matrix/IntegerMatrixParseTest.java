/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse.poc.matrix;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.IntegerParseHandle;
import uk.dangrew.kode.utility.io.IoCommon;

/**
 * Proof of concept test to prove matrices can be parsed when only one global key present.
 */
public class IntegerMatrixParseTest {
   
   private MatrixModel matrix;
   private JsonParser parser;
   
   @Before public void initialiseSystemUnderTest(){
      matrix = new MatrixModel();
      parser = new JsonParser();
      
      JsonArrayParseHandler< Integer > arrayHandler = new JsonArrayParseHandler<>( 
               matrix::arrayItem, matrix::arrayStarted, matrix::arrayFinished 
      );
      parser.when( "two-dimensional", new IntegerParseHandle( arrayHandler ) );
      parser.when( "three-dimensional", new IntegerParseHandle( arrayHandler ) );
   }//End Method

   @Test public void proofOfConceptTest() {
      String input = new IoCommon().readFileIntoString( getClass(), "matrix-model.json" );
      JSONObject inputObject = new JSONObject( input );
      
      parser.parse( inputObject );
      
      assertThat( 
            matrix.twoDimensional, 
            is( Arrays.asList(
                     Arrays.asList( 1, 2, 3, 4, 5, 6 ),
                     Arrays.asList( 7, 8, 9, 10, 11, 12 ),
                     Arrays.asList( 13, 14, 15, 16, 17, 18 )
            )
      ) );
      
      assertThat( 
               matrix.threeDimensional, 
               is( Arrays.asList(
                        Arrays.asList( 
                                 Arrays.asList( 1, 1, 1 ),
                                 Arrays.asList( 2, 2, 2 ),
                                 Arrays.asList( 3, 3, 3 )
                        ),
                        Arrays.asList( 
                                 Arrays.asList( 4, 4, 4 ),
                                 Arrays.asList( 5, 5, 5 ),
                                 Arrays.asList( 6, 6, 6 )
                        ),
                        Arrays.asList( 
                                 Arrays.asList( 7, 7, 7 ),
                                 Arrays.asList( 8, 8, 8 ),
                                 Arrays.asList( 9, 9, 9 )
                        )
               )
         ) );
   }//End Method

}//End Class
