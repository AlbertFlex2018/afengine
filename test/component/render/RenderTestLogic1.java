package component.render;

import afengine.component.render.RenderComponent;
import afengine.component.render.RenderComponentFactory;
import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.util.Debug;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.part.scene.ActorComponent;
import afengine.part.scene.SceneCamera;
import java.util.Iterator;
import java.util.Map;
import part.scene.ActorElementLogic;
import part.scene.ActorTest2;


public class RenderTestLogic1 implements IAppLogic{
    
    public static class DrawTest1 implements IDrawStrategy{
        private final SceneCamera camera=new SceneCamera(new Vector(),new Vector(),0,0);        
        @Override
        public void draw(IGraphicsTech tech) {
            if(ActorTest2.actor==null){
                tech.drawText(100, 100,tech.getFont(),tech.getColor(),"No Actor");
            }
            else{
                if(ActorTest2.actor.hasComponent(RenderComponent.COMPONENT_NAME)){
                    ActorComponent rendercomp = ActorTest2.actor.getComponent(RenderComponent.COMPONENT_NAME);
                    RenderComponent render = (RenderComponent)rendercomp;
                    render.renderComponent(camera,tech);
                }
                else tech.drawText(0, tech.getFont().getFontHeight(),tech.getFont(),tech.getColor(),"Actor has no RenderComponent!");
                
                Iterator<ActorComponent> compiter=ActorTest2.actor.getComponentsMap().values().iterator();
                int y=tech.getFont().getFontHeight()*2;
                while(compiter.hasNext()){                    
                    tech.drawText(0, y,tech.getFont(), tech.getColor(),"component   -    "+compiter.next().getComponentName());
                    y+=tech.getFont().getFontHeight();
                }
                Iterator<Map.Entry<String,String>> stringentry = ActorTest2.actor.valueMap.entrySet().iterator();
                while(stringentry.hasNext()){
                    Map.Entry<String,String> entry = stringentry.next();
                    tech.drawText(0, y,tech.getFont(), tech.getColor(),entry.getKey()+"     -      "+entry.getValue());
                    y+=tech.getFont().getFontHeight();
                }
            }
        }        
    }
    
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/rendertest1.xml");
    }
    private final Text initText=new Text("init...");
    @Override
    public boolean init(){
        ActorComponent.addFactory(RenderComponent.COMPONENT_NAME,new RenderComponentFactory());        
        ActorTest2.actor=ActorElementLogic.load("test/assets/actor2.xml");
        Debug.log_panel(new Text("init..."));
        Debug.log_panel(new Text("init..."));
        Debug.log_panel(new Text("init..."));
        Debug.log_panel(new Text("init..."));
        Debug.log_panel(new Text("init..."));
        Debug.log_panel(new Text("init..."));
        Debug.log_panel(new Text("init..."));
        Debug.log_panel(new Text("init..."));
        Debug.log_panel(initText);
        return true;
    }
    
    int count=0;
    @Override
    public boolean update(long time) {
        initText.value="init..."+(++count);
        if(count==1000){
            AppState.setValue("run","false");
        }
        return true;
    }

    @Override
    public boolean shutdown() {
        return true;
    }
    
}
