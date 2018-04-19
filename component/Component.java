package component;

import boxevolution.Box;
import boxevolution.Globals;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

public class Component extends JComponent implements ActionListener {
    
    public final Timer timer;
    
    public Component(){
        timer = new Timer(10, this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(Globals.counter == 5){
            Globals.counter = 0;
            Globals.advanceGeneration();
            System.out.println(Globals.boxes.size());
        }
        else{
            //System.out.println(Globals.counter++);
            Globals.counter++;
            repaint();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Globals.background.getImg(), 0, 0, this);
        for(Box b: Globals.boxes){
            b.draw(g);
        }
    }
    
}
