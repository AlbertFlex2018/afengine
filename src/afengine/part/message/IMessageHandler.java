/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.message;

/**
 * small handler for certain message<br>
 * @see Message
 * @author Albert Flex
 */
public interface IMessageHandler{
    public boolean handle(Message msg);
}
