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
public class AppState {
    static{
        AppState.valueMap=new HashMap<>();
        AppState.valueMap.put("run","true");        
    }
    private static boolean created=false;
    private static AbApp runningApp;
    private static Map<String,String> valueMap;
    public static String getValue(String name){
        return valueMap.get(name);
    }
    public static void setValue(String name,String value){
        valueMap.put(name, value);
    }
    public static boolean hasValue(String name){
        return valueMap.containsKey(name);
    }
    public static AbApp getRunningApp() {
        if(!created){
            System.out.println("there is no runningApp.");
            return null;
        }
        return runningApp;
    }
    public static boolean createState(AbApp app){
        if(created){
            System.out.println("It already have a instance of App.Should be exit the following.");
            return false;
        }
        AppState.runningApp=app;
        created=true;
        return true;
    }    
}
