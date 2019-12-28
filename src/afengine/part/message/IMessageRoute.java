/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.message;

/**
 * contains messageRoute for Message<br>
 * you should add a instance for IMessageRoute to MessageCenter<br>
 * and when a message should be dispatch,MessageCenter will select a  route matched MessageRoute,<br>
 * and call routeMessage
 * @see Message
 * @see MessageCenter
 * @author Albert Flex
 */
public interface IMessageRoute{
    public long getRouteType();    
    public void routeMessage(Message msg);
}
