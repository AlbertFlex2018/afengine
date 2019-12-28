/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * BigMessageHandler ,contains some Content relatived IMessageHandler<br>
 * you should add a bigMessageHandler instance to MessageHandlerRoute from MessageCenter as a messageType handler<br>
 * then add certain Content Message IMessageHandler to this bigHandlers<br>
 * @see MessageHandlerRoute
 * @author Albert Flex
 */
public class BigMessageHandler implements IMessageHandler{
    
    private final Map<Long,List<IMessageHandler>> msgContentHandlesMap;
    
    public BigMessageHandler(){
        msgContentHandlesMap=new HashMap<>();
    }
    
    @Override
    public boolean handle(Message msg) {
        if(!msgContentHandlesMap.containsKey(msg.msgContent))
        {
            System.out.println("Handle msg failed: no msgConten handle for this msg.");
            return false;
        }
        Iterator<IMessageHandler> handleriter=msgContentHandlesMap.get(msg.msgContent).iterator();
        while(handleriter.hasNext())
        {
            IMessageHandler handler=handleriter.next();
            if(handler==null)
                continue;
            if(handler.handle(msg)==true)
            {
                return true;
            }
        }
        
        return false;
    }    
    
    public void addIHandler(long msgContent,IMessageHandler handler)
    {
        List<IMessageHandler> handlerlist=null;
        if(!msgContentHandlesMap.containsKey(msgContent))
        {
            handlerlist=new ArrayList<>();
            msgContentHandlesMap.put(msgContent, handlerlist);
        }
        handlerlist.add(handler);
    }
    public void removeIHandler(long type,IMessageHandler handler)
    {        
        if(!msgContentHandlesMap.containsKey(type))
        {
            System.out.println("Handle remove failed: no this type for Handlelist");
            return;            
        }
        List<IMessageHandler> handlerlist=msgContentHandlesMap.get(type);
        handlerlist.remove(handler);
    }        
}
