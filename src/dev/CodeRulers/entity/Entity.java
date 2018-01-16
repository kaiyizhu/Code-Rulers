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
    public Entity(int x, int y, int ruler) {
        this.x = x;
        this.y =y;
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
        
//    //  Identification Number -> All entities should have an ID number that is unique to each entity
//        protected int ID;
//        
//        /**
//         * Tells the user the ID number of that entity. Each entity should have a unique
//         * entity identification number.
//         * @return the ID number of the entity.
//         */
//        public int getID() {
//            return ID;
//        }
    
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
        //whether or not this entity can move or capture this round. This will 
        //always be false for castles, and be true for other entities at the
        //beginning of the turn
        protected boolean hasAction;
        /**
         * Returns whether this entity can move or capture this turn. This
         * will always return false for a castle, and true for other units
         * at the beginning of the turn.
         * @return Whether the entity has an action remaining.
         */
        public abstract boolean hasAction();
        /**
         * Move this entity in the specified cardinal direction. If this
         * entity is a castle or has already moved or attacked this turn, 
         * nothing will occur.
         * @param dir The direction in which the entity should move. 1 being north
         * and moving counter-clockwise, so that 8 is north-west.
         */
        public abstract void move(int dir);
        
        /**
         * This is a convenience method for the move method. Its used to 
         * check whether the entity can move, and to translate direction
         * to actual movement.
         * @param dir The direction to move in.
         * @return Whether it was moved.
         */
        protected boolean changePos(int dir){
            if(dir > 8 || dir <1 || !hasAction()){
                return false;
            }
            switch(dir){
                case 1: 
                    y--;
                    break;
                case 2:
                    y--;
                    x++;
                    break;
                case 3:
                    x++;
                    break;
                case 4:
                    y++;
                    x++;
                    break;
                case 5:
                    y++;
                    break;
                case 6:
                    y++;
                    x--;
                    break;
                case 7:
                    x--;
                    break;
                case 8:
                    x--;
                    y--;
                    break;
            }
            //if they are out of bounds, move them back
            if(x<0){
                x++;
                return false;
            }else if(x>=64){
                x--;
                return false;
            }
            if(y<0){
                y++;
                return false;
            }else if(y >-64){
                y--;
                return false;
            }
            //check whether they moved into someones space
            return true;
        }
}
