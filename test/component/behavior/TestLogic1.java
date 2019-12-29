package component.behavior;

import afengine.component.behavior.ActorBehavior;
import afengine.component.behavior.BehaviorBeanComponent;
import afengine.component.behavior.BehaviorBeanComponentFactory;
import afengine.core.IAppLogic;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.part.scene.Actor;
import afengine.part.scene.ActorComponent;
import java.util.Iterator;
import part.scene.ActorTest2;

/**
 *
 * @author Albert Flex
 */
public class TestLogic1 implements IAppLogic{

    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/behaviortest.xml");
    }

    @Override
    public boolean init() {
        ActorComponent.addFactory("Component1",new ActorTest2.ComponentFactory1());
        ActorComponent.addFactory(BehaviorBeanComponent.COMPONENT_NAME,new BehaviorBeanComponentFactory());
        return true;
    }

    @Override
    public boolean update(long time) {
        ActorTest2.console();
        showBean();
        return true;
    }
    public void showBean(){
        Actor actor = ActorTest2.actor;
        if(actor==null)return;
        
        BehaviorBeanComponent bean = (BehaviorBeanComponent) actor.getComponent(BehaviorBeanComponent.COMPONENT_NAME);
        if(bean!=null){
            Debug.log("---- behavior bean ----");
            Iterator<ActorBehavior> beiter = bean.getBehaviorMap().values().iterator();
            while(beiter.hasNext()){
                ActorBehavior behavior = beiter.next();
                Debug.log("name\t"+behavior.getName()+" bean text:"+behavior.attributes.get("text"));
            }
        }
        else Debug.log("no bean.");
    }

    @Override
    public boolean shutdown() {
        return true;
    }
    
}
