/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.window;


import java.net.URL;

/**
 * contains the methods for create<br>
 * window,color,font,and texture for created.<br>
 * @see IGraphicsTech
 * @author Albert Flex
 */
public interface IGraphicsCreate {
    
    public void createWindow(int x,int y,int w,int h,ITexture icon,String title);
    public void createWindow(int w,int h,ITexture icon,String title);
    public void createFullWindow(ITexture icon,String title);
    
    public void setTitle(String title);
    public void setIcon(ITexture texture);    
    
    public void adjustWindow(int x,int y,int w,int h,ITexture newIcon,String newTitle);
    public void adjustWindow(int w,int h,ITexture newIcon,String newTitle);
    
    public void adjustFullWindow(ITexture newIcon,String newTitle);
    public void addAdjustHandler(IWindowAdjustHandler handler);
    
    public void destroyWindow();
    
    public ITexture createTexture(String iconPath);
    public ITexture createTexture(URL url);
    public ITexture createTexture(String iconPath,int cutFromX,int cutFromY,int cutWidth,int cutHeight);
    public IFont createFont(String fontvalue,boolean valueisFile,IFont.FontStyle style,int height);    
    public IColor createColor(int red,int green,int blue,int alpha);
    public IColor createColor(IColor.GeneraColor colortype);
}
