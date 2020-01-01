package component.action;

import afengine.component.action.ActionComponent;
import afengine.component.action.ActionComponentFactory;
import afengine.component.action.TimeAction.ITimeAction;
import afengine.component.render.RenderComponent;
import afengine.component.render.RenderComponentFactory;
import afengine.core.AppState;
import afengine.core.util.Debug;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.part.scene.Actor;
import afengine.part.scene.ActorComponent;
import part.scene.ActorElementLogic;
import part.scene.ActorTest2;
import static part.scene.ActorTest2.actor;
import part.scene.AppLogicBase;


public class ActionTestLogic1 extends AppLogicBase{        
    
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/actiontest1.xml");
    }
    
    public static class ActionTestDraw1 implements IDrawStrategy{
        @Override
        public void draw(IGraphicsTech tech) {
            Actor actor = ActorTest2.actor;
            if(actor==null){
                Debug.log_panel(new Text("actor is null"));
            }else{
                RenderComponent render = (RenderComponent)actor.getComponent(RenderComponent.COMPONENT_NAME);
                if(render==null){
                    Debug.log_panel(new Text("actor render is null"));
                }else{
                    render.renderComponent(tech);
                }
            }
        }        
    }
    public static class Action1 implements ITimeAction{
        @Override
        public void action(){
            Debug.log_panel(new Text("action1"));
        }        
    }
    public static class Action2 implements ITimeAction{
        @Override
        public void action() {
            AppState.setValue("run","false");
            Debug.log_panel(new Text("action2"));
        }
    }

    @Override
    public boolean init() {
        Debug.log_panel(new Text("Play sprite in 10 second"));
        ActorComponent.addFactory(RenderComponent.COMPONENT_NAME,new RenderComponentFactory());
        ActorComponent.addFactory(ActionComponent.COMPONENT_NAME,new ActionComponentFactory());
        ActorTest2.actor=ActorElementLogic.load("test/assets/actor3.xml");
        if(actor==null){
            Debug.log_panel(new Text("actor load failed."));
        }        
        else{
            actor.awakeAllComponents();
        }
        return true;
    }        

    @Override
    public boolean update(long time) {
        if(ActorTest2.actor!=null){
            ActorTest2.actor.updateActor(time);
        }
        return true;
    }    
}
