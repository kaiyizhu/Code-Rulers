/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.entity.Castle;
import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import dev.CodeRulers.ruler.AbstractRuler;
import java.util.Random;

/**
 *
 * @author Kaiyi Zhu
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
        for (Castle castle : this.getCastles()) {
            int change = r.nextInt(1);
            if (change == 0) {
                castle.createKnights();
            } else if (change == 1) {
                castle.createPeasants();
            }
        }
        //Knight:
        /*
        Generate 0-2. 0 = Stay. 1 = Move. 2 = Capture.
        Generate a number 1-8. 0 = N 1 = NE and so forth...
         */
        for (Knight knight : this.getKnights()) {
            int randomKnightMove = r.nextInt(3);
            int knightDir = r.nextInt(8) + 1;
            if (randomKnightMove == 1) {
                this.move(knight, knightDir);
            } else if (randomKnightMove == 2) {
                this.capture(knight, knightDir);
            } else {
                //Does nothing so the knight stays in his original land
            }
        }

        //Peasant:
        /*
        Generate 0-1. 0 = Stay. 1 = Move
        Generate a number 1-8. 0 = N 1 = NE and so forth...
         */
        for (Peasant peasant : this.getPeasants()) {
            int randomPeasantMove = r.nextInt(2);
            if (randomPeasantMove == 1) {
                int peasantDir = r.nextInt(8) + 1;
                this.move(peasant, peasantDir);
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
