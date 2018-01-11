/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.entity;

/**
 *
 * @author Sean Zhang
 */
public abstract class Entity {
    /**
     * The Constructor. Creates an entity with the following parameters:
     * @param x The x value of the coordinate where the entity is located
     * @param y The y value of the coordinate where the entity is located
     * @param ruler The ID number of the ruler
     * @param ID The ID number of this entity
     */
    public Entity(int x, int y, int ruler, int ID) {
        this.x = x;
        this.y =y;
        this.ID=ID;
        this.ruler = ruler;
        alive = true;
    }
    
    //this is the super abstract class to all pieces on the playing field. Each piece
    //should extend the entity class. 
    
    //some properties that the entity class should have:
    
    //  Ruler -> Each entity should be owned by a "ruler", or player in the game.
        //this method returns the ruler's unique ID number generated in the game.
        protected int ruler;
        /**
         * This method tells the user who owns this piece by telling the user the ruler's ID number.
         * @return the ID number associated to that ruler. This ID number is created when creating a ruler.
         */
        public int getRuler() {
            return ruler;
        }
        
    //  Coordinates -> Each entity should have a location on the playing grid
        protected int x,y;
        
        /**
         * This method will tell the user the x value of the 2D coordinate of where the entity is.
         * @return the x value of the coordinate where the entity is located.
         */
        public int getX() {
            return x;
        }
        
        /**
         * This method will tell the user the y value of the 2D coordinate of where the entity is.
         * @return the y value of the coordinate where the entity is located.
         */
        public int getY() {
            return y;
        }
        
    //  Alive -> Each entity should return a boolean stating if it has been captured or not.
        protected boolean alive;
        
        /**
         * Tells whether the piece has been captured or not.
         * @return Returns true if the piece is alive. False if not.
         */
        public boolean isAlive() {
            return alive;
        }
        
    //  Identification Number -> All entities should have an ID number that is unique to each entity
        protected int ID;
        
        /**
         * Tells the user the ID number of that entity. Each entity should have a unique
         * entity identification number.
         * @return the ID number of the entity.
         */
        public int getID() {
            return ID;
        }
    
    //  Width and height of the Entity -> All entities should have a width and height.
        protected int width,height;
        
        /**
         * Gets the width of the entity
         * @return the width of the entity
         */
        public int getWidth() {
            return width;
        }

        /**
         * Gets the height of the entity
         * @return the height of the entity
         */
        public int getHeight() {
            return height;
        }
        
        
}
