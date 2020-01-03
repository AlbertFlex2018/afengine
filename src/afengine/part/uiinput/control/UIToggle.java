package afengine.part.uiinput.control;

import afengine.core.util.Debug;
import afengine.core.util.Vector;
import afengine.core.window.ITexture;
import afengine.part.uiinput.UIActor;

/**
 *
 * @author Albert Flex
 */
public class UIToggle extends UIActor{
    
    private final ITexture[] textures;
    private final IUIAction[] actions;
    private int index;
    private int addindex;
    private final int capacity;
    public UIToggle(String name, Vector pos,int length) {
        super(name, pos);
        textures=new ITexture[length];
        actions=new IUIAction[length];
        capacity=length;
        addindex=0;
        index=0;
    }        

    public ITexture[] getTextures() {
        return textures;
    }

    public IUIAction[] getActions() {
        return actions;
    }

    public int getIndex() {
        return index;
    }

    public int getCapacity() {
        return capacity;
    }
    public void pushToggle(ITexture texture,IUIAction action){
        if(addindex>(capacity-1)){
            Debug.log("it's full on toggles,please do not add more.");
            return;
        }
        if(texture==null||action==null){
            Debug.log("texture or action should not null.");
            return;
        }
        textures[addindex]=texture;
        actions[addindex]=action;
        ++addindex;
    }
    public void popToggle(){
        if(addindex==0){
            Debug.log("it's empty now.do not pop more.");
            return;
        }
        --addindex;
    }
    
}
