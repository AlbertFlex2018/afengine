/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

import afengine.core.AbPartSupport;
import afengine.core.util.Debug;
import afengine.core.util.IXMLPartBoot;
import afengine.core.util.XMLEngineBoot;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * partboot for scene<br>
 * @see IXMLPartBoot
 * @author Albert Flex
 */
public class XMLScenePartBoot implements IXMLPartBoot{

    /**
     * <ScenePart main="">
     *      <ComponentFactoryList>
     *          <Component name="" class=""/>
     *          <Component name="" class=""/>
     *          <Component name="" class=""/>
     *      </ComponentFactoryList>
     *      <SceneList>
        *      <Scene id="" name="" class="" or path="" loader="" output="true"/>
        *      <Scene id="" name="" class="" or path="" loader=""/>
     *      </SceneList>
     *      <StaticActors path=""/>
     * </ScenePart>
     * 
     * @param element
     * @return 
     */
    @Override
    public AbPartSupport bootPart(Element element){
        
        ScenePart scenepart = new ScenePart();        

        String main = element.attributeValue("main");
        
        Iterator<Element> eleiter = element.element("ComponentFactoryList").elementIterator();
        while(eleiter.hasNext()){
            Element ele = eleiter.next();
            String name=ele.attributeValue("name");
            String classname=ele.attributeValue("class");
            try{    
                Class<?> cls = Class.forName(classname);
                Object obj=cls.newInstance();
                IComponentFactory fac = (IComponentFactory)obj;
                ActorComponent.addFactory(name, fac);
                Debug.log("Add ComponentFactory :"+name);
            }catch(Exception ex){
                Debug.log("ComponentFactory add error!");
            }                                                
        }
        
        eleiter = element.element("SceneList").elementIterator();
        while(eleiter.hasNext()){
            Element ele = eleiter.next();
            Scene scene = loadScene(ele);
            if(scene!=null){
                scene.awakeAllActors();
                Debug.log("Prepare Scene :"+scene.getName());
                SceneCenter.getInstance().prepareScene(scene);
            }
        }
                        
        Scene mainscene = SceneCenter.getInstance().findPreparedScene(main);
        if(mainscene==null){
            Debug.log("MainSceneNotFound!");            
        }
        else{
            SceneCenter.getInstance().pushScene(mainscene);
            Debug.log("Push Main Scene Successfully");
        }
        
        //load static actors
        Element staticActorsE = element.element("StaticActorList");
        String path= staticActorsE.attributeValue("path");
        if(path!=null){
            Document doc = XMLEngineBoot.readXMLFileDocument(path);
            if(doc!=null&&doc.getRootElement()!=null){
                Element root = doc.getRootElement();
                if(root.getName().equals("StaticActorList"))
                SceneFileHelp.loadStaticActorFromXML(root);                
            }
        }
        
        return scenepart;
    }   
    
    
    private Scene loadScene(Element sceneEle){

        Scene scene=null;
        String classpath=sceneEle.attributeValue("class");
        String id = sceneEle.attributeValue("id");
        AbSceneLoader loader=null;
        String loaderc = sceneEle.attributeValue("loader");
        if(loaderc!=null){
            try{    
                Class<?> cls = Class.forName(loaderc);
                Object obj=cls.newInstance();
                loader = (AbSceneLoader)obj;
            }catch(Exception ex){
                ex.printStackTrace();
                Debug.log("Scene Loader error!");
                loader=null;
            }                                    
        }
        if(classpath!=null){            
            try{    
                Class<?> cls = Class.forName(classpath);
                Object obj=cls.newInstance();
                scene = (Scene)obj;
                String name=sceneEle.attributeValue("name");
                scene.setName(name);
            }catch(ClassNotFoundException | IllegalAccessException | InstantiationException ex){
                ex.printStackTrace();
                Debug.log("Scene Classpath error!");
                scene=null;
            }                                    
        }        
        else{            
            //load scene from xml file 
            String path=sceneEle.attributeValue("path");
            if(path!=null){                
                Document scenedoc = XMLEngineBoot.readXMLFileDocument(path);
                if(scenedoc!=null){
                    Element root = scenedoc.getRootElement();
                    if(root!=null&&root.getName().equals("scene")){
                        scene=SceneFileHelp.loadSceneFromXML(root);                                            
                    }
                }
            }
        }                
        
        
        
        if(loader!=null&&scene!=null){
            loader.setThisScene(scene);            
            scene.setLoader(loader);
        }
        
        
        String output=sceneEle.attributeValue("output");
        if(output!=null&&output.equals("true")&&scene!=null){
            Debug.log("Set ShouldOutput for Scene:"+scene.getName());
            scene.setShouldoutput(true);
        }
        
        return scene;
    }    
}
