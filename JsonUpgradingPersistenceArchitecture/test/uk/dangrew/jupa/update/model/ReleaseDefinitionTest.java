/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.update.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link ReleaseDefinition} test.
 */
public class ReleaseDefinitionTest {

   private static final String ID = "1.2.3";
   private static final String DOWNLOAD = "http://...";
   private static final String DESCRIPTION = "anything can be used to describe the release";
   private static final String DATE = "Today";
   private ReleaseDefinition systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      systemUnderTest = new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION );
   }//End Method
   
   @Test public void shouldProvideReleaseIdentification() {
      assertThat( systemUnderTest.getIdentification(), is( ID ) );
   }//End Method
   
   @Test public void shouldProvideDownloadLink() {
      assertThat( systemUnderTest.getDownloadLocation(), is( DOWNLOAD ) );
   }//End Method
   
   @Test public void shouldProvideReleaseDescription() {
      assertThat( systemUnderTest.getDescription(), is( DESCRIPTION ) );
   }//End Method
   
   @Test public void shouldProvideDateIfSet(){
      assertThat( systemUnderTest.getDate(), is( nullValue() ) );
      assertThat( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION, DATE ).getDate(), is( DATE ) );
   }
   
   @Test public void differentDefinitionsShouldBeEqualWhen(){
      assertThat( systemUnderTest, is( equalTo( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION ) ) ) );
      assertThat( systemUnderTest, is( equalTo( systemUnderTest ) ) );
      assertThat( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION, DATE ), is( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION, "Today" ) ) );
   }//End Method
   
   @Test public void differentDefinitionsShouldNotBeEqualWhen(){
      assertThat( systemUnderTest, is( not( equalTo( new ReleaseDefinition( "anything", DOWNLOAD, DESCRIPTION ) ) ) ) );
      assertThat( systemUnderTest, is( not( equalTo( new ReleaseDefinition( ID, "anything", DESCRIPTION ) ) ) ) );
      assertThat( systemUnderTest, is( not( equalTo( new ReleaseDefinition( ID, DOWNLOAD, "anything" ) ) ) ) );
      assertThat( systemUnderTest, is( not( equalTo( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION, DATE ) ) ) ) );
      assertThat( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION, "anything" ), is( not( equalTo( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION, "Today" ) ) ) ) );
      assertThat( systemUnderTest, is( not( equalTo( null ) ) ) );
      assertThat( systemUnderTest, is( not( equalTo( new Object() ) ) ) );
   }//End Method
   
   @Test public void differentDefinitionsShouldHaveEqualHascodesWhen(){
      assertThat( systemUnderTest.hashCode(), is( equalTo( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION ).hashCode() ) ) );
      assertThat( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION, DATE ).hashCode(), is( equalTo( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION, "Today" ).hashCode() ) ) );
   }//End Method
   
   @Test public void differentDefinitionsShouldNotHaveEqualHashcodesWhen(){
      assertThat( systemUnderTest.hashCode(), is( not( equalTo( new ReleaseDefinition( "anything", DOWNLOAD, DESCRIPTION ).hashCode() ) ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( equalTo( new ReleaseDefinition( ID, "anything", DESCRIPTION ).hashCode() ) ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( equalTo( new ReleaseDefinition( ID, DOWNLOAD, "anything" ).hashCode() ) ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( equalTo( new ReleaseDefinition( ID, DOWNLOAD, DESCRIPTION, DATE ).hashCode() ) ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( equalTo( new Object().hashCode() ) ) ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullIdentification(){
      new ReleaseDefinition( null, DOWNLOAD, DESCRIPTION );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullDownloadLocation(){
      new ReleaseDefinition( ID, null, DESCRIPTION );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAcceptNullDescription(){
      new ReleaseDefinition( ID, DOWNLOAD, null );
   }//End Method

}//End Class
