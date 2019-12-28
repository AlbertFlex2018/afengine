/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core;
/**
 * The App Class is a Runable Application for Users,<br>
 * Make a Impl Class for AbApp,override the init,update,and shutdown methods<br>
 * Create a Instance for AbApp,and Create a AppLogic for your app run,<br>
 * And you can use app.run(logic),run this App or you can use app.run(null) for that no logic.<br>
 * @see IAppLogic
 * @author Albert Flex
 */
public abstract class AbApp implements IAppLogic{
    
    private final String appType;
    private String appName;    
    
    private IAppLogic logic=null;
    private final AppPartsManager partsManager;
    
    public AbApp(String appType){
        partsManager=new AppPartsManager();
        this.appType=appType;
        this.appName="defaultAppName";
    }
    
    public String getAppName() {
        return appName;
    }

    public String getAppType() {
        return appType;
    }

    
    public void setAppName(String appName) {
        this.appName = appName;
    }        

    public IAppLogic getAppLogic() {
        return logic;
    }

    public AppPartsManager getPartsManager() {
        return partsManager;
    }        
        
    public void run(IAppLogic logic){

        if(AppState.createState(this)==false){
            System.out.println("Create for  App :name["+this.appName+"],type["+this.appType+"] is Failed.");
            System.out.println("There is already a app running!.");
            System.out.println("Done to return");            
            return;
        }
        this.logic=logic;

        if(init()==false){
            System.out.println("The App :name["+this.appName+"],type["+this.appType+"] Init is Failed.");            
            return;
        }
        partsManager.initParts();
        if(this.logic!=null){
            if(this.logic.init()==false){
                System.out.println("The AppLogic Init is Failed.");
                return;                
            }
        }

        if(loop()==false){
            System.out.println("The App :name["+this.appName+"],type["+this.appType+"] Loop is Failed.");            
            return;                            
        }
        
        if(this.logic!=null){
            if(this.logic.shutdown()==false){
                System.out.println("The AppLogic Shutdown is Failed.");
                return;                
            }
        }        
        
        partsManager.shutdownParts();
        if(shutdown()==false){
            System.out.println("The App :name["+this.appName+"],type["+this.appType+"] Shutdown is Failed.");            
            return;
        }
        
        System.out.println("The App Run And Exit Successfully...");
        System.exit(0);
    }
    
    private boolean loop(){
        
        long ctime=System.currentTimeMillis();
        long ntime,deltatime;
        while(AppState.getValue("run").equals("true")){
            ntime=System.currentTimeMillis();
            deltatime=ntime-ctime;
            ctime=ntime;
            
            this.partsManager.updateParts(deltatime);
            if(logic!=null){
                if(logic.update(deltatime)==false){
                    System.out.println("The AppLogic Update is Failed.");                    
                    return false;                                    
                }
            }
            if(update(deltatime)==false){
                 System.out.println("The App Update is Failed.");                    
                 return false;                                                    
            }
        }
        return true;
    }
    
    @Override
    public abstract boolean init();
    @Override
    public abstract boolean update(long time);
    @Override
    public abstract boolean shutdown();
}
