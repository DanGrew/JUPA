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
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.write.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonArrayWriteHandler;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;

/**
 * Proof of concept test to prove a string model can be written.
 */
public class StringModelWriteTest {

   private static final String FIRST_NAME_VALUE = "Dan";
   private static final String LAST_NAME_VALUE = "Grew";
   private static final List< String > SKILLS_VALUE = Arrays.asList( "Java", "Testing", "Jenkins", "Design" );
   
   private StringModel model;
   private JSONObject jsonObject;
   
   @Before public void initialiseSystemUnderTest(){
      jsonObject = new JSONObject();
      
      model = new StringModel();
      model.developer = new Developer();
      model.developer.firstName = FIRST_NAME_VALUE;
      model.developer.lastName = LAST_NAME_VALUE;
      model.developer.skills = new ArrayList<>();
      model.developer.skills.addAll( SKILLS_VALUE );
      
      JsonStructure structure = new JsonStructure();
      structure.child( StringModel.FIRST_NAME, structure.root() );
      structure.child( StringModel.LAST_NAME, structure.root() );
      structure.array( StringModel.SKILLS, structure.root() );
      structure.arraySize( StringModel.SKILLS, SKILLS_VALUE.size() );
      structure.build( jsonObject );
   }//End Method
   
   @Test public void proofOfConceptTest() {
      JsonParser parser = new JsonParser();
      
      parser.when( StringModel.FIRST_NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( model::getFirstName ) ) );
      parser.when( StringModel.LAST_NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( model::getLastName ) ) );
      parser.when( StringModel.SKILLS, new JsonWriteHandleImpl( new JsonArrayWriteHandler( 
               model::getSkill, key -> {}, key -> {} 
      ) ) );
      
      parser.parse( jsonObject );
      
      assertThat( jsonObject.get( StringModel.FIRST_NAME ), is( FIRST_NAME_VALUE ) );
      assertThat( jsonObject.get( StringModel.LAST_NAME ), is( LAST_NAME_VALUE ) );
      
      JSONArray skills = jsonObject.getJSONArray( StringModel.SKILLS );
      assertThat( skills.length(), is( SKILLS_VALUE.size() ) );
      for ( int i = 0; i < SKILLS_VALUE.size(); i++ ) {
         assertThat( skills.get( i ), is( SKILLS_VALUE.get( i ) ) );
      }
   }//End Method

}//End Class
