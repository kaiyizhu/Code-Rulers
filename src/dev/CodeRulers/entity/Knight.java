/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.entity;

import dev.CodeRulers.util.IMAGE;
import dev.CodeRulers.world.World;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author seanz
 */
public class Knight extends Entity {

    //the strength of this knight
    private int strength;

    /**
     * Constructs a new Knight at the given location, under the given ruler.
     *
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
    public boolean hasAction() {
        return hasAction;
    }

    @Override
    public void move(int dir) {
        if (hasAction) {
            //if the piece can be move correctly in that direction
            if (changePos(dir)) {
                //do nothing. Its a knight
                hasAction = false;
            }
        }
    }

    /**
     * Returns the strength(health) of this knight. A knights begin with 100
     * strength, and if reduced to 0 health by other knights, are captured.
     *
     * @return The current strength of this knight
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Attempts to capture the given entity. Should only be called by the
     * AbstractRuler class.
     *
     * @param attacked The entity which is to be attacked
     * @return The number of points earned in attack (0 indicates no Entity
     * captured)
     */
    public int capture(Knight attacked) {
        if (Math.abs(attacked.getX() - this.getX()) > 1 || Math.abs(attacked.getY() - this.getX()) > 1 || !this.hasAction || this.getRuler() == attacked.getRuler()) {
            if (!this.hasAction) {
                System.out.println("no action");
            }
            if (this.getRuler() == attacked.getRuler()) {
                System.out.println(this.getRuler()+" - same ruler - " + attacked.getRuler());
            }

            return 0;

        }
        hasAction = false;
        //if the attacked entity is a knight
        //create a reference for it as a knight
        Knight k = (Knight) attacked;
        //reduce its strength by a random number from 1-25
        k.strength -= (int) Math.ceil(Math.random() * 25);

        System.out.println(k.strength);

        //if it is out of strength, remove it
        if (k.strength < 1) {
            //set up an arrayList of the knights
            ArrayList<Knight> knights = new ArrayList<>(Arrays.asList(World.getAllKnights()));
            //remove the dead knight from the arrayList
            knights.remove(k);
            //set the Knights in the World to the ArrayList
            World.setKnights(Arrays.copyOf(knights.toArray(), knights.size(), Knight[].class));
            //increase this unit's strength by 20
            this.strength += 20;
            return 6;
        }
        return 0;
    }

    /**
     * Attempts to capture the given entity. Should only be called by the
     * AbstractRuler class.
     *
     * @param attacked The entity which is to be attacked
     * @return The number of points earned in attack (0 indicates no Entity
     * captured)
     */
    public int capture(Peasant attacked) {
        if (Math.abs(attacked.getX() - this.getX()) > 1 || Math.abs(attacked.getY() - this.getX()) > 1 || !this.hasAction || this.getRuler() == attacked.getRuler()) {
            if (!this.hasAction) {
                System.out.println("no action");
            }
            if (this.getRuler() == attacked.getRuler()) {
                System.out.println("same ruler");
            }

            return 0;

        }
        hasAction = false;
        //create a peasant reference to it
        Peasant p = (Peasant) attacked;
        //set up an ArrayList of the peasants
        ArrayList<Peasant> peasants = new ArrayList<>(Arrays.asList(World.getAllPeasants()));
        //remove the dead peasant from the ArrayList
        peasants.remove(p);
        //set the World's peasants to the arrayList
        World.setPeasants(Arrays.copyOf(peasants.toArray(), peasants.size(), Peasant[].class));
        return 4;

    }

    /**
     * Attempts to capture the given entity. Should only be called by the
     * AbstractRuler class.
     *
     * @param attacked The entity which is to be attacked
     * @return The number of points earned in attack (0 indicates no Entity
     * captured)
     */
    public int capture(Castle attacked) {
        if (Math.abs(attacked.getX() - this.getX()) > 1 || Math.abs(attacked.getY() - this.getX()) > 1 || !this.hasAction || this.getRuler() == attacked.getRuler()) {
            if (!this.hasAction) {
                System.out.println("no action");
            }
            if (this.getRuler() == attacked.getRuler()) {
                System.out.println("same ruler");
            }

            return 0;

        }
        //create a castle reference
        Castle c = (Castle) attacked;
        //set this ruler to own the castle
        c.ruler = this.ruler;
        World.getLandOwned()[attacked.x][attacked.y] = ruler;
        return 15;
        //otherwise, if it is a peasant
    }

}
