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
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author seanzhang
 */
public class World {
    //image icons for the entities on the board
    
    
    //image for the background of the world
    
    public World() {
        knights = new Knight[0];
        peasants = new Peasant[0];
        castles = new Castle[0];
        
        landOwned = new int[64][64];
    }
    
    //these are all the knights that exist in the game.
    private static Knight[] knights;
    //these are all the peasants that exist in the game.
    private static Peasant[] peasants;
    //these are all the castles that exist in the game.
    private static Castle[] castles;
    
    //these are all the land owned by the ruler.
    private static int[][] landOwned;

    /**
     * Get method that gets the 2D map of which ruler owns which tile.
     * @return a 2D array containing data of the 64x64 tiles on the map.
     */
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
    
    
    
    public static void render(Graphics g) {
        
        g.setFont(new Font("Myriad", Font.BOLD, 16));
        
        g.drawString("Welcome to CodeRulers!", 10, 10);
    }
    
    public static void tick() {
        
        
    }
    
    /**
     * Gets the number of tiles owned by the ruler with the given ID.
     * @return The amount of land owned by this ruler
     * @param rulerID the ID of the ruler who's count you need
     */
    public static int getLandCount(int rulerID){
        //get the 2d int[] from the world
        int[][] allLand = getLandOwned();
        //initailize an integer to store the number of tiles
        int count = 0;
        //sort through it, increment integer with every tile that has this ID
        for(int[] i: allLand){
            for(int j: i){
                if(j == rulerID){
                    count++;
                }    
            }
        }
        return count;
    }
}
