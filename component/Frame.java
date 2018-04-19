package component;

import boxevolution.Globals;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Frame extends JFrame {
    
    public void initializeFrame(){
        setSize(Globals.frameWidth + 3, Globals.frameHeight + 25);
        setMinimumSize(new Dimension(Globals.frameWidth + 3, Globals.frameHeight + 25));
        setTitle("ColorBox Evolution Simulator V 1.0");
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Globals.gComponent.timer.start();
        getContentPane().add(Globals.gComponent);
        //addMouseListener(display);
        //addMouseMotionListener(display);
        pack();
        setVisible(true);
    }
    
}
