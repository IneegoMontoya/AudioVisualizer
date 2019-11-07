/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import java.util.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Byron Hammann - bjha43
 */
public class Bjha43PaintBySong implements Visualizer {
    
    
    private double magRange = 60; // Spectrum listener gives values on the interval of [-60.0,]
    private double phaseRange = (2*Math.PI); // phases are in the interval [-pi, pi] making its range 2 pi
    
    private double previousAmp;
    private double previousPhase;
    
    
    
    private final String name = "Bjha43 Visualizer: Paint by Song"; // 
    
    private double height;  // double to get the dimensions of the container 
    private double width;   // double to get the dimensions of the container
    
        
    private int bands = 0; // variable to hold the number of bands to be rendered
    
    private AnchorPane vizPane;     // Required container and name
    
    ArrayList<Rectangle> rectangles; // Using ArrayList as there will be a ludaris amount of elements
         
    
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end(); // Must make sure that the Pane is empty everytime the start method is called to avoid overlap
        
        /*   
            My thought for a visualizer was a take on some of the machines you see trying to duplicate a 
            Jackson Pollack or Impressionist painting.  I will be using the magnitudes and phases to determine the placesment of a colored element. 
            So since the pane is the "Canvas" I only need to get the dimensions and setup the array list.
            
            After meeting with Professor Wergeles I was told I could restrict the number of bands 
            if only because of the nature of my visualizer and the amount of rectangles I am placing.
            Not because it won't work, it works just fine, but changing visualizers takes time due to 
            having to remove millions of rectangle elements. 
        
        */
        if(numBands > 60)
        {
            bands = 60;
        }else bands = numBands;
        
        
        this.vizPane = vizPane;
                    
        rectangles = new ArrayList<>();
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
                    
        
    }

    @Override
    public void end() {
        if(rectangles != null){
            vizPane.getChildren().removeAll(rectangles);
      
        }
        rectangles = null;
       // If the song goes for too long, you will have to allow time for the computer to removed the millions of elements 
       // from the vizPane. 
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        
        double x, y;
        for(int index = 0; index < bands; index++){
            x = (width * magPercentage((double)magnitudes[index]))*1.15;
            y = (height * phasePercentage((double)phases[index]));
            
            Rectangle rectangle = new Rectangle();
            if((index%2) == 0)
            {
               
                if(x > width) //make sure the x coordinate stays within the boundries
            {
                x = width - 10;
            }
            if(y > height) // make sure the y coordinate stays within the boundries
            {
                y = height - 10;
            }
                rectangle.setX(x);
                rectangle.setY(y);
             }else 
            {
               
                if(y > width) //make sure the x coordinate stays within the boundries
            {
                y = width - 10;
            }
            if(x > height) // make sure the y coordinate stays within the boundries
            {
                x = height - 10;
            }
                rectangle.setX(y);
                rectangle.setY(x);
            }
            rectangle.setFill(null);
            rectangle.setStroke(Color.hsb(220.0-(magnitudes[index]* 6.0), phasePercentage((double)phases[index]), 1.0));
            rectangle.setWidth(15);
            rectangle.setHeight(10);
            rectangles.add(rectangle);
           
            vizPane.getChildren().add(rectangle);
            
            
            
        }
        
        
        
        
    }
    
    private double magPercentage(double mags){
        
        double x;
        x = (60.0 + mags)/magRange; 
        // by addind the negative values of the magnitude to 60.0 I get a positve value. 
        //By dividing by 60.0 I should get a percentage of the range.
        return x;
        
    }
    
    private double phasePercentage(double phase){
        
        
        double x;
        x = (Math.PI + phase)/(phaseRange);
        // Range of phases is 2 PI so to get percentage of that range must add PI
        // Then divide by 2*PI
        //
        return x;
        
    }
    
    
    
    
    
    
}
