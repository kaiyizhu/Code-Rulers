/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.game;

import dev.CodeRulers.SampleAI.*;
import dev.CodeRulers.ruler.AbstractRuler;

/**
 *
 * @author seanz
 */
public class Launch {
    public static  void main(String[]args) {
    //creates new game object
    
        //this statement is just for testing's sake.
        AbstractRuler[] r = {new SeanZhang()};
        
        CodeRulers game = new CodeRulers(r,false);
        //starts the thread
        game.start();
        
        
       
    }
    
}
