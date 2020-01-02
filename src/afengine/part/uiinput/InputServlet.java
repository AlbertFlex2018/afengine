package afengine.part.uiinput;

import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;

public class InputServlet implements IMessageHandler{
    
    public static final int INPUT_KEY_DOWN=0;
    public static final int INPUT_KEY_UP=1;
    public static final int INPUT_KEY_TYPE=3;
    
    public static final int INPUT_MOUSE_DOWN=4;
    public static final int INPUT_MOUSE_UP=5;
    public static final int INPUT_MOUSE_CLICK=7;

    public static final int INPUT_MOUSE_MOVE=8;
    public static final int INPUT_MOUSE_DRAG=9;
    public static final int INPUT_MOUSEWHEEL=10;
    
    public static final int INPUT_WINDOW_OPENED=12;
    public static final int INPUT_WINDOW_CLOSE=13;
    public static final int INPUT_WINDOW_CLOSING=18;
    public static final int INPUT_WINDOW_ICONED=14;
    public static final int INPUT_WINDOW_DEICONED=15;
    public static final int INPUT_WINDOW_ACTIVE=16;
    public static final int INPUT_WINDOW_DEACTIVE=17;
    
    private final int handleType;
    private final IMessageHandler handler;

    public final String servletName;

    public InputServlet(int type,String name,IMessageHandler handler){
        this.handleType=type;
        this.handler=handler;
        this.servletName=name;
    }
    
    public int getHandleType() {
        return handleType;
    } 

    public IMessageHandler getHandler() {
        return handler;
    }
    
    @Override
    public boolean handle(Message msg){
        if(handler!=null){
            return handler.handle(msg);
        }
        return false;
    }
}
