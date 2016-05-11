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

/**
 * {@link JsonNavigable} provides an interface for navigating over Json objects and arrays
 * while locating keys.
 */
public interface JsonNavigable {
   
   /**
    * Method to navigate through the given object according to the associated constraints.
    * @param object the {@link Object} to navigate through. 
    * @return the {@link Object} navigated to, or null.
    */
   public Object navigate( Object object );

}//End Interface
