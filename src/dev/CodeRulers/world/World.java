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

/**
 *
 * @author seanzhang
 */
public class World {
    //these are all the knights that exist in the game.
    private static Knight[] knights;
    //these are all the peasants that exist in the game.
    private static Peasant[] peasants;
    //these are all the castles that exist in the game.
    private static Castle[] castles;
    
    
    
    
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
    
    public static String getEntityAt(int x, int y) {
        if(x>63 || y>63 || x<0 || y<0) {
            return "Invalid Coordinate";
        }
        
        for(int i=0;i<knights.length;i++) {
            if(knights[i].getX()==x && knights[i].getY()==y) {
                return "Knight";
            }
        }
        
        for(int i=0;i<peasants.length;i++) {
            if(peasants[i].getX()==x && peasants[i].getY()==y) {
                return "Peasant";
            }
        }
        
        for(int i=0;i<castles.length;i++) {
            if(castles[i].getX()==x && castles[i].getY()==y) {
                return "Castle";
            }
        }
        
        return "No Entity";
    }
    
    
}
