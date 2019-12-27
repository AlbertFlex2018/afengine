/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core;

import afengine.core.util.IXMLAppTypeBoot;
import org.dom4j.Element;

/**
 *
 * @author Admin
 */
public class ServiceApp extends AbApp{
    
    public static final String APPTYPE="service";
    public ServiceApp() {
        super(ServiceApp.APPTYPE);
    }

    @Override
    public boolean init() {
        System.out.println("Start Service.."+this.getAppName());
        return true;
    }

    int ttime=0;
    @Override
    public boolean update(long time) {
        System.out.println("Update Service.."+this.getAppName());
        return true;
    }

    @Override
    public boolean shutdown() {
        System.out.println("ShutDown Service..");
        return true;
    }    
    public static class ServiceAppBoot implements IXMLAppTypeBoot{

        @Override
        public AbApp bootApp(Element element) {
            AbApp app = new ServiceApp();
            String name = element.attributeValue("name");
            app.setAppName(name);
            return app;
        }        
    }
}
