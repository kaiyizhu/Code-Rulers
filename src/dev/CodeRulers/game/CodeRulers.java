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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * This class is responsible for running the game.
 *
 * @author Sean Zhang
 */
public class CodeRulers implements Runnable {
    //this is the amount of turns a player makes before the game ends.
    private final int turnLimit=5000;
    
    //this is the name of the window and the file directory of where the logo image is
    private String title = "CodeRulers: An AI Program for Noobs", logo = "logo.png";

    //this boolean states whether this object is running or not.
    private boolean running = false;

    //this indicates whether the game has ended or not.
    private boolean gameEnd = false;

    //this is the time/turn counter that will be used to determine when the game
    //ends.
    private static int turnCount = 0;

    //creates a thread variable
    private Thread thread;

    //this timer is not our game loop. Instead, it controls the speed of the game (turns/second)
    Timer t;

    //this is the display varaible for the game.
    private Display display;

    private AbstractRuler[] r;

    private World w;

    private Boolean stopMessages = false;

    /**
     * The constructor for the CodeRulers Class.
     *
     * @param r - Pass in an array of abstractRulers that the user wants to
     * input into the game.
     * @param graphics Whether the game opens the GUI or runs in the console.
     */
    public CodeRulers(AbstractRuler[] r, boolean graphics) {
        //initialization confirmation
        System.out.println("CodeRulers Initialized.");

        //makes a copy of the abstract rulers that was passed in as an argument.
        //This is stored in the AbstractRuler array, r.
        this.r = Arrays.copyOf(r, r.length);

        //sets a ruler ID unique to every ruler.
        int count = 0;
        for (AbstractRuler ruler : this.r) {
            ruler.setRulerID(count);
            count++;
        }

        //creates a new world object. 
        w = new World(this);

        //creates a new timer object that loops every 10 miliseconds.
        t = new Timer(10, new TimerListener());

        if (graphics) {
            //creates a new JFrame display to display all the graphics in the game.
            display = new Display(title, logo, this);
        } else {
            //start simulation
            t = new Timer(1, new TimerListener());
            t.start();
        }

    }

    @Override
    public void run() {
        //this will keep our game alive.
        while (running) {
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
        if (running) {
            return;
        }
        //sets running to true so that it can run the "game loop"
        //(tick and render)
        running = true;
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
        if (!running) {
            return;
        }
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
    public void endGameNoDisplay() {
        //create an array to represent final points
        int[] finalPoints = new int[r.length];
        //for every ruler
        for (int i = 0; i < r.length; i++) {
            //calculate the final points at that index
            finalPoints[i] = r[i].getPoints()
                    + World.getLandCount(r[i].getRulerID()) / 10 + r[i].getCastles().length * 25
                    + r[i].getPeasants().length + r[i].getKnights().length * 2;
        }
        //for every ruler (every place in standings)
        for (int i = 0; i < r.length; i++) {
            //initailize a highest points and index of highest points
            int highest = 0;
            int highIndex = 0;
            //for all the rulers
            for (int j = 0; j < r.length; j++) {
                //if this ruler has more points than the last
                if (finalPoints[j] > highest) {
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
     *
     * @return the array of ruler objects in the game.
     */
    public AbstractRuler[] getRulerArray() {
        return r;
    }

    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!gameEnd) {
                //get the array of all castles
                Castle[] allC = World.getAllCastles();
                //for every castle
                for (Castle c : allC) {
                    //call its production
                    c.produce();
                }
                //get the array of all peasants
                Peasant[] allP = World.getAllPeasants();
                //for all peasants
                for (Peasant p : allP) {
                    //give them actions to use this turn
                    p.setAction(true);
                }
                //get the array of all knights
                Knight[] allK = World.getAllKnights();
                //for every knight
                for (Knight k : allK) {
                    //give them an action for this turn
                    k.setAction(true);
                }
                //This updates all the things related to the rulers. Once updated,
                //the graphics can then be updated.

                //This method is typically called once per cycle.
                for (AbstractRuler ruler : r) {
                    try {
                        ruler.orderSubjects();
                    } catch (Exception ex) {
                        if (!stopMessages) {
                            int confirmed = JOptionPane.showConfirmDialog(null,
                                    ruler.getRulerName() + " committed a game-breaking error! Exit game?", "Error Message",
                                    JOptionPane.YES_NO_CANCEL_OPTION);

                            if (confirmed == JOptionPane.YES_OPTION) {
                                System.exit(0);
                            } else if (confirmed == JOptionPane.NO_OPTION) {
                                stopMessages = true;
                            }
                        }
                    }
                }

                if (display != null) {
                    display.getPanel().repaint();
                }

                turnCount++;

                if (turnCount > turnLimit) {
                    t.stop();
                    gameEnd = true;
                    System.out.println("Game End");
                    AbstractRuler[] rulerArr = Arrays.copyOf(r,r.length);
                    Arrays.sort(rulerArr);
                    System.out.println("LEADERBOARD:");
                    for(int i =0;i<rulerArr.length;i++) {
                        try {Thread.sleep(1000);} catch (InterruptedException ex) {}
                        
                        System.out.println(i+1+". "+rulerArr[i].getRulerName() +": "+rulerArr[i].getPoints() + " points.");
                    }
                    
                    if(display==null) {
                        System.exit(0);
                    }
                    
                }

            }

        }

    }

    public Timer getTimer() {
        return t;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public static int getTurnCount() {
        return turnCount;
    }

}
