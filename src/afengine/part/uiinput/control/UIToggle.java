package afengine.part.uiinput.control;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.IDCreator;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.UIFace;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import org.dom4j.Element;

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
        if(msg.msgType!=InputServlet.INPUT_MOUSE_CLICK)return false;
        
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
        index=(index+1)%addindex;
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
    
    public static class UIToggleCreator implements IUICreator{
        private final IGraphicsTech tech=((WindowApp)AppState.getRunningApp()).getGraphicsTech();        

        /*
            <UIToggle name="" pos="" length="">
                <toggle texture="" action=""/>
                <toggle texture="" action=""/>
                <toggle texture="" action=""/>
                <toggle texture="" action=""/>
            </UIToggle>
        */
        @Override
        public UIActor createUi(Element element) {
            String name=element.attributeValue("name");
            Vector pos =createPos(element);
            if(pos==null)
                pos=new Vector(10,10,0,0);
            if(name==null)
                name="DefaultUiName"+IDCreator.createId();
            int length=1;
            String slength=element.attributeValue("length");
            if(slength!=null){
                length=Integer.parseInt(slength);
            }
            UIToggle toggle=new UIToggle(name,pos,length);
            Iterator<Element> toggleiter=element.elementIterator();
            if(toggleiter!=null){
                while(toggleiter.hasNext()){
                    Element t=toggleiter.next();
                    addToggle(toggle,t);
                }
            }
            return toggle;
        }       
         private Vector createPos(Element element){
            String poss=element.attributeValue("pos");
            String[] posl=poss.split(",");
            double x = Double.parseDouble(posl[0]);
            double y = Double.parseDouble(posl[1]);
            return new Vector(x,y,0,0);
        }
         /*
            <toggle texture="" action=""/>
         */
        public void addToggle(UIToggle toggle,Element element){            
             ITexture texture=createTexture(element);
             IUIAction action=createAction(element);
             if(texture!=null&&action!=null){
                 toggle.pushToggle(texture, action);
             }
        }
        private ITexture createTexture(Element element){
            String path=element.attributeValue("texture");
            if(path==null){
                Debug.log("path for texture is not defined.return null texture");
                return null;
            }
            else return tech.createTexture(path);
        } 
        private IUIAction createAction(Element element){
            String action=element.attributeValue("action");
            if(action==null){
                Debug.log("action for button not defined");
                return null;
            }
            IUIAction act=(IUIAction)XMLEngineBoot.instanceObj(action);
            return act;
        }                        
    }
}
