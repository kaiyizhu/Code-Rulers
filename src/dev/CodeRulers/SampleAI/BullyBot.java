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
    
    //the ruler ID which is targeted by this AI
    int target;
    //the castle owned by the person the AI will be attacking
    Castle attacking;
    //The direction in which peasants claim land and knights attack
    int dir;
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
            if(getOtherCastles().length != 0){
                assignNewTarget();
            }else if(getOtherPeasants().length != 0){
                attackPeasants();
            }else if(getOtherKnights().length != 0){
                attackKnights();
            }else{
                Castle[] myC = getCastles();
                for(Castle c: myC){
                    c.createPeasants();
                }
            }
                
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
            //if the castle is not yet captured
            if(attacking.getRuler() != rulerID){
                //if they are 1 tile away from the castle
                if(k.getDistanceTo(attacking.getX(), attacking.getY()) == 1){
                    //capture the castle
                    capture(k, k.getDirectionTo(attacking.getX(), attacking.getY()));
                }
                //move towards the castle (to prevent backups of knights)
                k.move(k.getDirectionTo(attacking.getX(), attacking.getY()));
            //otherwise
            }else{
                //set captured to true
                captured = true;
                break;
            }
        }
    }
    
    private void orderPeasants(){
        //get the list of my peasants
        Peasant[] myP = getPeasants();
        //if the turn number is a multiple of 25
        if(CodeRulers.getTurnCount()%25 == 0){
            //pick a new direction to move in
            chooseDir();
       //if it is the turn before the 25th
        }else if(CodeRulers.getTurnCount()%25 == 24){
            //move one space north
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
        //get the list of all other castles
        Castle[] otherCastles = getOtherCastles();
        //for every castle
        for(Castle c: otherCastles){
            //if this castle is owned by the target
            if(c.getRuler() != rulerID){
                //stick the knights on it
                attacking = c;
                //break the for loop
                break;
            }
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
    private void attackPeasants(){
        Knight[] myK = getKnights();
        Peasant[] otherP = getOtherPeasants();
        for(int i=0; i<myK.length;i++){
            if(otherP.length > i){
                myK[i].move(myK[i].getDirectionTo(otherP[i].getX(), otherP[i].getY()));
            }else{
                if(myK[i].getDistanceTo(otherP[otherP.length-1].getX(), otherP[otherP.length-1].getY()) == 1){
                    myK[i].capture(otherP[otherP.length-1]);
                }else{
                    myK[i].move(myK[i].getDirectionTo(otherP[otherP.length-1].getX(), otherP[otherP.length-1].getY()));
                }
            }
        }
    }
    private void attackKnights(){
        Knight[] myK = getKnights();
        Knight[] otherK = getOtherKnights();
        for(int i=0; i<myK.length;i++){
            if(otherK.length > i){
                if(myK[i].getDistanceTo(otherK[i].getX(), otherK[i].getY()) == 1){
                    myK[i].capture(otherK[i]);
                }else{
                    myK[i].move(myK[i].getDirectionTo(otherK[i].getX(), otherK[i].getY()));
                }
            }else{
                if(myK[i].getDistanceTo(otherK[otherK.length-1].getX(), otherK[otherK.length-1].getY()) == 1){
                    myK[i].capture(otherK[otherK.length-1]);
                }else{
                    myK[i].move(myK[i].getDirectionTo(otherK[otherK.length-1].getX(), otherK[otherK.length-1].getY()));
                }
            }
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
