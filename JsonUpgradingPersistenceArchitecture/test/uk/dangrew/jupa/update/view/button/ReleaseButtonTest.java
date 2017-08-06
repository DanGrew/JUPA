/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.view.button;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.layout.GridPane;
import uk.dangrew.jupa.update.model.ReleaseDefinition;
import uk.dangrew.kode.launch.TestApplication;

/**
 * {@link ReleaseButton} test.
 */
public class ReleaseButtonTest {

   private static final String RELEASE = "some release";
   private static final String DOWNLOAD = "some download";
   private static final String DESCRIPTION = "some description";
   private static final String DATE = "some date";
   
   @Mock private InstallerButtonController controller;
   private ReleaseDefinition release;
   private ReleaseButton systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      release = new ReleaseDefinition( RELEASE, DOWNLOAD, DESCRIPTION );
      systemUnderTest = new ReleaseButton( controller, release );
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
   
   @Test public void releaseWithDateShouldBehaveTheSameAsOneWithout(){
      systemUnderTest = new ReleaseButton( controller, new ReleaseDefinition( RELEASE, DOWNLOAD, DESCRIPTION, DATE ) );
      labelsShouldProvideDescriptionOfItem();
      valueLabelsShouldProvideReleaseInformation();
      descriptionLabelMustWrapText();
      buttonShouldStretchToWidth();
   }//End Method
   
   @Test public void dateShouldBeDisplayedBetweenOtherLabels(){
      systemUnderTest = new ReleaseButton( controller, new ReleaseDefinition( RELEASE, DOWNLOAD, DESCRIPTION, DATE ) );
      assertThat( systemUnderTest.dateLabel().getText(), is( ReleaseButton.DATE_LABEL ) );
      assertThat( systemUnderTest.date().getText(), is( DATE ) );
   }//End Method
   
   @Test public void shouldContainElementsToDisplayReleaseWithDate() {
      systemUnderTest = new ReleaseButton( controller, new ReleaseDefinition( RELEASE, DOWNLOAD, DESCRIPTION, DATE ) );
      
      GridPane graphic = ( GridPane ) systemUnderTest.getGraphic();
      assertThat( graphic.getChildren(), contains( 
               systemUnderTest.versionLabel(), 
               systemUnderTest.version(),
               systemUnderTest.dateLabel(),
               systemUnderTest.date(),
               systemUnderTest.descriptionLabel(), 
               systemUnderTest.description()
      ) );
   }//End Method
   
   @Test public void shouldBeAssociatedWithCorrectController(){
      assertThat( systemUnderTest.hasController( controller ), is( true ) );
      assertThat( systemUnderTest.hasController( mock( InstallerButtonController.class ) ), is( false ) );
   }//End Method
   
   @Test public void shouldBeAssociatedWithCorrectRelease(){
      assertThat( systemUnderTest.hasRelease( release ), is( true ) );
      assertThat( systemUnderTest.hasRelease( mock( ReleaseDefinition.class ) ), is( false ) );
   }//End Method
}//End Class
