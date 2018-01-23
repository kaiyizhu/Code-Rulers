/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.entity;

import dev.CodeRulers.util.IMAGE;
import dev.CodeRulers.world.World;

/**
 *
 * @author seanz
 */
public class Peasant extends Entity {

    /**
     * Construct a new Peasant at the given location, under the given ruler.
     *
     * @param x The x coordinate of this peasant
     * @param y The y coordinate of this peasant
     * @param ruler The ID of the ruler that this peasant serves
     */
    public Peasant(int x, int y, int ruler) {
        //call the entity constructer
        super(x, y, ruler);
        entityIcn = IMAGE.getBufferedImage("src/resources/images/CodeRulersSprites2_0.png");
    }

    @Override
    public boolean hasAction() {
        return hasAction;
    }

    @Override
    public void move(int dir) {
        if (hasAction) {
            if (changePos(dir)) {
                //claim this peice of land
                hasAction = false;
                World.getLandOwned()[x][y] = ruler;
            } 
        }
    }
}
