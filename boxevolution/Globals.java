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
        FADING_CIRCLE,
        FADING_RECT
    }
    
    /*
        This method is currently in use for the program; 20 new box organisms have
        offspring at random, and then the list of boxes is sorted by each box's
        getBlendPercentage() value; the first 20 with the lowest blend percentages
        are killed off.
    
        This entire process occurs once per method call.
    */
    
    public static void advanceGeneration(){
        
        int growth = 20;
            
        Collections.shuffle(boxes, new Random());

        int index = 0;
        while(growth > 0){
            boxes.add(boxes.get(index).offspring(100));
            index++;
            if(index >= boxes.size()){
                index = 0;
            }
            growth--;
        }
            
        Collections.sort(
            boxes,
            new Comparator<Box>(){
                @Override
                public int compare(Box o1, Box o2) {
                    if(o1.getBlendPercentage() < o2.getBlendPercentage()){
                        return -1;
                    }
                    else if(o1.getBlendPercentage() > o2.getBlendPercentage()){
                        return 1;
                    }
                    return 0;
                }
            }
        );
            
        growth = 20;

        index = 0;
        while(growth > 0){
            boxes.remove(index);
            index++;
            if(index >= boxes.size()){
                index = 0;
            }
            growth--;
        }
        
    }
    
    /*
        This method attempts to approximate a logistical growth model
        for the total box population.  Not currently in use; unstable and uninteresting
        after a couple dozen iterations.
    */
    public static void advanceGeneration2(){
        for(int i = 0;i < boxes.size(); i++){
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
                double survivalRate = Math.pow(b.getBlendPercentage(), 0.75);
                if(survivalRate < f){
                    Globals.boxes.remove(boxes.indexOf(b));
                }
            }
        }
        
        double P = boxes.size();
        double deltaT = (10.0 / 1000.0);
        double k = 0.2;
        double M = 150.0;
        
        deltaP += deltaT * k * P * (1 - (P/M));
        
        if(Math.round(deltaP) != 0.0){
            
            int dP = (int)Math.round(deltaP);

            Collections.shuffle(boxes, new Random());

            if(dP > 0){
                int birthRate = 2 * dP;
                int index = 0;
                while(birthRate > 0){
                    boxes.add(boxes.get(index).offspring(100));
                    index++;
                    if(index >= boxes.size()){
                        index = 0;
                    }
                    birthRate--;
                }
            }

            Collections.sort(
                boxes,
                new Comparator<Box>(){
                    @Override
                    public int compare(Box o1, Box o2) {
                        if(o1.getBlendPercentage() < o2.getBlendPercentage()){
                            return -1;
                        }
                        else if(o1.getBlendPercentage() > o2.getBlendPercentage()){
                            return 1;
                        }
                        return 0;
                    }
                }
            );

            int deathRate = (dP > 0) ? -dP : dP;

            int index = 0;
            while(deathRate > 0){
                boxes.remove(index);
                index++;
                if(index >= boxes.size()){
                    index = 0;
                }
                deathRate--;
            }
            
        }
    }
}
