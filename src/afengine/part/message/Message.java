/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.message;

/**
 * a message for dispath<br>
 * you should create a Message and then use it.<br>
 * commonly created a instance of Message,and then use MessageCenter.sendMessage(msg) to sendMessage,<br>
 * this will select matched MessageRoute and etc.<br>
 * @see MessageCenter
 * @see IMessageRoute
 * @author Albert Flex
 */
public class Message {    
    
    public final long routeType;//路由类型
    
    public final long msgType;//消息类型
    public final long msgContent;//消息内容
    public final String msgInfo;//消息提示
    public final Object[] extraObjs;//消息附加内容
    
    public final long timetamp;//时间戳
    public final long delaytime;//发送延迟时间
    public final long senderType;//发送方类型
    public final Object senderObj;//发送方主体

    public final long receiveType;//接收方类型
    public final long senderId;//发送方Id
    public final long receiveId;//接收方Id

    public Message(long routeType, long msgType, long msgContent, 
            String msgInfo, Object[] extraObjs, long timetamp, 
            long delaytime, long senderType, Object senderObj, 
            long receiveType, long senderId, long receiveId) {
        this.routeType = routeType;
        this.msgType = msgType;
        this.msgContent = msgContent;
        this.msgInfo = msgInfo;
        this.extraObjs = extraObjs;
        this.timetamp = timetamp;
        this.delaytime = delaytime;
        this.senderType = senderType;
        this.senderObj = senderObj;
        this.receiveType = receiveType;
        this.senderId = senderId;
        this.receiveId = receiveId;
    }
    
}
