/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.locator;

import java.util.function.BiFunction;

/**
 * THe {@link JsonPathPopulator} is responsible for populating a path of {@link JsonNavigable}s
 * based on the last visited. The idea is to be able to go from nothing to a json structure by
 * only defining the items the path goes through.
 */
class JsonPathPopulator implements BiFunction< JsonNavigable, Object, Object >{
   
   private Object previousParent;
   private JsonNavigable previouslyNavigated;
   
   /**
    * {@inheritDoc}
    * Method to navigate over the parent or populate the child if needed.
    */
   @Override public Object apply( JsonNavigable navigable, Object parent ) {
      if ( navigable == null ) {
         throw new NullPointerException( "Must provide JsonNavigable." );
      }

      if ( parent == null && previousParent == null ) {
         /* Note that this is because while the mechanism could cope, there would be
          * no way of retrieving the root. */
         throw new NullPointerException( "Parent must not be null when starting populating." );
      }
      
      if ( parent != null ) {
         storePrevious( navigable, parent );
         return navigable.navigate( parent );
      }
      
      Object structure = navigable.generateStructure();
      previouslyNavigated.put( previousParent, structure );
      storePrevious( navigable, structure );
      return null;
   }//End Method
   
   /**
    * Method to store the previous {@link JsonNavigable} and {@link Object}.
    * @param navigable the {@link JsonNavigable} last visited.
    * @param parent the last parent in the json structure.
    */
   private void storePrevious( JsonNavigable navigable, Object parent ) {
      previouslyNavigated = navigable;
      previousParent = parent;
   }//End Method

}//End Class
