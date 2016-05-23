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
    * @param parent the {@link Object} to navigate through. 
    * @return the {@link Object} navigated to, or null.
    */
   public Object navigate( Object parent );
   
   /**
    * Method to put the value given into the parent object.
    * @param parent the parent to put the value in.
    * @param value the value to put.
    */
   public void put( Object parent, Object value );

   /**
    * Method to generate the structure associated with the {@link JsonNavigable}.
    * @return the structure such as {@link org.json.JSONObject} or {@link org.json.JSONArray}.
    */
   public Object generateStructure();

}//End Interface
