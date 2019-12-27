/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

/**
 *
 * @author Admin
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
        if(id>IDCreator.instance_id)
        {
            instance_id=id+1;
        }
        return id;
    }
}

