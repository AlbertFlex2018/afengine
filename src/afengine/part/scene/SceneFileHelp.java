package afengine.part.scene;

import afengine.core.util.Debug;
import afengine.core.util.IDCreator;
import afengine.core.util.Transform;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * contains methods for scene xml config<br>
 * need impl
 * @Scene
 * @author Albert Flex
 */
public class SceneFileHelp{

    /**
     * <staticactors>
     *      <actordata>
     *          <name1 module="">
     *              <transform pos="1,1,1,1" scale="1,1,1,1" anchor="0.5,0.5,0.5,0" rotate="0,0,0,0"/>
     *              <components>
     *                  <Component1></Component1>
     *                  <Component2></Component2>
     *              </components>
     *              <datas>
     *                  <data key="" value="" />
     *                  <data key="" value="" />
     *                  <data key="" value="" />
     *                  <data key="" value="" />
     *                  ...
     *              </datas>
     *          </name1>
     *          <name2 />
     *          ...
     * </staticactors>
     * @param xmlpath
     * @return Scene
     */
    public static void loadStaticActorFromXML(String xmlpath){
        loadActors(xmlpath,true);
    }
    
    /**
     * <scene>
     *      <actordata>
     *          <name1 module="" output>
     *              <transform pos="1,1,1,1" scale="1,1,1,1" anchor="0.5,0.5,0.5,0" rotate="0,0,0,0"/>
     *              <components>
     *                  <Component1></Component1>
     *                  <Component2></Component2>
     *              </components>
     *              <datas>
     *                  <key value="" />
     *                  ...
     *              </datas>
     *          </name1>
     *          <name2 />
     *          ...
     *      </actordata>
     *      <actormap>
     *          <name1/>
     *          <name2>
     *              <name3/>
     *          </name2>
     *          ...
     *      </actormap>
     * </scene>
     * @param xmlpath
     * @return Scene
     */
    private static Map<String,Actor> loadActorMap=new HashMap<>();

    private static void loadTransform(Actor actor,Element trans){
        String pos = trans.attributeValue("pos");
        String anchor = trans.attributeValue("anchor");
        String scale = trans.attributeValue("scale");
        String rotate = trans.attributeValue("rotate");
        if(pos!=null){
            transVali(actor.getTransform().position,pos.split(","));
        }
        if(anchor!=null){
            transVali(actor.getTransform().anchor,anchor.split(","));
        }
        if(scale!=null){
            transVali(actor.getTransform().scalation,scale.split(","));
        }
        if(rotate!=null){
            transVali(actor.getTransform().rotation,rotate.split(","));
        }
    }
    private static void transVali(Vector v,String[] data){
        if(data.length<4)return;
        v.setX(Double.parseDouble(data[0]));
        v.setY(Double.parseDouble(data[1]));
        v.setZ(Double.parseDouble(data[2]));
        if(data.length==4)
            v.setA(Double.parseDouble(data[3]));
    }
    private static Scene loadActors(String xmlpath,boolean isStatic){
        Scene scene=null;
        
        if(!isStatic)
            scene=new Scene("");
        //init actor
        Element root =XMLEngineBoot.getXMLFileRoot(xmlpath).getRootElement();
        if(root==null||!isStatic&&!root.getName().equals("scene")){
            Debug.log("not a scene file!:"+xmlpath);
            return null;
        }
        if(root==null||isStatic&&!root.getName().equals("staticactors")){
            Debug.log("not a staticactors file!:"+xmlpath);
            return null;
        }
        
        
        
        Element actordata=root.element("actordata");
        Iterator<Element> actore = actordata.elementIterator();
        while(actore.hasNext()){
             Element actorele = actore.next();
             Actor actor = loadActorFromXML(actorele);
             if(actor!=null){
                loadActorMap.put(actor.getName(), actor);                                
             }             
        }            
        
        if(!isStatic){
            Element actormap=root.element("actormap");
            List<Actor> rootlist=loadActorMap(actormap,loadActorMap);                     
            Iterator<Actor> actoriter=rootlist.iterator();
            while(actoriter.hasNext()){
                 Actor actor=actoriter.next();                 
                 scene.addNodeActor(actor.getName(), actor);
            }                        
        }
        else{
            Iterator<Actor> actoriter=loadActorMap.values().iterator();
            while(actoriter.hasNext()){
                 Actor actor=actoriter.next();
                 Actor.addStaticActor(actor);
            }                        
        }
        
        return scene;
    }    
    public static Scene loadSceneFromXML(String xmlpath){        
        return loadActors(xmlpath,false);
    }
    /*
     *      <actormap>
     *          <name1/>
     *          <name2>
     *              <name3/>
     *          </name2>
     *          ...
     *      </actormap>
     */
    private static List<Actor> loadActorMap(Element element,Map<String,Actor> actorMap){
        Iterator<Element> mapiter=element.elementIterator();
        List<Actor> rootList=new LinkedList<>();
        while(mapiter.hasNext()){      
            Element mape = mapiter.next();
            String name = mape.getName();
            Actor actor = actorMap.get(name);
            if(actor!=null){
                rootList.add(actor);
                loadActorMapMap(null,actor,actorMap,mape);
            }
        }
        return rootList;
    }
    /**
     * <name1>
     *      <name2/>
     *      <name3>
     *          <name4/>
     *      </name3>
     * </name1>
     */
    private static void loadActorMapMap(Actor parent,Actor child,Map<String,Actor> actorMap,Element element){

        if(parent!=null)
            parent.addChild(child);
        
        Iterator<Element> childeiter=element.elementIterator();
        while(childeiter.hasNext()){
            Element childnamee=childeiter.next();
            String childname=childnamee.getName();
            Actor child0 = actorMap.get(childname);
            if(child0!=null){
                loadActorMapMap(child,child0,actorMap,element);
            }
        }
    }
    
