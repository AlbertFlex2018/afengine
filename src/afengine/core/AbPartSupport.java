/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public abstract class AbPartSupport implements IAppLogic{    

    private final String name;
    protected final Map<String,Object> objMap=new HashMap<>();
    public AbPartSupport(String name){
        this.name=name;
    }
    public Object getObj(String name){
        return objMap.get(name);
    }
    public String getName() {
        return name;
    }
    boolean inited=false;
    public void initPart(){
        if(inited==false){
            init();
            inited=true;
        }
    }
}

