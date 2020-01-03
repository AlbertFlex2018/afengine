package part.uiinput;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.control.IUIAction;
import afengine.part.uiinput.control.UIToggle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import part.scene.AppLogicBase;
/**
 *
 * @author Albert Flex
 */
public class ToggleTestLogic extends AppLogicBase{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/inputtest3.xml");
    }
    public static class Listener implements MouseListener,MouseMotionListener{
        
        @Override
        public void mouseClicked(MouseEvent e) {
            Message msg=createMsg(InputServlet.INPUT_MOUSE_CLICK,e);
            sendMsg(msg);            
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }
        /*
            long routeType, long msgType, long msgContent, 
            String msgInfo, Object[] extraObjs, long timetamp, 
            long delaytime, long senderType, Object senderObj, 
            long receiveType, long senderId, long receiveId        
        */
        private Message createMsg(long type,Object event){
            Message msg=new Message(0,type,0,"",new Object[]{event},
                System.currentTimeMillis(),0,0,null,0,0,0);
            return msg;
        }

        @Override
        public void mouseReleased(MouseEvent e) {     
        }
        @Override
        public void mouseEntered(MouseEvent e) {      }

        @Override
        public void mouseExited(MouseEvent e) {     }

        @Override
        public void mouseDragged(MouseEvent e) {     }

        @Override
        public void mouseMoved(MouseEvent e) {
        }        
        private void sendMsg(Message msg){
            UIActor ui=ToggleTestLogic.toggle;
            if(ui!=null)
                ui.handle(msg);
        }
    }
    
    public static class Draw1 implements IDrawStrategy{
        @Override
        public void draw(IGraphicsTech tech) {
            UIActor b=toggle;
            if(b!=null){
                b.draw(tech);
            }
        }        
    }    
    
    public static UIToggle toggle;

    @Override
    public boolean update(long time) {
        if(toggle!=null)
            toggle.update(time);
        return true;
    }

    @Override
    public boolean init() {
        toggle=new UIToggle("toggle",new Vector(10,10,10,0),2);
        IGraphicsTech tech=((WindowApp)(AppState.getRunningApp())).getGraphicsTech();
        ITexture t1=tech.createTexture("test/assets/duke0.gif");
        ITexture t2=tech.createTexture("test/assets/duke1.gif");
        IUIAction action1=(action)->{
            Debug.log("action1");
        };
        IUIAction action2=(action)->{
            Debug.log("action2");
        };
        toggle.pushToggle(t2, action2);
        toggle.pushToggle(t1, action1);
        return true;
    }    
}
