/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.display;


/**
 *
 * @author seanz
 */
public class Handler {
    private CodeRulers game;
    private World world;
    
    public Handler(Game game) {
        this.game=game;
    }
    
    //getters and setters
    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }
    
    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }
    
    public int getWidth() {
        return game.getWidth();
    }
    
    public int getHeight() {
        return game.getHeight();
    }
    
    //getters and setters
    public CodeRulers getGame() {
        return game;
    }

    public void setGame(CodeRulers game) {
        this.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
