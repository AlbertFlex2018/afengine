package all;

import afengine.core.AppState;
import afengine.core.util.Debug;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.control.IUIAction;
import java.awt.event.KeyEvent;

/**
 *
 * @author Admin
 */
public class Actions {
    public static class ExitServlet implements IMessageHandler{
        @Override
        public boolean handle(Message msg){
            long type=msg.msgType;
            if(type==InputServlet.INPUT_KEY_UP){
                KeyEvent event=(KeyEvent) msg.extraObjs[0];
                if(event.getKeyCode()==KeyEvent.VK_ESCAPE){
                    AppState.setValue("run","false");
                    return true;
                }
            }
            return false;
        }    
    }
    public static class ButtonDown1 implements IUIAction{
        @Override
        public void action(UIActor ui) {
            Debug.log("button down1");
        }
    }
    public static class ButtonDown2 implements IUIAction{
        @Override
        public void action(UIActor ui) {
            Debug.log("button down2");
        }
    }
    public static class ButtonDown3 implements IUIAction{
        @Override
        public void action(UIActor ui) {
            Debug.log("button down3 exit...");
            AppState.setValue("run","false");
        }
    }    
}
