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
    
    public RandomBot(int rulerID) {
        super(rulerID);
    }
    @Override
    public void orderSubjects() {
        Random r = new Random();
        //Knight:

        for (int i = 0; i < this.getKnights().length; i ++){
            int randomKnightMove = r.nextInt(2);
            int knightDir = r.nextInt(7) + 1;
            if (randomKnightMove == 1){           
            this.move(this.getKnights()[i], knightDir);
            }
            else if (randomKnightMove == 2){
                this.capture(this.getKnights()[i], knightDir);
            }
            else{
                //do nothing
            }
        }
        /*
                Generate 0-2. 0 = Stay. 1 = Move. 2 = Capture.
                Generate a number 1-8. 0 = N 1 = NE and so forth...
        */
        //Peasant:
            for (int i = 0; i < this.getPeasants().length; i ++){
            int randomPeasantMove = r.nextInt(1);
            if(randomPeasantMove == 1){
            int peasantDir = r.nextInt(7) + 1;
            this.move(this.getPeasants()[i], peasantDir);
            }
            else{
                //Does nothing so peasant stays
            }
        }
            
        /*
                Generate 0-1. 0 = Stay. 1 = Move
                Generate a number 1-8. 0 = N 1 = NE and so forth... 
        */
    }
    @Override
    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