     /*          <name1 id="" module="" output="">
     *              <transform pos="1,1,1,1" scale="1,1,1,1" anchor="0.5,0.5,0.5,0" rotate="0,0,0,0"/>
     *              <components>
     *                  <Component1></Component1>
     *                  <Component2></Component2>
     *              </components>
     *              <datas>
     *                  <key value="" />
     *                  ...
     *              </datas>
     *          </name1>    
    */
    public static Actor loadActorFromXML(Element element){
        //then instance actor.
        //at first,load all datas,contains datas from module
        //instance components for module,add to actor.
        //instance components for actor,add to actor.
        //actor awake all components.
        String name =element.getName();
        String idstring = element.attributeValue("id");        
        long id;
        if(idstring!=null){
            long idd = Long.parseLong(idstring);
            IDCreator.validId(idd);
            id=idd;
        }
        else id=IDCreator.createId();
        Transform transform=new Transform();
        Actor actor = new Actor(id,name,transform);
        loadTransform(actor,element.element("transform"));
        //output set
        String output =element.attributeValue("output");
        if(output!=null&&output.equals("true")){
            actor.setOutput(true);
        }

        //load datas
        String modulepath= element.attributeValue("module");
        boolean module=false;
        Element moduleroot =null;        
        
        if(modulepath!=null){
            module=true;
            moduleroot=XMLEngineBoot.getXMLFileRoot(modulepath).getRootElement();
        }
        //add module datas first
        Map<String,String> actordatas=null;
        if(module&&moduleroot!=null){
            if(!moduleroot.getName().equals("module")){
                Debug.log("not a module xml file!");            
                return null;
            }
            Element moduledatas=moduleroot.element("datas");
            actordatas=getDatas(moduledatas);
            addDatasToActor(actordatas,actor);
        }
        //add actor datas
        actordatas=getDatas(element.element("datas"));
        addDatasToActor(actordatas,actor);
        

        //instance components        
        //instance module component first
        if(module&&moduleroot!=null){
            Element modulecomp = moduleroot.element("components");
            if(modulecomp!=null){
                createComponents(modulecomp,actordatas,actor);
            }
        }
        //instance actor component
        Element actorcomp = element.element("components");
        if(actorcomp!=null){
            createComponents(actorcomp,actordatas,actor);
        }        
        
        return actor;
    }
    /*
        <components>
            <ComponentName1/>
            ...
        </components>
    */
    private static void createComponents(Element element,Map<String,String> datas,Actor actor){
        Iterator<Element> compiter=element.elementIterator();
        while(compiter.hasNext()){
            Element compe=compiter.next();
            ActorComponent comp = instanceComponent(compe,datas);
            if(comp!=null){
                actor.addComponent(comp,false);
            }            
        }        
    }
    /*
        <ComponentName  />
        component can find datas from actor
    */
    private static ActorComponent instanceComponent(Element element,Map<String,String> datas){
        IComponentFactory factory = ActorComponent.getFactory(element.getName());
        if(factory==null){
            Debug.log("not found component factory for:"+element.getName()+", make sure whether you added factory or not.");
            return null;
        }
        return factory.createComponent(element,datas);        
    }
    private static void addDatasToActor(Map<String,String> data,Actor actor){
        Iterator<Map.Entry<String,String>> entryiter=data.entrySet().iterator();
        while(entryiter.hasNext()){
            Map.Entry<String,String> entry =entryiter.next();
            String key = entry.getKey();
            String value=entry.getValue();
            actor.valueMap.put(key, value);
        }
    }
    /**
     * <module>
     *      <transform />
     *      <datas />
     *      <components />
     * </module>
     * @param element
     * @return 
     */
    /**
     * <datas>
     *      <key value=""/>
     * </datas>
     * @param element
     * @return 
     */
    private static Map<String,String> getDatas(Element element){
        Map<String,String> values = new HashMap<>();                
        Iterator<Element> eleiter=element.elementIterator();
        while(eleiter.hasNext()){
            Element ele = eleiter.next();
            String key = ele.getName();
            String value=ele.attributeValue("value");
            values.put(key, value);
        }        
        return values;
    }

