/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.game;

import dev.CodeRulers.display.Display;
import dev.CodeRulers.ruler.AbstractRuler;
import dev.CodeRulers.util.IMAGE;
import dev.CodeRulers.world.World;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * This class is responsible for running the game. 
 * @author Sean Zhang
 */
public class CodeRulers implements Runnable{
    //these two variables dictate the width and height of the window of the game.
    private int width, height;
    
    //this is the name of the window and the file directory of where the logo image is
    private String title="CodeRulers: An AI Program for Noobs", logo="logo.png";
    
    //this is the graphics object that will be used to draw to the screen.
    private Graphics g;
    
    //this boolean states whether this object is running or not.
    private boolean running = false;
    
    //creates a thread variable
    private Thread thread;
    
    //this is the display varaible for the game.
    private Display display;
    
    private AbstractRuler [] r;
    
    private BufferedImage sidePanelImage;
    
    int sidePanelWidth;
    int panelWidth;
    int panelHeight;
    
    
    
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
        
        
    }
    
    
    /**
     * This method initializes all components of the game. This is called in the run method.
     */
    private void init() {
        display = new Display(title, logo);
        //gets the graphics in the JPanel and assings by reference the graphics
        //object there to the graphics object in this class.
        g=display.getPanel().getGraphics();
        
        initGUI();
        
    }
    
    private void initGUI() {
        sidePanelWidth = display.getPanel().getWidth()-display.getPanel().getHeight();
        panelWidth = display.getPanel().getWidth();
        panelHeight = display.getPanel().getHeight();
        
        if(sidePanelImage==null) {
            sidePanelImage = IMAGE.getResizedImage(IMAGE.getBlurredImage(IMAGE.getBufferedImage("src/resources/images/sidePanelImage.jpg"), 20), sidePanelWidth, panelHeight);
        } 
        
        g.drawImage(sidePanelImage,panelWidth-sidePanelWidth,0,null);
        
        
        
        
    }
    
    /**
     * This method updates game variables every frame.
     */
    private void tick() {
        World.tick();
        
    }
    
    
    /**
     * This method draws all of the graphics in the window.
     */
    private void render() {
        g=display.getGraphics();
        display.repaint();
        
        for(int i=0;i<r.length;i++) {
            g.setColor(r[i].getColor());
           
            g.fillRect(panelWidth-sidePanelWidth,40+i*panelHeight/10+(i*40), sidePanelWidth, panelHeight/10);

             g.drawImage(IMAGE.getResizedImage(r[i].getProfileImage(),panelHeight/10-12,panelHeight/10-12), panelWidth-sidePanelWidth+7,47+i*panelHeight/10+(i*40) , null);
            
        }
        
        World.render(g);
        
        Font f = new Font("Myriad", Font.BOLD, 16);
        g.setFont(f);
        g.drawString("sean xhang", 25, 25);
        //g.drawRect(10, 20, 20, 20);
        g.dispose();
    }
    
    @Override
    public void run() {
        //calls the init method which creates the display object
        init();
        
        //amount of times you want to call the tick and render method
        //per second
        int fps = 18;
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
        int ticks=0;
        
        //int count just sees how many seconds the program has run
        int count=0;
        
        //This is our Basic Game Loop :)
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
                //resfreshes the images 
                if(count%2==0) {
                    initGUI();
                }
            }
            
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
     * Gives the user the width of the game window
     * @return the width of the JFrame
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gives the height dimension of the game window
     * @return the height of the JFrame
     */
    public int getHeight() {
        return height;
    }
    
}
