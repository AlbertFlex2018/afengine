package part.uiinput;

import afengine.core.util.Debug;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.UIFace;
import afengine.part.uiinput.control.IUIAction;
import afengine.part.uiinput.control.UIControlHelp;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import org.dom4j.Document;
import org.dom4j.Element;
import part.scene.AppLogicBase;

/**
 *
 * @author Albert Flex
 */
public class UIControlFileTest extends AppLogicBase{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/ui/faceboot1.xml");
    }
    
    public static class Draw1 implements IDrawStrategy{
        @Override
        public void draw(IGraphicsTech tech){
            if(face!=null){
                face.getActorList().forEach((ui)->{
                    ui.draw(tech);
                });
            }
        }        
    }    
    

    public static class Action1 implements IUIAction{
        @Override
        public void action(UIActor ui){
            Debug.log_panel(new Text("action1"));            
        }        
    }
    public static class Action2 implements IUIAction{
        @Override
        public void action(UIActor ui){
            Debug.log_panel(new Text("action2"));            
        }        
    }
    public static class Action3 implements IUIAction{
        @Override
        public void action(UIActor ui){
            Debug.log_panel(new Text("action3"));            
        }        
    }
    
    public static class Listener implements MouseListener,MouseMotionListener,KeyListener{        
        @Override
        public void mouseClicked(MouseEvent e) {
            sendMsg(InputServlet.INPUT_MOUSE_CLICK,e);
        }
        @Override
        public void mousePressed(MouseEvent e){
            sendMsg(InputServlet.INPUT_MOUSE_DOWN,e);            
        }
        /*
            long routeType, long msgType, long msgContent, 
            String msgInfo, Object[] extraObjs, long timetamp, 
            long delaytime, long senderType, Object senderObj, 
            long receiveType, long senderId, long receiveId        
        */
        private void sendMsg(long type,Object event){
            Message msg=new Message(0,type,0,"",new Object[]{event},
                System.currentTimeMillis(),0,0,null,0,0,0);
            if(face!=null)
                face.handle(msg);
        }
        @Override
        public void mouseReleased(MouseEvent e){     
            sendMsg(InputServlet.INPUT_MOUSE_UP,e);
        }
        @Override
        public void mouseEntered(MouseEvent e) {      }

        @Override
        public void mouseExited(MouseEvent e) {     }

        @Override
        public void mouseDragged(MouseEvent e) {     }

        @Override
        public void mouseMoved(MouseEvent e) {
            sendMsg(InputServlet.INPUT_MOUSE_MOVE,e);            
        }        

        @Override
        public void keyTyped(KeyEvent e) {
            sendMsg(InputServlet.INPUT_KEY_TYPE,e);            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            sendMsg(InputServlet.INPUT_KEY_DOWN,e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            sendMsg(InputServlet.INPUT_KEY_UP,e);
        }
    }
        
    private static UIFace face;
    
    @Override
    public boolean init(){
        Document doc=XMLEngineBoot.readXMLFileDocument("test/assets/ui/uiface1.xml");
        Element root=doc.getRootElement();
        if(!root.getName().equals("face")){
            Debug.log("load uiface failed:file not face file.");
            return false;
        }
        face=new UIFace("face1");
        face=UIControlHelp.loadFace(face,root);
        Debug.log_panel(new Text("uiface-"+face.getFaceName()));
        return true;
    }

    @Override
    public boolean update(long time){
        face.getActorList().forEach((ui)->{
            ui.update(time);
        });
        return true;
    }    
}
