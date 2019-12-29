package component.behavior;

import afengine.component.behavior.ActorBehavior;
import afengine.core.util.Debug;

/**
 *
 * @author Albert Flex
 */
public class Behavior1 extends ActorBehavior{

    @Override
    public void toWake() {
        Debug.log("awake for "+this.getName());
    }
    
    @Override
    public void update(long time) {
        Debug.log("update - "+this.getName());
    }    
}
