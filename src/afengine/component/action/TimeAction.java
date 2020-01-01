package afengine.component.action;

import afengine.component.action.ActionComponent.ActAction;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.part.scene.ActorComponent;
import java.util.Map;
import org.dom4j.Element;

public class TimeAction extends ActAction{
    public static interface ITimeAction{
        public void action();
    }
    
    private boolean repeat;
    private final ITimeAction action;
    private final long timeGap;
    private boolean isEnd=false;

    private long temptime=0;

    public TimeAction(String name,long timeGap,boolean repeat,ITimeAction action){
        super(name);
        this.timeGap=timeGap;
        this.repeat=repeat;
        this.action=action;
    }

    @Override
    public boolean isEnd() {
        return isEnd;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    
    @Override
    public void update(long time) {
        if(isEnd)return;
        
        temptime+=time;
        if(temptime>=timeGap){
            action.action();
            temptime=0;
            isEnd = !repeat;            
        }
    }    
    
    public static class TimeActionFactory implements IActActionFactory{
        /*
            <act type="Time" name="" gap="" repeat="true" action=""/>
        */
        @Override
        public ActAction createAction(Element element, Map<String, String> actordatas) {
            String gap=element.attributeValue("gap");
            String name=element.attributeValue("name");            
            if(name==null){
                Debug.log("name for action is not found.");
                return null;
            }
            name=ActorComponent.getRealValue(name, actordatas);
            if(gap==null){
                Debug.log("gap for time action is not found.");
                return null;
            }
            long gapl=Long.parseLong(ActorComponent.getRealValue(gap, actordatas));
            String repeat=element.attributeValue("repeat");
            boolean re=false;
            if(repeat==null){
                Debug.log("repeat for time action is not found.default to false");
            }
            else if(!repeat.equals("true")){
                Debug.log("repeat for time action is not true.default to false");                
            }
            else re=true;
            String actionclass=element.attributeValue("action");
            if(actionclass==null){
                Debug.log("action class is not found from action attributes.");                                
                return null;
            }            
            ITimeAction timeAction = (ITimeAction)XMLEngineBoot.instanceObj(ActorComponent.getRealValue(actionclass, actordatas));
            if(timeAction==null){
                Debug.log("action instance failed..");                                                
                return null;
            }            
            return new TimeAction(name,gapl,re,timeAction);
        }        
    }
}
