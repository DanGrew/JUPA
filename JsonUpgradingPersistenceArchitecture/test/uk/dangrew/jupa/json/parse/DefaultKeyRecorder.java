/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.json.parse;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import uk.dangrew.jupa.json.parse.handle.key.JsonKeyHandle;

/**
 * The {@link DefaultKeyRecorder} is respisible for providing a basic testable
 * output when handling keys from a Json parse.
 */
public class DefaultKeyRecorder implements JsonKeyHandle< String > {

   static final String OBJECT_STARTED = "ObjectStarted";
   static final String OBJECT_FINISHED = "ObjectFinished";
   static final String ARRAY_STARTED = "ArrayStarted";
   static final String ARRAY_FINISHED = "ArrayFinished";
   
   private List< Pair< String, Object > > recordedKeys;
   private int recordCount = 0;
   
   /**
    * Constructs a new {@link DefaultKeyRecorder}.
    */
   DefaultKeyRecorder() {
      recordedKeys = new ArrayList<>();
   }//End Constructor
   
   /**
    * Method to record a key when encountered.
    * @param key the key found.
    * @param value the value found.
    */
   private void recordKey( String key, String value ){
      recordedKeys.add( new Pair<>( key, value ) );
   }//End Method
   
   /**
    * Method to expect and assert that the given key and value ore recorded next.
    * @param key the expected key.
    * @param value the expected value.
    */
   void expect( String key, String value ) {
      assertThat( recordCount, lessThan( recordedKeys.size() ) );
      assertThat( recordedKeys.get( recordCount ).getKey(), is( key ) );
      
      if ( value == null ) {
         assertThat( recordedKeys.get( recordCount ).getValue(), is( nullValue() ) );
      } else {
         assertThat( recordedKeys.get( recordCount ).getValue(), is( value ) );
      }
      
      recordCount++;
   }//End Method
   
   /**
    * Method to assert that all keys have been found and nothing more.
    */
   void expectKeysFound(){
      assertThat( recordedKeys, hasSize( recordCount ) );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void handle( String key, String value ) {
      recordKey( key, value );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void startedObject( String key ) {
      recordKey( key, OBJECT_STARTED );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void finishedObject( String key ) {
      recordKey( key, OBJECT_FINISHED );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void startedArray( String key ) {
      recordKey( key, ARRAY_STARTED );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void finishedArray( String key ) {
      recordKey( key, ARRAY_FINISHED );
   }//End Method
      
}//End Class
