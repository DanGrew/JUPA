/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse.handle.type;

import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;
import uk.dangrew.jupa.json.parse.handle.key.JsonValueHandler;

/**
 * {@link Enum} {@link JsonParseHandleImpl}.
 */
public class EnumTypeHandle< EnumTypeT extends Enum< EnumTypeT > > extends JsonParseHandleImpl< EnumTypeT > {
   
   private final Class< EnumTypeT > enumType;
   
   /**
    * Constructs a new {@link EnumTypeHandle}.
    * @param enumType the {@link Class} of the {@link Enum}.
    * @param handle the {@link JsonKeyHandle} associated.
    */
   public EnumTypeHandle( Class< EnumTypeT > enumType, JsonKeyHandle< EnumTypeT > handle ) {
      super( handle );

      if ( enumType == null ) {
         throw new IllegalArgumentException( "Null enum type not permitted." );
      }
      this.enumType = enumType;
   }//End Constructor
   
   /**
    * Constructs a new {@link EnumTypeHandle} with the given method in a {@link JsonValueHandler}.
    * @param enumType the {@link Class} of the {@link Enum}.
    * @param handle the handle to use in a {@link JsonValueHandler}.
    */
   public EnumTypeHandle( Class< EnumTypeT > enumType, BiConsumer< String, EnumTypeT > handle ) {
      this( enumType, new JsonValueHandler<>( handle ) );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleKeyPresent( String key, JSONObject object ) {
      EnumTypeT value = object.optEnum( enumType, key );
      super.handle( key, value );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handleArrayIndexPresent( String key, JSONArray array, int index ) {
      EnumTypeT value = array.optEnum( enumType, index );
      super.handle( key, value );
   }//End Method

}//End Class
