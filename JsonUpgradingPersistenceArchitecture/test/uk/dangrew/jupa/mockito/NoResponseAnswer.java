/*
 * ----------------------------------------
 *           Json Upgrading and 
 *        Persistence Architecture
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2016
 * ----------------------------------------
 */
package uk.dangrew.jupa.mockito;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * The {@link NoResponseAnswer} provides an {@link Answer} that executes a {@link Runnable}.
 */
public class NoResponseAnswer< TypeT > implements Answer< TypeT >{

   private final Runnable runnable;
   
   /**
    * Constructs a new {@link NoResponseAnswer}.
    * @param runnable the {@link Runnable} to run when invoked.
    */
   public NoResponseAnswer( Runnable runnable ) {
      this.runnable = runnable;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public TypeT answer( InvocationOnMock invocation ) throws Throwable {
      runnable.run();
      return null;
   }//End Method

}//End Class
