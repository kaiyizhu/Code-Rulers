/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.display;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * This class is responsible for running the game. 
 * @author Sean Zhang
 */
public class CodeRulers implements Runnable{
    //these two variables dictate the width and height of the window of the game.
    private int width, height;
    
    //this is the name of the window and the file directory of where the logo image is
    private String title="Code Rulers", logo;
    
    //this is the graphics object that will be used to draw to the screen.
    private Graphics g;
    
    //this boolean states whether this object is running or not.
    private boolean running = false;
    
    //creates thread object
    private Thread thread;
    private Display display;
    
    //a buffer is something that holds the same data as ur 
    //computer screen
    //buffers prevent flickering
    private BufferStrategy bs;
    
    /**
     * This method initializes all components of the game. 
     */
    public void init() {
        //gets the graphics in the JPanel and assings by reference the graphics
        //object there to the graphics object in this class.
        g=display.getPanel().getGraphics();
    }
    
    /**
     * This method updates game variables every frame.
     */
    private void tick() {
        /*
        keyManager.tick();
        
        if(State.getState()!=null) {
            State.getState().tick();
        }
        */
    }
    
    /**
     * This method draws all of the graphics in the window.
     */
    private void render() {
        //as long as there is a state active, it will draw whatever is in that state by
        //calling the render method in state.
        if(State.getState()!=null) {
            State.getState().render(g);
        }

        
        /*
        //properly disposes and releases system resources
        //it is using. This will prevent any mishaps
        g.dispose();
        */
    }
    
    @Override
    public void run() {
        //calls the init method which creates the display object
        init();
        
        //amount of times you want to call the tick and render method
        //per second
        int fps = 60;
        //maximum amount of time we have to exectute the tick and render
        //method in nanoseconds <-- 
        double timePerTick=1000000000/fps;
        //amount of time we have until we have to call the tick and 
        //render methods again
        double delta =0;
        //this is the current time of our computer
        long now;
        //returns system time in nanoseconds
        long lastTime=System.nanoTime(); 
        //time until we get to 1 sec
        long timer=0;
        //
        int ticks=0;
        
        //gameLoop
        //loops while running. 
        while(running) {
            //sets the now time variable to the current system time.
            now=System.nanoTime();
            //defines when and when not to call the gameLoop methods
            delta+=(now-lastTime)/timePerTick;
            //adds amount of time since we last executed this block of code
            timer+= now-lastTime;
            //sets the last time the block of code was run
            lastTime=now;
            
            //if delta is greater than one, then execute 
            //the tick and render methods.
            if(delta >=1) {
                tick();
                render();
                //increments tick by one to rep
                //that we ticked
                ticks++;
                //removes one from delta since we ticked 
                //once.
                delta--;
            }
            
            //if the timer is greater than one second
            if(timer>= 1000000000) {
                //prints how many times the computer ticked in that second
                System.out.println("Ticks+Frames: " + ticks);
                //resets tick and timer
                ticks=0;
                timer=0;
            }
            
        }
        //even though the thread is stopped when the stop method
        //is called (which exits the loop)
        //we will call another stop method just in case the while loop
        //exits by itself somehow....
        stop();
    }
    
    //method that starts the thread
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
    
    //stops the thread
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
    
}
