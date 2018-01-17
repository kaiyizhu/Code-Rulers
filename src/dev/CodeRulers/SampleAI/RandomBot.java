/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.sampleAI;
import dev.CodeRulers.ruler.AbstractRuler;
import java.util.Random;
/**
 *
 * @author 335550372
 */
public class RandomBot extends AbstractRuler{
    Random r = new Random();
    
    public RandomBot(int rulerID) {
        super(rulerID);
    }
    @Override
    public void orderSubjects() {
        
        /*
            //Knight:
                Generate 0-2. 0 = Stay. 1 = Move. 2 = Capture.
                Generate a number 0-7. 0 = N 1 = NE and so forth...
            //Peasant:
                Generate 0-1. 0 = Stay. 1 = Move
                Generate a number 0-7. 0 = N 1 = NE and so forth... 
        */
    }
    @Override
    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
