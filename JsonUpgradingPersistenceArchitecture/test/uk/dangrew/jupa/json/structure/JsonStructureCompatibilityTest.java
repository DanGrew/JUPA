/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.structure;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link JsonStructureCompatibility} test.
 */
public class JsonStructureCompatibilityTest {
   
   private static final String CHILDA = "ChildA";
   private static final String CHILDB = "ChildB";
   private static final String CHILDC = "ChildC";
   private static final String CHILDD = "ChildD";
   private static final String CHILDE = "ChildE";
   private static final Function< String, Integer > SIZE_RETRIEVER = key -> 3;
   
   private JsonStructureTree structureTree;
   private JsonStructureCompatibility systemUnderTest;
   
   private JSONObject object;
   
   @Before public void initialiseSystemUnderTest() {
      structureTree = new JsonStructureTree();
      systemUnderTest = new JsonStructureCompatibility( structureTree );
      
      object = new JSONObject();
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullTree(){
      new JsonStructureCompatibility( null );
   }//End Method

   @Test public void shouldAcceptObjectThatContainsAllKeys() {
      structureTree.addChild( CHILDA, structureTree.getRoot() );
      structureTree.addChild( CHILDB, CHILDA );
      structureTree.addChild( CHILDC, CHILDA );
      structureTree.addChild( CHILDD, CHILDC );
      
      JSONObject childA = new JSONObject();
      object.put( CHILDA, childA );
      
      JSONObject childB = new JSONObject();
      childA.put( CHILDB, childB );
      JSONObject childC = new JSONObject();
      childA.put( CHILDC, childC );
      
      JSONObject childD = new JSONObject();
      childC.put( CHILDD, childD );
      
      assertThat( systemUnderTest.isCompatible( object ), is( true ) );
   }//End Method
   
   @Test public void shouldAcceptObjectThatContainsAllKeysWithPlaceholders() {
      structureTree.addChild( CHILDA, structureTree.getRoot() );
      structureTree.addChild( CHILDB, structureTree.getRoot() );
      
      object.put( CHILDA, new Object() );
      object.put( CHILDB, new Object() );
      
      assertThat( systemUnderTest.isCompatible( object ), is( true ) );
   }//End Method
   
   @Test public void shouldAcceptArrayThatContainsPlaceholders() {
      structureTree.addArray( CHILDA, structureTree.getRoot(), SIZE_RETRIEVER );
      
      JSONArray array = new JSONArray();
      object.put( CHILDA, array );
      
      for ( int i = 0; i < 3; i++ ) {
         array.put( new Object() );
      }
      
      assertThat( systemUnderTest.isCompatible( object ), is( true ) );
   }//End Method
   
   @Test public void shouldAcceptObjectThatContainsAdditionalKeys() {
      structureTree.addChild( CHILDA, structureTree.getRoot() );
      structureTree.addChild( CHILDB, structureTree.getRoot() );
      
      JSONObject childA = new JSONObject();
      object.put( CHILDA, childA );
      
      JSONObject childB = new JSONObject();
      object.put( CHILDB, childB );
      
      JSONObject childC = new JSONObject();
      object.put( CHILDC, childC );
      
      JSONObject childD = new JSONObject();
      object.put( CHILDD, childD );
      
      assertThat( systemUnderTest.isCompatible( object ), is( true ) );
   }//End Method
   
   @Test public void shouldRejectObjectThatIsMissingTopLevelKey() {
      structureTree.addChild( CHILDA, structureTree.getRoot() );
      structureTree.addChild( CHILDB, structureTree.getRoot() );
      structureTree.addChild( CHILDC, structureTree.getRoot() );
      
      JSONObject childA = new JSONObject();
      object.put( CHILDA, childA );
      
      JSONObject childB = new JSONObject();
      object.put( CHILDB, childB );
      
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );
   }//End Method
   
