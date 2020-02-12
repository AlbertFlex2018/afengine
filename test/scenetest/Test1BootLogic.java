package scenetest;

import afengine.core.IAppLogic;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import java.awt.event.KeyEvent;

/**
 *
 * @author Admin
 */
public class Test1BootLogic implements IAppLogic{
    
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/asset/scenetest-test1-boot.xml");
    }
    
    @Override
    public boolean init() {
        Debug.log("init logic");
        return true;
    }

    @Override
    public boolean update(long l) {
        return true;
    }

    @Override
    public boolean shutdown() {
        Debug.log("shutdown logic");
        return true;
    }    
    public static class MenuServlet implements IMessageHandler{
        private int i=1;
        @Override
        public boolean handle(Message msg){
            KeyEvent key = (KeyEvent)msg.extraObjs[0];
            int code=key.getKeyCode();
            switch (code) {
                case KeyEvent.VK_DOWN:
                    if(i==3){
                        i=1;
                    }else ++i;
                    Debug.log("down to - "+i);
                    return false;
                case KeyEvent.VK_UP:
                    if(i==1){
                        i=3;
                    }else --i;
                    Debug.log("up to - "+i);
                    return true;
                case KeyEvent.VK_ENTER:
                    Debug.log("enter to - "+i);
                    return true;                
                default:
                    break;
            }
            
            return false;
        }        
    }
}
