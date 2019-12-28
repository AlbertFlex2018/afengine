/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.window;

/**
 * contains the methods for draw<br>
 * when you in a loop method.<br>
 * you should set drawstrategy for draw something<br>
 * operate for draw is following:<br>
 * get graphicstech instance,set drawstrategy<br>
 *  graphicstech.beginDraw<br>
 *  graphicstech.getDrawstrategy().draw(graphicstech)<br>
 *  graphicstech.endDraw<br>
 * @see IGraphicsTech
 * @author Albert Flex
 */
public interface IGraphicsDraw {
    
    public boolean isDrawing();
    public void beginDraw();
    public void endDraw();    
    public IDrawStrategy getDrawStrategy();
    public void setDrawStrategy(IDrawStrategy strategy);
    
    public void clear(int x,int y,int width,int height,IColor color);
    public void drawPoint(float x,float y);
    public void drawLine(float x1,float y1,float x2,float y2);
    public void drawPolygon(float[] x,float[] y,boolean fill);
    public void drawOval(float x,float y,float w,float h,boolean fill);    
    public void drawCircle(float x,float y,float radius,boolean fill);
    
    public void drawTexture(float x,float y,ITexture texture);
    public void drawText(float x,float y,IFont font,IColor color,String text);
    public void drawTexts(float[] x,float[] y,IFont[] font,IColor[] color,String[] text);
    
    //use this draw for extensions.
    public void drawOther(String drawType,Object[] value); 
}
