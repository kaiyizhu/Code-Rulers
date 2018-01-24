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

    Random r = new Random();

    @Override
    public void initialize()
    {
        profileURL = ("http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg");
        setColor(new Color(147, 112, 219));
        setColor(Color.BLUE);
    }

    @Override
    public void orderSubjects()
    {
        //Move peasants in random order
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

        //Rush knights to aid my starting castle
        for (Knight knight : this.getKnights())
        {

            for (int x = -1; x < 1; x++)
            {
                for (int y = 1; y > -1; y--)
                {
                    if(getCastles().length >= 1){
                    this.move(knight, knight.getDirectionTo(this.getCastles()[getCastles().length-1].getX() + x, this.getCastles()[getCastles().length-1].getY() + y));
                    }
                    else{
                        //RIP
                    }
                }
            }

        }

        /*
            if (knight.getDistanceTo(getOtherCastles()[getOtherCastles().length - 1].getX(), getOtherCastles()[getOtherCastles().length - 1].getY()) == 1)
            {
                capture(knight, knight.getDirectionTo(getOtherKnights()[getOtherKnights().length-1].getX(), getOtherKnights()[getOtherKnights().length-1].getY()));
            } 
            /*
            else
            {
                knight.capture(getOtherKnights()[getOtherKnights().length - 1]);
            }
         */
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
