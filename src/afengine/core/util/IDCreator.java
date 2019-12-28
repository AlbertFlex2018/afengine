/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

/**
 * provide a global unique id for app.<br>
 * if you get a extern id for create,then you should use validId to check this id.<br>
 * if you get a extern id for the previously created to init the IDCreator,than you should createIDCreator<br> 
 * if you want get a unique id ,then use static method createId.
 * @author Albert Flex
 */
public class IDCreator {

    private static long instance_id=0;    
    public static void createIDCreator(long lastId)
    {
        instance_id=lastId+1;
    }
    public static long createId()
    {
        return ++instance_id;
    }

    public static long validId(long id)
    {
        //if extern id bigger than noted id,
        //synchronized the noted id
        if(id>IDCreator.instance_id){
            instance_id=id+1;            
        }
        return id;
    }
}

