/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.window;

/**
 * contains a method for draw,<br>
 * after instanced a IGraphicsTech,instanced a IDrawStrategy,<br>
 * and you should set the drawstrategy for IGraphicsTech<br>
 * use graphicstech.setDrawStrategy(strategy).<br>
 * @see IGraphicsTech
 * @author Albert Flex
 */
public interface IDrawStrategy {
    
    public void draw(IGraphicsTech tech);
}