    //change actor datas.
    //change actor maps.
    public static void outputSceneToXML(Scene scene,String xmlpath){
        try {
            //output actor
            Document doc=XMLEngineBoot.getXMLFileRoot(xmlpath);
            Element root = doc.getRootElement();
            Element datase = root.element("actordata");
            Element mapse = root.element("actormap");
            mapse.clearContent();
            outputActors(scene,datase);
                       
            changeActorMap(scene,mapse);
            
            XMLWriter writer = null;
            File file = new File(xmlpath);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            writer=new XMLWriter(new FileWriter(file),format);
            writer.write(doc);
            writer.close();            
        } catch (IOException ex) {
            Logger.getLogger(SceneFileHelp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /*
        <actordata>
            <name1>
                <transform />
                <datas/>
                <components/>
            </name1>
        </actordata>
    */
    private static void outputTransform(Actor actor,Element transEle){
        setTransAtt(actor.getTransform().position,"pos",transEle);
        setTransAtt(actor.getTransform().anchor,"anchor",transEle);
        setTransAtt(actor.getTransform().scalation,"scale",transEle);
        setTransAtt(actor.getTransform().rotation,"rotate",transEle);        
    }    
    private static void setTransAtt(Vector v,String name,Element transEle){
        String pos1,pos2,pos3,pos4;
        pos1=Double.toString(v.getX());
        pos2=Double.toString(v.getY());
        pos3=Double.toString(v.getZ());
        pos4=Double.toString(v.getA());
        String poss=""+pos1+","+pos2+","+pos3+","+pos4;
        String pos = transEle.attributeValue(name);
        if(pos==null){
            transEle.addAttribute(name,poss);
        }
        else{
            transEle.attribute(name).setValue(poss);
        }        
    }
    
    /**
     * <actordata>
     *      
     * <actordata>
     * @param scene
     * @param element 
     */
    private static void outputActors(Scene scene,Element element){        
        List<Element> needmove=new LinkedList<>();
        
        
        Iterator<Element> actoriter=element.elementIterator();
        while(actoriter.hasNext()){
            Element ele = actoriter.next();
            String actorname=ele.getName();
            Actor actor = scene.findActorByName(actorname);
            if(actor==null){
                needmove.add(ele);
            }else{
                outputActorToXML(actor,ele);
            }
        }        
        
        actoriter=needmove.iterator();
        while(actoriter.hasNext()){
            String name=actoriter.next().getName();
            element.remove(element.element(name));
        }
    }
    
    
    //change actor datas. 
    /**
     * <datas>
     *      <key value="" />
     * </datas>
     * @param actor
     * @param element 
     */
    public static void outputActorToXML(Actor actor,Element element){        
        Element transform = element.element("transform");
        if(transform==null){
            transform=element.addElement("transform");
        }
        outputTransform(actor,transform);

        Map<String,String> datas=actor.valueMap;
        Iterator<Map.Entry<String,String>> entryiter=datas.entrySet().iterator();
        Element datase = element.element("datas");
        datase.clearContent();
        while(entryiter.hasNext()){
            Map.Entry<String,String> entry = entryiter.next();
            String key = entry.getKey();
            String value=entry.getValue();
            outputData(key,value,datase);
        }
        
        Element components = element.element("components");
        if(components==null)
            components=element.addElement("components");
        outputActorComponent(actor,components);
    }
    
    /*
        <actormap>
            <name1/>
        </actormap>
    */
    private static void changeActorMap(Scene scene,Element actormap){
        Iterator<Actor> actoriter=scene.nodeActorMap.values().iterator();
        while(actoriter.hasNext()){
            Actor actor = actoriter.next();
            changeActorMapMap(actor,actormap);
        }
    }
    private static void changeActorMapMap(Actor child,Element parentmap){            
        Element childe = parentmap.element(child.getName());        
        if(childe==null){
            childe=parentmap.addElement(child.getName());
        }
        Iterator<Actor> childiter=child.getChildren().iterator();
        while(childiter.hasNext()){
             Actor child0 = childiter.next();
             changeActorMapMap(child0,childe);
        }
    }
    private static void outputData(String key,String value,Element datas){        
        Element datae = datas.element(key);
        if(datae!=null){
            Attribute atti = datae.attribute(key);
            if(atti!=null){
                atti.setValue(value);
            }
            else {
                datae.addAttribute("value", value);
            }
        }
        else{
            datae=datas.addElement(key);
            datae.addAttribute("value", value);
        }
    }

    /*
        <components>
            <ComponentName />
        </components>
    */
    private static void outputActorComponent(Actor actor, Element components) {
        Iterator<ActorComponent> compiter=actor.getComponentsMap().values().iterator();
        while(compiter.hasNext()){
            ActorComponent comp=compiter.next();
            outputComponent(comp,components);
        }
    }
    /*
        <components>
            <ComponentName />
        </components>
    */
    private static void outputComponent(ActorComponent comp,Element element){
        Element compe=element.element(comp.getComponentName());
        if(compe==null){
            element.addElement(compe.getName());
            IComponentFactory factory = ActorComponent.getFactory(comp.getComponentName());
            factory.outputComponent(comp, compe);
        }        
    }
}
