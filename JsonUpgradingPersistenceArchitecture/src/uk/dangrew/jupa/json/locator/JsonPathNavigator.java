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

import java.util.List;
import java.util.function.BiFunction;

/**
 * The {@link JsonPathNavigator} is responsible to navigating through {@link JsonNavigable}s
 * given certain behaviour defined in {@link BiFunction}s.
 */
public class JsonPathNavigator {
   
   private final Object parent; 
   private final List< JsonNavigable > path; 
   private final BiFunction< JsonNavigable, Object, Object > navigationHandler;
   private final BiFunction< JsonNavigable, Object, Object > destinationHandler;
   
   /**
    * Constructs a new {@link JsonPathNavigator}.
    * @param parent the parent to navigate from.
    * @param path the {@link List} of {@link JsonNavigable} to navigate through.
    * @param navigationHandler the function for navigating.
    * @param destinationHandler the function for handling the destination of the path.
    */
   JsonPathNavigator(
            Object parent, 
            List< JsonNavigable > path, 
            BiFunction< JsonNavigable, Object, Object > navigationHandler,
            BiFunction< JsonNavigable, Object, Object > destinationHandler 
   ) {
      if ( parent == null || path == null || navigationHandler == null || destinationHandler == null ) {
         throw new NullPointerException( "Navigation parameters must not be null." );
      }
      
      this.parent = parent;
      this.path = path;
      this.navigationHandler = navigationHandler;
      this.destinationHandler = destinationHandler;
   }//End Constructor
   
   /**
    * Method to navigate along the path and extract the destination.
    * @return the extracted value at the end of the path, or null.
    */
   Object navigate() {
      Object destination = navigateToDestination();
      return handleDestination( destination );
   }//End Method
   
   /**
    * Method to perform the navigation through all but the last {@link JsonNavigable}.
    * @return the destination json structure.
    */
   private Object navigateToDestination(){
      if ( path.isEmpty() ) {
         return null;
      }
      
      if ( path.size() == 1 ) {
         return parent;
      }
      
      Object subject = parent;
      
      for ( int i = 0; i < path.size() - 1; i++ ) {
         JsonNavigable navigable = path.get( i );
         subject = navigationHandler.apply( navigable, subject );
         if ( subject == null ) {
            return null;
         }
      }
      
      return subject;
   }//End Method
   
   /**
    * Method to handle the arrival at the destination.
    * @param destination the object representing the destination.
    * @return the result of handling the destination with the associated function.
    */
   private Object handleDestination( Object destination ) {
      if ( destination == null ) {
         return null;
      }
      
      JsonNavigable lastNavigable = path.get( path.size() - 1 );
      return destinationHandler.apply( lastNavigable, destination );
   }//End Method

}//End Class
