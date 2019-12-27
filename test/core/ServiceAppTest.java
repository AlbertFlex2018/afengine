/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import afengine.core.AbApp;
import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.ServiceApp;
import afengine.core.util.Debug;
import java.util.Scanner;


public class ServiceAppTest implements IAppLogic{
    
    public static void main(String[] args) {
        AbApp app = new ServiceApp();
        app.setAppName("App1");
        IAppLogic logic = new ServiceAppTest();
        app.run(logic);
    }
    
    private Scanner scan;
    @Override
    public boolean init() {
        scan = new Scanner(System.in);    
        return true;
    }
    
    @Override
    public boolean update(long time) {
        String word = scan.nextLine();
        if(word.equals("info")){
            Debug.log("App type:"+AppState.getRunningApp().getAppType()+",name:"+
                    AppState.getRunningApp().getAppName());
        }        
        else if(word.equals("exit")){
            AppState.setValue("run","false");
        }
        else{
            Debug.log(word);
        }
        return true;
    }

    @Override
    public boolean shutdown() {
        return true;
    }    
}
