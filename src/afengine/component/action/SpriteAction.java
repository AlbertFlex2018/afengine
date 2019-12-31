package afengine.component.action;

import afengine.component.action.ActionComponent.ActAction;
import afengine.component.render.RenderComponent;
import afengine.component.render.TextureRenderComponent;
import afengine.core.window.ITexture;
import afengine.part.scene.Actor;
import java.util.ArrayList;
import java.util.List;

public class SpriteAction extends ActAction{
    public SpriteAction(String name,int looptype) {
        super(name);
        shapeList=new ArrayList<>();
        this.loopType=looptype;
    }

    private boolean realEnd;
    
    @Override
    public boolean isEnd() {
        return realEnd;
    }

    @Override
    public void update(long time) {
        updateAnimate(time);
        ITexture nowTexture = getTexture();
        Actor actor = super.actionComp.getActor();
        TextureRenderComponent render = (TextureRenderComponent) actor.getComponent(RenderComponent.COMPONENT_NAME);
        if(render==null){
            render = new TextureRenderComponent(nowTexture);
            actor.addComponent(render);
            render.awake();
        }        
        else{
            render.setTexture(nowTexture);
        }        
    }
    
    public static final int LOOP_ONCE_RETURN=0;
    public static final int LOOP_ONCE_STOP=1;
    public static final int LOOP_FOREVER=2;
    public static final int LOOP_PASS=3;
    
    private final List<AnimateShape> shapeList;
    private int index;   
    private  int size;
    private int loopType;
            
    private long totaltime;
    private long updatetime;
    private boolean isEnd;
           
    public int getSize()
    {
        return size;
    }
    public int getLoopType()
    {
        return loopType;
    }    
    public void setLoopType(int loop)
    {
        loopType=loop;
    }
    public ITexture getTexture()
    {
        return shapeList.get(index).texture;
    }
    
    public void addAnimate(ITexture texture,long delttime)
    {
        totaltime+=delttime;
        ++size;
        shapeList.add(new AnimateShape(texture,totaltime));
    }
    private class AnimateShape
    {
        ITexture texture;
        long endtime;
        public AnimateShape(ITexture t,long time)
        {
            texture=t;
            endtime=time;
        }
    }    
    @Override
    public void start()
    {
        updatetime=0;
        index=0;
        isEnd=false;
    }

    private void updateAnimate(long time)
    {
        if(isEnd)return;
        
        if (shapeList.size() > 0) 
        {
            updatetime += time;

            if (updatetime >= totaltime) 
            {
                updatetime = updatetime % totaltime;
                index = 0;
                if(loopType==SpriteAction.LOOP_FOREVER){
                    isEnd=false;                    
                }
                else if(loopType==SpriteAction.LOOP_PASS){
                    isEnd=true;
                }
            }
            while (updatetime > shapeList.get(index).endtime) 
            {
                index++;
            }
        }
    }    
}
