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
 * @author Sean Zhang
 */
public class SeanZhang extends AbstractRuler {

    @Override
    public void orderSubjects() {
        
        //just for testing... These just move the objects north.
        for (Knight knight : this.getKnights()) {
            move(knight, 5);
            capture(knight, 5);
        }
        
        for (Peasant peasant : getPeasants()) {
            move(peasant, 1);
        }
        
        
    }

    @Override
    public void initialize() {
        //Message telling the user that this object has been intialized.
        System.out.println("Ruler Sean Zhang been initialized");
        
        //this sets the profileURL of this AI. All you have to do is to provide
        //an internet link to the image.
        profileURL =("https://avatars1.githubusercontent.com/u/20467017?s=460&v=4");
        
        //this is the preferred color for my AI. This color will be the main 
        //color scheme displayed in the GUI for this AI.
        setColor(new Color(139, 91, 183,178));
    }

    @Override
    public String getSchoolName() {
        return "Newmarket High School";
    }

    @Override
    public String getRulerName() {
        return "Sean Zhang";
    }
    
    
}
