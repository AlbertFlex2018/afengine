package afengine.component.render;

import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.part.scene.Actor;
import afengine.part.scene.Scene;
import afengine.part.scene.SceneCamera;
import afengine.part.scene.SceneCenter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Albert Flex
 */
public class SceneRenderComponentDraw implements IDrawStrategy{
    private final SceneCenter sceneCenter=SceneCenter.getInstance();    
    @Override
    public void draw(IGraphicsTech tech) {
        Scene scene=sceneCenter.getRunningScene();
        Map<String,Actor> actormap=scene.nodeActorMap;
        Iterator<Map.Entry<String,Actor>> entryiter=actormap.entrySet().iterator();
        while(entryiter.hasNext()){
            Map.Entry<String,Actor> entry=entryiter.next();
            Actor actor=entry.getValue();
            renderActor(actor,tech,scene.getCamera());
        }
    }
    private void renderActor(Actor actor,IGraphicsTech tech,SceneCamera camera){
        RenderComponent render=(RenderComponent) actor.getComponent(RenderComponent.COMPONENT_NAME);
        if(render!=null){
            render.renderComponent(camera, tech);
        }                
        List<Actor> children=actor.getChildren();        
        children.forEach((ac) -> {
            renderActor(ac,tech,camera);
        });
    }
}
