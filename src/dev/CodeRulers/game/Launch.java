/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.game;

import dev.CodeRulers.SampleAI.DefendBot;
import dev.CodeRulers.SampleAI.BullyBot;
import dev.CodeRulers.SampleAI.RandomBot;
import dev.CodeRulers.SampleAI.SeanZhang;
import dev.CodeRulers.SampleAI.UnknownBot;
import dev.CodeRulers.ruler.AbstractRuler;

/**
 *
 * @author seanzhang
 */
public class Launch {
    public static  void main(String[]args) {
    //creates new game object
    
        //this statement is just for testing's sake.
        AbstractRuler[] r = {new BullyBot(), new UnknownBot(), new RandomBot()};
        
        CodeRulers game = new CodeRulers(r,true);
        //starts the thread
        game.start();
    }
    
}
