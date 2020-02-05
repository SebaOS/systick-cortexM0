import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics2D; 

public class Knob extends JComponent implements MouseListener {
    int angle;
    int newAngle;
    int[] tab = new int[24];
    boolean pressed = false;
    boolean entered = false;
    ActionListener actionListener;
  
    public Knob() 
    { 
       this(0);
    }
     
    public Knob(int angle) { 
       this.angle = this.newAngle = (int)(1500 / 6.66667);
       addMouseListener(this);
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 50);
    }
    
    @Override  
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Dimension d = getSize();
        int r, r1, r2, w, h, ang;
        h = d.height;
        w = d.width;
        r = Math.min(h - 100, w - 100);
        r1 = Math.min(h - 120, w - 120);
        r2 = Math.min(h - 150, w - 150);
    
        // Linie pomocnicze
        for (ang = 0; ang <= 360; ang += 10) {
            int a = 90 - ang;
            g2d.drawLine(w / 2, h / 2,
            w / 2 + (int) ((r + 8) / 2 * Math.cos(a * Math.PI / 180)),
            h / 2 - (int) ((r + 8) / 2 * Math.sin(a * Math.PI / 180)));
         }
        
        // Linie główne
        for (ang = 0; ang < 360; ang += 30) {
            int a = 90 - ang;
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(w / 2, h / 2,
            w / 2 + (int) ((r + 16) / 2 * Math.cos(a * Math.PI / 180)),
            h / 2 - (int) ((r + 16) / 2 * Math.sin(a * Math.PI / 180)));
         }
        
        // Wypełnienie
        g2d.setColor(Color.white);
        g2d.fillOval((w - r) / 2, (h - r) / 2, r, r);
        
        g2d.setColor(new Color(220, 220, 220));
        g2d.fillOval((w - r1) / 2, (h - r1) / 2, r1, r1);
        
        g2d.setColor(new Color(130, 130, 130));
        g2d.fillOval((w - r2) / 2, (h - r2) / 2, r2, r2);
        
        // Linia wskazująca
        g2d.setColor(new Color(190, 0, 0));
        g2d.setStroke(new BasicStroke(4));
        int alfa = 90 - angle;
        g2d.drawLine(
        w / 2 + (int) ((r / 2 * Math.cos(alfa * Math.PI / 180)) / 2), 
        h / 2 - (int) ((r / 2 * Math.sin(alfa * Math.PI / 180)) / 2),
        w / 2 + (int) (r / 2 * Math.cos(alfa * Math.PI / 180)),
        h / 2 - (int) (r / 2 * Math.sin(alfa * Math.PI / 180)));
        
        // Wartości 
        setFont(new Font("Arial", Font.PLAIN, 10)); 

        for (int i = 0; i < 24; i += 2) {
            int a = 90 - (i * 15);
            int xx = w / 2 + (int) ((r + 16) / 2 * Math.cos(a * Math.PI / 180));
            int yy = h / 2 - (int) ((r + 16) / 2 * Math.sin(a * Math.PI / 180));
            tab[i] = xx;
            int tmp = i + 1;
            tab[tmp] = yy;
         }

         tab[0] -= 2;
         tab[1] += -3;
         tab[2] += 2;
         tab[3] += -2;
         tab[4] += 3;
         tab[5] += 3;
         tab[6] += 3;
         tab[7] += 3;
         tab[8] += 3;
         tab[9] += 8;
         tab[10] += 1;
         tab[11] += 10;
         tab[12] += -15;
         tab[13] += 12;
         tab[14] += -30;
         tab[15] += 10;
         tab[16] += -30;
         tab[17] += 10;
         tab[18] += -32;
         tab[19] += 5;
         tab[20] += -32;
         tab[21] += -1;
         tab[22] += -30;
         tab[23] += -1;

        for (int i = 0; i < 24; i += 2) {
            int tmp = i + 1;
            g.drawString(new Integer(i * 100).toString(), tab[i], tab[tmp]);
        }
    }
     
    public int getAngle() {
        newAngle = (int)(newAngle * 6.66667);
        return newAngle;
    }
    
    public void setAngle(int value) {
        angle = (int)(value / 6.66667);
        repaint();
    }
    
    public void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
    }
    
    public void removeActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }
      
    public void mouseClicked(MouseEvent me) { }   
    
    public void mouseEntered(MouseEvent me) { }   
      
    public void mouseExited(MouseEvent me) {
        entered = false; 
        pressed = false;
        repaint();
    } 
     
    public void mousePressed(MouseEvent me) {
        pressed = true;
        repaint(); 
    }   
     
    public void mouseReleased(MouseEvent me) {
        int x, y;
        if (pressed == true) {
            pressed = false;
            x = me.getX(); y = me.getY();
            double yy = (getSize().height / 2 - y);
            double xx = (x - getSize().width / 2) ;
                
            newAngle = 90 - (int) (180 / Math.PI * Math.atan(yy / xx) );
            if (xx < 0) newAngle += 180;
            angle =  newAngle ;
            repaint();
            if (actionListener != null)
             actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "delay"));
            }   
     }

} 

