/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.game;

//Import statements
import dev.CodeRulers.display.Display;
import dev.CodeRulers.entity.*;
import dev.CodeRulers.ruler.AbstractRuler;
import dev.CodeRulers.world.World;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.Timer;

/**
 * This class is responsible for running the game. 
 * @author Sean Zhang
 */
public class CodeRulers implements Runnable{
    
    //this is the name of the window and the file directory of where the logo image is
    private String title="CodeRulers: An AI Program for Noobs", logo="logo.png";
    
    //this boolean states whether this object is running or not.
    private boolean running = false;
    
    //creates a thread variable
    private Thread thread;
    
    
    
    //this is the display varaible for the game.
    private Display display;
    
    private AbstractRuler [] r;

    private World w;
    
    /**
     * The constructor for the CodeRulers Class.
     * @param r - Pass in an array of abstractRulers that the user wants to input into the game.
     * @param graphics Whether the game opens the GUI or runs in the console.
     */
    public CodeRulers(AbstractRuler[] r, boolean graphics) {
        //initialization confirmation
        System.out.println("CodeRulers Initialized.");
        
        //makes a copy of the abstract rulers that was passed in as an argument.
        //This is stored in the AbstractRuler array, r.
        this.r = Arrays.copyOf(r, r.length);
        
        //sets a ruler ID unique to every ruler.
        int count=0;
        for(AbstractRuler ruler : this.r) {
            ruler.setRulerID(count);
            count++;
        }
        
        //creates a new world object. 
        w = new World(this);
        
        if(graphics){
            //creates a new JFrame display to display all the graphics in the game.
            display = new Display(title, logo, this);
        }else{
            Timer t= new Timer(10, new TimerListener());
            t.start();
        }
        
        
        
    }
    
    @Override
    public void run() {
        //the "game" loop (even though it is empty)
        while(running) {
           
        }
        
        //even though the thread is stopped when the stop method
        //is called (which exits the loop)
        //we will call another stop method just in case the while loop
        //exits by itself somehow....
        stop();
    }
    
    /**
     * This method will be responsible for starting the thread.
     */
    public synchronized void start() {
        //ensures that there is no thread running at the
        //moment. If there is, it will result in another new thread
        //which is not good.
        if(running) {return;}
        //sets running to true so that it can run the "game loop"
        //(tick and render)
        running=true;
        //creates new thread (because you dont have a thread yet)
        thread = new Thread(this);
        //starts the thread.
        thread.start();

    }
    
    /**
     * This method will be responsible for stopping the thread.
     */
    public synchronized void stop() {
        //if the thread is not running, we do not want to stop it again
        //so we exit the loop if the boolean is false
        if(!running) {return;}
        //sets running to false
        running = false;
        try {
            //this waits for the thread to die:
            //AKA when the run method is complete/returns
            thread.join();
        } catch (InterruptedException ex) {
            //prints the exception
            ex.printStackTrace();
        }
    }
    
    /**
     * Calculates the number of points the ruler has.
     */
    public void endGameNoDisplay(){
        //create an array to represent final points
        int[] finalPoints = new int[r.length];
        //for every ruler
        for(int i=0; i<r.length;i++){
            //calculate the final points at that index
            finalPoints[i] = r[i].getPoints() + 
                    World.getLandCount(r[i].getRulerID()) / 10 + r[i].getCastles().length * 25 
                    + r[i].getPeasants().length + r[i].getKnights().length * 2;
        }
        //for every ruler (every place in standings)
        for(int i=0; i<r.length; i++){
            //initailize a highest points and index of highest points
            int highest= 0;
            int highIndex = 0;
            //for all the rulers
            for(int j=0; j<r.length;j++){
                //if this ruler has more points than the last
                if(finalPoints[j] > highest){
                    //set highest and highest index accordingly
                    highest = finalPoints[j];
                    highIndex = j;
                }
            }
            //print out the place that the ruler cam in, along with their points
            System.out.println(i + ". " + r[highIndex].getRulerName() + " with"
                    + highest + " points.");
            //set their final points to 0 (so that it doesn't get printed out)
            finalPoints[highIndex] = 0;
        }
    }
    /**
     * This method provides access to the array of rulers in the game.
     * @return the array of ruler objects in the game.
     */
    public AbstractRuler[] getRulerArray() {
        return r;
    }
    
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //get the array of all castles
            Castle[] allC = World.getAllCastles();
            //for every castle
            for(Castle c : allC){
                //call its production
                c.produce();
            }
            //get the array of all peasants
            Peasant[] allP = World.getAllPeasants();
            //for all peasants
            for(Peasant p : allP){
                //give them actions to use this turn
                p.setAction(true);
            }
            //get the array of all knights
            Knight[] allK = World.getAllKnights();
            //for every knight
            for(Knight k : allK){
                //give them an action for this turn
                k.setAction(true);
            }
            //This updates all the things related to the rulers. Once updated,
            //the graphics can then be updated.

            //This method is typically called once per cycle.
            for (AbstractRuler ruler : r) {
                ruler.orderSubjects();
            }
        }

    }
}
