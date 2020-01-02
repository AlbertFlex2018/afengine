package afengine.part.uiinput;

import afengine.core.util.Debug;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UIFace implements IMessageHandler{    
    
    private String faceName;
    
    private final List<UIActor> actorList;//用以更新和绘制的界面列表,均只考虑一个界面没有子界面面的情况
    private final List<UIActor> reservedActorList;//保存可以访问的更新列表
    private final Map<Long,List<UIActor>> msgTypeUIMap;//已经将消息和界面绑定的界面列表

    public UIFace(String faceName) {
        this.faceName = faceName;
        actorList=new LinkedList<>();
        reservedActorList=new LinkedList<>();
        msgTypeUIMap=new HashMap<>();
    }

    public String getFaceName() {
        return faceName;
    }

    public void setFaceName(String faceName){        
        this.faceName = faceName;
        actorList.forEach((ui) -> {
            ui.setFaceName(faceName);
        });
    }
    
    public UIActor findUiInReserved(String uiname){
        for(UIActor ui : reservedActorList){
            if(ui.getUiName().equals(uiname))
                return ui;
        }
        return null;
    }
    public UIActor findUiInAll(String uiname){
        for(UIActor ui : actorList){
            UIActor dest=findUiInAll_Imp(ui,uiname);
            if(dest!=null)return dest;
        }
        return null;        
    }
    //寻找包括子节点里面的界面
    private UIActor findUiInAll_Imp(UIActor actor,String uiname){
        if(actor.getUiName().equals(uiname)){
            return actor;
        }
        
        List<UIActor> child=actor.getUiChildren();
        for(UIActor ui:child){
            UIActor dest=findUiInAll_Imp(ui,uiname);
            if(dest!=null)return dest;
        }
        return null;
    }
    
    public boolean containsUiInAll(String uiname){   
        UIActor ui=findUiInAll(uiname);
        return ui!=null;
    }
    public boolean containsUiInReserved(String uiname){
        if (reservedActorList.stream().anyMatch((ui) -> (ui.getUiName().equals(uiname)))) {
            return true;
        }        
        return false;
    }
    
    public boolean addUiInAll(UIActor actor){
        if(!containsUiInAll(actor.getUiName())){
            actorList.add(actor);
            actor.setFaceName(faceName);
            return true;
        }
        else{
            Debug.log("already has ui for"+actor.getUiName());
            return false;
        }
    }    
    public void addUiInAllAndReserve(UIActor actor){
        if(addUiInAll(actor)){
            reservedActorList.add(actor);
        }else{
            Debug.log("add ui failed.");
        }
    }
    //删除列表里的ui之后，会自动将可访问的ui删除
    public void removeUiInAll(String uiname){
        UIActor dest=null;
        for(UIActor ui : actorList){
            if(ui.getUiName().equals(uiname)){
                dest=ui;
                break;
            }                
        }
        if(dest==null){
            Debug.log("does not found "+uiname);
        }else{
            actorList.remove(dest);
            reservedActorList.remove(dest);
        }
    }
    public void removeUiInReserved(String uiname){
        UIActor dest=null;
        for(UIActor ui : reservedActorList){
            if(ui.getUiName().equals(uiname)){
                dest=ui;
                break;
            }                
        }
        if(dest==null){
            Debug.log("does not found "+uiname);
        }else{
            reservedActorList.remove(dest);
        }        
    }
    public List<UIActor> getActorList() {
        return actorList;
    }
    public List<UIActor> getReservedActorList() {
        return reservedActorList;
    }
    public Map<Long, List<UIActor>> getMsgUIMap() {
        return msgTypeUIMap;
    }
    
    //交由msgUIMap处理
    @Override
    public boolean handle(Message msg){
        List<UIActor> handList=msgTypeUIMap.get(msg.msgType);
        if(handList==null){
            Debug.log("not hand for msg type:"+msg.msgType);
            return false;
        }
        return handList.stream().anyMatch((ui) -> (ui.handle(msg)));
    }    
}
