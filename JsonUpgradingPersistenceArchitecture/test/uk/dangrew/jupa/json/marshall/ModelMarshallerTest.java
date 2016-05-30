/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.marshall;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.file.protocol.FileLocationProtocol;
import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.write.JsonStructure;

/**
 * {@link ModelMarshaller} test.
 */
public class ModelMarshallerTest {

   @Mock private FileLocationProtocol protocol;
   @Mock private JsonStructure structure;
   @Mock private JsonParser parserWithReadHandles;
   @Mock private JsonParser parserWithWriteHandles;
   @Captor private ArgumentCaptor< JSONObject > jsonObjectCaptor;
   private ModelMarshaller systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ModelMarshaller( structure, parserWithReadHandles, parserWithWriteHandles, protocol );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullStructure(){
      systemUnderTest = new ModelMarshaller( null, parserWithReadHandles, parserWithWriteHandles, protocol ); 
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullRead(){
      systemUnderTest = new ModelMarshaller( structure, null, parserWithWriteHandles, protocol ); 
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullWrite(){
      systemUnderTest = new ModelMarshaller( structure, parserWithReadHandles, null, protocol ); 
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullProtocol(){
      systemUnderTest = new ModelMarshaller( structure, parserWithReadHandles, parserWithWriteHandles, null ); 
   }//End Method
   
   @Test public void writeShouldBuildNewObjectWriteAndPassToProtocol() {
      systemUnderTest.write();
      
      InOrder order = inOrder( structure, parserWithReadHandles, parserWithWriteHandles, protocol );
      order.verify( structure ).build( jsonObjectCaptor.capture() );
      
      JSONObject builtObject = jsonObjectCaptor.getValue();
      assertThat( builtObject, is( not( nullValue() ) ) );
      
      order.verify( parserWithWriteHandles ).parse( builtObject );
      order.verify( protocol ).writeToLocation( builtObject );
   }//End Method
   
   @Test public void readShouldRequestProtocolReadAndParseUsingReadHandles() {
      final JSONObject readObject = new JSONObject();
      when( protocol.readFromLocation() ).thenReturn( readObject );
      
      systemUnderTest.read();
      
      InOrder order = inOrder( structure, parserWithReadHandles, parserWithWriteHandles, protocol );
      order.verify( protocol ).readFromLocation();
      order.verify( parserWithReadHandles ).parse( readObject );
   }//End Method
}//End Class
