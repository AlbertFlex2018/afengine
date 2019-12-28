/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core;

import java.util.HashMap;
import java.util.Map;

/**
 * AbPartSupport is a part for App,<br>
 * When you created a Instance for App, you could add Some Part to App Core ,and let themselves get their lifespan<br>
 * One AbPartSupport has init,update,and shutdown,and initPart for the PartsManager<br>
 * Create a Instance for app, and Create Some Instances for partsupports,<br>
 * add these partsupports to app.PartsManager on order, cause there has a priority for the part init,update,and shutdown<br>
 * @see AppPartsManager
 * @author Albert Flex
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

