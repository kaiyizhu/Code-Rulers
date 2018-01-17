/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.world;

import dev.CodeRulers.entity.Castle;
import dev.CodeRulers.entity.Entity;
import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import java.awt.Graphics;

/**
 *
 * @author seanzhang
 */
public class World {
    //image icons for the entities on the board
    
    //image for the background of the world
    
    
    
    //these are all the knights that exist in the game.
    private static Knight[] knights;
    //these are all the peasants that exist in the game.
    private static Peasant[] peasants;
    //these are all the castles that exist in the game.
    private static Castle[] castles;
    
    //these are all the land owned by the ruler.
    private static int[][] landOwned;

    public static int[][] getLandOwned() {
        return landOwned;
    }
    
    
    
    
    public static void setKnights(Knight[] knights) {
        World.knights = knights;
    }

    public static void setPeasants(Peasant[] peasants) {
        World.peasants = peasants;
    }
    
    public static Knight[] getKnights() {
        return knights;
    }

    public static Peasant[] getPeasants() {
        return peasants;
    }

    public static Castle[] getCastles() {
        return castles;
    }
    
    public static Entity getEntityAt(int x, int y) {
        if(x>63 || y>63 || x<0 || y<0) {
            System.out.println("Coordinates out of bounds.");
            return null;
        }
        
        for(int i=0;i<knights.length;i++) {
            if(knights[i].getX()==x && knights[i].getY()==y) {
                return knights[i];
            }
        }
        
        for(int i=0;i<peasants.length;i++) {
            if(peasants[i].getX()==x && peasants[i].getY()==y) {
                return peasants[i];
            }
        }
        
        for(int i=0;i<castles.length;i++) {
            if(castles[i].getX()==x && castles[i].getY()==y) {
                return castles[i];
            }
        }
        
        System.out.println("No Entity Found.");
        return null;
    }
    
    public static int getLandOwner(int x, int y) {
        return landOwned[x][y];
    }
    
    
    
    public void render(Graphics g) {
        
        
    }
    
}
