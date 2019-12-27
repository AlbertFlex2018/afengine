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
public interface IFont {
    
    public static enum FontStyle{
        PLAIN,
        BOLD,
        ITALIC,        
    }
        
    public String getFontPath();
    public String getFontName();
    public FontStyle    getFontStyle();    
    public int    getFontHeight();
    public int    getFontWidth(String text);    
}