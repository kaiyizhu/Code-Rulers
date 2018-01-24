/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.ruler;
import dev.CodeRulers.entity.*;
import dev.CodeRulers.util.IMAGE;
import dev.CodeRulers.world.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Luke
 */
public abstract class AbstractRuler implements Comparable{
    
/*Fields and features of the Ruler class*/
    //integer used to identify this ruler in game(roughly equivalent to player number)
    protected int rulerID; //not sure about final or not
    //the number of points this ruler has
    private int points;
    
    //this is the default profile URL for any AI.
    protected String profileURL="http://www.havoca.org/wp-content/uploads/2016/03/icon-user-default-300x300.png";
    
    private BufferedImage profileImage;
    //this is the default profile color for the AI.
    private Color c=Color.WHITE;
    
    /**
     * Initializes a new Abstract Ruler, based upon the implementation of the
     * initialize() method within implementing classes.
     */
    public AbstractRuler(){
        initialize();
        points = 0;
        profileImage = IMAGE.getBufferedImageURL(profileURL);
    }

    
    /**
     * This is where the Ruler can perform actions. Every turn, the implementation
     * of this method will be called. Within this method is the strategy of 
     * your AI. Each time this is called, the Ruler may move() every subject
     * once, attempt to capture() with a knight, and possibly change their 
     * castle's development using createKnights() or createPeasants().
     */
    public abstract void orderSubjects();
    
    /**
     * This method is called when this ruler is initialized. It allows for
     * custom values to be assigned at the beginning of the match.
     * The time spent in initialization is to be kept below one second.
     */
    public abstract void initialize();
    
    /**
     * Moves one of this Ruler's subjects 1 space in the given direction. 
     * Direction starts with 1 being North, and moves Clockwise so that NW is 8.
     * If piece does not have movement or is a castle, no movement will occur.
     * @param moved The subject which is to move
     * @param dir The cardinal direction in which it moves
     */
    public void move(Entity moved, int dir){
        //tell the entity to move itself. What a lazy ruler
        moved.move(dir);
    }

    /**
     * Commands a knight to attack whatever is in the given direction.
     * If no suitable target exists, nothing will happen.
     * @param attacking The knight under this ruler's command
     * @param dir the cardinal direction to attack in
     */
    public void capture(Knight attacking, int dir){
        //check that this knight can attack
        if(attacking.hasAction()){
            //see if anything is in that space
            int[] xy = translateDir(dir);
            Entity attacked = World.getEntityAt(attacking.getX()+xy[0], attacking.getY()+xy[1]);    
            if(attacked != null){
                //call the attack
                if(attacked.getClass().isAssignableFrom(Knight.class)) {
                    points += attacking.capture((Knight)attacked);
                } else if(attacked.getClass().isAssignableFrom(Castle.class)) {
                    points += attacking.capture((Castle)attacked);
                } else if(attacked.getClass().isAssignableFrom(Peasant.class)) {
                    points += attacking.capture((Peasant)attacked);
                }
                
            }
        }
    }
    /**
     * Sets the castle to start creating knights instead of peasants.
     * @param commanded The castle which being told to create knights
     */
    public void createKnights(Castle commanded){
        //tell the castle to create knights
        commanded.createKnights();
    }
    /**
     * Sets the castle to start creating peasants instead of knights.
     * @param commanded The castle which being told to create knights
     */
    public void createPeasants(Castle commanded){
        //tell the castle to create peasants
        commanded.createPeasants();
    }
    /**
     * Gets the name of the school, team, or individual who created this Ruler AI
     * @return The given name of the developers of the AI
     */
    public abstract String getSchoolName();
    /**
     * Gets the name given to this Ruler, as determined by its developers. 
     * This name may be used to describe the strategy that the AI employs 
     * (such as all Knights), or may be completely unrelated (Kaiser Wilhelm II)
     * @return The name of the Ruler, as decided by its developers
     */
    public abstract String getRulerName();
    /**
     * Gets the unique integer used to distinguish ruler's and their subjects.
     * The ID is roughly equivalent to player number.
     * @return This ruler's integer identification
     */
    public int getRulerID() {
        return rulerID;
    }
    
/*Convience Methods for AI development*/
    /**
     * Gets the array of all peasants under this ruler.
     * @return Array containing all peasants owned by this ruler
     */
    public Peasant[] getPeasants(){
        //get the list of all peasants from world
        Peasant[] all = World.getAllPeasants();
        ArrayList<Peasant> ours = new ArrayList<>(0);
        //sort through the list, add every peasant that has this ruler to stack?
        for(Peasant p: all){
            if(p.getRuler() == rulerID){
                ours.add(p);
            }
        }
        //convert ArrayList to array
        Peasant[] returned = ours.toArray(new Peasant[ours.size()]);
        //return array
        return returned;
    }
    
