/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.state;

import java.awt.Graphics;

/**
 *
 * @author Sean Zhang
 */

//state class. This is a blueprint for other states (subclasses of the state class)
public abstract class State {
    //store current state object
    private static State currentState=null;
    
    //set method for state
    public static void setState(State state) {
        currentState=state;
    }
    //get method for state
    public static State getState() {
        return currentState;
    }
    
    //protected varaible handler object. This will be inherited in subclasses.
    protected Handler handler;
    
    //constructor takes in game object and assigns it to local varaible 
    //game.
    public State(Handler handler) {
        //sets handler to the parameter handler
        this.handler = handler;
    }
    
    //abstact methods. will be implemented in subclasses.
    public abstract void tick();
    
    //abstact methods. will be implemented in subclasses.
    public abstract void render(Graphics g);
    
}