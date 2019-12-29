/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

import afengine.core.AbApp;
import afengine.core.AbPartSupport;
import afengine.core.IAppLogic;
import afengine.core.ServiceApp;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * provide app boot from xml config.<br>
 * and the xml config must have afengine tag,apptypebased type,and partsboot,partsconfig tag.
 * @see IXMLPartBoot
 * @see IXMLAppTypeBoot
 * @author Albert Flex
 */
public class XMLEngineBoot {
   /**
    * <app>
    *     <afengine debug="" logicpath="" typeboot="" type=""/>
    *    window <window title="" icon="" size="800,600" render=""/> 
    *    or window <window title="" icon="" full="true" render=""/>
    *    or service <service name=""/>
    *     <partsboot>
    *       <part name="" path=""/>
    *       <part name="" path=""/>
    *       <part name="" path=""/>
    *       <part name="" path=""/>
    *     </partsboot>
    *     <partsconfig>
    *           <PartName/>
    *           <PartName/>
    *           <PartName/>
    *           ...
    *     </partsconfig>
    * </app>
    */
    public static void bootEngineFromXML(Element element){                
        AbApp app;
        Map<String,IXMLPartBoot> bootMap=new HashMap<>();
        IXMLAppTypeBoot appboot=null;

        Element afe = element.element("afengine");        

        String debugs=afe.attributeValue("debug");
        if(debugs!=null&&debugs.equals("true")){
            Debug.enable();
        }
        
        String typeboot = afe.attributeValue("typeboot");
        String type=afe.attributeValue("type");
        if(type==null){
            type=ServiceApp.APPTYPE;
        }
        try{
            Class<?> cls = Class.forName(typeboot);
            Object obj=cls.newInstance();
            appboot = (IXMLAppTypeBoot)obj;
        }catch(Exception ex){
            ex.printStackTrace();
            Debug.log("typeboot error!");
            return;
        }                
        
        if(appboot==null){
            Debug.log("typeboot error!");
            return;
        }
        
        Element typee = element.element(type);
        app=appboot.bootApp(typee);
        
        if(app==null){
            System.out.println("app create error!");
            return;
        }
        
        
        Debug.log("boot app type:"+app.getAppType());
        Debug.log("set app name:"+app.getAppName());        
        
        //put part boot
        Debug.log("-put part boot-");
        Element partlist = element.element("partsboot");
        Iterator<Element> eleiter = partlist.elementIterator();
        while(eleiter.hasNext()){
            Element ele = eleiter.next();
            String path = ele.attributeValue("path");            
            String bootname=ele.attributeValue("name");
            try{
                Class<?> cls = Class.forName(path);
                Object obj=cls.newInstance();
                IXMLPartBoot boot=(IXMLPartBoot)obj;
                bootMap.put(bootname, boot);
                Debug.log("add part boot - "+bootname);
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }            
        }
                
        //part boot
        Debug.log("-start part config-");
        Element partconfigs = element.element("partsconfig");
        eleiter=partconfigs.elementIterator();
        int priority=100;
        while(eleiter.hasNext()){
            Element ele = eleiter.next();
            String ename = ele.getName();
            IXMLPartBoot boot = bootMap.get(ename);
            if(boot==null){
                Debug.log("no boot for:"+ename);
                continue;
            }
            AbPartSupport part=boot.bootPart(ele);
            if(part==null){
                Debug.log("The Part for PartBoot["+ename+"]is Null: Don't you want put part to partsManager?");
                continue;
            }
            app.getPartsManager().addPartPriority(--priority, part);
            Debug.log("boot part - "+ename);
        }
        
        //create logic and run.
        String logicpath = afe.attributeValue("logicpath");
        IAppLogic logic;
        try{
                Class<?> cls = Class.forName(logicpath);
                Object obj=cls.newInstance();
                logic = (IAppLogic)obj;
         }catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
                Debug.log("Logic error!");
                return;
         }      
        Debug.log("boot app to logic..");
        app.run(logic);
    }    
    
    public static Document getXMLFileRoot(String xmlpath){
        File file = new File(xmlpath);
        SAXReader reader =new SAXReader();
        Document doc;
        try{
            doc = reader.read(file);            
            System.out.println("root : "+doc.getRootElement().getName());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return doc;
    }
    
    public static void bootEngine(String xmlpath){
        Element root=getXMLFileRoot(xmlpath).getRootElement();
        if(root!=null){
            bootEngineFromXML(root);
        }
    }
    
    public static Object instanceObj(String classpath){
        try{
                Class<?> cls = Class.forName(classpath);
                Object obj=cls.newInstance();
                return obj;
         }catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
                Debug.log("Class Not Found : "+classpath);
                return null;
         }                      
    }
}
