package afengine.part.uiinput.control;

import afengine.core.util.Debug;
import afengine.core.util.Vector;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.UIFace;
import java.awt.event.MouseEvent;

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
    public void pushToggle(ITexture texture,IUIAction toToggleaction){
        if(addindex>(capacity-1)){
            Debug.log("it's full on toggles,please do not add more.");
            return;
        }
        if(texture==null||toToggleaction==null){
            Debug.log("texture or action should not null.");
            return;
        }
        textures[addindex]=texture;
        actions[addindex]=toToggleaction;
        ++addindex;
    }
    public void popToggle(){
        if(addindex==0){
            Debug.log("it's empty now.do not pop more.");
            return;
        }
        --addindex;
    }
    public IUIAction getToggleActionAt(int eindex){
        if(eindex<0||eindex>=addindex)return null;
        
        return actions[eindex];
    }
    public ITexture getToggleTextureAt(int eindex){
        if(eindex<0||eindex>=addindex)return null;
        
        return textures[eindex];        
    }
    @Override
    public void update(long time){
        ITexture text=textures[index];
        if(text!=null){
            super.width=text.getWidth();
            super.height=text.getHeight();
        }
    }
    
    //INPUT_MOUSE_CLICK
    @Override
    public boolean handle(Message msg){
        MouseEvent event=(MouseEvent) msg.extraObjs[0];
        if(event!=null){
            int mx=event.getX();
            int my=event.getY();
            boolean in=super.isPointInUi(mx, my);
            if(in){
                toggle();
                IUIAction action=actions[index];
                action.action(this);
                return true;
            }
        }
        return false;
    }
    public void toggle(){
        index=(index+1)%(addindex+1);
    }

    @Override
    public void draw(IGraphicsTech tech){
        ITexture text=textures[index];
        if(text!=null){
            int dx=super.getUiX();
            int dy=super.getUiY();
            tech.drawTexture(dx,dy,text);
        }
    }   

    @Override
    protected void loadOutFromFace(UIFace face) {
        face.removeMsgUiMap(InputServlet.INPUT_MOUSE_CLICK,this);
    }

    @Override
    protected void loadInToFace(UIFace face) {
        face.addMsgUiMap(InputServlet.INPUT_MOUSE_CLICK,this);
    }    
}
