package boxevolution;

import boxevolution.Globals.ENV_SHAPE;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Environment {
    private final BufferedImage background;
    
    public Environment(ENV_SHAPE type, Color col1, Color col2){
        background = new BufferedImage(Globals.frameWidth, Globals.frameHeight, BufferedImage.TYPE_INT_ARGB);
        
        int rI = col1.getRed();
        int gI = col1.getGreen();
        int bI = col1.getBlue();
        int rF = col2.getRed();
        int gF = col2.getGreen();
        int bF = col2.getBlue();
        
        if(type == ENV_SHAPE.FADING_RECT){
            for(int row = 0; row < Globals.frameHeight; row++){
                /*System.out.println(
                    (int)(rI + (rF - rI) * (row * 1.0 / (1.0 * Globals.frameHeight + 1))) + " " +
                    (int)(gI + (gF - gI) * (row * 1.0 / (1.0 * Globals.frameHeight + 1))) + " " +
                    (int)(bI + (bF - bI) * (row * 1.0 / (1.0 * Globals.frameHeight + 1)))
                );*/
                Color particular = new Color(
                    (int)(rI + (rF - rI) * (row * 1.0 / (1.0 * Globals.frameHeight + 1))),
                    (int)(gI + (gF - gI) * (row * 1.0 / (1.0 * Globals.frameHeight + 1))),
                    (int)(bI + (bF - bI) * (row * 1.0 / (1.0 * Globals.frameHeight + 1)))
                );
                for(int col = 0; col < Globals.frameWidth; col++){
                    background.setRGB(col, row, particular.getRGB());
                }
            }
        }
        else if(type == ENV_SHAPE.FADING_CIRCLE){
            
            double sourceX = Globals.frameWidth / 2;
            double sourceY = Globals.frameHeight / 2;
            //double sourceX = 20;
            //double sourceY = 20;
            
            
            double xSign = (sourceX < Globals.frameWidth / 2) ? 1 : -1;
            double ySign = (sourceY < Globals.frameHeight / 2) ? 1 : -1;
            
            double maxDistance = Math.sqrt(
                Math.pow((Globals.frameWidth / 2 + (xSign * (Globals.frameWidth / 2))) - sourceX, 2) +
                Math.pow((Globals.frameHeight / 2 + (ySign * (Globals.frameHeight / 2))) - sourceY, 2)
            );
            for(int row = 0;row < Globals.frameHeight; row++){
                for(int col = 0;col < Globals.frameWidth; col++){
                    double distance = Math.sqrt(
                        Math.pow(sourceX - col, 2) + Math.pow(sourceY - row, 2)
                    );
                    double intensity = 1.0 - (distance / maxDistance);
                    
                    /*System.out.println(
                        intensity + " " + 
                        (int)(rI + (rF - rI) * intensity) + " " +
                        (int)(gI + (gF - gI) * intensity) + " " + 
                        (int)(bI + (bF - bI) * intensity)
                    );*/
                    
                    Color particular = new Color(
                        (int)(rI + (rF - rI) * intensity),
                        (int)(gI + (gF - gI) * intensity),
                        (int)(bI + (bF - bI) * intensity)
                    );
                    background.setRGB(col, row, particular.getRGB());
                }
            }     
        }
    }
    
    public BufferedImage getImg(){
        return background;
    }
}
