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
 * a component contains for scene.<br>
 * main topic for app game.<br>
 * you can create actor,then add actor to scene,<br>
 * scene will manager actor lifespan<br>
 * @see ActorComponent
 * @see Scene
 * @author Albert Flex
 */
public class Actor implements IMessageHandler{
    
    private Transform transform;
    public final long id;
    private String name;
    public final Map<String, Object> valueMap = new HashMap<>();
    private boolean deleted=false;//remove only!

    private Actor parent;
    private final List<Actor> children = new ArrayList<>();
    //下一个层次的孩子节点的map
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
    
    //从直属的孩子节点之中找到符合id的
    public final Actor getChild(long id){
        return childMap.get(id);
    }
    
    //循环迭代孩子实体，找到符合id的
    public final Actor findChild(long id){
        if(this.id==id)
            return this;
        
        Iterator<Actor> childiter = children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            if(child.findChild(id)!=null)
                return child;
        }
        
        return null;
    }
    
    //从直属孩子节点之中查找符合name的
    public final Actor getChild(String name){
        Iterator<Actor> childiter = children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();            
            if(child.name.equals(name)){
                return child;
            }
        }                        
        return null;
    }
    
    //迭代循环孩子实体，找到符合name的实体
    public final Actor findChild(String name){
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
    
    //添加到直属孩子节点之中
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

    //从直属孩子节点之中删除
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

    //消息转交由组件应答
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
        if(deleted)
            return;

        Iterator<ActorComponent> compiter = componentsMap.values().iterator();
        while(compiter.hasNext()){
            ActorComponent comp = compiter.next();            
            if(comp.isActive()){
                comp.update(time);                
            }
        }
        
        //update child
        Iterator<Actor> childiter = children.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            if(child.deleted){
                deletedchildlist.add(child);
            }
            child.updateActor(time);
        }
        
        //remove deleted actor
        childiter=deletedchildlist.iterator();
        while(childiter.hasNext()){
            Actor child = childiter.next();
            children.remove(child);
        }        
        deletedchildlist.clear();
    }    
}
