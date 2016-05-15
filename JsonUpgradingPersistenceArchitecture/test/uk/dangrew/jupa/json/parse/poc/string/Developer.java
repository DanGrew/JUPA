/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse.poc.string;

import java.util.List;

/** Simple data class for fields.**/
public final class Developer {
   
   String firstName;
   String lastName;
   List< String > skills;
   List< Project > projects;
   
}//End Class
