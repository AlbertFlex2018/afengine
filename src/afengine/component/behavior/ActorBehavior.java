package afengine.component.behavior;

//actorbehavior extends actorcomponent,while it's not follow the rule for actorcomponent,but for behavior.

import afengine.part.scene.ActorComponent;

//actorbehavior is used to controll the actor
/**
 * 
 * @author Albert Flex
 */
public abstract class ActorBehavior extends ActorComponent{
    
    //the key-value is for the file.
    protected BehaviorBeanComponent behaviorbean;
    private String name;

    public ActorBehavior() {
        super("ActorBehavior");
    }
    
    @Override
    public abstract void update(long time);
        
    @Override
    public void toWake(){
    }        
    
    @Override
    public void toSleep() {
    }        

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BehaviorBeanComponent getBehaviorbean() {
        return behaviorbean;
    }

    public void setBehaviorbean(BehaviorBeanComponent behaviorbean) {
        this.behaviorbean = behaviorbean;
    }    
}
