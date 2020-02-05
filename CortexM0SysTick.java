import java.util.Observable;

public class CortexM0SysTick extends Observable
{
    private int valueRVR;
    private int valueCVR;
    private boolean countFlag;
    private boolean tickInt;
    private boolean enable;
    
    public CortexM0SysTick()
    {
        this(4, 10, false);
    }
    
    public CortexM0SysTick(int regCVR, int regRVR, boolean enableFlag) {
        this.valueCVR = regCVR;
        this.valueRVR = regRVR;
        this.enable = enableFlag;
    }
    
    public void setEnable(boolean function) {
        enable = function;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setTickInt(boolean function) {
        tickInt = function;
    }

    public boolean getTickInt() {
        return tickInt;
    }

    public void setCountFlag(boolean function) {
        countFlag = function;
    }

    public boolean getCountFlag() {
        return countFlag;
    }

    public void setRVR(int wartosc) {
        if (wartosc >= 0 && wartosc < 16777215) {
            valueRVR = wartosc;
        }
    }

    public int getRVR() {
        return valueRVR;
    }
 
    public void setCVR(int wartosc) {
        countFlag = false;
        this.valueCVR = 0;
    }
 
    public int getCVR() {
        return valueCVR;
    }
 
    public void impuls() {
        if (this.tickInt == true) {
            setChanged();
            notifyObservers();
        }
        
        if (enable == true) {
            if (valueCVR == 0) {
                this.valueCVR = valueRVR;
                setChanged();
                notifyObservers();
            }
            else {
                this.valueCVR--;
                if (this.valueCVR == 0) {
                this.countFlag = true;
                setChanged();
                notifyObservers();
                }
            }
         }
    }

    public String toString() {
        return "CortexM0SysTick RVR  = " + valueRVR + "\n" + "CVR = " + valueCVR + "\n" + "tickInt =  " + tickInt + "\n" + "countFlag = " + countFlag + "\n" + "enable = " + enable;
    }
}
