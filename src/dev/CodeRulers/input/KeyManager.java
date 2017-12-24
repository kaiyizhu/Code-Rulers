package dev.CodeRulers.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Sean Zhang
 */

public class KeyManager implements KeyListener{
    //boolean array for which key was pressed (true)
    //or not pressed (false)
    private boolean[] keys;
    
    //these will be the basic movement booleans that we will access
    //in the player class 
    public boolean up,down,left,right;
    
    //constructor initialises array to have a capacity of 256
    public KeyManager() {
        keys=new boolean[256];
    }
    
    //the tick method. Assigns boolean values from keys array (which values are
    //modified in the keyPressed and keyRelased methods to directional boolean values
    //this helps abstract the keyPressed keyEvents into variables that are easier to
    //access and understand
    
    /**
     * This method assigns boolean values from keys array (which values are modified
     * in the keyPressed and keyReleased methods) to boolean variables that 
     */
    public void tick() {
        up=keys[KeyEvent.VK_W];
        down=keys[KeyEvent.VK_S];
        left=keys[KeyEvent.VK_A];
        right=keys[KeyEvent.VK_D];
    }
    
    //overrides the abstract method found in the keyListener class that the
    //keyManager implements.
    @Override
    public void keyTyped(KeyEvent e) {
        //noting for now
    } 
    
    //overrides the abstact method found in the keyListener class that the 
    //keyManager implements
    @Override
    public void keyPressed(KeyEvent e) {
        //gets the keyCode of the key pressed and assigns
        //the corresponding index of the boolean array to true,
        //since that key was pressed.
        keys[e.getKeyCode()] = true;
    }

    //overrides the abstract method found in the keyListener class that the
    //keyManager implements
    @Override
    public void keyReleased(KeyEvent e) {
        //gets the keyCode of the key pressed and assigns
        //the corresponding index of the boolean array to false,
        //since that key was pressed.
        keys[e.getKeyCode()] = false;
    }
    
}
