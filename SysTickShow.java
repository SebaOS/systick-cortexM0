import java.util.Observer;
import java.util.Observable;

public class SysTickShow 
{
    private CortexM0SysTick sysTickPokazowy;
    
    public SysTickShow()
    {
        sysTickPokazowy = new CortexM0SysTick();
        makeShow();
    }
     
    public static void main (String [] arg) {
        new SysTickShow();
    }
    
    public void makeShow() {
        System.out.println("\n----------------------------\n");
        System.out.println("\n Stan początkowy SysTicka:\n\n" + sysTickPokazowy.toString());
        sysTickPokazowy.impuls();
        sysTickPokazowy.setRVR(9);
        sysTickPokazowy.setEnable(true);
        sysTickPokazowy.impuls();
        System.out.println("\nStan SysTicka po 1 impulsie :\n\n" + sysTickPokazowy.toString());
        sysTickPokazowy.impuls();
        System.out.println("\nStan SysTicka po 2 impulsie:\n\n" + sysTickPokazowy.toString());
        sysTickPokazowy.impuls();
        System.out.println("\nStan SystTicka po 3 impulsie:\n\n" + sysTickPokazowy.toString());
                 
    }
}



