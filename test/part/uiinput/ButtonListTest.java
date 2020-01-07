package part.uiinput;

import afengine.core.util.Debug;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.control.UIButtonList;
import afengine.part.uiinput.control.UITextButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import part.scene.AppLogicBase;

/**
 *
 * @author Albert Flex
 */
public class ButtonListTest extends AppLogicBase{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/inputtest5.xml");
    }
    public static class Listener implements KeyListener,MouseListener,MouseMotionListener{
        
        @Override
        public void mouseClicked(MouseEvent e){
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Message msg=createMsg(InputServlet.INPUT_MOUSE_DOWN,e);
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
            sendMsg(msg);            
        }        
        private void sendMsg(Message msg){
            UIActor ui=ButtonListTest.buttonList;
            if(ui!=null)
                ui.handle(msg);
        }

        @Override
        public void keyTyped(KeyEvent e){
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e){
            if(buttonList==null)return;
            
            int code=e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_UP:
                    buttonList.upActive();
                    break;
                case KeyEvent.VK_DOWN:
                    buttonList.downActive();
                    break;
                default:                    
                    break;
            }
        }
    }
    public static class Draw1 implements IDrawStrategy{
        @Override
        public void draw(IGraphicsTech tech) {
            UIActor b=buttonList;
            if(b!=null){
                b.draw(tech);
            }
        }        
    }    
    
    public static UIButtonList buttonList;

    @Override
    public boolean update(long time) {
        if(buttonList!=null){
            buttonList.update(time);
        }
        return true;
    }

    @Override
    public boolean init(){
        Vector pos1=new Vector(10,20,10);
        Text text1=new Text("Text1");
        UITextButton button1=new UITextButton("Name1",pos1,text1);
        button1.setToCoverAction((ui)->{
            Debug.log("cover button1");            
        });
        button1.setToDownAction((ui)->{
            Debug.log("click button1");
        });
        
        Vector pos2=new Vector(10,40,10);
        Text text2=new Text("Text2");
        UITextButton button2=new UITextButton("Name2",pos2,text2);       
        button2.setToCoverAction((ui)->{
            Debug.log("cover button2");            
        });
        button2.setToDownAction((ui)->{
            Debug.log("click button2");
        });
        
        Vector pos3=new Vector(10,60,10);
        Text text3=new Text("Text3");
        UITextButton button3=new UITextButton("Name3",pos3,text3);
        button3.setToCoverAction((ui)->{
            Debug.log("cover button3");            
        });
        button3.setToDownAction((ui)->{
            Debug.log("click button3");
        });
        
        Vector buttonlistpos=new Vector(100,100,0);
        buttonList=new UIButtonList("buttonList",buttonlistpos);
        buttonList.addUiButton(button1);
        buttonList.addUiButton(button2);
        buttonList.addUiButton(button3);
        buttonList.activeUiButton(0);
        return true;
    }        
}
