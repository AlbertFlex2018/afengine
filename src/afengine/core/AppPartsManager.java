/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class AppPartsManager {
    
    //分布部分的优先数越大，越先进行处理
    private final Map<String,AbPartSupport> supportMap;
    private final Map<String,Integer> nameForPriority;
    private final Map<Integer,AbPartSupport> priorityMap;

    public AppPartsManager() {
        supportMap=new HashMap<>();
        nameForPriority=new HashMap<>();
        priorityMap=new HashMap<>();
    }
       
    public Map<String, AbPartSupport> getSupportMap() {
        return supportMap;
    }

    public Map<String, Integer> getNameForPriority() {
        return nameForPriority;
    }

    public Map<Integer, AbPartSupport> getPriorityMap() {
        return priorityMap;
    }
            
    public void removePartSupport(String name){
        if(!nameForPriority.containsKey(name))return;
        
        AbPartSupport support=supportMap.remove(name);        
        priorityMap.remove(nameForPriority.get(name));
        support.shutdown();
        nameForPriority.remove(name);
    }
    public boolean hasPartSupport(String name){        
        return nameForPriority.containsKey(name);
    }
    public int getPartPriority(String name){
        if(!nameForPriority.containsKey(name))return -1;
        return nameForPriority.get(name);
    }
    public AbPartSupport getPartSupport(String name)
    {
        if(!hasPartSupport(name))
        {
            System.out.println("There is no partsupport for:"+name);
            return null;
        }
        
        return supportMap.get(name);
    }
    public boolean changePartPriority(String name,int priority){
        if(!nameForPriority.containsKey(name))return false;
        
        int prePriority=nameForPriority.get(name);
        AbPartSupport support = supportMap.get(name);
        nameForPriority.replace(name, priority);
        priorityMap.remove(prePriority);
        priorityMap.put(priority, support);
        return true;
    }
    public boolean addPartPriority(int priority,AbPartSupport support){
        if(nameForPriority.containsKey(support.getName()))return false;
        
        supportMap.put(support.getName(), support);        
        nameForPriority.put(support.getName(),priority);
        priorityMap.put(priority, support);
        System.out.println("add priority "+priority+",the Part of:"+support.getName());        
        return true;
    }
    
    public void initParts(){
        AbPartSupport[] supportlist=new AbPartSupport[0];
        supportlist=priorityMap.values().<AbPartSupport>toArray(supportlist);
        if(supportlist.length<1)return;

        for(int i=supportlist.length-1;i>-1;--i)
        {            
            AbPartSupport support = supportlist[i];
            support.initPart();
        }        
    }
    public void updateParts(long time){
        AbPartSupport[] supportlist=new AbPartSupport[0];
        supportlist=priorityMap.values().<AbPartSupport>toArray(supportlist);
        if(supportlist.length<1)return;

        for(int i=supportlist.length-1;i>-1;--i)
        {            
            AbPartSupport support = supportlist[i];
            if(support.update(time)==false){
                System.out.println("Update PartSupport error:the Part of:"+support.getName());                
                System.out.println("Will Skip the ParSupport Update:the Part of:"+support.getName());                
            }
        }
    }
    public void shutdownParts(){        
        Iterator<AbPartSupport> iter = priorityMap.values().iterator();
        while(iter.hasNext()){
            AbPartSupport support = iter.next();
            if(support.shutdown()==false){
                System.out.println("Shutdown PartSupport error:the Part of:"+support.getName());                
                System.out.println("Will Skip the ParSupport Shutdown:the Part of:"+support.getName());                
                continue;
            }
            System.out.println("Shutdown successfully the Part of:"+support.getName());
        }
    }
}
