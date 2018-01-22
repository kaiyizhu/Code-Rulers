/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.world;

import dev.CodeRulers.entity.Castle;
import dev.CodeRulers.entity.Entity;
import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import dev.CodeRulers.game.CodeRulers;
import dev.CodeRulers.util.IMAGE;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author seanzhang
 */
public class World {

    //image icons for the entities on the board
    private static BufferedImage peasantIcn;
    private static BufferedImage knightIcn;
    private static BufferedImage castleIcn;

    //image for the background of the world
    private static BufferedImage worldMap;

    public World(CodeRulers r) {
        knights = new Knight[0];
        peasants = new Peasant[0];
        castles = new Castle[0];

        landOwned = new int[64][64];

        this.r = r;

        initGame();
    }

    private static void initGame() {
        for (int i = 0; i < landOwned.length; i++) {
            Arrays.fill(landOwned[i], -1);
        }
        Random rand = new Random();
        knights = Arrays.copyOf(knights, 10 * r.getRulerArray().length);
        peasants = Arrays.copyOf(peasants, 10 * r.getRulerArray().length);
        castles = Arrays.copyOf(castles, r.getRulerArray().length);
        int count = 0;
        for (int i = 0; i < r.getRulerArray().length; i++) {
            for (int j = 0; j < 10; j++) {
                int rndX = rand.nextInt(64), rndY = rand.nextInt(64);
                while (landOwned[rndX][rndY] != -1) {
                    rndX = rand.nextInt(64);
                    rndY = rand.nextInt(64);
                }
                peasants[count] = new Peasant(rndX, rndY, i);
                landOwned[peasants[count].getX()][peasants[count].getY()] = i;
                while (landOwned[rndX][rndY] != -1) {
                    rndX = rand.nextInt(64);
                    rndY = rand.nextInt(64);
                }
                knights[count] = new Knight(rndX, rndY, i);
                landOwned[knights[count].getX()][knights[count].getY()] = i;
                count++;
            }
            int rndX = rand.nextInt(64), rndY = rand.nextInt(64);
            while (landOwned[rndX][rndY] != -1) {
                rndX = rand.nextInt(64);
                rndY = rand.nextInt(64);
            }
            castles[i] = new Castle(rndX, rndY, i);
            landOwned[castles[i].getX()][castles[i].getY()] = i;
        }

    }

    //the game object
    private static CodeRulers r;

    //these are all the knights that exist in the game.
    private static Knight[] knights;
    //these are all the peasants that exist in the game.
    private static Peasant[] peasants;
    //these are all the castles that exist in the game.
    private static Castle[] castles;

    //these are all the land owned by the ruler.
    private static int[][] landOwned;

    /**
     * Get method that gets the 2D map of which ruler owns which tile.
     *
     * @return a 2D array containing data of the 64x64 tiles on the map.
     */
    public static int[][] getLandOwned() {
        return landOwned;
    }

    public static void setKnights(Knight[] knights) {
        World.knights = knights;
    }

    public static void setPeasants(Peasant[] peasants) {
        World.peasants = peasants;
    }

    public static Knight[] getAllKnights() {
        return knights;
    }

    public static Peasant[] getAllPeasants() {
        return peasants;
    }

    public static Castle[] getAllCastles() {
        return castles;
    }

    public static Entity getEntityAt(int x, int y) {
        if (x > 63 || y > 63 || x < 0 || y < 0) {
            System.out.println("Coordinates out of bounds.");
            return null;
        }

        for (int i = 0; i < knights.length; i++) {
            if (knights[i].getX() == x && knights[i].getY() == y) {
                return knights[i];
            }
        }

        for (int i = 0; i < peasants.length; i++) {
            if (peasants[i].getX() == x && peasants[i].getY() == y) {
                return peasants[i];
            }
        }

        for (int i = 0; i < castles.length; i++) {
            if (castles[i].getX() == x && castles[i].getY() == y) {
                return castles[i];
            }
        }

        System.out.println("No Entity Found.");
        return null;
    }

    public static int getLandOwner(int x, int y) {
        return landOwned[x][y];
    }

    public static void render(Graphics g, CodeRulers r) {
        if (worldMap == null) {
            worldMap = IMAGE.getResizedImage(IMAGE.getBufferedImage("src/resources/images/codeRulersTerrain.png"), 768, 768);
        }

        g.drawImage(worldMap, 0, 0, null);

        for (int i = 0; i < landOwned[0].length; i++) {
            for (int j = 0; j < landOwned.length; j++) {
                if (landOwned[i][j] == -1) {

                } else {
                    Color c = r.getRulerArray()[landOwned[i][j]].getColor();
                    g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
                    g.fillRect(i * 12, j * 12, 12, 12);
                }

            }
        }

        for (Peasant p : peasants) {
            p.drawEntity(g);
        }

        for (Knight k : knights) {
            k.drawEntity(g);
        }

        for (Castle c : castles) {
            c.drawEntity(g);
        }

    }

    /**
     * Gets the number of tiles owned by the ruler with the given ID.
     *
     * @return The amount of land owned by this ruler
     * @param rulerID the ID of the ruler who's count you need
     */
    public static int getLandCount(int rulerID) {
        //initailize an integer to store the number of tiles
        int count = 0;
        //sort through it, increment integer with every tile that has this ID
        for (int[] i : landOwned) {
            for (int j : i) {
                if (j == rulerID) {
                    count++;
                }
            }
        }
        return count;
    }
}
