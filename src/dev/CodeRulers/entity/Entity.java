/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.entity;
import dev.CodeRulers.ruler.AbstractRuler;
import dev.CodeRulers.util.IMAGE;
import dev.CodeRulers.world.World;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 *
 * @author Sean Zhang
 */
public abstract class Entity {
    protected BufferedImage entityIcn;
    
    /**
     * The Constructor. Creates an entity with the following parameters:
     * @param x The x value of the coordinate where the entity is located
     * @param y The y value of the coordinate where the entity is located
     * @param ruler The ID number of the ruler
     */
    public Entity(int x, int y, int ruler) {
        this.x = x;
        this.y =y;
        this.ruler = ruler;
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
         * to actual movement. Note that it also move the entity if possible.
         * @param dir The direction to move in.
         * @return Whether it was moved.
         */
        protected boolean changePos(int dir){
            //check whether the direction is valid
            if(dir > 8 || dir <1 || !hasAction()){
                return false;
            }
            //translate the direction into x,y differences
            int[] xy = AbstractRuler.translateDir(dir);
            //check if someone is at the location where they are trying to move
            Entity inLocation = World.getEntityAt(x+xy[0], y+xy[1]);
            if(inLocation != null){
                //if this is a knight and it moves into an enemy peasant
                if(this instanceof Knight && inLocation instanceof Peasant &&
                        this.ruler != inLocation.ruler){
                    //create a knight reference to this entity
                    Knight k = (Knight)this;
                    //give the knight the action to capture this entity
                    k.hasAction = true;
                    //capture the peasant
                    k.capture((Peasant)inLocation);
                }else{
                    //return false, stop it from moving into another entity's space
                    return false;
                }
            }
            
            //Check if the entity would move out of bounds
            if(x + xy[0] < 0 || x + xy[0] >= 64 || y + xy[1] < 0 || y + xy[1] >= 64) {
                return false;
            }
            
            //move the piece in that direction
            x+=xy[0];
            y+=xy[1];
            return true;
        }
        
        /**
         * This method returns an integer (1-8) direction for the closest direction to a point on the board
         * @param x The X-Coordinate
         * @param y The Y-Coordinate
         * @return the closest direction to the point
         */
        public int getDirectionTo(int x, int y) {
            //Calculate the Coordinate
            double dy = x - this.x;
            double dx = y - this.y;
            
            //Find the angle of from this entity to the tile
            double hypot = Math.sqrt(dy*dy + dx*dx);
            double angle = Math.toDegrees(Math.asin(dy/hypot)) + 360;
            
            //Find the raw direction
            if(dx < 0) {
                angle = 180 + 360 - angle;
            }
            angle = angle%360;
            
            int dir = (int) Math.round(angle / 45);
            
            //Find Game Direction
            dir = 8 - dir + 3;
            dir %= 8;
            
            return dir;
        }
        
        /**
         * This method just returns the double for distance between this entity and a given point
         * @param x The X-Coordinate
         * @param y The Y-Coordinate
         * @return the tile distance between this entity and a given point
         */
        public int getDistanceTo(int x, int y) {
            int dx = this.x - x;
            int dy = this.y - y;
            
            int distance = Math.min(dx, dy);
            distance += Math.abs(dx - dy);
            
            return Math.abs(distance);
        }
        
        public void setAction(boolean hasAction){
            this.hasAction = hasAction;
        }
        
        public void drawEntity(Graphics g,double scaleFactor,int xOffset,int yOffset) {
            BufferedImage icn =IMAGE.getResizedImage(entityIcn, (int)(12*scaleFactor), (int)(12*scaleFactor));
            g.drawImage(icn,(int)(x*12*scaleFactor)+xOffset,(int)(y*12*scaleFactor)+yOffset, null);
        }
}
