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
public interface IWindowAdjustHandler { 
    public void handleWindowAdjust(IGraphicsTech create,boolean oldfull,int oldx,int oldy,int oldwidth,int oldheight,
                                        boolean newfull,int newx,int newy,int newwidth,int newheight);
}

