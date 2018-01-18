/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.display;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author seanz
 */
public class Display extends JFrame {
    
    //this is the logo for the game
    private ImageIcon logo;
    
    //Jframe variables:
    private String title; //title of the jframe
    private int width,height; //width and height of jframe
    
    public JPanel getPanel() {
        return mapPanel;
    }

    
    /**
     * The Constructor for the Display Class. This will contain all of the
     * variables responsible for the window of the game.
     * @param title the title of the program/JFrame. This title will be shown as the name of the program when the program is run.
     * @param width this is the width of the program window.
     * @param height this is the height of the program window.
     * @param imageName this is the directory where the logo image is stored.
     */
    public Display() {
        System.out.println("hi");
        logo = new ImageIcon(getClass().getResource("/resources/logo/logo.png"));
        
        initComponents();
        this.setVisible(true);
        this.setIconImage(logo.getImage());
        this.setTitle("CodeRulers - An AI Program For Noobs");
    }
    
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startPanel = new javax.swing.JPanel();
        mapPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout startPanelLayout = new javax.swing.GroupLayout(startPanel);
        startPanel.setLayout(startPanelLayout);
        startPanelLayout.setHorizontalGroup(
            startPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        startPanelLayout.setVerticalGroup(
            startPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        getContentPane().add(startPanel, "card3");

        mapPanel.setBackground(new java.awt.Color(204, 255, 204));
        mapPanel.setPreferredSize(new java.awt.Dimension(768, 768));

        javax.swing.GroupLayout mapPanelLayout = new javax.swing.GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        getContentPane().add(mapPanel, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mapPanel;
    private javax.swing.JPanel startPanel;
    // End of variables declaration//GEN-END:variables
}
