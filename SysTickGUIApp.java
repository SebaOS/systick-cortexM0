import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Component;
import java.util.Observer;
import java.util.Observable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SysTickGUIApp extends JFrame 
{
    private CortexM0SysTick sysTick;
    private Generator generator = new Generator();
    private Container frame;
    private FlashingLED blink;
    private Knob knob;
    
    JButton
    buttonWriteCVR = new JButton("Write CVR"),
    buttonWriteRVR = new JButton("Write RVR"),
    buttonGenerator= new JButton("Start Generator"),
    buttonImpuls = new JButton("Impuls"),
    buttonInterrupt = new JButton("Interrupt");
    
    JTextField 
    textFieldValueCVR = new JTextField("1", 8),
    textFieldValueRVR = new JTextField("1", 8),
    textFieldWriteCVR = new JTextField(8),
    textFieldWriteRVR = new JTextField(8),
    textFieldGenerator = new JTextField("1500 ms", 8),
    textFieldImpuls = new JTextField("1",8 );
    
    JRadioButton
    radioButtonCountFlag = new JRadioButton("Count Flag", false),  
    radioButtonTickIntFlag = new JRadioButton("Tick Int", false), 
    radioButtonEnableFlag = new JRadioButton("Enable Flag", false),
    radioButtonSendCont = new JRadioButton("Wysyłanie ciągle", false);
    
    JSlider sliderFreq = new JSlider(JSlider.HORIZONTAL, 0, 2400, 1200);
    
    Border line;
    
    TitledBorder 
    borderCVR, 
    borderRVR, 
    borderImpuls,
    borderGenerator;
    
    public static void main(String[] arg)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
           public void run() {
               new SysTickGUIApp();
           }
        });
    }
    
    public SysTickGUIApp()
    {
        sysTick = new CortexM0SysTick();
        setTitle("Systick Counter App");
        sysTick.setEnable(true);
        setSize(960,480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        generator = new Generator();
        blink = new FlashingLED();
        knob = new Knob();
        makeGUI();
        setVisible(true);
        
        generator.addActionListener(e->
        {
            sysTick.impuls();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {counterValue();}});      
        });
    }
    
    void makeGUI()
    {
        JPanel layout = new JPanel(new BorderLayout());
        
        JPanel up = new JPanel(new GridLayout(1, 3));
        up.setBorder(BorderFactory.createTitledBorder("SysTick Control & Status"));
        up.setBackground(Color.WHITE);
        up.add(radioButtonEnableFlag);
        up.add(radioButtonCountFlag);
        up.add(radioButtonTickIntFlag);
        
        JPanel down = new JPanel(new GridLayout(2, 2));

        JPanel fi = new JPanel(new GridLayout(3,2));
        fi.setBorder(BorderFactory.createTitledBorder("Register Values"));
        fi.setBackground(Color.WHITE);
        fi.add(textFieldValueCVR);
        textFieldValueCVR.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldValueCVR.setEnabled(false);
        fi.add(textFieldValueRVR);
        textFieldValueRVR.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldValueRVR.setEnabled(false);
        fi.add(textFieldWriteCVR);
        textFieldWriteCVR.setHorizontalAlignment(SwingConstants.CENTER);
        fi.add(textFieldWriteRVR);
        textFieldWriteRVR.setHorizontalAlignment(SwingConstants.CENTER);
        fi.add(buttonWriteCVR);
        fi.add(buttonWriteRVR);
        
        JPanel se = new JPanel(new GridLayout(3, 1));
        se.setBorder(BorderFactory.createTitledBorder("Impuls & Interrupt"));
        se.setBackground(Color.WHITE);
        se.add(textFieldImpuls);
        textFieldImpuls.setHorizontalAlignment(SwingConstants.CENTER);
        se.add(buttonImpuls);
        se.add(buttonInterrupt);
        
        JPanel th = new JPanel(new GridLayout(1,2));
        th.setBorder(BorderFactory.createTitledBorder("Component"));
        th.setBackground(Color.WHITE);
        th.add(blink);
        th.add(knob);
        
        JPanel fo = new JPanel(new GridLayout(3, 1));
        fo.setBorder(BorderFactory.createTitledBorder("Generator"));
        fo.setBackground(Color.WHITE);
        fo.add(textFieldGenerator);
        textFieldGenerator.setHorizontalAlignment(SwingConstants.CENTER);
        fo.add(sliderFreq);
        fo.add(buttonGenerator);
        
        down.add(fi);
        down.add(se);
        down.add(th);
        down.add(fo);
        
        layout.add(up, BorderLayout.PAGE_START);
        layout.add(down, BorderLayout.CENTER);        

        
        buttonWriteRVR.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                int rvr = Integer.parseInt(textFieldWriteRVR.getText());
                sysTick.setRVR(rvr);
                counterValue();
            }
        });
        
        buttonWriteCVR.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                int cvr = Integer.parseInt(textFieldWriteCVR.getText());
                sysTick.setRVR(cvr);
                counterValue();
            }
        }); 
        
        buttonGenerator.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
               if(radioButtonEnableFlag.isSelected()) {
                    generator.trigger();
                }
                else {
                    generator.halt();
                }
            }
        });
        
        buttonImpuls.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                int num = Integer.parseInt(textFieldImpuls.getText().trim()); 
                if (num >= 0 && radioButtonEnableFlag.isSelected()){
                    for (int i = 0; i < num; i++){
                            sysTick.impuls();}
                    counterValue();
                }
                else{
                    counterValue();
                }
            }
        });
     
        buttonInterrupt.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                    generator.halt();
            }
        });
        
        radioButtonEnableFlag.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                sysTick.setEnable(radioButtonEnableFlag.isSelected());
                counterValue();
            }
        });
        
        radioButtonTickIntFlag.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                sysTick.setTickInt(radioButtonTickIntFlag.isSelected());
                counterValue();
            }});
        
        sliderFreq.setMajorTickSpacing(500);
        sliderFreq.setPaintLabels(true);
        sliderFreq.setPaintTicks(true);
        sliderFreq.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
            int currentFreq = sliderFreq.getValue();
            textFieldGenerator.setText("" + currentFreq + " ms");
            generator.setFreq(currentFreq + 1);
            knob.setAngle(currentFreq);
            }
        });
        
        knob.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                sliderFreq.setValue(knob.getAngle());
            }
        });
        
        Container frame = getContentPane();
        frame.add(layout);
        counterValue();
        validate();
}

    private void counterValue() {
        int currCVR = sysTick.getCVR();
        int currRVR = sysTick.getRVR();
        textFieldValueCVR.setText("" + currCVR);
        textFieldValueRVR.setText("" + currRVR);
        radioButtonCountFlag.setSelected(sysTick.getCountFlag());
        radioButtonTickIntFlag.setSelected(sysTick.getTickInt());
        if ((sysTick.getCountFlag() == true && sysTick.getCVR() == 0)
                && (sysTick.getTickInt() == true)) {
            JOptionPane.showMessageDialog(frame, "Interrupt!","Information", 
            JOptionPane.INFORMATION_MESSAGE);  
        }
        if (sysTick.getCountFlag() == true && sysTick.getCVR() == 0)
        {
            update(); 
        }
    }
    
    public void update() {
            blink.blink();
    }
}
    