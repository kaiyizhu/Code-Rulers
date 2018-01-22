/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.game;

//Import statements
import dev.CodeRulers.display.Display;
import dev.CodeRulers.ruler.AbstractRuler;
import dev.CodeRulers.world.World;
import java.util.Arrays;

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
     */
    public CodeRulers(AbstractRuler[] r) {
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
        
        //creates a new JFrame display to display all the graphics in the game.
        display = new Display(title, logo, this);
        
        //creates a new world object. 
        w = new World(this);
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
     * This method provides access to the array of rulers in the game.
     * @return the array of ruler objects in the game.
     */
    public AbstractRuler[] getRulerArray() {
        return r;
    }
}
