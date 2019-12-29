package afengine.component.render;

import afengine.core.util.Vector;
import afengine.core.window.IGraphicsTech;
import afengine.part.scene.ActorComponent;

/**
 * base class for rendercomponent.
 * @author Albert Flex
 */
public class RenderComponent extends ActorComponent{
    public static final String COMPONENT_NAME="RenderComponent";

    public RenderComponent() {
        super(RenderComponent.COMPONENT_NAME);
    }    
    
    public void beforeRender(IGraphicsTech tech){
        
    }
    /*
        need override.
    */
    public void render(IGraphicsTech tech){
        
    }
    public void afterRender(IGraphicsTech tech){
        
    }
    public boolean isPointIn(Vector point){
        return false;
    }
    
}