   @Test public void shouldRejectObjectThatHasWrongValueForKey() {
      structureTree.addChild( CHILDA, structureTree.getRoot() );
      structureTree.addChild( CHILDB, CHILDA );
      
      object.put( CHILDA, false );
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );
   }//End Method
   
   @Test public void shouldRejectObjectThatIsMissingLowLevelKey() {
      structureTree.addChild( CHILDA, structureTree.getRoot() );
      structureTree.addChild( CHILDB, CHILDA );
      structureTree.addChild( CHILDC, CHILDB );
      structureTree.addChild( CHILDD, CHILDC );
      
      JSONObject childA = new JSONObject();
      object.put( CHILDA, childA );
      
      JSONObject childB = new JSONObject();
      childA.put( CHILDB, childB );
      
      JSONObject childC = new JSONObject();
      childA.put( CHILDC, childC );
      
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );
   }//End Method
   
   @Test public void shouldRejectMissingArray(){
      structureTree.addArray( CHILDA, structureTree.getRoot(), SIZE_RETRIEVER );
      
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );
   }//End Method
   
   @Test public void shouldRejectNonArrayWhenArrayExpected(){
      structureTree.addArray( CHILDA, structureTree.getRoot(), SIZE_RETRIEVER );
      object.put( CHILDA, new Object() );
      
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );
   }//End Method
   
   @Test public void shouldRejectMissingNestedArray(){
      structureTree.addArray( CHILDA, structureTree.getRoot(), SIZE_RETRIEVER );
      structureTree.addArray( CHILDB, CHILDA, SIZE_RETRIEVER );
      
      JSONArray childA = new JSONArray();
      object.put( CHILDA, childA );
      
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );      
   }//End Method
   
   @Test public void shouldAcceptArrayWithAnyNumberOfElementsSoLongAsOneIsPresent(){
      structureTree.addArray( CHILDA, structureTree.getRoot(), SIZE_RETRIEVER );
      structureTree.addArray( CHILDB, CHILDA, SIZE_RETRIEVER );
      
      JSONArray childA = new JSONArray();
      object.put( CHILDA, childA );
      
      for ( int i = 0; i < 2; i++ ) {
         JSONArray childB = new JSONArray();
         childA.put( childB );
      }
      
      assertThat( systemUnderTest.isCompatible( object ), is( true ) );  
   }//End Method
   
   @Test public void shouldAcceptFullyPopulatedNestedArrays(){
      structureTree.addArray( CHILDA, structureTree.getRoot(), SIZE_RETRIEVER );
      structureTree.addArray( CHILDB, CHILDA, SIZE_RETRIEVER );
      
      JSONArray childA = new JSONArray();
      object.put( CHILDA, childA );
      
      for ( int i = 0; i < 3; i++ ) {
         JSONArray childB = new JSONArray();
         childA.put( childB );
         
         for ( int j = 0; j < 3; j++ ) {
            childB.put( new JSONArray() );
         }
      }
      
      assertThat( systemUnderTest.isCompatible( object ), is( true ) );  
   }//End Method

   @Test public void shouldHandleMoreRealisticSituationAppropriately(){
      structureTree.addChild( CHILDA, structureTree.getRoot() );
      structureTree.addArray( CHILDB, CHILDA, SIZE_RETRIEVER );
      structureTree.addChild( CHILDC, CHILDB );
      structureTree.addChild( CHILDD, CHILDC );
      structureTree.addChild( CHILDE, CHILDC );
      
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );
      
      JSONObject childA = new JSONObject();
      object.put( CHILDA, childA );
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );
      
      JSONArray array = new JSONArray();
      childA.put( CHILDB, array );
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );
      
      JSONObject childC3 = new JSONObject();
      array.put( childC3 );
      JSONObject childD3 = new JSONObject();
      childC3.put( CHILDD, childD3 );
      assertThat( systemUnderTest.isCompatible( object ), is( false ) );
      
      JSONObject childE3 = new JSONObject();
      childC3.put( CHILDE, childE3 );
      
      assertThat( systemUnderTest.isCompatible( object ), is( true ) );
   }//End Method
}//End Class
