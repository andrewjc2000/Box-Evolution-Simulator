package boxevolution;

import component.Component;
import component.Frame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Globals {
    public static int frameWidth, frameHeight, counter;
    private static double deltaP = 0.0;
    public static Frame mainFrame;
    public static Component gComponent;
    public static Environment background;
    public static ArrayList<Box> boxes;
    
    public static enum ENV_SHAPE {
        FADING_CIRLCE,
        FADING_RECT
    }
    
    public static void advanceGeneration(){
        
        /*for(int i = 0;i < boxes.size(); i++){
            Box b = boxes.get(0);
            b.generationNum++;
            Random rand = new Random();
            float f = rand.nextFloat();
            if(b.generationNum == 10){
                Globals.boxes.remove(b);
            }
            else if(b.generationNum % 3 == 0){
                if(f < .75){
                    Globals.boxes.add(b.offspring(100));
                }
            }
            else{                
                double survivalRate = Math.pow(b.blendPercentage(background), 0.75);
                if(survivalRate < f){
                    Globals.boxes.remove(boxes.indexOf(b));
                }
            }
        }*/
        
        /*double P = boxes.size();
        double deltaT = (10.0 / 1000.0);
        double k = 0.2;
        double M = 150.0;
        
        deltaP += deltaT * k * P * (1 - (P/M));
        */
        //if(Math.round(deltaP) != 0.0){
        int growth = 20;//(int)Math.round(deltaP);
            
        Collections.shuffle(boxes, new Random());
            
        //if(dP > 0){
        //int birthRate = 2 * dP;

        int index = 0;
        while(growth > 0){
            boxes.add(boxes.get(index).offspring(100));
            index++;
            if(index >= boxes.size()){
                index = 0;
            }
            growth--;
        }

        //}
            
        Collections.sort(
            boxes,
            new Comparator<Box>(){
                @Override
                public int compare(Box o1, Box o2) {
                    if(o1.blendPercentage(background) < o2.blendPercentage(background)){
                        return -1;
                    }
                    else if(o1.blendPercentage(background) > o2.blendPercentage(background)){
                        return 1;
                    }
                    return 0;
                }
            }
        );
            
        growth = 20;//deathRate = (dP > 0) ? -dP : dP;

        index = 0;
        while(growth > 0){
            boxes.remove(index);
            index++;
            if(index >= boxes.size()){
                index = 0;
            }
            growth--;
        }
            
        //}
        
    }
}
