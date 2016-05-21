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
 * {@link JsonValueSetterFunction} is a {@link BiFunction} that will put a specific
 * value into a {@link JsonNavigable}.
 */
public class JsonValueSetterFunction implements BiFunction< JsonNavigable, Object, Object >{

   private final Object value;
   
   /**
    * Constructs a new {@link JsonValueSetterFunction}.
    * @param value the value to put.
    */
   JsonValueSetterFunction( Object value ) {
      this.value = value;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public Object apply( JsonNavigable navigable, Object parent ) {
      navigable.put( parent, value );
      return null;
   }//End Method

}//End Class
