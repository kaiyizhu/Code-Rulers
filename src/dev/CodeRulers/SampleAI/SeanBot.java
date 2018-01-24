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
import java.util.Random;

/**
 * I am using this class for graphical testing. I also added a game-breaking bug
 *
 * @author Sean Zhang
 */
public class SeanBot extends AbstractRuler {

    @Override
    public void orderSubjects() {
        Random r = new Random();

        for (Castle castle : this.getOtherCastles()) {
            if (CodeRulers.getTurnCount() > 500) {
                castle.createKnights();
            } else {
                castle.createPeasants();
            }

        }

        for (int k=0;k<this.getKnights().length && k<20;k++) {

            int[] distances = new int[World.getAllCastles().length];
            for (int i = 0; i < World.getAllCastles().length; i++) {
                distances[i] = this.getKnights()[k].getDistanceTo(World.getAllCastles()[i].getX(), World.getAllCastles()[i].getY());
            }
            for (int dirC = 1; dirC < 9; dirC++) {
                capture(this.getKnights()[k], dirC);
            }
            
            int smallestCastle=0;
            int smallestD=9999999;
            
            for(int i=0;i<distances.length;i++) {
                if(smallestD>distances[i]) {
                    smallestD = distances[i];
                    smallestCastle=i;
                }
            }
            move(this.getKnights()[k], this.getKnights()[k].getDirectionTo(World.getAllCastles()[smallestCastle].getX(), World.getAllCastles()[smallestCastle].getY()));
        }
        
        //===================
        //capture and move
        for (Knight knight : this.getKnights()) {
            //peasant.move(findDir(peasant.getClosestUnownedTile(peasant)[0], peasant.getClosestUnownedTile(peasant)[1]));

            //Search for uncaptured tile around the peasant
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    //Stop searching if the Land Tile is outside of the bounds
                    if (!(knight.getX() + x <= 63 && knight.getX() + x >= 0 && knight.getY() + y >= 0 && knight.getY() + y <= 63)) {
                        break;
                    }

                    //If the land owner is not ours, then move onto it
                    if (World.getLandOwner(knight.getX() + x, knight.getY() + y) != rulerID) {
                        this.move(knight, findDir(x, y));
                    }
                }
            }

            //If the peasant can still move, then move towards the bottom right
            if (knight.hasAction()) {
                this.move(knight, (int) (Math.random() * 8));
            }
        }

        //===================
        //capture and move
        for (Peasant peasant : this.getPeasants()) {
            //peasant.move(findDir(peasant.getClosestUnownedTile(peasant)[0], peasant.getClosestUnownedTile(peasant)[1]));

            //Search for uncaptured tile around the peasant
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    //Stop searching if the Land Tile is outside of the bounds
                    if (!(peasant.getX() + x <= 63 && peasant.getX() + x >= 0 && peasant.getY() + y >= 0 && peasant.getY() + y <= 63)) {
                        break;
                    }

                    //If the land owner is not ours, then move onto it
                    if (World.getLandOwner(peasant.getX() + x, peasant.getY() + y) != rulerID) {
                        this.move(peasant, findDir(x, y));
                    }
                }
            }

            //If the peasant can still move, then move towards the bottom right
            if (peasant.hasAction()) {
                this.move(peasant, (int) (Math.random() * 8));
            }
        }
    }

    private int findDir(int x, int y) {
        //Calculates the direction of the tile
        double angle = Math.toDegrees(Math.asin(-y / Math.sqrt(x * x + y * y))) + 360;

        if (x < 0) {
            angle = 180 + 360 - angle;
        }
        angle %= 360;

        return (11 - (int) Math.round(angle / 45)) % 8;
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
        setColor(Color.orange);
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
