import java.awt.AWTEventMulticaster ;
import java.awt.event.ActionListener ;
/**
 * Write a description of class PulsSourceAdapter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class PulseSourceAdapter implements IPulseSource
{
    ActionListener actionListener;//Refers to a list of ActionListener objects
    PulseMode  mode = PulseMode.CONTINOUS ; 
    int msPeriod ;
   
   /*The following method adds ActionListener objects to the list 
    described above
    */ 
   public void addActionListener(ActionListener listener) {
     actionListener = AWTEventMulticaster.add(actionListener, listener);
   }//end addActionListener()
   //-----------------------------------------------------------------------
  /*The following method removes ActionListener objects from the list 
    described above
    */
   public void removeActionListener(ActionListener listener) {
      actionListener = AWTEventMulticaster.remove(actionListener, listener);
   }

    
    @Override
    public void setMode(PulseMode mode) {}
    @Override
    public PulseMode getMode() {return mode ;}
    
    @Override
    public void setPulsePeriod(int ms) {}
    @Override
    public int getPulsePeriod() {return msPeriod ;}
    @Override
    public void setPulseCount(int burst) {}
    
}
