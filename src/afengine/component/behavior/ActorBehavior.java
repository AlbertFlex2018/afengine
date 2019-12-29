package afengine.component.behavior;

//actorbehavior extends actorcomponent,while it's not follow the rule for actorcomponent,but for behavior.

import afengine.part.scene.ActorComponent;

//actorbehavior is used to controll the actor
/**
 * Use ActorBehavior for one method your logic code put.<br>
 * you could write a behavior class,and contains some game logic for operate actor,and so on.<br>
 * override the update,or toWake,toSleep<br>
 * as BehaviorBean update ,it will call behavior.update for some logic circle<br>
 * you could sleep this behavior,or wake this behavior.when something is nesscessary.<br>
 * @see BehaviorBeanComponent
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
