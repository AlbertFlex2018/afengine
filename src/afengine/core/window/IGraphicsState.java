/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.window;

/**
 *
 * @author Albert Flex
 */
public interface IGraphicsState {
    
    public String getRenderName();
    public IFont getFont();
    public void  setFont(IFont font);
    public IColor getColor();
    public void setColor(IColor color);
    
    public int getMoniterWidth();
    public int getMoniterHeight();
    public int getDisplayX();
    public boolean isFullWindow();
    public int getDisplayY();
    public int getWindowWidth();
    public int getWindowHeight();
    public int getFPS();
    public String getTitle();
    public ITexture getIcon();
    
    public void rotate(double rx,double ry,double rz);
    public void scale(double sx,double sy,double sz);
    public void translate(double x,double y,double z);

    public Object[] getValue(String name);
    public void   setValue(String name,Object obj[]);
        
    public ITexture getMouseIcon();
    public void     setMouseIcon(ITexture texture);
}
