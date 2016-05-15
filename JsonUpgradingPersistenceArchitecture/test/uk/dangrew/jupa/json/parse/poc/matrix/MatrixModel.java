/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse.poc.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic model to hold a two and three dimensional array parse from Json.
 */
public class MatrixModel {
   
   static final String TWO_DIMENSIONAL = "two-dimensional";
   static final String THREE_DIMENSIONAL = "three-dimensional";
   
   List< List< Integer > > twoDimensional;
   List< List< List< Integer > > > threeDimensional;
   
   private List< List< Integer > > currentSecondDimension;
   private List< Integer > currentValues;
   
   /**
    * Constructs a new {@link MatrixModel}.
    */
   MatrixModel() {
      twoDimensional = new ArrayList<>();
      threeDimensional = new ArrayList<>();
   }//End Constructor
   
   /**
    * Method triggered when any array is started.
    * @param key the key parsed for.
    */
   void arrayStarted( String key ) {
      if ( TWO_DIMENSIONAL.equals( key ) ) {
         currentValues = new ArrayList<>();
      } else if ( THREE_DIMENSIONAL.equals( key ) ) {
         if ( currentSecondDimension == null ) {
            currentSecondDimension = new ArrayList<>();
         } else if ( currentValues == null ) {
            currentValues = new ArrayList<>();   
         }
      }
   }//End Method
   
   /**
    * Method triggered when any array is finished.
    * @param key the key parsed for.
    */
   void arrayFinished( String key ) {
      if ( currentValues != null ) {
         if ( TWO_DIMENSIONAL.equals( key ) ) {
            twoDimensional.add( currentValues );
         } else if ( THREE_DIMENSIONAL.equals( key ) ) {
            currentSecondDimension.add( currentValues );
         }
         currentValues = null;
      } else if ( currentSecondDimension != null ) {
         threeDimensional.add( currentSecondDimension );
         currentSecondDimension = null;
      }
   }//End Method
   
   /**
    * Method triggered when an item of an array is parsed.
    * @param key the key parsed for.
    * @param value the value parsed.
    */
   void arrayItem( String key, int value ) {
      currentValues.add( value );
   }//End Method

}//End Class
