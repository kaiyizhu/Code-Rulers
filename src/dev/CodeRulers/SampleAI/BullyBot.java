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
    
    //the castle owned by the person the AI will be attacking
    Castle attacking;
    //The direction in which peasants claim land and knights attack
    int dir;
    //random number generator for choosing which peasants to kill
    Random rd = new Random();

    @Override
    public void initialize() {
        profileURL =("http://www.cse.lehigh.edu/~munoz/CSE497/assignments/files/coderuler_files/fig2.gif");
        setColor(new Color(200,50,50)); 
    }
    @Override
    public void orderSubjects() {
        //tell the peasants to expand
            orderPeasants();
        
        if(getOtherKnights().length < getKnights().length){
            //send the knights to bully them
            attackKnights();
        //otherwise, try to capture a castle
        }else if(getOtherCastles().length > 0){
            //assign a new castle as a target
            capture();
        //otherwise, if there are still peasants
        }else if(getOtherPeasants().length != 0){
            //send the knights to bully them
            attackPeasants();
        //otherwise, if there are still other knights
        }else{
            //tell the castles to create peasants
            Castle[] myC = getCastles();
            for(Castle c: myC){
                c.createPeasants();
            }
        }
                    
    }
    
    private void capture(){
        if(attacking == null){
            assignNewTarget();
        }
        if(attacking.getRuler() == rulerID){
            assignNewTarget();
        }
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
                }else{
                    //move towards the castle (to prevent backups of knights)
                    move(k, k.getDirectionTo(attacking.getX(), attacking.getY()));
                }
            //otherwise
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
        attacking = getOtherCastles()[0];
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
        //get an array of my knights
        Knight[] myK = getKnights();
        //get the array of other peasants
        Peasant[] otherP = getOtherPeasants();
        //for all of my knights
        for(int i=0; i<myK.length;i++){
            //if there are more peasants to assign knights to attacking
            if(otherP.length > i){
                //move the knight towards the next peasant in the array
                move(myK[i], myK[i].getDirectionTo(otherP[i].getX(), otherP[i].getY()));
            //otherwise
            }else{
                //move the knight towards the peasant at the end of the array
                move(myK[i], myK[i].getDirectionTo(otherP[otherP.length-1].getX(), otherP[otherP.length-1].getY()));
            }
        }
    }
    private void attackKnights(){
        //get the array of my knights
        Knight[] myK = getKnights();
        //get the array of other knights
        Knight[] otherK = getOtherKnights();
        //for every one of my knights
        for(int i=0; i<myK.length;i++){
            //if there are more knights to attack
            if(otherK.length > i){
                //if the knight at this index is close enough to attack, attack
                if(myK[i].getDistanceTo(otherK[i].getX(), otherK[i].getY()) == 1){
   //                 myK[i].capture(otherK[i]);
                    capture(myK[i], myK[i].getDirectionTo(otherK[i].getX(), otherK[i].getY()));
                //otherwise, move them towards the other knights
                }else{
                    move(myK[i], myK[i].getDirectionTo(otherK[i].getX(), otherK[i].getY()));
                }
            //if there are no more knights in the knight array
            }else{
                //if this knight is close enough to attack the knight at the end of the array, attack
                if(myK[i].getDistanceTo(otherK[otherK.length-1].getX(), otherK[otherK.length-1].getY()) == 1){
                    capture(myK[i], myK[i].getDirectionTo(otherK[otherK.length-1].getX(), otherK[otherK.length-1].getY()));
                //otherwise, move towards it
                }else{
            //        myK[i].move(myK[i].getDirectionTo(otherK[otherK.length-1].getX(), otherK[otherK.length-1].getY()));
                    move(myK[i], myK[i].getDirectionTo(otherK[otherK.length-1].getX(), otherK[otherK.length-1].getY()));
                }
            }
        }
    }
    @Override
    public String getSchoolName() {
        return "NHS CodeRulers";
    }

    @Override
    public String getRulerName() {
        return "Bully Bot";
    }

    
}
