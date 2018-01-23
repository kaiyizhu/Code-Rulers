/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import dev.CodeRulers.ruler.AbstractRuler;
import java.awt.Color;

/**
 * I am using this class for graphical testing.
 * 
 * @author Kaiyi Zhu
 */
public class AttackBot extends AbstractRuler {

    @Override
    public void orderSubjects() {
        
        //just for testing... These just move the objects north.
        for (Knight knight : this.getKnights()) {
            this.getOtherPeasants();
            move(knight, 5);
            capture(knight, 5);
        }
        
        for (Peasant peasant : getPeasants()) {
            move(peasant, 5); 
        }
        
        
    }

    @Override
    public void initialize() {
        //Message telling the user that this object has been intialized.
        System.out.println("Ruler AttackBot been initialized");
        
        //this sets the profileURL of this AI. All you have to do is to provide
        //an internet link to the image.
        profileURL =("http://i.dailymail.co.uk/i/pix/2017/04/20/13/3F6B966D00000578-4428630-image-m-80_1492690622006.jpg");
        
        //this is the preferred color for my AI. This color will be the main 
        //color scheme displayed in the GUI for this AI.
        setColor(new Color(255,255,0)); 
    }

    @Override
    public String getSchoolName() {
        return "Newmarket High School";
    }

    @Override
    public String getRulerName() {
        return "Attack Bot";
    }
    
    
}
