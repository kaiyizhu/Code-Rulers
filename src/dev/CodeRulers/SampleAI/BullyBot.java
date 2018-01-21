/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.ruler.*;
import dev.CodeRulers.entity.*;
import dev.CodeRulers.world.World;
import java.util.Random;

/**
 *
 * @author Luke Klassen
 */
public class BullyBot extends AbstractRuler {
    
    //represents how much this ruler is being threatened by nearby Knights
    int danger = 0;
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
    Random rd = new Random();
    //whether the capture phase is complete
    boolean captured = false;
    @Override
    public void initialize() {
        //get the starting castle of this ruler
        Castle first = World.getCastles()[0];
        //if the castle is in the top right
        if(first.getX() > 32 && first.getY() < 32){
            //set corner to one, direction of expansion south west
            corner = 1;
            dir = 6;
        //if the castle is in the bottom right
        }else if(first.getX() > 32 && first.getY() >= 32){
            //set corner to 2, direction of expansion to north west
            corner = 2;
            dir = 8;
        //if the castle is in the bottom left
        }else if(first.getX() <= 32 && first.getY() >= 32){
            //set the corner to 3, direction of expansion north east
            corner = 3;
            dir = 2;
        //otherwise
        }else{
            //set the corner to 4, direction of expansion to south east
            corner = 4;
            dir = 4;
        }
    }
    @Override
    public void orderSubjects() {
        //tell the peasants to expand
        orderPeasants();
        //if our castle was captured, move to capture others
        if(getCastles().length < 1){
            capture();
        }
        //if it is within the first 20 turns
        if( true){
            //generate knights, start moving towards the center
            setUp();
        //if it is the 20th turn
        }else if( true){
            //get the target to bully
            assignNewTarget();
            //move to attack it
            capture();
        //if it is after turn 20    
        }else if( !captured){
            //attempt to capture the target's castle
            capture();
        //if another castle was captured
        }else{
            //start murdering peasants
            cleanUp();
        }    
    }
    
    private void setUp(){
        //get a list of my knights
        Knight[] myK = getKnights();
        //for all of the knights
        for(Knight k : myK){
            //move the knights in direction of expansion protect peasants
            k.move(dir);
        }
    }
    private void capture(){
        //get the array of the AIs knights
        Knight[] myK = getKnights();
        //for every one of our knights
        for(Knight k : myK){
            //move them to the castle
           k.move (k.getDirectionTo(attacking.getX(), attacking.getY()));
        }
        //if they captured a castle
        if(getCastles().length != numCastles){
            //move to the next phase
            captured = true;
            //update number of castles
            numCastles = getCastles().length;
        }
    }
    private void cleanUp(){
       //Get the list of my knights
       Knight[] myK = getKnights();
       //get the list of all peasants
       Peasant[] myP = World.getPeasants();
       //create a minor target variable, set it to null
       Peasant minorTarget = null;
       //for all peasants
       for(Peasant p : myP){
           //if their ruler matches our target
           if(p.getRuler() == target){
               //set them as the minorTarget
               minorTarget = p;
               break;
           }
       }
       //if a minor target was found
       if(minorTarget != null){
           //move every knight towards them
            for(Knight k: myK){
                k.move(k.getDirectionTo(minorTarget.getX(), minorTarget.getY()));
            }
       //if they have no more peasants
       }else{
           //get a new target
           assignNewTarget();
           //move to capture phase again
           captured = false;
       }
    }
    
    private void orderPeasants(){
        //get the list of my peasants
        Peasant[] myP = getPeasants();
        //for every one of my peasants
        for(Peasant p : myP){
            //if the tile they would move to is not captured
            if(World.getLandOwner(p.getX()+translateDir(dir)[0], p.getY()+translateDir(dir)[1]) != rulerID){
                //move them there
                p.move(dir);
            //otherwise
            }else{
                //move them in another direction
                p.move(dir/2);
            }
        }
    }
    private void assignNewTarget(){
        //get a list of my castles
        Castle[] myC = getCastles();
        //for all of my castles
        for(Castle c: myC){
            //get them to produce knights
            c.createKnights();
        }
        //represents the lowest found number of tiles owned
        int low = 4096;
        //the id of the ruler with the least land owned so far
        int lowID = -1;
        //for all of the rulers in the game
        for(int i=0; i<4; i++){
            //set a temporary variable to the count of this ruler's land
            int temp = World.getLandCount(i);
            //if the ruler at this index is not Bully
            //and its count is smaller than the last count
            if(i != rulerID && temp < low){
                //set its ID and land count to the lowest
                low = temp;
                lowID = i;
            }
        }
        //set the target to the ruler with the least land
        target = lowID;
        //get the array of all the castles
        Castle[] allCastles = World.getCastles();
        //for every castle
        for(Castle c: allCastles){
            //if this castle is owned by the target
            if(c.getRuler() == target)
                //sick the knights on it
                attacking = c;
        }
    }
    
    

    @Override
    public String getSchoolName() {
        return "The FlexQueue Dev. Team";
    }

    @Override
    public String getRulerName() {
        return "Bender";
    }
    
}
