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
public interface ITexture {
    
    public String getTexturePath();
    public int getWidth();
    public int getHeight();
    public ITexture getScaleInstance(double sx,double sy);
    public ITexture getCutInstance(int x,int y,int w,int h);
}
