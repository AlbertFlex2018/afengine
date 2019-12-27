/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class ActorComponent implements IMessageHandler{
    
    public static Map<String,IComponentFactory> factoryMap=new HashMap<>();
    public static void addFactory(String compname,IComponentFactory factory){
        if(factoryMap.containsKey(factory.getName()))return;
        
        factoryMap.put(factory.getName(), factory);
    }
    public static boolean hasFactory(String compname){
        return factoryMap.containsKey(compname);
    }
    public static void removeFactory(String compname){
        factoryMap.remove(compname);
    }    
    public static IComponentFactory getFactory(String name){
        return factoryMap.get(name);
    }

    
    private Actor actor;
    private final String componentName;
    private boolean active;
    public final Map<String,Object> valueMap=new HashMap<>();
    
    public ActorComponent(String compname) {
        this.componentName = compname;
    }

    public final Actor getActor() {
        return actor;
    }
    
    public final String getComponentName() {
        return componentName;
    }

    public final boolean isActive() {
        return active;
    }

    public final void awake() {
        if (active) {
            return;
        }
        active = true;
        toWake();
    }

    public final void asleep() {
        if (active == false) {
            return;
        }
        active = false;
        toSleep();
    }

    public final void setActor(Actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean handle(Message msg) {
        return false;
    }

    public void update(long time){
    }

    public void toWake() {
    }
    
    public void toSleep() {
    }    
}
