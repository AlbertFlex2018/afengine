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
 * component for actor<br>
 * @see Actor
 * @author Albert Flex
 */
public class ActorComponent implements IMessageHandler{
    
    public static Map<String,IComponentFactory> factoryMap=new HashMap<>();
    public static void addFactory(String compname,IComponentFactory factory){
        if(factoryMap.containsKey(compname))return;
        
        factoryMap.put(compname, factory);
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

    public final Map<String,String> attributes=new HashMap<>();
    private Actor actor;
    private final String componentName;
    private boolean active;
    
    public ActorComponent(String compname) {
        this.componentName = compname;
        actor=null;
        active=false;
    }

    public final Actor getActor() {
        return actor;
    }
    
    public static final String getRealValue(String value,Map<String,String> actorvalues){
        if(!value.startsWith("#"))
            return value;
        else{
            return value.substring(1,value.length());
        }
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
