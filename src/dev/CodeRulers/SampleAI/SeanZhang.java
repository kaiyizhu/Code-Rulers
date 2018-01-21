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
 *
 * @author seanz
 */
public class SeanZhang extends AbstractRuler {

    @Override
    public void orderSubjects() {
        for (Knight knight : getKnights()) {
            knight.move(1);
        }
        
        for (Peasant peasant : getPeasants()) {
            peasant.move(1);
        }
        
        
    }

    @Override
    public void initialize() {
        System.out.println("Ruler Sean Zhang been initialized");
        
        //some secret methods :) Just for testing
        profileURL =("https://avatars1.githubusercontent.com/u/20467017?s=460&v=4");
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
