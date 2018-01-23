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
        profileURL =("http://www.cse.lehigh.edu/~munoz/CSE497/assignments/files/coderuler_files/fig2.gif");
        setColor(new Color(200,50,50)); 
    }
    @Override
    public void orderSubjects() {
        //tell the peasants to expand
            orderPeasants();
        
        //if our castle was captured, move to capture others
        if( CodeRulers.getTurnCount() < 20){
            //generate knights, start moving towards the center
            setUp();
        //if it is the 20th turn
        }else if( CodeRulers.getTurnCount() ==20){
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
            assignNewTarget();
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
            if(attacking != null){
                k.move (k.getDirectionTo(attacking.getX(), attacking.getY()));
            }else{
                captured = true;
            }
        }
    }
    
    private void orderPeasants(){
        //get the list of my peasants
        Peasant[] myP = getPeasants();
        if(CodeRulers.getTurnCount()%20 == 0){
            chooseDir();
        }else if(CodeRulers.getTurnCount()%20 == 19){
            dir = 1;
        }
        //for every one of my peasants
        for(Peasant p : myP){
            //move them in that direction
            p.move(dir);
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
        for(int i=0; i<World.getNumRulers(); i++){
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
        Castle[] otherCastles = getOtherCastles();
        //for every castle
        for(Castle c: otherCastles){
            //if this castle is owned by the target
            if(c.getRuler() == target)
                //stick the knights on it
                attacking = c;
        }
        captured = false;
    }

    private void chooseDir(){
        //get the list of peasants from the ruler
        Peasant[] myPeasants = getPeasants();
        //represents the net position of the peasants, + is to the right, - to the left
        int netX = 0;
        //represents the net position of peasants, + is below, - is above
        int netY = 0;
        //for every one of my peasant
        for(Peasant p : myPeasants){
            //if they are to the right
            if(p.getX() >= 32){
                //increment netX
                netX++;
            }else{
                //decrement netX
                netX--;
            }
            //if they are to the bottom
            if(p.getY() >= 32){
                //increment netY
                netY++;
            //otherwise
            }else{
                //decrement netY
                netY--;
            }
        }
        //if the peasants are to the left
        if(netX <= 0){
            //set their direction to the right
            dir = 3;
        //otherwise
        }else{
            //set their direction to the left
            dir = 7;
        }
        //if the peasants are below
        if(netY >= 0){
            //move them diagonally upwards
            if(dir == 7)dir=8;
            if(dir ==3)dir=2;
        //if the peasants are above
        }else if(netY < 0){
            //move them diagonally downwards
            if(dir == 7)dir=6;
            if(dir ==3)dir=4;
        }
    }

    @Override
    public String getSchoolName() {
        return "NHS CodeRulers -Luke";
    }

    @Override
    public String getRulerName() {
        return "Bully Bot";
    }

    
}
