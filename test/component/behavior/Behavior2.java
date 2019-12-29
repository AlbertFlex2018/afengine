package component.behavior;

import afengine.component.behavior.ActorBehavior;
import afengine.core.util.Debug;

/**
 *
 * @author Albert Flex
 */
public class Behavior2 extends ActorBehavior{

    @Override
    public void toWake() {
        Debug.log("awake behavior2 for "+this.getName());
    }
    
    @Override
    public void update(long time) {
        Debug.log("update behavior2 - "+this.getName());
    }    
}
