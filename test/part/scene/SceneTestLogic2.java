package part.scene;

import afengine.component.behavior.BehaviorBeanComponent;
import afengine.component.behavior.BehaviorBeanComponentFactory;
import afengine.component.render.RenderComponent;
import afengine.component.render.RenderComponentFactory;
import afengine.core.AppState;
import afengine.core.util.Debug;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import afengine.part.scene.Actor;
import afengine.part.scene.ActorComponent;
import afengine.part.scene.Scene;
import afengine.part.scene.SceneFileHelp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 *
 * @author Albert Flex
 */
public class SceneTestLogic2 extends AppLogicBase{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/scenetestboot2.xml");
    }    
    private Scene scene;
    private final String scenepath="test/assets/scene1.xml";
    private final String destpath1="test/assets/scene1_1.xml";
    private final String destpath2="test/assets/scene1_2.xml";
    @Override
    public boolean init() {
        Debug.log_panel(text);
        ActorComponent.addFactory(BehaviorBeanComponent.COMPONENT_NAME,new BehaviorBeanComponentFactory());
        ActorComponent.addFactory(RenderComponent.COMPONENT_NAME,new RenderComponentFactory());

        Element sceneroot=XMLEngineBoot.readXMLFileDocument(scenepath).getRootElement();
        scene = SceneFileHelp.loadSceneFromXML(sceneroot);
        if(scene==null){
            Debug.log_panel(new Text("scene load failed."));
        }else{
            logScene(scene);
            Map<String,Actor> actorMap=scene.nodeActorMap;
            Iterator<Map.Entry<String,Actor>> actoriter= actorMap.entrySet().iterator();
            while(actoriter.hasNext()){
                Map.Entry<String,Actor> actorentry=actoriter.next();
                logActor(actorentry.getValue());
            }
        }        
        return true;
    }
    private void logActorMap(Scene scene){
        Debug.log("--- actor map ---");
        
        //使用先序遍历        
        Map<String,Actor> actormap = scene.nodeActorMap;
        Iterator<Map.Entry<String,Actor>> iter = actormap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String,Actor> actorentry= iter.next();
            Actor actor=actorentry.getValue();
            preOrder("",actor);
        }        
    }
    private void preOrder(String match,Actor actor){
        String name = actor.getName();
        Debug.log_panel(new Text(match+""+name));
        List<Actor> actorlist=actor.getChildren();
        actorlist.forEach((child) -> {
            preOrder(match+" ",child);
        });
    }
    
    private void logScene(Scene scene){
        Debug.log_panel(new Text("scene - "+scene.getName()));
        Debug.log_panel(new Text("output - "+(scene.isShouldoutput()==true?"true":"false")));
    }
    private void logV(Vector v,String name){
        Debug.log_panel(new Text(name+" - "+v.getX()+","+v.getY()+","+v.getZ()));
    }
    private void logActor(Actor actor){
        Debug.log_panel(new Text("-------  actor:"+actor.getName()+" --------"));
        Debug.log_panel(new Text("id - "+actor.id));
        Map<String,String> attris = actor.valueMap;

        Debug.log_panel(new Text("== transform =="));
        Vector p = actor.getTransform().position;
        Vector a = actor.getTransform().anchor;
        Vector r = actor.getTransform().rotation;
        Vector s = actor.getTransform().scalation;
        logV(p,"pos");
        logV(a,"anchor");
        logV(r,"rotate");
        logV(s,"scale");
        
        Debug.log_panel(new Text("== datas =="));
        Iterator<Map.Entry<String,String>> attrisiter=attris.entrySet().iterator();
        while(attrisiter.hasNext()){
            Map.Entry<String,String> entry = attrisiter.next();
            Debug.log_panel(new Text(entry.getKey()+" - "+entry.getValue()));
        }

        Debug.log_panel(new Text("== components =="));
        Iterator<ActorComponent> complist = actor.getComponentsMap().values().iterator();
        while(complist.hasNext()){
            ActorComponent comp=complist.next();
            Debug.log_panel(new Text(comp.getComponentName()));
        }
        
        List<Actor> children=actor.getChildren();
        children.forEach((act) -> {
            logActor(act);
        });
    }

    private int count=0;
    
    private final Text text=new Text("");
    @Override
    public boolean update(long time) {
        ++count;
        text.value=""+count;
        if(count==2000){            
            Debug.clear_panellog();
            Debug.log_panel(text);
            logActorMap(scene);
        }
        else if(count==4000){
            handScene(scene);
            Document doc = XMLEngineBoot.getXMLWritableDocument(destpath1);
            if(doc.getRootElement()==null){
                doc.addElement("root");
            }
            SceneFileHelp.outputSceneToXML(scene,doc.getRootElement());
            XMLEngineBoot.writeXMLFile(destpath1,doc);
            doc=XMLEngineBoot.getXMLWritableDocument(destpath2);
            if(doc.getRootElement()==null){
                doc.addElement("root");
            }
            SceneFileHelp.outputSceneToXML(scene,doc.getRootElement());
            XMLEngineBoot.writeXMLFile(destpath2,doc);
        }
        else if(count==6000){
            AppState.setValue("run","false");
        }
        return true;
    }       
    private void handScene(Scene scene){        
        Map<String,Actor> actorMap=scene.nodeActorMap;
        Set<String> namesets = actorMap.keySet();
        String[] names=new String[10];
        actorMap.remove(namesets.toArray(names)[0]);
    }
}
