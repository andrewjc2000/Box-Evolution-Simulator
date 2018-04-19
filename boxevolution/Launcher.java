package boxevolution;

import component.Component;
import component.Frame;
import java.awt.Color;
import java.util.ArrayList;

public class Launcher {
    
    private static Thread startupThread;
    
    public static void main(String[] args) {
        Globals.frameWidth = 1000;
        Globals.frameHeight = 650;
        Globals.counter = 0;
        Globals.mainFrame = new Frame();
        Globals.gComponent = new Component();
        Globals.boxes = new ArrayList();
        Globals.background = new Environment(Globals.ENV_SHAPE.FADING_RECT, Color.green, Color.blue);
        
        for(int i = 0;i < Globals.frameWidth - 100; i+= 105){
            for(int j = 0;j < Globals.frameHeight - 100; j+=105){
                Globals.boxes.add(
                    new Box(i, j, 4, 100)
                );
            }
        }
        
        startupThread = new Thread(){
            @Override
            public void run(){
                Globals.mainFrame.initializeFrame();
            }
        };
        
        startupThread.start();
        
        //System.out.println(Globals.boxes.get(0).blendPercentage(Globals.background));
        
    }
    
}
