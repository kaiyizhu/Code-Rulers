/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.ruler.AbstractRuler;
import java.util.Random;

/**
 *
 * @author 335550372
 */
public class RandomBot extends AbstractRuler {

    public RandomBot(){
        super();
    }

    @Override
    public void orderSubjects() {
        Random r = new Random();
        //Castles: 
        /*
        Change the creation of knights or peassants. Knight = 0; Peasant = 1;
        */
        for (int i = 0; i < this.getCastles().length; i++) {
            int change = r.nextInt(1);
            if (change == 0) {
                this.getCastles()[i].createKnights();
            } else if (change == 1) {
                this.getCastles()[i].createPeasants();
            }
        }
        //Knight:
        /*
        Generate 0-2. 0 = Stay. 1 = Move. 2 = Capture.
        Generate a number 1-8. 0 = N 1 = NE and so forth...
        */
        for (int i = 0; i < this.getKnights().length; i++) {
            int randomKnightMove = r.nextInt(3);
            int knightDir = r.nextInt(8) + 1;
            if (randomKnightMove == 1) {
                this.move(this.getKnights()[i], knightDir);
            } else if (randomKnightMove == 2) {
                this.capture(this.getKnights()[i], knightDir);
            } else {
                //Does nothing so the knight stays in his original land
            }
        }

        //Peasant:
          /*
         Generate 0-1. 0 = Stay. 1 = Move
         Generate a number 1-8. 0 = N 1 = NE and so forth... 
         */
        for (int i = 0; i < this.getPeasants().length; i++) {
            int randomPeasantMove = r.nextInt(2);
            if (randomPeasantMove == 1) {
                int peasantDir = r.nextInt(8) + 1;
                this.move(this.getPeasants()[i], peasantDir);
            } else {
                //Does nothing so the peasant stays in his original land
            }
        }
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public String getSchoolName() {
        return "Newmarket High School";
    }

    @Override
    public String getRulerName() {
        return "Random Bot";
    }

}
