/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.entity;

import dev.CodeRulers.world.World;

/**
 *
 * @author seanz
 */
public class Peasant extends Entity{
    /**
     * Construct a new Peasant at the given location, under the given ruler.
     * @param x The x coordinate of this peasant
     * @param y The y coordinate of this peasant
     * @param ruler The ID of the ruler that this peasant serves
     */
    public Peasant(int x, int y, int ruler) {
        //call the entity constructer
        super(x, y, ruler);
    }

    @Override
    public boolean hasAction(){
        return hasAction;
    }

    @Override
    public void move(int dir) {
        if(changePos(dir)){
            //claim this peice of land
            World.getLandOwned()[x][y]=ruler;
        }else{
            //if the move was obstructed somehow, print it out to the console
            System.out.println("Illegal Movement attempted by ruler " + ruler +
                    " and Peasant at " + x + " , " + y );
        }
    }
}
