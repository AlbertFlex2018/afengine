package part.scene;

import afengine.component.behavior.BehaviorBeanComponent;
import afengine.component.behavior.BehaviorBeanComponentFactory;
import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.part.scene.Actor;
import afengine.part.scene.ActorComponent;
import afengine.part.scene.IComponentFactory;
import java.util.Map;
import java.util.Scanner;
import org.dom4j.Element;
import static part.scene.ActorElementLogic.load;
import static part.scene.ActorElementLogic.output;
import static part.scene.ActorElementLogic.show;

/**
 * console input<br>
 * exit - exit app<br>
 * show - show actor for actor.xml<br>
 * add - add data to actor<br>
 * remove - remove data from actor.<br>
 * load - load actor from test/assets/actor.xml<br>
 * output [dest] - output actor to the dest path,when no arg, output to default path<br>
 * @author Albert Flex
 */
public class ActorTest2 implements IAppLogic{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/actortest2.xml");
    }
        
    
    private static Scanner scan = new Scanner(System.in);
    public static Actor actor=null;
    private static    String actorxmlpath="test/assets/actor.xml";
    @Override
    public boolean init() {
        ActorComponent.addFactory("Component1",new ComponentFactory1());
        ActorComponent.addFactory(BehaviorBeanComponent.COMPONENT_NAME,new BehaviorBeanComponentFactory());
        return true;
    }
    
    public static class ComponentFactory1 implements IComponentFactory{
            
        public class Component1 extends ActorComponent{            
            public Component1(String name) {
                super("Component1");
                this.attributes.put("name", name);
            }            
        }
        /*
            <Component1>
                <data value="actor" or value="#actor"/>
            </Component1>
        */
        @Override
        public ActorComponent createComponent(Element element, Map<String, String> actordatas) {
            Element data = element.element("data");
            String value=data.attributeValue("value");            
            if(value.startsWith("#")){
                value=value.substring(1, value.length());
                if(!actordatas.containsKey(value)){
                    Debug.log("No data found!");
                    value="Not found";
                }else{
                    value=actordatas.get(value);
                }
            }
            ActorComponent comp1=new Component1(value);
            comp1.attributes.put("data",data.attributeValue("value"));
            comp1.attributes.put("realvalue", value);
            return comp1;
        }       
    }

    @Override
    public boolean shutdown() {
        return true;
    }

    @Override
    public boolean update(long time) {
        console();
        return true;
    }
    public static void console(){
        String word = scan.nextLine();
        if(word.equals("load")){
            actor=load(actorxmlpath);
        }
        else if(word.equals("exit"))
            AppState.setValue("run", "false");
        else if(word.startsWith("output")){
            String[] cmd = word.split(" ");
            if(cmd.length==1){                
                output(actor,actorxmlpath);
            }
            else{
                String path = cmd[1];
                output(actor,path);
            }            
        }
        else if(word.equals("show")){
            show(actor);
        }
        else if(word.startsWith("add")){
            String[] cmd=word.split(" ");
            actor.valueMap.put(cmd[1],cmd[2]);
        }
        else if(word.startsWith("remove")){
            String[] cmd=word.split(" ");            
            actor.valueMap.remove(cmd[1]);
        }
        else;
    }
}
