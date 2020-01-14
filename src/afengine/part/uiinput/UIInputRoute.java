package afengine.part.uiinput;

import afengine.core.util.IDCreator;
import afengine.part.message.IMessageRoute;
import afengine.part.message.Message;
import java.util.List;
import java.util.Map;

public class UIInputRoute implements IMessageRoute{
    public static final long ROUTE_ID=IDCreator.createId();
    private final UIInputCenter center;
    public UIInputRoute(){
        center=UIInputCenter.getInstance();
    }
    
    @Override
    public long getRouteType() {
        return UIInputRoute.ROUTE_ID;
    }
    
    //先通过先界面过滤列表
    //后通过已激活界面或者弹出界面
    //最后通过后界面过滤列表
    @Override
    public void routeMessage(Message msg){
        Map<Long,List<InputServlet>> preServlets=center.getPreServlets();
        List<InputServlet> slist=preServlets.get(msg.msgType);
        if(slist!=null){
            for(InputServlet ser:slist){
                if(ser.handle(msg))
                    return;
            }
        }
        
        UIFace popupFace=center.getPopupFace();
        if(popupFace!=null){
            if(popupFace.handle(msg))
                return;
        }else{
            List<UIFace> activedFaces=center.getActivedfaceList();
            for(UIFace face:activedFaces){
                if(face.handle(msg))
                    return;
            }
        }
        
        Map<Long,List<InputServlet>> afterServlets=center.getPreServlets();
        slist=afterServlets.get(msg.msgType);
        if(slist!=null){
            for(InputServlet ser:slist){
                if(ser.handle(msg))
                    return;
            }
        }        
    }    
}
