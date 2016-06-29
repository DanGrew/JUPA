/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.view;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.layout.GridPane;
import uk.dangrew.jupa.graphics.launch.TestApplication;
import uk.dangrew.jupa.update.model.ReleaseDefinition;

/**
 * {@link ReleaseButton} test.
 */
public class ReleaseButtonTest {

   private static final String RELEASE = "some release";
   private static final String DOWNLOAD = "some download";
   private static final String DESCRIPTION = "some description";
   
   private ReleaseDefinition release;
   private ReleaseButton systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      release = new ReleaseDefinition( RELEASE, DOWNLOAD, DESCRIPTION );
      systemUnderTest = new ReleaseButton( release );
   }//End Method
   
   @Test public void shouldContainElementsToDisplayRelease() {
      assertThat( systemUnderTest.getGraphic(), is( instanceOf( GridPane.class ) ) );
      
      GridPane graphic = ( GridPane ) systemUnderTest.getGraphic();
      assertThat( graphic.getChildren(), contains( 
               systemUnderTest.versionLabel(), 
               systemUnderTest.version(),
               systemUnderTest.descriptionLabel(), 
               systemUnderTest.description()
      ) );
   }//End Method
   
   @Test public void labelsShouldProvideDescriptionOfItem(){
      assertThat( systemUnderTest.versionLabel().getText(), is( ReleaseButton.RELEASE_LABEL ) );
      assertThat( systemUnderTest.descriptionLabel().getText(), is( ReleaseButton.DESCRIPTION_LABEL ) );
   }//End Method
   
   @Test public void valueLabelsShouldProvideReleaseInformation(){
      assertThat( systemUnderTest.version().getText(), is( RELEASE ) );
      assertThat( systemUnderTest.description().getText(), is( DESCRIPTION ) );
   }//End Method

   @Test public void descriptionLabelMustWrapText(){
      assertThat( systemUnderTest.description().isWrapText(), is( true ) );
   }//End Method
   
   @Test public void buttonShouldStretchToWidth(){
      assertThat( systemUnderTest.getMaxWidth(), is( Double.MAX_VALUE ) );
   }//End Method
}//End Class
