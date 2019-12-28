/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.message;

import afengine.core.util.IDCreator;
import java.util.HashMap;
import java.util.Map;

/**
 * messageroute for handler<br>
 * you could add type - content map handlers<br>
 * @see IMessageRoute
 * @see BigMessageHandler
 * @author Albert Flex
 */
public class MessageHandlerRoute implements IMessageRoute{        
    
    public final static long ROUTE_ID=IDCreator.createId();
        
    public MessageHandlerRoute() {
        handlersMap=new HashMap<>();
    }
    private final Map<Long,BigMessageHandler> handlersMap;

    public BigMessageHandler getTypeHandler(long msgType) {
        return handlersMap.get(msgType);
    }
    public boolean containTypeHandlers(long msgType)
    {
        return handlersMap.containsKey(msgType);
    }
    public void addTypeHandler(long msgType,BigMessageHandler handlers)
    {
        if(!containTypeHandlers(msgType))
        {
            handlersMap.put(msgType, handlers);
        }
    }
    public void addIHandler(long msgType,long msgContent,IMessageHandler handler){
        BigMessageHandler handlers = handlersMap.get(msgType);
        if(handlers==null){
            handlers = new BigMessageHandler();
            handlersMap.put(msgType, handlers);
        }
        handlers.addIHandler(msgContent, handler);
    }
    public void removeTypeHandler(long msgType)
    {
        handlersMap.remove(msgType);
    }
    
    @Override
    public long getRouteType() {
        return MessageHandlerRoute.ROUTE_ID;
    }

    @Override
    public void routeMessage(Message message) {
        BigMessageHandler handlers = handlersMap.get(message.msgType);
        if(handlers==null)
        {
            System.out.println("No message bighandler for msgType.");
            return;
        }
        if(!handlers.handle(message))
        {
            System.out.println("Solve for msg is failed,msg :");
        }
    }
}
