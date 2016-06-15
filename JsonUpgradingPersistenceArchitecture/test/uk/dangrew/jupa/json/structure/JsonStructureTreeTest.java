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

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.jupa.json.structure.JsonStructureTree;

/**
 * {@link JsonStructureTree} test.
 */
public class JsonStructureTreeTest {

   private static final String ROOT = JsonStructureTree.ROOT;
   private static final String CHILD = "any child";
   private static final String ANOTHER_CHILD = "another child";
   private static final Function< String, Integer > ARRAY_SIZE_FUNCTION = key -> 5;
   private JsonStructureTree systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      systemUnderTest = new JsonStructureTree();
   }//End Method
   
   @Test public void shouldProvideRoot() {
      assertThat( systemUnderTest.getRoot(), is( ROOT ) );
   }//End Method
   
   @Test public void shouldAddChildToTreeForParent() {
      assertThat( systemUnderTest.getChildrenOf( ROOT ), is( empty() ) );
      systemUnderTest.addChild( CHILD, ROOT );
      assertThat( systemUnderTest.getChildrenOf( ROOT ), hasSize( 1 ) );
      assertThat( systemUnderTest.getChildrenOf( ROOT ), contains( CHILD ) );
   }//End Method
   
   @Test public void shouldIgnoreDuplicateChildren() {
      assertThat( systemUnderTest.getChildrenOf( ROOT ), is( empty() ) );
      systemUnderTest.addChild( CHILD, ROOT );
      systemUnderTest.addChild( CHILD, ROOT );
      assertThat( systemUnderTest.getChildrenOf( ROOT ), hasSize( 1 ) );
      assertThat( systemUnderTest.getChildrenOf( ROOT ), contains( CHILD ) );
   }//End Method
   
   @Test public void shouldAddArraysToTree(){
      systemUnderTest.addArray( CHILD, ROOT, ARRAY_SIZE_FUNCTION );
      assertThat( systemUnderTest.getChildrenOf( ROOT ), contains( CHILD ) );
      assertThat( systemUnderTest.getArraySize( CHILD ), is( ARRAY_SIZE_FUNCTION.apply( CHILD ) ) );
   }//End Method
   
   @Test public void shouldDetermineWhetherKeysAreArrays(){
      assertThat( systemUnderTest.isArray( CHILD ), is( false ) );
      systemUnderTest.addArray( CHILD, ROOT, ARRAY_SIZE_FUNCTION );
      assertThat( systemUnderTest.isArray( CHILD ), is( true ) );
      
      assertThat( systemUnderTest.isArray( ANOTHER_CHILD ), is( false ) );
      systemUnderTest.addChild( ANOTHER_CHILD, ROOT );
      assertThat( systemUnderTest.isArray( ANOTHER_CHILD ), is( false ) );
   }//End Method
   
   @Test( expected = NullPointerException.class ) public void shouldNotAcceptNullArraySizeFunction(){
      systemUnderTest.addArray( CHILD, ROOT, null );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldOnlyAllowOneChildForAnArray(){
      systemUnderTest.addArray( CHILD, ROOT, ARRAY_SIZE_FUNCTION );
      systemUnderTest.addChild( ANOTHER_CHILD, CHILD );
      systemUnderTest.addChild( "anything else", CHILD );
   }//End Method
   
   @Test public void shouldAllowArraysWithinArrays(){
      systemUnderTest.addArray( CHILD, ROOT, ARRAY_SIZE_FUNCTION );
      systemUnderTest.addArray( ANOTHER_CHILD, CHILD, ARRAY_SIZE_FUNCTION );
      
      assertThat( systemUnderTest.getChildrenOf( ROOT ), contains( CHILD ) );
      assertThat( systemUnderTest.getChildrenOf( CHILD ), contains( ANOTHER_CHILD ) );
   }//End Method

}//End Class
