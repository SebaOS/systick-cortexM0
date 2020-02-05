import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CortexM0SysTickTest
{
    /**
     * Default constructor for test class CortexM0SysTickTest
     */
    public CortexM0SysTickTest()
    {
    }


    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void setCVRTest()
    {
        CortexM0SysTick valCVR = new CortexM0SysTick();
        valCVR.setCVR(3);
        valCVR.impuls();
        valCVR.impuls();
        valCVR.impuls();
        assertEquals(0, valCVR.getCVR());
    }

    @Test
    public void setRVRTest()
    {
        CortexM0SysTick valRVR = new CortexM0SysTick();
        valRVR.setRVR(3);
        valRVR.impuls();
        valRVR.impuls();
        assertEquals(3, valRVR.getRVR());
    }
    
    @Test
    public void reloadTest()
    {
        CortexM0SysTick cortexM01 = new CortexM0SysTick();
        cortexM01.setRVR(12);
        cortexM01.setCVR(123);
        cortexM01.setEnable(true);
        for (int i = 0; i < 18; i++) cortexM01.impuls();
        assertEquals(8, cortexM01.getCVR());
        assertEquals(true, cortexM01.getCountFlag());
    }

    @Test
    public void enableFlagTest()
    {
        CortexM0SysTick enable = new CortexM0SysTick();
        enable.setRVR(3);
        enable.impuls();
        enable.impuls();
        assertEquals(3, enable.getCVR());
        assertEquals(false, enable.getEnable());
    }
    

    @Test
    public void countFlagTest()
    {
        CortexM0SysTick countflag = new CortexM0SysTick();
        countflag.setRVR(3);
        countflag.setEnable(true);
        countflag.impuls();
        countflag.impuls();
        countflag.impuls();
        assertEquals(0, countflag.getCVR());
        assertEquals(true, countflag.getCountFlag());
    }
}













