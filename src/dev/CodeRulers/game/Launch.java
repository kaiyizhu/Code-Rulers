/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.game;

import dev.CodeRulers.SampleAI.BullyBot;
import dev.CodeRulers.SampleAI.RandomBot;
import dev.CodeRulers.SampleAI.SeanZhang;
import dev.CodeRulers.SampleAI.UnknownBot;
import dev.CodeRulers.ruler.AbstractRuler;

/**
 *
 * @author seanz
 */
public class Launch {
    public static  void main(String[]args) {
    //creates new game object
    
        //this statement is just for testing's sake.
        AbstractRuler[] r = {new SeanZhang(),new RandomBot(), new UnknownBot()};
        
        CodeRulers game = new CodeRulers(r);
        //starts the thread
        game.start();
        
        
       
    }
    
}
