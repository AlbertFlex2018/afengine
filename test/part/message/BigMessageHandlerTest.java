/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part.message;

import afengine.part.message.BigMessageHandler;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;

/**
 *
 * @author Admin
 */
public class BigMessageHandlerTest{
    
    public static void main(String[] args) {
        BigMessageHandler bighandler = new BigMessageHandler();
        bighandler.addIHandler(0,handler1);
        bighandler.addIHandler(1,handler2);
        Message msg1 = createMessage(0,0,0);
        Message msg2 = createMessage(0,0,1);
        Message msg3 = createMessage(0,0,1);
        Message msg4 = createMessage(0,0,0);
        bighandler.handle(msg1);
        bighandler.handle(msg2);
        bighandler.handle(msg3);
        bighandler.handle(msg4);        
    }
    
    public static Message createMessage(long route,long type,long content){
        Message msg = new Message(route,type,content,
                        "msg",null,System.currentTimeMillis(),
                        0,0,null,
                        0,0,0);
        return msg;
    }
    
    private static final IMessageHandler handler1=(Message msg) -> {
        System.out.println("handler1:"+msg.msgContent);
        return true;        
    };
    private static final IMessageHandler handler2=(Message msg)->{
      System.out.println("handler2:"+msg.msgContent);
      return true;
    };
}
