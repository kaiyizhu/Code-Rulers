/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.entity;

import dev.CodeRulers.util.IMAGE;
import dev.CodeRulers.world.*;
import java.util.Arrays;

/**
 *
 * @author seanz
 */
public class Castle extends Entity{
    //whether this castle is currently creating peasants
    private boolean creatingPeasants = true;
    //The number of turns until the castle produces
    private int numTurns;
    //The production bracket that the castle is currently in
    private int maxToCreation;
    /**
     * Constructs a new Castle object. Should only be preformed at the
     * start of a game
     * @param x The x location of the castle on the board
     * @param y The y location of the castle on the board
     * @param ruler The ID of the ruler owning this castle
     */
    public Castle(int x, int y, int ruler) {
        //call the entity constructer
        super(x, y, ruler);
        entityIcn = IMAGE.getBufferedImage("src/resources/images/CodeRulersSprites2_2.png");
        hasAction = false;
    }

    @Override
    public boolean hasAction() {
        //castles never have actions
        return false;
    }
    
    @Override
    public void move(int dir){
        //do nothing. Castles cant move
    }
    /**
     * Tells this castle to make Peasants instead of Knights.
     */
    public void createPeasants(){
        //set this castle to manufacture peasants
        creatingPeasants = true;
    }
    /**
     * Tells this castle to make Knights instead of Peasants.
     */
    public void createKnights(){
        //set this castle to manufacture knights
        creatingPeasants = false;
    }
    
    /**
     * Produces Peasants or Knights
     */
    public void produce(){
        //get the count of land under this ruler
        int land = World.getLandCount(ruler);
        //initialize a new variable, to represent change in maxToCreation
        int tempMax = maxToCreation;
        //if they own fewer than 124 pieces of land
        if(land < 124){
            //do nothing
        //between 125 and 249
        }else if(land < 250){
            tempMax = 14;
        //between 250 and 499
        }else if(land < 500){
            tempMax = 12;
        //between 500 and 999
        }else if (land < 1000){
            tempMax = 10;
        //between 1000 and 1999
        }else if(land < 2000){
            tempMax = 8;
        //between 2000 and 3999
        }else if (land < 4000){
            tempMax = 6;
        //greater than or equal to 4000
        }else if (land >= 4000){
            tempMax = 4;
        }
        
        //if they changed brackets
        if(maxToCreation != tempMax){
            //reduce the number of turns to that change
            numTurns -= tempMax-maxToCreation;
        }
        //set the max to the temp
        maxToCreation = tempMax;
        //decrement number of turns till production
        numTurns--;
        //if there are no more turns remaining
        if(numTurns <= 0){
            //if they want to create peasants
            if(creatingPeasants){
                //get the peasants list from world
                Peasant[] p = World.getAllPeasants();
                //make a larger copy of it
                Arrays.copyOf(p, p.length +1);
                //set the final element to the newly created peasant
                //created on top of the castle
                p[p.length-1] = new Peasant(x,y,ruler);
                //set the World's peasants to this new array
                World.setPeasants(p);
            //if they are creating knights
            }else{
                //get the knights list
                Knight[] k = World.getAllKnights();
                //make a larger copy
                Arrays.copyOf(k, k.length +1);
                //set the final knight to a new knight
                k[k.length-1] = new Knight(x,y,ruler);
                //set the World's knights to the array
                World.setKnights(k);
            }
        }
    }
}
