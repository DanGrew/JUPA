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

import uk.dangrew.jupa.json.parse.handle.key.JsonKeyParseHandle;
import uk.dangrew.jupa.json.parse.handle.key.JsonValueParseHandler;

/**
 * {@link Enum} {@link JsonParseHandleImpl}.
 */
public class EnumParseHandle< EnumTypeT extends Enum< EnumTypeT > > extends JsonParseHandleImpl< EnumTypeT > {
   
   private final Class< EnumTypeT > enumType;
   
   /**
    * Constructs a new {@link EnumParseHandle}.
    * @param enumType the {@link Class} of the {@link Enum}.
    * @param handle the {@link JsonKeyParseHandle} associated.
    */
   public EnumParseHandle( Class< EnumTypeT > enumType, JsonKeyParseHandle< EnumTypeT > handle ) {
      super( handle );

      if ( enumType == null ) {
         throw new IllegalArgumentException( "Null enum type not permitted." );
      }
      this.enumType = enumType;
   }//End Constructor
   
   /**
    * Constructs a new {@link EnumParseHandle} with the given method in a {@link JsonValueParseHandler}.
    * @param enumType the {@link Class} of the {@link Enum}.
    * @param handle the handle to use in a {@link JsonValueParseHandler}.
    */
   public EnumParseHandle( Class< EnumTypeT > enumType, BiConsumer< String, EnumTypeT > handle ) {
      this( enumType, new JsonValueParseHandler<>( handle ) );
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
