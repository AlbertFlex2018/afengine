package afengine.component.action;

import afengine.component.action.ActionComponent.ActAction;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.part.scene.ActorComponent;
import afengine.part.scene.IComponentFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Element;

public class ActionComponentFactory implements IComponentFactory{

    /*
        <Action>
            <act type="Sprite" act-factory="" path="*.xml" /> 
            <act type="Timed" act-factory=""/>                
        </Action>    
    */
    private static Map<String,IActActionFactory> factoryMap;
    static{
        factoryMap=new HashMap<>();
        factoryMap.put("Sprite",new SpriteAction.SpriteActionFactory());
    }
    @Override
    public ActorComponent createComponent(Element element, Map<String, String> actordatas){
        ActionComponent acomp = new ActionComponent();
        Iterator<Element> eleiter = element.elementIterator();
        while(eleiter.hasNext()){
            Element ele=eleiter.next();
            String actname=ele.attributeValue("type");
            ActAction act = createAction(ele,actordatas);
            if(act==null){
                Debug.log("actaction create failed for type "+actname);                
            }else{
                acomp.addAction(act);
            }
        }
        return acomp;
    }        
    
    /*
        <act type="" act-factory="" />
    */
    public static ActAction createAction(Element element,Map<String,String> actordatas){
        String type=element.attributeValue("type");
        if(type==null){
            Debug.log("type has not set. for actaction");
            return null;
        }
                    
        IActActionFactory actf = factoryMap.get(type);
        if(actf==null){
            Debug.log("actaction type for ["+type+"] is not found.");
            String actfa = element.attributeValue("act-factory");
            if(actfa==null){
                Debug.log("actaction,actfactory for "+type+" is not set.");
                return null;
            }
            else{
                IActActionFactory fac =(IActActionFactory)XMLEngineBoot.instanceObj(actfa);
                if(fac==null){
                    Debug.log("actaction,actfactory for "+type+",actfa"+" instance failed.");                    
                    return null;
                }
                else{
                    Debug.log("actaction,actfactory for "+type+",actfa"+" instance sucessfully.");                    
                    return fac.createAction(element, actordatas);
                }
            }            
        }                
        return null;
    }
}
