package part.scene;

import afengine.core.AppState;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.part.scene.Actor;
import afengine.part.scene.SceneFileHelp;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.Element;


public class StaticActorTestLogic extends AppLogicBase{
    public static void main(String[] args){
        XMLEngineBoot.bootEngine("test/assets/statictestboot.xml");
    }
    @Override
    public boolean init() {
        Document doc = XMLEngineBoot.readXMLFileDocument("test/assets/staticactor-1.xml");
        if(doc!=null&&doc.getRootElement().getName().equals("staticactor-list")){
            SceneFileHelp.loadStaticActorFromXML(doc.getRootElement());
            showSActors();
            handActor();
            showSActors();
            Document doc2= XMLEngineBoot.getXMLWritableDocument("test/assets/staticactor-2.xml");
            Element root = doc2.getRootElement();
            SceneFileHelp.outputStaticActorToXML(root);
            XMLEngineBoot.writeXMLFile("test/assets/staticactor-2.xml", doc2);
        }
        AppState.setValue("run","false");
        
        return true;
    }        
    private void handActor(){
        Map<String,Actor> actorMap=Actor.staticActorMap;
        Set<String> namesets = actorMap.keySet();
        String names[]=new String[10];
        String name=namesets.toArray(names)[0];
        Debug.log("name:"+name);
        actorMap.remove(name);      
        
    }
    private void showSActors(){
        Iterator<Actor> actors = Actor.staticActorMap.values().iterator();
        while(actors.hasNext()){
            Actor act = actors.next();
            Debug.log("actor - "+act.getName());
        }
    }
}
