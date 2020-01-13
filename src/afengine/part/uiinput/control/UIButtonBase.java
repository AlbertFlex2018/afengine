package afengine.part.uiinput.control;

import afengine.core.util.Debug;
import afengine.core.util.Vector;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.UIFace;
import java.awt.event.MouseEvent;

public class UIButtonBase extends UIActor{
    
    public static final int DOWN=0;
    public static final int NORMAL=1;
    public static final int COVER=2;

    protected int buttonState;
    
    private IUIAction toDownAction;
    private IUIAction toCoverAction;
    private IUIAction toNormalAction;    
    
    public UIButtonBase(String name, Vector pos) {
        super(name, pos);
        buttonState=NORMAL;
    }

    public IUIAction getToDownAction() {
        return toDownAction;
    }

    public void setToDownAction(IUIAction toDownAction) {
        this.toDownAction = toDownAction;
    }

    public IUIAction getToCoverAction() {
        return toCoverAction;
    }

    public void setToCoverAction(IUIAction toCoverAction) {
        this.toCoverAction = toCoverAction;
    }

    public IUIAction getToNormalAction() {
        return toNormalAction;
    }

    public void setToNormalAction(IUIAction toNormalAction) {
        this.toNormalAction = toNormalAction;
    }

    @Override
    protected void loadInToFace(UIFace face) {
        Debug.log("loadin to face");
        face.addMsgUiMap(InputServlet.INPUT_MOUSE_DOWN,this);
        face.addMsgUiMap(InputServlet.INPUT_MOUSE_UP,this);
        face.addMsgUiMap(InputServlet.INPUT_MOUSE_MOVE,this);                                
    }

    @Override
    protected void loadOutFromFace(UIFace face) {
        Debug.log("loadout from face");
        face.removeMsgUiMap(InputServlet.INPUT_MOUSE_DOWN,this);
        face.removeMsgUiMap(InputServlet.INPUT_MOUSE_UP,this);
        face.removeMsgUiMap(InputServlet.INPUT_MOUSE_MOVE,this);                        
    }
    
    
    //need override these three methods
    protected void doCover(){}
    protected void doNormal(){}
    protected void doDown(){}
    
    
    public void cover(){
        if(buttonState!=COVER){
            buttonState=COVER;
            if(toCoverAction!=null){
                toCoverAction.action(this);
            }
            doCover();            
        }
    }
    public void down(){
        if(buttonState!=DOWN){
            buttonState=DOWN;
            if(toDownAction!=null){
                toDownAction.action(this);
            }
            doDown();
        }
    }
    public void normal(){
        if(buttonState!=NORMAL){
            buttonState=NORMAL;
            if(toNormalAction!=null){
                toNormalAction.action(this);
            }            
            doNormal();                
        }
    }    
    //msg.extraobj[0]=mouseevent
    //INPUT_MOUSE_DOWN,INPUT_MOUSE_UP,INPUT_MOUSE_MOVE
    @Override
    public boolean handle(Message msg){
        MouseEvent event=(MouseEvent)msg.extraObjs[0];
        if(event==null){
            Debug.log("msg do not contains mousevent for handle.");
            return false;
        }
        
        int mx=event.getX();
        int my=event.getY();
        boolean in=this.isPointInUi(mx, my); 
        long type=msg.msgType;        
        if(type==InputServlet.INPUT_MOUSE_DOWN){
            return handleDown(in);
        }else if(type==InputServlet.INPUT_MOUSE_UP){
            return handleUp(in,mx,my);
        }else if(type==InputServlet.INPUT_MOUSE_MOVE){
            return handleMove(in);
        }else;
        
        return false;  
    }    
    //msgextra[0]=mouseevent
    private boolean handleDown(boolean in){
        if(in){
            down();
            return true;
        }
        return false;
    }
    private boolean handleUp(boolean in,int mx,int my){        
        if(in){
            if(buttonState==DOWN){
                normal();
                cover();
                return true;
            }else return false;
        }else{
            if(buttonState==DOWN){
                normal();
                return true;
            }else return false;
        }
    }
    
    private boolean handleMove(boolean in){
        if(!in&&buttonState==COVER){
            normal();
        }
        if(in&&buttonState==NORMAL){
            cover();
        }

        //cosume
        return false;
    }
    
}
