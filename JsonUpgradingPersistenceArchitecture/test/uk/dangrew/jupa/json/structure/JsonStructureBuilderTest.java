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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.function.Function;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link JsonStructureBuilder} test.
 */
public class JsonStructureBuilderTest {

   private static final String ROOT = JsonStructureTree.ROOT;
   private static final String CHILD = "Child";
   private static final String ANOTHER_CHILD = "Another Child";
   private static final String ARRAY = "array";
   private static final String KEY_A = "KeyA";
   private static final String KEY_B = "KeyB";
   private static final String KEY_C = "KeyC";
   private static final Function< String, Integer > ARRAY_SIZE_FUNCTION = key -> 3;
   
   private JSONObject jsonObject;
   private JsonStructureTree tree;
   private JsonStructureBuilder systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      jsonObject = new JSONObject();
      tree = new JsonStructureTree();
      systemUnderTest = new JsonStructureBuilder( tree );
   }//End Method
   
   @Test public void shouldBuildSingleKeyIntoRoot() {
      tree.addChild( CHILD, ROOT );
      systemUnderTest.build( jsonObject );
      
      assertThat( jsonObject.has( CHILD ), is( true ) );
      assertThat( jsonObject.get( CHILD ), is( JsonStructureBuilder.PLACEHOLDER ) );
   }//End Method
   
   @Test public void shouldBuildMultipleKeysIntoRoot(){
      tree.addChild( KEY_A, ROOT );
      tree.addChild( KEY_B, ROOT );
      tree.addChild( KEY_C, ROOT );
      
      systemUnderTest.build( jsonObject );
      
      assertThat( jsonObject.has( KEY_A ), is( true ) );
      assertThat( jsonObject.get( KEY_A ), is( JsonStructureBuilder.PLACEHOLDER ) );
      
      assertThat( jsonObject.has( KEY_B ), is( true ) );
      assertThat( jsonObject.get( KEY_B ), is( JsonStructureBuilder.PLACEHOLDER ) );
      
      assertThat( jsonObject.has( KEY_B ), is( true ) );
      assertThat( jsonObject.get( KEY_B ), is( JsonStructureBuilder.PLACEHOLDER ) );
   }//End Method
   
   @Test public void shouldBuildNestedObjectWithKeys(){
      tree.addChild( CHILD, ROOT );
      tree.addChild( KEY_A, CHILD );
      tree.addChild( KEY_B, CHILD );
      tree.addChild( KEY_C, CHILD );
      
      systemUnderTest.build( jsonObject );
      
      assertThat( jsonObject.has( CHILD ), is( true ) );
      JSONObject child = jsonObject.getJSONObject( CHILD );
      
      assertThat( child.has( KEY_A ), is( true ) );
      assertThat( child.get( KEY_A ), is( JsonStructureBuilder.PLACEHOLDER ) );
      
      assertThat( child.has( KEY_B ), is( true ) );
      assertThat( child.get( KEY_B ), is( JsonStructureBuilder.PLACEHOLDER ) );
      
      assertThat( child.has( KEY_C ), is( true ) );
      assertThat( child.get( KEY_C ), is( JsonStructureBuilder.PLACEHOLDER ) );
   }//End Method
   
   @Test public void shouldBuildNestedArrayWithValues(){
      tree.addArray( ARRAY, ROOT, ARRAY_SIZE_FUNCTION );
      final int arrayLength = ARRAY_SIZE_FUNCTION.apply( ARRAY );
      
      systemUnderTest.build( jsonObject );
      
      assertThat( jsonObject.has( ARRAY ), is( true ) );
      JSONArray jsonArray = jsonObject.getJSONArray( ARRAY );
      assertThat( jsonArray.length(), is( arrayLength ) );
      for ( int i = 0; i < arrayLength; i++ ) {
         assertThat( jsonArray.get( i ), is( JsonStructureBuilder.PLACEHOLDER ) );
      }
   }//End Method
   
   @Test public void shouldBuildNestedArrayWithObjects(){
      tree.addArray( ARRAY, ROOT, ARRAY_SIZE_FUNCTION );
      tree.addChild( CHILD, ARRAY );
      tree.addChild( KEY_A, CHILD );
      
      final int arrayLength = ARRAY_SIZE_FUNCTION.apply( ARRAY );
      
      systemUnderTest.build( jsonObject );
      
      assertThat( jsonObject.has( ARRAY ), is( true ) );
      JSONArray jsonArray = jsonObject.getJSONArray( ARRAY );
      assertThat( jsonArray.length(), is( arrayLength ) );
      for ( int i = 0; i < arrayLength; i++ ) {
         JSONObject arrayObject = jsonArray.getJSONObject( i );
         assertThat( arrayObject.get( KEY_A ), is( JsonStructureBuilder.PLACEHOLDER ) );
      }
   }//End Method
   
   @Test public void shouldBuildNestedObjectsWithArraysAndKeys(){
      tree.addChild( CHILD, ROOT );
      tree.addArray( ARRAY, CHILD, ARRAY_SIZE_FUNCTION );
      tree.addChild( ANOTHER_CHILD, ARRAY );
      tree.addChild( KEY_A, ANOTHER_CHILD );
      tree.addChild( KEY_B, CHILD );
      
      final int arrayLength = ARRAY_SIZE_FUNCTION.apply( ARRAY );
      
      systemUnderTest.build( jsonObject );
      
      assertThat( jsonObject.has( CHILD ), is( true ) );
      
      JSONObject child = jsonObject.getJSONObject( CHILD );
      assertThat( child.get( KEY_B ), is ( JsonStructureBuilder.PLACEHOLDER ) );
      
      JSONArray jsonArray = child.getJSONArray( ARRAY );
      assertThat( jsonArray.length(), is( arrayLength ) );
      for ( int i = 0; i < arrayLength; i++ ) {
         JSONObject arrayObject = jsonArray.getJSONObject( i );
         assertThat( arrayObject.get( KEY_A ), is( JsonStructureBuilder.PLACEHOLDER ) );
      }
   }//End Method
   
   @Test public void shouldBuildArraysWithinArrays(){
      final int childLength = 10;
      final int anotherChildLength = 15;
      
      tree.addArray( CHILD, ROOT, key -> childLength );
      tree.addArray( ANOTHER_CHILD, CHILD, key -> anotherChildLength );
      
      systemUnderTest.build( jsonObject );
      
      JSONArray child = jsonObject.getJSONArray( CHILD );
      assertThat( child.length(), is( childLength ) );
      for ( int i = 0; i < childLength; i++ ) {
         JSONArray anotherChild = child.getJSONArray( i );
         assertThat( anotherChild.length(), is( anotherChildLength ) );
         
         for ( int j = 0; j < anotherChildLength; j++ ) {
            assertThat( anotherChild.get( i ), is( JsonStructureBuilder.PLACEHOLDER ) );
         }
      }
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullArrayFunction(){
      tree.addArray( ARRAY, ROOT, null );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullArraySize(){
      tree.addArray( ARRAY, ROOT, key -> null );
      systemUnderTest.build( jsonObject );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleChildForArrays(){
      JsonStructureTree mockedTree = mock( JsonStructureTree.class );
      systemUnderTest = new JsonStructureBuilder( mockedTree );
      
      when( mockedTree.getRoot() ).thenReturn( ROOT );
      when( mockedTree.getChildrenOf( ROOT ) ).thenReturn( Arrays.asList( ARRAY ) );
      when( mockedTree.isArray( ARRAY ) ).thenReturn( true );
      when( mockedTree.getChildrenOf( ARRAY ) ).thenReturn( Arrays.asList( "one", "two" ) );
      
      systemUnderTest.build( jsonObject );
   }//End Method
   
   @Test public void placeholderShouldProvideConstant(){
      assertThat( systemUnderTest.getPlaceholder(), is( JsonStructureBuilder.PLACEHOLDER ) );
   }//End Method

}//End Class
