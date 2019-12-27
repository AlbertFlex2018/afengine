/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.message;

/**
 *
 * @author Admin
 */
public interface IMessageHandler{
    public boolean handle(Message msg);
}
