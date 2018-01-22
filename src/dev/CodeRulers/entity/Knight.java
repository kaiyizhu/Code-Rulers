/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.entity;

import dev.CodeRulers.util.IMAGE;

/**
 *
 * @author seanz
 */
public class Knight extends Entity{
    //the strength of this knight
    private int strength;
    /**
     * Constructs a new Knight at the given location, under the given ruler.
     * @param x The x coordinate of the Knight on the game board.
     * @param y The y coordinate of the Knight on the game board.
     * @param ruler The ID of the ruler who owns this Knight.
     */
    public Knight(int x, int y, int ruler) {
        //call the Entity constructor
        super(x, y, ruler);
        //set strength to 100
        strength = 100;
        entityIcn = IMAGE.getBufferedImage("src/resources/images/CodeRulersSprites2_1.png");
    }

    @Override
    public boolean hasAction(){
        return hasAction;
    }
    
    @Override
    public void move(int dir){
        //if the piece can be move correctly in that direction
        if(changePos(dir)){
            //do nothing. Its a knight
        }else{
            System.out.println("Illegal Movement attempted by ruler " + ruler +
                    " and Knight at " + x + " , " + y );
        }    
    }
    /**
     * Returns the strength(health) of this knight. A knights begin with
     * 100 strength, and if reduced to 0 health by other knights, are captured.
     * @return 
     */
    public int getStrength() {
        return strength;
    }
    
    /**
     * Attempts to capture the given entity. Should only be called by the
     * AbstractRuler class.
     * @param attacked The entity which is to be attacked 
     * @return  The number of points earned in attack (0 indicates no Entity captured)
     */
    public int capture(Entity attacked){
        if(this.getDistanceTo(attacked.getX(), attacked.getY()) > 1 || !this.hasAction){
            return 0;
        }
        //if the attacked entity is a knight
        if(attacked instanceof Knight){
            //create a reference for it as a knight
            Knight k = (Knight)attacked;
            //reduce its strength by a random number from 1-25
            k.strength -= (int)Math.ceil(Math.random()*25);
            //check if it has strenght left
            if(k.strength <1){
                //if not, set it to not be alive
                k.alive = false;
                //increase this unit's strength by 20
                this.strength +=20;
                return 6;
            }
        //otherwise, if the attacked entity is a castle
        }else if (attacked instanceof Castle){
            //create a castle reference
            Castle c = (Castle)attacked;
            //set this ruler to own the castle
            c.ruler = this.ruler;
            return 15;
        //otherwise, if it is a peasant
        }else if(attacked instanceof Peasant){
            //create a peasant reference to it
            Peasant p = (Peasant)attacked;
            //assign the peasant under this ruler
            p.ruler = this.ruler;
            return 4;
        }
        return 0;
    }
    
}
