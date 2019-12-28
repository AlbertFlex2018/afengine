/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

import afengine.core.util.Transform;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Albert Flex
 */
public class Actor implements IMessageHandler{
    
    private Transform transform;
    public final long id;
    private String name;
    public final Map<String, Object> valueMap = new HashMap<>();
    private boolean deleted=false;

    private Actor parent;
    private final List<Actor> children = new ArrayList<>();
    private final Map<Long,Actor> childMap = new HashMap<>();
    private final Map<String, ActorComponent> componentsMap = new HashMap<>();
    
    public Actor(long id,String name,Transform transform){
        this.id=id;
        this.name=name;
        this.transform=transform;
    }

    public final void setTransform(Transform transform) {
        this.transform = transform;
    }    

    public final Transform getTransform() {
        return transform;
    }
   
    public final Map<String, ActorComponent> getComponentsMap() {
        return componentsMap;
    }

    public final boolean hasComponent(String compname) {        
        return componentsMap.containsKey(compname);
    }
    
    public final void addComponent(ActorComponent comp){
        if(componentsMap.containsKey(comp.getComponentName()))return;        
        
        if(comp.getActor()!=null){
            comp.getActor().removeComponent(comp.getComponentName());
        }
        componentsMap.put(comp.getComponentName(), comp);
        comp.setActor(this);
    }
    public final void removeComponent(String compname){

        if(!componentsMap.containsKey(compname))return;        
        ActorComponent comp=componentsMap.remove(compname);        
        comp.setActor(null);        
    }
    public final ActorComponent getComponent(String compname){
        return componentsMap.get(compname);
    }

    public final String getName() {
        return name;
    }

    public final Actor getParent() {
        return parent;
    }

    public final List<Actor> getChildren() {
        return children;
    }
    
    public final Actor getChild(long id){
        return childMap.get(id);
    }
    
    public final Actor getChild(String name){
        if(this.name.equals(name))
            return this;
        
        Iterator<Actor> childiter = children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();            
            if(child.getChild(name)!=null){
                return child;
            }
        }                
        
        return null;
    }    
    
    public final void addChild(Actor child) {
        if(childMap.containsKey(id)){
            return;
        }

        children.add(child);
        if (child.parent != null){
            child.parent.removeChild(child.id);
        }
        child.parent = this;
        childMap.put(child.id, child);
    }

    public final void removeChild(long actorid) {
        Actor child=childMap.remove(actorid);
        children.remove(child);
        child.parent = null;        
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setParent(Actor parent) {
        this.parent = parent;
    }

    public void removeThisActor(){
        deleted=true;
    }

    @Override
    public boolean handle(Message msg){
        
        Iterator<ActorComponent> compiter = componentsMap.values().iterator();
        while(compiter.hasNext()){
            ActorComponent comp = compiter.next();
            if(comp.handle(msg))return true;
        }        
        return false;
    }
    
    List<Actor> deletedchildlist = new ArrayList<>();
    public final void updateActor(long time){
        Iterator<ActorComponent> compiter = componentsMap.values().iterator();
        while(compiter.hasNext()){
            ActorComponent comp = compiter.next();            
            if(comp.isActive()){
                comp.update(time);                
            }
        }
        
        
        Iterator<Actor> childiter = children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            if(child.deleted){
                deletedchildlist.add(child);
            }
            child.updateActor(time);
        }
        
        childiter=deletedchildlist.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            children.remove(child);
        }        
        deletedchildlist.clear();
    }    
}
