import java.awt.event.ActionListener ;
public interface IPulseSource
{

enum PulseMode
{
    BURST, CONTINOUS
}

    void addActionListener(ActionListener pl);		
    void removeActionListener(ActionListener pl);	

    void trigger() ;
    void setMode(PulseMode mode) ;
    PulseMode getMode() ;
    void halt() ;	// zatrzymaj generację 
    void setPulsePeriod(int ms) ;
    int getPulsePeriod() ;
    void setPulseCount(int burst) ;
}
