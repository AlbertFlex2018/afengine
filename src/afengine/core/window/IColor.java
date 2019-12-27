/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.window;

/**
 *
 * @author Admin
 */
public interface IColor {
    public static enum GeneraColor{
        RED,GREEN,BLUE,
        YELLOW,GREY,ORANGE,
        WHITE,BLACK,PINK,
    }
    
    public int getRed();
    public int getGreen();
    public int getBlue();
    public int getAlpha();
    public IColor getAntiColor();
}
