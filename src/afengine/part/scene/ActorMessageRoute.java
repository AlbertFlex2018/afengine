/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

import afengine.core.util.IDCreator;
import afengine.part.message.IMessageRoute;
import afengine.part.message.Message;

/**
 * messageroute for actor<br>
 * @see IMessageRoute
 * @see Actor
 * @author Albert Flex
 */
public class ActorMessageRoute implements IMessageRoute{

    public static final long ROUTE_ID=IDCreator.createId();

    @Override
    public long getRouteType() {
        return ActorMessageRoute.ROUTE_ID;
    }
    
    @Override
    public void routeMessage(Message msg) {
        SceneCenter center = SceneCenter.getInstance();
        Scene nowScene = center.getRunningScene();

        if(nowScene==null)
            return;
        
        long reId=msg.receiveId;
        
        Actor actor = nowScene.findActorByID(reId);
        if(actor!=null){
            actor.handle(msg);
        }
    }    
}
