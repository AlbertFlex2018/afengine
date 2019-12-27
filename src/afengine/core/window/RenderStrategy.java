/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.window;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class RenderStrategy implements IDrawStrategy{
    public static RenderStrategy instance=new RenderStrategy();
    
    public IDrawStrategy rootStrategy;
    public final Map<Long,IDrawStrategy> beforeRootMaps = new HashMap<>();
    public final Map<Long,IDrawStrategy> afterRootMaps=new HashMap<>();
    
    private static final IDrawStrategy defaultstrategy=new IDrawStrategy(){
        @Override
        public void draw(IGraphicsTech tech) {
        }        
    };
    private RenderStrategy(){
        this.rootStrategy=defaultstrategy;
    }
    
    @Override
    public void draw(IGraphicsTech tech){
        Iterator<IDrawStrategy> strategyiter = beforeRootMaps.values().iterator();
        while(strategyiter.hasNext()){
            IDrawStrategy strategy = strategyiter.next();
            strategy.draw(tech);
        }
                
        if(rootStrategy!=null)
            rootStrategy.draw(tech);
        
        strategyiter = afterRootMaps.values().iterator();
        while(strategyiter.hasNext()){
            IDrawStrategy strategy = strategyiter.next();
            strategy.draw(tech);
        }
    }    
}