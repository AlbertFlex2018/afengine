package part.uiinput;

import afengine.core.util.Debug;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.control.UIInputLine;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import part.scene.AppLogicBase;

public class UIInputLineTestLogic extends AppLogicBase{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/inputtest4.xml");
    }
    
    public static class Listener implements KeyListener,MouseListener{
        private Message createMsg(long type,KeyEvent obj,int code){
            Message msg=new Message(0,type,code,"",new Object[]{obj},
                System.currentTimeMillis(),0,0,null,0,0,0);
            return msg;            
        }
        private Message createMsg(long type,MouseEvent obj){
            Message msg=new Message(0,type,0,"",new Object[]{obj},
                System.currentTimeMillis(),0,0,null,0,0,0);
            return msg;            
        }
        private void sendMsg(Message msg){           
            if(ui!=null)
                ui.handle(msg);
        }        
        @Override
        public void keyTyped(KeyEvent e) {
            Debug.log("char:"+e.getKeyChar());
            Message msg=createMsg(InputServlet.INPUT_KEY_TYPE,e,(int)e.getKeyChar());            
            sendMsg(msg);
        }

        @Override
        public void keyPressed(KeyEvent e){}

        @Override
        public void keyReleased(KeyEvent e){
            Message msg=createMsg(InputServlet.INPUT_KEY_UP,e,e.getKeyCode());    
            Debug.assetTrue(msg.msgContent==e.getKeyCode(),"content failed.");
            sendMsg(msg);        
        }

        @Override
        public void mouseClicked(MouseEvent e){
            Message msg=createMsg(InputServlet.INPUT_MOUSE_CLICK,e);
            sendMsg(msg);            
        }

        @Override
        public void mousePressed(MouseEvent e){}
        @Override
        public void mouseReleased(MouseEvent e){}
        @Override
        public void mouseEntered(MouseEvent e){}
        @Override
        public void mouseExited(MouseEvent e){}                
    }
    
    public static class Draw1 implements IDrawStrategy{
        @Override
        public void draw(IGraphicsTech tech) {
            if(ui!=null){
                ui.draw(tech);
            }
        }        
    }
    
    public static UIInputLine ui;
    public static Text widtht=new Text("width:");
    public static Text heightt=new Text("height:");
    @Override
    public boolean update(long time){
        if(ui!=null){
            ui.update(time);
            widtht.value="width:"+ui.getWidth();
            heightt.value="height:"+ui.getHeight();
        }
        return true;
    }

    @Override
    public boolean init(){
        Vector pos=new Vector(100,100,0,0);
        ui=new UIInputLine("InputLine",pos,20,null,null);
        ui.setText("Albert Flex");
        Debug.log_panel(widtht);
        Debug.log_panel(heightt);
        return true;
    }        
}
