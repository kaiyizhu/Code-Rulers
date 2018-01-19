/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.game;

/**
 *
 * @author seanz
 */
public class Launch {
    public static  void main(String[]args) {
    //creates new game object with parameters
        //1. Window name 2.Width (in px)  3.Height (in px)
        //4. Logo file name (path is handled automatically if you 
        //   put it in the right folder)
        CodeRulers game = new CodeRulers({new RandomBot()});
        //starts the thread
        game.start();
        
        
       
    }
    
}
