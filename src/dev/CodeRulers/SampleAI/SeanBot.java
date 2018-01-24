/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.entity.Castle;
import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import dev.CodeRulers.game.CodeRulers;
import dev.CodeRulers.ruler.AbstractRuler;
import dev.CodeRulers.world.World;
import java.awt.Color;
import java.util.Arrays;

/**
 * I am using this class for graphical testing. I also added a game-breaking bug
 *
 * @author Sean Zhang
 */
public class SeanBot extends AbstractRuler {

    @Override
    public void orderSubjects() {
        System.out.println("I just went");
        System.out.println(Arrays.toString(this.getCastles()));
        System.out.println(this.getCastles().length);
        System.out.println(Arrays.toString(this.getKnights()));
        System.out.println(this.getKnights().length);

        if (this.getCastles().length == 1 && this.getKnights().length < 20) {
            System.out.println("I have castles and less than 20 knights");

            //target castles
            int avgx = 0, avgy = 0;
            for (Knight k : this.getKnights()) {
                avgx += k.getX();
                avgy += k.getY();
            }

            avgx /= this.getKnights().length;
            avgy /= this.getKnights().length;

            int smallestDistance = 100000;

            Castle targetCastle = null;
            for (Castle c : this.getOtherCastles()) {
                if (c.getDistanceTo(avgx, avgy) <= smallestDistance) {
                    targetCastle = c;
                    smallestDistance = c.getDistanceTo(avgx, avgy);
                }
            }

            //===================
            
            //capture and move
            for (Knight knight : this.getKnights()) {
                for (int dirC = 1; dirC < 9; dirC++) {
                    capture(knight, dirC);
                }
                move(knight, knight.getDirectionTo(targetCastle.getX(), targetCastle.getY()));
            }

            for(Peasant peasant:this.getPeasants()) {
                move(peasant,peasant.getDirectionTo(targetCastle.getX(), targetCastle.getY()));
            }
        }

    }

    @Override
    public void initialize() {
        //Message telling the user that this object has been intialized.
        System.out.println("Ruler Sean Zhang been initialized");

        //this sets the profileURL of this AI. All you have to do is to provide
        //an internet link to the image.
        profileURL = ("https://avatars1.githubusercontent.com/u/20467017?s=460&v=4");

        //this is the preferred color for my AI. This color will be the main 
        //color scheme displayed in the GUI ` for this AI.
        setColor(new Color(139, 91, 183, 178));
    }

    @Override
    public String getSchoolName() {
        return "Newmarket High School";
    }

    @Override
    public String getRulerName() {
        return "Sean Zhang";
    }

}
