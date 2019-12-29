package afengine.component.behavior;

import afengine.core.util.Debug;
import afengine.part.scene.ActorComponent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Albert Flex
 */
public class BehaviorBeanComponent extends ActorComponent{
    
    public static final String COMPONENT_NAME="BehaviorBean";
    
    protected final Map<String,ActorBehavior> behaviorMap;
    
    public BehaviorBeanComponent() {
        super(BehaviorBeanComponent.COMPONENT_NAME);
        behaviorMap=new HashMap<>();
    }
    
    //wake all behaviors
    @Override
    public void toWake() {
        Iterator<ActorBehavior> beiter = behaviorMap.values().iterator();
        while(beiter.hasNext()){
            ActorBehavior be = beiter.next();
            be.awake();
        }
    }
    
    /**
     * update all behaviors
     * @param time
     */
    @Override
    public void update(long time){
        Iterator<ActorBehavior> compiter = behaviorMap.values().iterator();
        while(compiter.hasNext()){
            ActorBehavior comp = compiter.next();
            if(comp.isActive()){
                comp.update(time);                
            }
        }        
    }

    public Map<String, ActorBehavior> getBehaviorMap() {
        return behaviorMap;
    }    

    public ActorBehavior getBehavior(String name){
        return behaviorMap.get(name);
    }
    public void addBehavior(ActorBehavior behavior){
        if(behaviorMap.containsKey(behavior.getName())){
            Debug.log("already has behavior - "+behavior.getName()+",please do not add again.");
            return;
        }
        behaviorMap.put(behavior.getName(), behavior);
        behavior.setBehaviorbean(this);
        Debug.log("add behavior successfully. - "+behavior.getName());
    }
    public void removeBehavior(String name){
        if(!behaviorMap.containsKey(name)){
            Debug.log("does not have behavior - "+name+",can not remove.");
            return;            
        }

        ActorBehavior be=behaviorMap.remove(name);        
        be.setBehaviorbean(null);
    }
}
