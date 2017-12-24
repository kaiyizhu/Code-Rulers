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
public interface Entity {
    //this is the super interface to all pieces on the playing field. Each piece
    //should implement the entity interface. 
    
    //some properties that the entity class should have:
    //  Ruler -> Each entity should be owned by a "ruler", or player in the game.
        //this method returns the ruler's unique ID number generated in the game.
    
        /**
         * This method tells the user who owns this piece by telling the user the ruler's ID number.
         * @return the ID number associated to that ruler. This ID number is created when creating a ruler.
         */
        public int getRuler();
    //  Coordinates -> Each entity should have a location on the playing grid
        
        public int getX();
    //  Alive -> Each entity should return a boolean stating if it has been captured or not.
    //  Identification Number -> All entities should have an ID number that is unique to each entity
    
    
}
