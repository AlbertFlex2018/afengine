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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

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
        if(typeboot==null){
            Debug.log("typeboot error,auto to service app boot");            
            appboot=new ServiceApp.ServiceAppBoot();
        }
        else{
            System.out.println("typeboot:"+typeboot);
            try{
                Class<?> cls = Class.forName(typeboot);
                Object obj=cls.newInstance();
                appboot = (IXMLAppTypeBoot)obj;
            }catch(Exception ex){
                ex.printStackTrace();
                Debug.log("typeboot class load error!");
                return;
            }                            
        }
        String type=afe.attributeValue("type");
        if(type==null){
            type=ServiceApp.APPTYPE;
        }
        
        Element typee = element.element(type);
        if(typee==null){
            Debug.log("app type is not found,auto set to service");
            app=new ServiceApp();            
        }else{
            app=appboot.bootApp(typee);
        }
        
        if(app==null){
            System.out.println("app create error!");
            return;
        }
                
        Debug.log("boot app type:"+app.getAppType());
        Debug.log("set app name:"+app.getAppName());        
        
        //put part boot
        Debug.log("-put part boot-");
        Element partlist = element.element("partsboot");
        Iterator<Element> eleiter=null;
        if(partlist!=null){
            eleiter=partlist.elementIterator();
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
        }
        //part boot
        Debug.log("-start part config-");
        Element partconfigs = element.element("partsconfig");
        if(partconfigs!=null){
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
        }

        //create logic and run.
        String logicpath = afe.attributeValue("logicpath");
        IAppLogic logic=null;
        if(logicpath!=null){
            try{
                    Class<?> cls = Class.forName(logicpath);
                    Object obj=cls.newInstance();
                    logic = (IAppLogic)obj;
             }catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
                    Debug.log("Logic error!");
                    return;
             }      
        }
        Debug.log("boot app to logic..");
        app.run(logic);
    }    
    
    public static Document getXMLFileRoot(String xmlpath){
        if(xmlpath==null){
            Document doc=DocumentHelper.createDocument();
            doc.addElement("root");
            return doc;
        }

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
    public static void writeXMLFile(String xmlpath,Document doc){
        FileOutputStream out =null;
        try {
            out = new FileOutputStream(xmlpath);
            OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
            format.setEncoding("UTF-8");
            XMLWriter writer=new XMLWriter(out,format);
            //2.写出Document对象
            writer.write(doc);
            //3.关闭流
            writer.close();
        } catch (Exception ex) {
            Logger.getLogger(XMLEngineBoot.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(XMLEngineBoot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
