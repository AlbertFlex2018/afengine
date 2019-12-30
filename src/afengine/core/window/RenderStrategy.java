/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.window;

import afengine.core.util.Debug;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * contains internal drawstrategy for windowapp use.<br>
 * @see IDrawStrategy
 * @author Albert Flex
 */
public class RenderStrategy implements IDrawStrategy{
    private static RenderStrategy instance=null;
    
    public IDrawStrategy rootStrategy;
    public final Map<Long,IDrawStrategy> beforeRootMaps = new HashMap<>();
    public final Map<Long,IDrawStrategy> afterRootMaps=new HashMap<>();
    
    private static final IDrawStrategy defaultstrategy=(IGraphicsTech tech) -> {
        tech.drawText(100, 100,tech.getFont(),tech.getColor(), "default root draw");        
    };

    public static RenderStrategy getInstance(){
        if(instance==null)
            instance=new RenderStrategy();
        
        return instance;
    }
    private RenderStrategy(){
        this.rootStrategy=RenderStrategy.defaultstrategy;
    }
    
    @Override
    public void draw(IGraphicsTech tech){ 
        Iterator<Long> strategyiter=beforeRootMaps.keySet().iterator();
        while(strategyiter.hasNext()){
            IDrawStrategy strategy = beforeRootMaps.get(strategyiter.next());
            strategy.draw(tech);
        }
                
        if(rootStrategy!=null)
            rootStrategy.draw(tech);
        
        strategyiter=afterRootMaps.keySet().iterator();
        while(strategyiter.hasNext()){
            IDrawStrategy strategy = afterRootMaps.get(strategyiter.next());
            strategy.draw(tech);
        }
    }    
}