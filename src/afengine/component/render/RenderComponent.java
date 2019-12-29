package afengine.component.render;

import afengine.core.util.Vector;
import afengine.core.window.IGraphicsTech;
import afengine.part.scene.ActorComponent;

/**
 * base class for rendercomponent.<br>
 * some render component should override the render method for use,<br>
 * and you chould use beforerender,and afterrender<br>
 * while you should add any drawstrategy to graphicstech,to call render component.renderComponent by hand<br>
 * @author Albert Flex
 */
public abstract class RenderComponent extends ActorComponent{
    public static final String COMPONENT_NAME="Render";

    public RenderComponent() {
        super(RenderComponent.COMPONENT_NAME);
    }    
    
    public void beforeRender(IGraphicsTech tech){
        
    }
    /*
        need override.
    */
    public abstract void render(IGraphicsTech tech);

    public final void renderComponent(IGraphicsTech tech){
        beforeRender(tech);
        render(tech);
        afterRender(tech);
    }

    public void afterRender(IGraphicsTech tech){
        
    }
    public boolean isPointIn(Vector point){
        return false;
    }
    
}
