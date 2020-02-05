import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FlashingLED extends JComponent
{
    private boolean state;
    int blinkMs;
    Color onColor, offColor;
    Timer blinkTimer;

    public FlashingLED() 
    {
        this(Color.GREEN, Color.GREEN.darker(), 500);
    }
    
    public FlashingLED(Color on, Color off, int blink) {
        onColor = on;
        offColor = off;
        blinkMs = blink;
        blinkTimer = new Timer(blinkMs, blinkFinisher);
        blinkTimer.setRepeats(false);
    }

    public void setState(boolean state) {
        this.state = state;
        repaint();
    }
    
    public void setBlinkTime(int blink) {
        this.blinkMs = blink;
        repaint();
    }
    
    public void blink() {
        setState(true);
        blinkTimer.start();
        repaint();
    }
    
    ActionListener blinkFinisher = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            state = false;
            repaint();
        }
    };
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100,100);
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50,50);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Dimension d = getSize();
        int r, w, h;
        w = d.width;
        h = d.height;
        r = Math.min(w - 100, h - 100);
        g2d.setColor(state?onColor:offColor);
        g2d.fillOval((w - r) / 2,(h - r) / 2, r - 1, r - 1);
    }
    
    public static void main(String[] a) {
        JFrame f = new JFrame("Test diody led");
        f.setLayout(new FlowLayout());
        FlashingLED f1 = new FlashingLED();
        f1.setBlinkTime(3000);
        JRadioButton setButton = new JRadioButton("OnOFF");
        f.add(setButton);
        setButton.addActionListener (e ->{
            f1.setState(setButton.isSelected());
        });
        JButton blinkButton = new JButton ("blink");
        f.add(blinkButton);
        blinkButton.addActionListener (e ->{
            f1.blink();
        });
        f.add(f1);
        f.setSize(200, 200);
        f.setVisible(true);
    }
}
