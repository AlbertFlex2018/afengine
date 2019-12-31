package afengine.component.action;

import afengine.component.action.ActionComponent.ActAction;

public class TimedAction extends ActAction{
    public static interface ITimeAction{
        public void action();
    }
    
    private final boolean repeat;
    private final ITimeAction action;
    private final long timeGap;
    private boolean isEnd;

    private long temptime=0;

    public TimedAction(String name,long timeGap,boolean repeat,ITimeAction action){
        super(name);
        this.timeGap=timeGap;
        this.repeat=repeat;
        this.action=action;
    }

    @Override
    public boolean isEnd() {
        return isEnd;
    }
    
    @Override
    public void update(long time) {
        if(isEnd)return;
        
        temptime+=time;
        if(temptime>=timeGap){
            action.action();
            if(repeat){
                temptime=0;
            }else{
                isEnd=true;
            }
        }
    }    
}
