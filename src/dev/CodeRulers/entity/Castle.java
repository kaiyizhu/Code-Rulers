/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.entity;

/**
 *
 * @author seanz
 */
public class Castle extends Entity{
    //whether this castle is currently creating peasants
    private boolean creatingPeasants = true;
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
    
    public void produce(){
     //   int land = AbstractRuler.landCount();
    }
}
