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

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link JsonStructureTree} test.
 */
public class JsonStructureTreeTest {

   private static final String ROOT = JsonStructureTree.ROOT;
   private static final String CHILD = "any child";
   private static final String ANOTHER_CHILD = "another child";
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
      systemUnderTest.addArray( CHILD, ROOT );
      assertThat( systemUnderTest.getChildrenOf( ROOT ), contains( CHILD ) );
      assertThat( systemUnderTest.getArraySize( CHILD ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldDetermineWhetherKeysAreArrays(){
      assertThat( systemUnderTest.isArray( CHILD ), is( false ) );
      systemUnderTest.addArray( CHILD, ROOT );
      assertThat( systemUnderTest.isArray( CHILD ), is( true ) );
      
      assertThat( systemUnderTest.isArray( ANOTHER_CHILD ), is( false ) );
      systemUnderTest.addChild( ANOTHER_CHILD, ROOT );
      assertThat( systemUnderTest.isArray( ANOTHER_CHILD ), is( false ) );
   }//End Method
   
   @Test public void shouldProvideArraySizeForConstruction(){
      systemUnderTest.addArray( CHILD, ROOT );
      systemUnderTest.setArraySize( CHILD, 5 );
      assertThat( systemUnderTest.getArraySize( CHILD ), is( 5 ) );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAcceptArraySizeForMissingArrayKey(){
      systemUnderTest.setArraySize( CHILD, 5 );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldOnlyAllowOneChildForAnArray(){
      systemUnderTest.addArray( CHILD, ROOT );
      systemUnderTest.addChild( ANOTHER_CHILD, CHILD );
      systemUnderTest.addChild( "anything else", CHILD );
   }//End Method
   
   @Test public void shouldAllowArraysWithinArrays(){
      systemUnderTest.addArray( CHILD, ROOT );
      systemUnderTest.addArray( ANOTHER_CHILD, CHILD );
      
      assertThat( systemUnderTest.getChildrenOf( ROOT ), contains( CHILD ) );
      assertThat( systemUnderTest.getChildrenOf( CHILD ), contains( ANOTHER_CHILD ) );
   }//End Method

}//End Class
