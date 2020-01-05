package afengine.part.uiinput;

import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;

public class InputServlet implements IMessageHandler{
    
    public static final long INPUT_KEY_DOWN=0;
    public static final long INPUT_KEY_UP=1;
    public static final long INPUT_KEY_TYPE=2;
    
    public static final long INPUT_MOUSE_DOWN=3;
    public static final long INPUT_MOUSE_UP=4;
    public static final long INPUT_MOUSE_CLICK=5;

    public static final long INPUT_MOUSE_MOVE=6;
    public static final long INPUT_MOUSE_DRAG=7;
    public static final long INPUT_MOUSEWHEEL=8;
    
    public static final long INPUT_WINDOW_OPENED=9;
    public static final long INPUT_WINDOW_CLOSE=10;
    public static final long INPUT_WINDOW_CLOSING=11;
    public static final long INPUT_WINDOW_ICONED=12;
    public static final long INPUT_WINDOW_DEICONED=13;
    public static final long INPUT_WINDOW_ACTIVE=14;
    public static final long INPUT_WINDOW_DEACTIVE=15;
    public static final long INPUT_WINDOW_MOUSEENTER=16;
    public static final long INPUT_WINDOW_MOUSEEXIT=17;
    private final long handleType;
    private final IMessageHandler handler;

    public final String servletName;

    public InputServlet(long type,String name,IMessageHandler handler){
        this.handleType=type;
        this.handler=handler;
        this.servletName=name;
    }
    
    public long getHandleType() {
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
