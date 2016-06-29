/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
 package uk.dangrew.jupa.graphics.launch;

import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import com.sun.javafx.application.PlatformImpl;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The {@link TestApplication} provides a JavaFx {@link Application} that can be used to launch
 * graphics in a test environment.
 */
public class TestApplication extends Application {

   private Parent center;
   
   /**
    * Constructs a new {@link TestApplication} with a {@link Parent} to display.
    * @param center the {@link Parent}.
    */
   public TestApplication( Parent center ) {
      this.center = center;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void start( Stage primaryStage ) throws Exception {
      Scene scene = new Scene( center, 100, 100 );
      primaryStage.setScene( scene );
      primaryStage.show();
   }//End Method
   
   /**
    * Method to launch a {@link TestApplication} in a simple way, using a {@link Supplier} so that
    * the graphical {@link Parent} is constructed on the JavaFx {@link Thread}.
    * @param supplier the {@link Supplier} of the {@link Parent}.
    */
   public static void launch( Supplier< Parent > supplier ) throws InterruptedException {
      CountDownLatch latch = new CountDownLatch( 1 );
      PlatformImpl.startup( () -> {
         try {
            TestApplication application = new TestApplication( supplier.get() );
            application.start( new Stage() );
            latch.countDown();
         } catch ( Exception e ) {
            fail();
         }
      } );
      latch.await();
   }//End Method
   
   /**
    * Method to start the {@link PlatformImpl} if not already started.
    */
   public static void startPlatform(){
      PlatformImpl.startup( () -> {} );
   }//End Method

}//End Class
