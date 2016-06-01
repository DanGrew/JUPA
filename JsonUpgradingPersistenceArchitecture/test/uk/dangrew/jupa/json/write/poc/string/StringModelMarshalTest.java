/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.write.poc.string;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static uk.dangrew.jupa.json.write.poc.string.StringModelParsing.PROJECTS_VALUE;
import static uk.dangrew.jupa.json.write.poc.string.StringModelParsing.SKILLS_VALUE;

import org.junit.Test;

import uk.dangrew.jupa.file.protocol.JarLocationProtocol;
import uk.dangrew.jupa.json.marshall.ModelMarshaller;

/**
 * Proof of concept and end to end test for marshalling the {@link StringModel}
 * to the {@link JarLocationProtocol}.
 */
public class StringModelMarshalTest {

   @Test public void marshallingShouldWriteToFileAndReadBackIn() {
      StringModelParsing parsing = new StringModelParsing();
      
      StringModel readModel = new StringModel();
      StringModel writeModel = parsing.constructStringModelWithData();
      ModelMarshaller marshaller = new ModelMarshaller( 
               parsing.constructStructure( SKILLS_VALUE.size(), PROJECTS_VALUE.size() ),
               parsing.constructParserWithReadHandles( readModel ),
               parsing.constructParserWithWriteHandles( writeModel ),
               new JarLocationProtocol( "StringModelMarshalTest-output-file.json" )
      );
      
      marshaller.write();
      assertThat( readModel.developer.firstName, is( nullValue() ) );
      marshaller.read();
      
      assertThat( readModel.developer.firstName, is( writeModel.developer.firstName ) );
      assertThat( readModel.developer.lastName, is( writeModel.developer.lastName ) );
      assertThat( readModel.developer.skills, is( writeModel.developer.skills ) );
      
      for ( int i = 0; i < PROJECTS_VALUE.size(); i++ ) {
         Project readProject = readModel.developer.projects.get( i );
         Project writeProject = writeModel.developer.projects.get( i );
         assertThat( readProject.projectName, is( writeProject.projectName ) );
         assertThat( readProject.fullProjectName, is( writeProject.fullProjectName ) );
         assertThat( readProject.vcs, is( writeProject.vcs ) );
      }
   }//End Method

}//End Class
