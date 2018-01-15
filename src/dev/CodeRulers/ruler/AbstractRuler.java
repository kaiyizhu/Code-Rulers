/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.ruler;
import dev.CodeRulers.entity.*;
/**
 *
 * @author Luke
 */
public abstract class AbstractRuler {
    
/*Fields and features of the Ruler class*/
    //name of the school/team who made this ruler
    private String schoolName;
    //name of this ruler (given by students)
    private String rulerName;
    //integer used to identify this ruler in game(roughly equivalent to player number)
    private final int rulerID; //not sure about final or not
    
    /**
     * Initializes a new Abstract Ruler, based upon the implementation of the
     * initialize() method within implementing classes.
     * @param rulerID number by which this ruler is identified
     */
    public AbstractRuler(int rulerID){
        this.rulerID = rulerID;
        initialize();
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
        //test to see if it has movement & is not a castle
        //test to see whether the space is empty
        //if so, call its move method in the given direction
        //see if this movement claimed land
    }
    /**
     * Commands a knight to attack the specified subject. If the subject is more
     * than 1 block away, nothing will happen.
     * @param attacking The knight under this ruler's command
     * @param attacked The entity which is to be attacked
     */
    public void capture(Knight attacking, Entity attacked){
        //check that this knight can attack
        //check that the attacked is within 1 space of this knight
        //check that it is not one of this ruler's subjects
        //call the attack
    }
    /**
     * Commands a knight to attack whatever is in the given direction.
     * If no suitable target exists, nothing will happen.
     * @param attacking The knight under this ruler's command
     * @param dir the cardinal direction to attack in
     */
    public void capture(Knight attacking, int dir){
        //check that this knight can attack
        //see if anything is in that space
        //check that it is not one of this ruler's subjects
        //call the attack
    }
    /**
     * Sets the castle to start creating knights instead of peasants.
     * @param commanded The castle which being told to create knights
     */
    public void createKnights(Castle commanded){
        //tell the castle to create knights
    }
    /**
     * Sets the castle to start creating peasants instead of knights.
     * @param commanded The castle which being told to create knights
     */
    public void createPeasants(Castle commanded){
        //tell the castle to create peasants
    }
    /**
     * Gets the name of the school, team, or individual who created this Ruler AI
     * @return The given name of the developers of the AI
     */
    public String getSchoolName() {
        return schoolName;
    }
    /**
     * Gets the name given to this Ruler, as determined by its developers. 
     * This name may be used to describe the strategy that the AI employs 
     * (such as all Knights), or may be completely unrelated (Kaiser Wilhelm II)
     * @return The name of the Ruler, as decided by its developers
     */
    public String getRulerName() {
        return rulerName;
    }
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
        //sort through the list, add every peasant that has this ruler to stack?
        //convert stack to array
        //return array
        return null;
    }
    
    /**
     * Gets the array of all knights under this ruler.
     * @return Array containing all knights owned by this ruler
     */
    public Knight[] getKnights(){
        //get the list of all knights from world
        //sort through the list, add every knight that has this ruler to stack?
        //convert stack to array?
        //return array
        return null;
    }
    
    /**
     * Gets the array of all castles under this ruler.
     * @return Array containing all castles owned by this ruler
     */
    public Castle[] getCastles(){
        //get the list of all castles from world
        //sort through the list, add every castles that has this ruler to stack?
        //convert stack to array?
        //return array
        return null;
    }
    
    /**
     * Gets the number of tiles owned by this ruler
     * @return The amount of land owned by this ruler
     */
    public int LandCount(){
        //get the 2d int[] from the world
        //sort through it, increment integer with every tile that has this ID
        return 0;
    }
    
    /**
     * Gets this Ruler's number of points.
     * @return The points that the ruler has earned so far.
     */
    public int getPoints(){
        //grab from game or add field in ruler
        return 0;
    }
}
