package part.uiinput;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.control.IUIAction;
import afengine.part.uiinput.control.UIImageButton;
import afengine.part.uiinput.control.UITextButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import part.scene.AppLogicBase;

public class TextButtonTest extends AppLogicBase{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/inputtest2.xml");
    }
    

    public static class Listener implements MouseListener,MouseMotionListener{
        
        @Override
        public void mouseClicked(MouseEvent e) {        }

        @Override
        public void mousePressed(MouseEvent e) {
            Message msg=createMsg(InputServlet.INPUT_MOUSE_DOWN,e);
            Debug.log("mouse pressed");
            sendMsg(msg);
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
            Message msg=createMsg(InputServlet.INPUT_MOUSE_UP,e);
            Debug.log("mouse up");
            sendMsg(msg);
        }
        @Override
        public void mouseEntered(MouseEvent e) {      }

        @Override
        public void mouseExited(MouseEvent e) {     }

        @Override
        public void mouseDragged(MouseEvent e) {     }

        @Override
        public void mouseMoved(MouseEvent e) {
            Message msg=createMsg(InputServlet.INPUT_MOUSE_MOVE,e);
            Debug.log("mouse move");
            sendMsg(msg);
        }        
        private void sendMsg(Message msg){
            UIActor button0=TextButtonTest.button;
            if(button0!=null)            
                button0.handle(msg);            
        }
    }
    public static UIImageButton button;
    private static IUIAction downA=(UIActor ui) -> {
        Debug.log("down");
    };
    private static IUIAction coverA=(UIActor ui) -> {
        Debug.log("cover");
    };
    private static IUIAction normalA=(UIActor ui) -> {
        Debug.log("normal");
    };

    public static class Draw1 implements IDrawStrategy{
        @Override
        public void draw(IGraphicsTech tech) {
            UIActor b=TextButtonTest.button;
            if(b!=null){
                b.draw(tech);
            }
        }        
    }    
    @Override
    public boolean init() {
        Debug.log("init logic");
        IGraphicsTech tech=((WindowApp)(AppState.getRunningApp())).getGraphicsTech();
        ITexture normal=tech.createTexture("test/assets/duke0.gif");
        ITexture down=tech.createTexture("test/assets/duke1.gif");
        ITexture cover=tech.createTexture("test/assets/duke2.gif");
        
        Vector pos=new Vector(100,100,0);
        button=new UIImageButton("Button1",pos,normal,down,cover);
        button.setToCoverAction(coverA);
        button.setToDownAction(downA);
        button.setToNormalAction(normalA);          
        return true;
    }        

    @Override
    public boolean update(long time) {
        if(button!=null){
            button.update(time);
        }
        return true;
    }    
}