    /**
     * Gets the array of all knights under this ruler.
     * @return Array containing all knights owned by this ruler
     */
    public Knight[] getKnights(){
        //get the list of all knights from world
        Knight[] all = World.getAllKnights();
        ArrayList<Knight> ours = new ArrayList<>();
        //sort through the list, add every knight that has this ruler to stack?
        for(Knight k : all){
            if(k.getRuler() == rulerID)
            ours.add(k);
        }
        //ArrayList converted to an array.
        Knight[] returned = ours.toArray(new Knight[ours.size()]);
        //return array
        return returned;
    }
    
    /**
     * Gets the array of all castles under this ruler.
     * @return Array containing all castles owned by this ruler
     */
    public Castle[] getCastles(){
        //get the list of all castles from world
        Castle[] all = World.getAllCastles();
        ArrayList<Castle> ours = new ArrayList<>();
        
        //sort through the list, add every castles that has this ruler to stack?
        for(Castle i : all){
            if(i.getRuler() == rulerID){
                ours.add(i);
            }
        }
        //convert ArrayList to array
        Castle[] returned = ours.toArray(new Castle[ours.size()]);
        
        
        //return array
        return returned;
    }
    
    
    
    /**
     * Gets the array of all enemy peasants.
     * @return Array containing all peasants owned by other rulers.
     */
    public Peasant[] getOtherPeasants(){
        //get the list of all peasants from world
        Peasant[] all = World.getAllPeasants();
        ArrayList<Peasant> others = new ArrayList<>();
        //sort through the list, add every peasant that has this ruler to stack?
        for(Peasant p: all){
            if(p.getRuler() != rulerID){
                others.add(p);
            }
        }
        //convert ArrayList to array
        Peasant[] returned = others.toArray(new Peasant[others.size()]);
        //return array
        return returned;
    }
    
    /**
     * Gets the array of all enemy knights.
     * @return Array containing all knights owned by other rulers
     */
    public Knight[] getOtherKnights(){
        //get the list of all knights from world
        Knight[] all = World.getAllKnights();
        ArrayList<Knight> others = new ArrayList<>();
        //sort through the list, add every knight that has this ruler to stack?
        for(Knight k : all){
            if(k.getRuler() != rulerID)
            others.add(k);
        }
        //convert ArrayList to array
        Knight[] returned = others.toArray(new Knight[others.size()]);
        //return array
        return returned;
    }
    
    /**
     * Gets the array of all enemy castles.
     * @return Array containing all castles owned by other rulers.
     */
    public Castle[] getOtherCastles(){
        //get the list of all castles from world
        Castle[] all = World.getAllCastles();
        ArrayList<Castle> others = new ArrayList<>();
        
        //sort through the list, add every castles that has this ruler to stack?
        for(Castle i : all){
            if(i.getRuler() != rulerID){
                others.add(i);
            }
        }
        //convert ArrayList to array
        Castle[] returned = others.toArray(new Castle[others.size()]);
        //return array
        return returned;
    }
    
    
    
    
    
    /**
     * Gets this Ruler's number of points.
     * @return The points that the ruler has earned so far.
     */
    public int getPoints(){
        //grab from game or add field in ruler
        return points + World.getLandCount(this.getRulerID()) / 10
                    + this.getCastles().length * 25 + this.getPeasants().length + this.getKnights().length * 2;
    }
    /**
     * Translates an integer direction into a x,y difference. Based on the
     * board's origin at the top left.
     * @param dir The cardinal direction
     * @return The change in x and change in y as an array
     */
    public static int[] translateDir(int dir){
        //create an array with 2 elements, where [1] is x, [2] is y
        int[] xy =new int[2];
        //if the direction involves moving east
        if(dir < 5 && dir >1){
            //set change in x to positive 1
            xy[0] = 1;
        //otherwise, if the direction involves moving west
        }else if(dir > 5 && dir <9){
            //set change in x to -1
            xy[0] = -1;
        }
        //if the direction involves moving north (and not west)
        if(dir >0 && dir<3){
            //set the change in y to -1
            xy[1] = -1;
        //otherwsie, if the direction involves moving northwest    
        }else if(dir == 8){
            //set the change in y to -1
            xy[1] = -1;
        //otherwise if the direction involves moving south
        }else if(dir > 3 && dir <7){
            //set the change in y to positive 1
            xy[1] = 1;
        }
        //return the directional coordinates
        return xy;
    }

    /**
     * Gets the Color variable of this ruler
     * @return The ruler's color.
     */
    public Color getColor() {
        return c;
    }
    /**
     * Sets the Color of this ruler.
     * @param c The ruler's new color.
     */
    public void setColor(Color c) {
        this.c = c;
    }
    /**
     * Returns the profile image of this ruler.
     * @return The profile image of the ruler.
     */
    public BufferedImage getProfileImage() {
        return profileImage;
    }
    /**
     * Sets the ID of this ruler to the given int.
     * @param rulerID The new rulerID.
     */
    public void setRulerID(int rulerID) {
        this.rulerID = rulerID;
    }
    
    /**
     * Compares the rulers by point value. 
     * @param o The object being compared to this ruler
     * @return An integer representing the difference between the points of the objects
     */
    @Override
    public int compareTo(Object o) {
        return -((Integer)(this.getPoints())).compareTo((Integer)((AbstractRuler)o).getPoints());
    }
}
