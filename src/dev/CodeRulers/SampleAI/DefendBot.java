/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.ruler.*;
import dev.CodeRulers.entity.Castle;
import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import dev.CodeRulers.game.CodeRulers;

import dev.CodeRulers.world.World;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Kaiyi Zhu
 */
public class DefendBot extends AbstractRuler
{

    //the ruler ID which is targeted by this AI
    int target;
    //the castle owned by the person the AI will be attacking
    Castle attacking;
    //represents the location of this castle in relation to the others, 
    //1 is northeast, 4 northwest
    int corner;
    //The direction in which peasants claim land and knights attack
    int dir;
    //the current number of castles under this ruler
    int numCastles = 1;
    //random number generator for choosing which peasants to kill
    Random r = new Random();
    //whether the capture phase is complete
    boolean captured = false;

    @Override
    public void initialize()
    {
        profileURL = ("http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg");
        setColor(new Color(147, 112, 219));
    }

    @Override
    public void orderSubjects()
    {
        for (Peasant peasant : this.getPeasants())
        {
            int randomPeasantMove = r.nextInt(2);
            if (randomPeasantMove == 1)
            {
                int peasantDir = r.nextInt(8) + 1;
                this.move(peasant, peasantDir);
            } else
            {
                //Does nothing so the peasant stays in his original land
            }
        }
        for (Castle castle : this.getCastles())
        {

            castle.createKnights();
        }
        int count = 0;
        for (Knight knight : this.getKnights())
        {
            this.move(knight, knight.getDirectionTo(this.getCastles()[0].getX(), this.getCastles()[0].getY()));
            if (knight.getDistanceTo(getOtherPeasants()[getOtherPeasants().length - 1].getX(), getOtherPeasants()[getOtherPeasants().length - 1].getY()) == 1)
            {
                //capture(knight, getOtherPeasants()[getOtherPeasants().length - 1].getX(), getOtherPeasants()[getOtherPeasants().length - 1].getY());
            }
            else
            {
                knight.capture(getOtherKnights()[getOtherKnights().length - 1]);
            }
        }
    }

    @Override
    public String getSchoolName()
    {
        return "FlexQueue Dev Team";
    }

    @Override
    public String getRulerName()
    {
        return "Protec";
    }

}
