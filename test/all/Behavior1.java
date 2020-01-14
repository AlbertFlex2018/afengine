package all;

import afengine.component.behavior.ActorBehavior;
import afengine.core.util.Debug;

/**
 *
 * @author Admin
 */
public class Behavior1 extends ActorBehavior{

    @Override
    public void toWake() {
        Debug.log("to wake behavior1 for actor:"+super.behaviorbean.getActor().getName());
    }
    
    @Override
    public void update(long time){        
    }    
}
