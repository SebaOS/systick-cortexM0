import java.awt.event.ActionEvent;

public class Generator extends PulseSourceAdapter implements Runnable
{
    Thread generatorThread;
    
    /**
     * Constructor for objects of class PulseGenerator
     */
    public Generator()
    {
        msPeriod = 2000 ;
    }
    
    public Generator(int freq) {msPeriod = freq;}
    
    public void setFreq(int freq) {this.msPeriod = freq;}
    
    @Override
    public void halt() { 
        try {
            generatorThread.interrupt();
            
        } catch (Exception e) { /* in case halt before trigger*/}
    }
    
    @Override
    public void trigger() {
        if ((generatorThread == null) || (!generatorThread.isAlive()) ) createGeneratorEngine() ;
       
    }

    private void createGeneratorEngine() {
        generatorThread = new Thread (this);
        generatorThread.start() ;
         
    }
    
    public void run () {
       while (!Thread.currentThread().isInterrupted()) {
         try {
             Thread.sleep(msPeriod) ;
          
             if(actionListener != null) 
             {
                actionListener.actionPerformed
                (new ActionEvent (this, ActionEvent.ACTION_PERFORMED, "impuls"));
             }
         } catch (InterruptedException ie) {
                break ; /* Allow thread to exit */
            }
       }
    }
}
