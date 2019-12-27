/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part.message;

import afengine.core.util.IDCreator;
import afengine.part.message.IMessageHandler;
import afengine.part.message.IMessageRoute;
import afengine.part.message.Message;
import afengine.part.message.MessageCenter;
import afengine.part.message.MessageHandlerRoute;

public class MessageCenterTest{

    public static void main(String[] args) {
        
        MessageCenter center = MessageCenter.getInstance();
        
        MessageHandlerRoute handleRoute = new MessageHandlerRoute();

        center.addRoute(handleRoute);        
        center.addRoute(route2);
        center.updateRoute();
        
        long type1=0,type2=1,type3=2;
        
        handleRoute.addIHandler(type1,0,handler1);// 0 - 0 - handler1
        handleRoute.addIHandler(type1,1,handler2);// 0 - 1 - handler2
        handleRoute.addIHandler(type2,2, handler3);//1 - 2 - handler3
                
        Message msg0 = BigMessageHandlerTest.createMessage(handleRoute.getRouteType(),0,0);
        Message msg1 = BigMessageHandlerTest.createMessage(handleRoute.getRouteType(),0,1);
        Message msg2 = BigMessageHandlerTest.createMessage(handleRoute.getRouteType(),1,1);
        Message msg3 = BigMessageHandlerTest.createMessage(handleRoute.getRouteType(),1,2);
        Message msg4 = BigMessageHandlerTest.createMessage(route2.getRouteType(),1,2);
        
        center.sendMessage(msg0);
        center.sendMessage(msg1);
        center.sendMessage(msg2);
        center.sendMessage(msg3);
        center.sendMessage(msg4);
        
        center.updateSendMessage(10);
    }
            
    private static IMessageHandler handler1 = (Message msg)->{
        System.out.println("type:"+msg.msgType+",handler 1 :"+msg.msgContent);
        return true;
    };
    private static IMessageHandler handler2 = (Message msg)->{
        System.out.println("type:"+msg.msgType+",handler 2 :"+msg.msgContent);
        return true;
    };
    private static IMessageHandler handler3 = (Message msg)->{
        System.out.println("type:"+msg.msgType+",handler 3 :"+msg.msgContent);
        return true;
    };
    
    private static IMessageRoute route2 = new IMessageRoute(){
        
        private long id = IDCreator.createId();
        @Override
        public long getRouteType() {
            return id;
        }
        @Override
        public void routeMessage(Message msg) {
            System.out.println("route route2 ");
        }        
    };
}
