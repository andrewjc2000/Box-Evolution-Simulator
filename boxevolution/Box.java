package boxevolution;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Box {
    
    private final ArrayList<Color> cols;//contains colors
    private final int n, length;//number of colors (aka cols.size())
    private final String DNA;//string with DNA code; compressed info of colors
    public int x, y, generationNum;
    private double blendPercentage;
    
    public Box(int x, int y, int numColors, int length){
        cols = new ArrayList();
        String DNA = "";
        n = numColors;
        generationNum = 0;
        this.length = length;
        this.x = x;
        this.y = y;
        for(int i = 0;i < numColors; i++){
            int red  = new Random().nextInt(255);
            int green = new Random().nextInt(255);
            int blue = new Random().nextInt(255);
            cols.add(new Color(red, green, blue));
            DNA += (char)red; 
            DNA += (char)green;
            DNA += (char)blue;
        }
        
        this.DNA = DNA;
        
        blendPercentage = blendPercentage(Globals.background);
    }
    
    public Box(int x, int y, String DNA, int length){
        this.DNA = DNA;
        cols = new ArrayList();
        n = DNA.length() / 3;
        generationNum = 0;
        this.length = length;
        this.x = x;
        this.y = y;
        //System.out.println(DNA.length());
        for(int i = 0;i < DNA.length() - 2; i += 3){
            //System.out.println((int)DNA.charAt(i+2));
            cols.add(
                new Color(
                    (int)DNA.charAt(i),
                    (int)DNA.charAt(i+1),
                    (int)DNA.charAt(i+2)
                )
            );
        }
        
        blendPercentage = blendPercentage(Globals.background);
    }
    
    private String getMutatedDNA(){
        String mutantDNA = "";
        for(int i = 0;i < DNA.length(); i++){
            char rgb = DNA.charAt(i);
            //for each character of the DNA, add random int between -25 and 25 to ASCII value
            // if greater than 255, % 255 the whole number <-- EXTREME mutation; mostly harmful
                                                                //sometimes helpful
            
            int newInt = -25 + new Random().nextInt(50) + (int)rgb;
            if(newInt < 0){
                newInt += 255;
            }
            else if(newInt > 255){
                newInt = newInt % 255;
            }
            mutantDNA += (char)newInt;
        }
        return mutantDNA;
    }
    
    public void draw(Graphics g){
        //colors are just going to be stripes for now, maybe improve on this later :P
        //also, if the length < cols.size(), the end colors just get cut off
        int stripeWidth = (int)Math.ceil(length / n);
        
        for(int i = 0;i < length; i += stripeWidth){
            g.setColor(cols.get(i / stripeWidth));
            g.fillRect(x + i, y, stripeWidth, length);
        }
        
    }
    
    
    public double getBlendPercentage(){
        return blendPercentage;
    }
    
    //public double getBlendPercentage(Environment e){
    //    return blendPercentage(e);
    //}
    
    private double blendPercentage(Environment e){
        BufferedImage img = e.getImg();
        
        double maxDifference = 255 * length * length;
        
        double redOffset = 0;
        double greenOffset = 0;
        double blueOffset = 0;
        
        int stripeWidth = (int)Math.ceil(length / n);
        
        for(int i = 0;i < length; i += stripeWidth){
            for(int j = i;j < i + stripeWidth; j++){
                for(int k = y;k <= y + length; k++){
                    int xCoord = x + i;
                    int yCoord = k;
                    Color col1 = cols.get(i / stripeWidth);
                    Color col2 = new Color(e.getImg().getRGB(xCoord, yCoord));
                    redOffset += Math.abs(col1.getRed() - col2.getRed());
                    greenOffset += Math.abs(col1.getGreen() - col2.getGreen());
                    blueOffset += Math.abs(col1.getBlue() - col2.getBlue());
                }
            }
        }
        
        return 1 - (((redOffset / maxDifference) + 
                (greenOffset / maxDifference) + 
                (blueOffset / maxDifference)
        ) / 3.0);
    }
    
    public Box offspring(int length){
        String newDNA = getMutatedDNA();
        //System.out.println(newDNA.length());
        return new Box(x, y, newDNA, length);
    }
    
}
