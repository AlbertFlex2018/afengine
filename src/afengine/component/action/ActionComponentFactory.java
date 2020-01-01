package afengine.component.action;

import afengine.component.action.ActionComponent.ActAction;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.part.scene.ActorComponent;
import afengine.part.scene.IComponentFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.dom4j.Element;

public class ActionComponentFactory implements IComponentFactory{

    /*
        <Action>
            <act type="Sprite" act-factory="" path="*.xml" next=""/> 
            <act type="Timed" />                
        </Action>    
    */
    public static final Map<String,IActActionFactory> factoryMap;
    static{
        factoryMap=new HashMap<>();
        factoryMap.put("Sprite",new SpriteAction.SpriteActionFactory());
        factoryMap.put("Time",new TimeAction.TimeActionFactory());        
    }
    @Override
    public ActorComponent createComponent(Element element, Map<String, String> actordatas){
        ActionComponent acomp = new ActionComponent();
        Iterator<Element> eleiter = element.elementIterator();
        Map<String,String> actMap=new HashMap<>();
        Map<String,ActAction> actionMap=new HashMap<>();
        while(eleiter.hasNext()){
            Element ele=eleiter.next();
            String actname=ele.attributeValue("name");
            if(actname==null){
                Debug.log("name not found by attribute.");
                return null;
            }
            actname=ActorComponent.getRealValue(actname, actordatas);
            String next=ele.attributeValue("next");
            ActAction act = createAction(ele,actordatas);
            if(act==null){
                Debug.log("actaction create failed for type "+actname);                
            }else{
                Debug.log("act-"+actname);
                actionMap.put(actname, act);
                if(next!=null){
                    next=ActorComponent.getRealValue(next, actordatas);
                    Debug.log(actname+"->"+next);
                    actMap.put(actname,next);                    
                }
            }
        }
        
        //对动作链进行链接，返回没有链接为next的动作
        List<ActAction> actionList=mapAction(actionMap,actMap);
        //将没有链接为next动作的动作添加到动作组件中
        actionList.forEach((act) -> {
            acomp.addAction(act);
            Debug.log("add act "+act.actionName);
        });
        
        return acomp;
    }        
    private List<ActAction> mapAction(Map<String,ActAction> actionMap,Map<String,String> actMap){
        Iterator<Map.Entry<String,String>> entrtiter=actMap.entrySet().iterator();
        List<ActAction> actionList=new LinkedList<>();
        Iterator<ActAction> actiter=actionMap.values().iterator();
        while(actiter.hasNext()){
            ActAction act = actiter.next();
            actionList.add(act);
        }

        while(entrtiter.hasNext()){
            Map.Entry<String,String> entry=entrtiter.next();
            String key= entry.getKey();
            String value=entry.getValue();
            ActAction k = actionMap.get(key);
            ActAction v = actionMap.get(value);
            if(k!=null&&v!=null){
                k.setNextAction(v);
                actionList.remove(v);
                Debug.log("set next act :"+k.actionName+"->"+v.actionName);
            }else{
                Debug.log("set next act failed, ");
            }
        }
        return actionList;
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
                    factoryMap.put(type, fac);
                    return fac.createAction(element, actordatas);
                }
            }            
        }                
        return actf.createAction(element, actordatas);
    }
}
