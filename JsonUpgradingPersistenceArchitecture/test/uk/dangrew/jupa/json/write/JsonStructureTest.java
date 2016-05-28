/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.write;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * {@link JsonStructure} test.
 */
public class JsonStructureTest {
   
   private static final String PEOPLE = "People";
   private static final String PERSON = "Person";
   private static final String FIRST_NAME = "First Name";
   private static final String LAST_NAME = "Last Name";
   private static final String AGE = "Age";
   private static final String CHILDREN = "Children";
   
   private JSONObject jsonObject;
   private JsonStructure systemUnderTest;
   @Mock private JsonStructureTree tree;
   @Mock private JsonStructureBuilder builder;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new JsonStructure( tree, builder );
   }//End Method
   
   @Test public void shouldBuildObjectStructureWithAttributesAsPlaceholders(){
      jsonObject = new JSONObject();
      systemUnderTest = new JsonStructure();
      
      systemUnderTest.child( PERSON, systemUnderTest.root() );
      systemUnderTest.value( FIRST_NAME, PERSON );
      systemUnderTest.value( LAST_NAME, PERSON );
      systemUnderTest.value( AGE, PERSON );
      
      systemUnderTest.build( jsonObject );
      
      JSONObject person = jsonObject.getJSONObject( PERSON );
      assertThat( person.get( FIRST_NAME ), is( systemUnderTest.placeholder() ) );
      assertThat( person.get( LAST_NAME ), is( systemUnderTest.placeholder() ) );
      assertThat( person.get( AGE ), is( systemUnderTest.placeholder() ) );
   }//End Method
   
   @Test public void shouldBuildArrayStructureWithAttributesInSpecificNumberOfItems(){
      jsonObject = new JSONObject();
      systemUnderTest = new JsonStructure();
      
      systemUnderTest.array( PEOPLE, systemUnderTest.root() );
      systemUnderTest.child( PERSON, PEOPLE );
      systemUnderTest.value( FIRST_NAME, PERSON );
      systemUnderTest.value( LAST_NAME, PERSON );
      systemUnderTest.value( AGE, PERSON );
      
      final int peopleCount = 3;
      systemUnderTest.arraySize( PEOPLE, peopleCount );
      
      systemUnderTest.build( jsonObject );
      
      JSONArray people = jsonObject.getJSONArray( PEOPLE );
      assertThat( people.length(), is( peopleCount ) );
      for ( int i = 0; i < peopleCount; i++ ) {
         JSONObject person = people.getJSONObject( i );
         assertThat( person.get( FIRST_NAME ), is( systemUnderTest.placeholder() ) );
         assertThat( person.get( LAST_NAME ), is( systemUnderTest.placeholder() ) );
         assertThat( person.get( AGE ), is( systemUnderTest.placeholder() ) );
      }
   }//End Method
   
   @Test public void rootShouldProvideTreeRoot() {
      when( tree.getRoot() ).thenReturn( "anything" );
      assertThat( systemUnderTest.root(), is( "anything" ) );
      
      when( tree.getRoot() ).thenReturn( "something else" );
      assertThat( systemUnderTest.root(), is( "something else" ) );
   }//End Method
   
   @Test public void placeholderShouldProvideBuilderPlaceholder(){
      when( builder.getPlaceholder() ).thenReturn( "anything" );
      assertThat( systemUnderTest.placeholder(), is( "anything" ) );
      
      when( builder.getPlaceholder() ).thenReturn( "something else" );
      assertThat( systemUnderTest.placeholder(), is( "something else" ) );
   }//End Method

   @Test public void childShouldCallThroughToTree() {
      systemUnderTest.child( PERSON, PEOPLE );
      verify( tree ).addChild( PERSON, PEOPLE );
   }//End Method

   @Test public void valueShouldCallThroughToTree() {
      systemUnderTest.value( PERSON, PEOPLE );
      verify( tree ).addChild( PERSON, PEOPLE );
   }//End Method

   @Test public void arrayShouldCallThroughToTree() {
      systemUnderTest.array( CHILDREN, PERSON );
      verify( tree ).addArray( CHILDREN, PERSON );
   }//End Method
   
   @Test public void arraySizeShouldCallThroughToTree() {
      systemUnderTest.arraySize( CHILDREN, 6 );
      verify( tree ).setArraySize( CHILDREN, 6 );
   }//End Method

   @Test public void buildSizeShouldCallThroughToBuilder() {
      systemUnderTest.build( jsonObject );
      verify( builder ).build( jsonObject );
   }//End Method

}//End Class
