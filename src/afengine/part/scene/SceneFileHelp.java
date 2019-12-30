package afengine.part.scene;

import afengine.core.util.Debug;
import afengine.core.util.Transform;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Element;

public class SceneFileHelp{

    /*
        <scene>
            <actordata>
                <name1/>
                ...
            </actordata>
            <actormap>
            </actormap>
        </scene>
    */
    public static Scene loadSceneFromXML(Element sceneroot) {
        if(!sceneroot.getName().equals("scene")){
            Debug.log("file is not a scene file.");
            return null;
        }

        Scene scene = new Scene("");
        //先导入全部实体
        Map<String,Actor> actorMap=loadAllActors(sceneroot);

        Element actormap = sceneroot.element("actormap");
        
        if(actormap==null){
            Debug.log("has no actor map of scene.");
        }else{
        //再创建实体父子映射
            List<Actor> rootactorList = orderMap(actorMap,actormap);
            rootactorList.forEach((actor) -> {
                Debug.log("put "+actor.getName());
                scene.nodeActorMap.put(actor.getName(), actor);
            });
        }
        
        return scene;
    }
    //导入实体
    private static Map<String,Actor> loadAllActors(Element root){
        Element actordata = root.element("actordata");        
        Map<String,Actor> actorMap=new HashMap<>();
        if(actordata==null){
            Debug.log("has no actor data of scene.");            
        }else{
            Iterator<Element> actoriter = actordata.elementIterator();
            while(actoriter.hasNext()){
                Element actorelement = actoriter.next();
                Actor actor = loadActorFromXML(actorelement);
                if(actor!=null){
                    actorMap.put(actor.getName(), actor);
                }
            }
        }        
        return actorMap;
    }
    /**
            <actormap>
     *          <name1/>
     *          <name2>
     *              <name3/>
     *              <name4/>
     *          </name2>
            </actormap>
     */
    //对实体进行父子联系，并返回根的实体
    private static List<Actor> orderMap(Map<String,Actor> actorMap,Element orderRoot){
        List<Actor> rootlist = new LinkedList<>();
        Iterator<Element> eleiter = orderRoot.elementIterator();
        while(eleiter.hasNext()){
            Element ele = eleiter.next();
            String name =ele.getName();
            Actor actor = actorMap.get(name);
            if(actor==null){
                Debug.log("actor for "+name+" is not defined.");
                continue;
            }else{
                //将要添加到场景的根下
                rootlist.add(actor);
                mapActortoParent(actorMap,null,ele);
            }            
        }
        return rootlist;
    }
    //迭代对父子进行联系
    private static void mapActortoParent(Map<String,Actor> actorMap,Element parent,Element child){
        if(child==null)return;

        //先把父子贴起来
        if(parent!=null){
            String pname=parent.getName();
            Actor pactor = actorMap.get(pname);
            if(pactor==null){
                Debug.log("parent actor - "+pname+" not found.");
            }else{
                Actor cactor=actorMap.get(child.getName());
                if(cactor==null){
                    Debug.log("child actor - "+child.getName()+" not found.");
                }else{
                    Debug.log("actor: "+parent.getName()+ " add node - "+child.getName());
                    pactor.addChild(cactor);
                }
            }
        }
        
        //在循环遍历子的子节点
        Iterator<Element> childiter = child.elementIterator();
        while(childiter.hasNext()){
            Element childe = childiter.next();
            mapActortoParent(actorMap,child,childe);
        }
    }
    
    /*
        <scene>
            <actordata>
                <name1/>
                ...
            </actordata>
            <actormap>
            </actormap>
        </scene>
    */
    public static void outputSceneToXML(Scene scene,Element sceneroot){
        sceneroot.setName("scene");
        //清楚全部的内容
        sceneroot.clearContent();
        //创建实体数据
        Element actordata = sceneroot.addElement("actordata");
        outputActors(scene.nodeActorMap,actordata);
        //创建实体父子映射
        Element actormap=sceneroot.addElement("actormap");
        makeParent(scene.nodeActorMap,actormap);
    }
    private static void outputActors(Map<String,Actor> rootActors,Element actordata){
        Iterator<Map.Entry<String,Actor>> entryiter = rootActors.entrySet().iterator();
        while(entryiter.hasNext()){
            Map.Entry<String,Actor> actorentry = entryiter.next();
            String actorname=actorentry.getKey();
            Actor actor = actorentry.getValue();

            Element actore = actordata.addElement(actorname);
            outputActorToXML(actor,actore);
            List<Actor> child = actor.getChildren();
            child.forEach((act)->{
                outputAllActors(act,actordata);
            });
        }
    }    
    private static void outputAllActors(Actor actor,Element actordata){
        if(actor==null)return;
        
        Element element = actordata.addElement(actor.getName());
        outputActorToXML(actor,element);

        List<Actor> children = actor.getChildren();
        children.forEach((act)->{
            outputAllActors(act,actordata);
        });
    }
    private static void makeParent(Map<String,Actor> rootActors,Element actormap){
        Iterator<Map.Entry<String,Actor>> entryiter = rootActors.entrySet().iterator();
        Map<String,Element> elementMap=new HashMap<>();
        while(entryiter.hasNext()){
            Map.Entry<String,Actor> actorentry = entryiter.next();
            String actorname=actorentry.getKey();
            Element ele=actormap.addElement(actorname);
            elementMap.put(actorname, ele);
        }        
        
        Iterator<Map.Entry<String,Actor>> entryiter2 = rootActors.entrySet().iterator();
        while(entryiter2.hasNext()){
            Map.Entry<String,Actor> actorentry = entryiter2.next();
            Actor actor = actorentry.getValue();
            makeParentMatch(null,actor,elementMap);
        }        
        
    }
    private static void makeParentMatch(Actor parent,Actor child,Map<String,Element> elementMap){
        if(child==null)return;
        
        if(parent!=null){
            Element parente = elementMap.get(parent.getName());
            if(parente==null){
                Debug.log("not found for parent element for child.");
            }else{
                elementMap.put(child.getName(),parente.addElement(child.getName()));
            }
        }
        
        List<Actor> children = child.getChildren();
        children.forEach((actor)->{
            makeParentMatch(child,actor,elementMap);
        });
    }
    

    /*
        <staticactor-list> 只考虑没有树形结构的实体
            <actor1/>
            <actor2/>
            ...
        </staticactor-list>
    */
    public static void loadStaticActorFromXML(Element staticactorroot) {
        //按次序导入实体
        //将实体附加再Actor上        
        if(staticactorroot==null||!staticactorroot.getName().equals("staticactor-list")){
            Debug.log("not a static actor list file.");
            return;
        }
        
        Iterator<Element> eleiter = staticactorroot.elementIterator();
        while(eleiter.hasNext()){
            Element ele = eleiter.next();
            Actor actor = loadActorFromXML(ele);
            if(actor!=null){
                Actor.addStaticActor(actor);
            }
        }
    }
    
    public static void outputStaticActorToXML(Element staticactorroot){
        //清楚全部的实体数据
        //按次序导出实体
        if(staticactorroot==null){
            Debug.log("static actor root is null,output static actor list failed");
            return;
        }
        staticactorroot.setName("staticactor-list");
        staticactorroot.clearContent();
        Iterator<Map.Entry<String,Actor>> actorentry = Actor.staticActorMap.entrySet().iterator();
        while(actorentry.hasNext()){
            Map.Entry<String,Actor> entry = actorentry.next();
            String name = entry.getKey();
            Actor actor = entry.getValue();
            outputActorToXML(actor,staticactorroot.addElement(name));
        }
    }
    
    /*
        <actorname mod="file#name,file#name"> 组件模型原型，组件全部在模型之中设计，组件不需要导出
            <transform />
            <datas>
                <key value=""/>
                ...
            </datas>
        </actorname>
    */
    public static void outputActorToXML(Actor actor, Element root) {  
        //先清楚全部内容，设置mod的路径值
        root.clearContent();
        root.setName(actor.getName());
        Attribute mod = root.attribute("mod");
        if(mod==null)
            root.addAttribute("mod",actor.getModPath());
        else mod.setValue(actor.getModPath());
        //输出变换
        outputTransform(actor.getTransform(),root);
        //再输出属性值        
        outputDatas(root.addElement("datas"),actor.valueMap);
    }
    private static void outputTransform(Transform transform,Element root){
        Element trans = root.addElement("transform");
        outputV(transform.position,"pos",trans);
        outputV(transform.rotation,"rotate",trans);
        outputV(transform.scalation,"scale",trans);
        outputV(transform.anchor,"anchor",trans);
    }
    private static void outputDatas(Element datase,Map<String,String> values){
        Iterator<Map.Entry<String,String>> entryiter=values.entrySet().iterator();
        while(entryiter.hasNext()){
            Map.Entry<String,String> entry=entryiter.next();
            String key = entry.getKey();
            String value=entry.getValue();
            datase.addElement(key).addAttribute("value", value);
        }
    }
    private static void outputV(Vector v,String name,Element tran){
        double x = v.getX();
        double y = v.getY();
        double z = v.getZ();
        double a = v.getA();
        tran.addAttribute(name,""+x+","+y+","+z+","+a);
    }
    /*
        <actorname mod="file#name,file#name"> 组件模型原型，组件全部在模型之中设计，组件不需要导出
            <transform />
            <datas>
                <key value=""/>
                ...
            </datas>
        </actorname>
    */
    public static Actor loadActorFromXML(Element root) {
        //先导入变换
        //再导入属性值,生成实体
        //最后加载组件的模块
        Transform trans = loadTransform(root);
        Map<String,String> attributes = loadDatas(root);
        String name=root.getName();
        Actor actor = new Actor(name,trans);        
        actor.valueMap.putAll(attributes);
        String modspath=root.attributeValue("mod");
        if(modspath!=null){
            createActorModule(modspath,actor);
            actor.setModPath(modspath);
        }        
        actor.awakeAllComponents();
        return actor;
    }
    //file#mod,file#mod
    private static void createActorModule(String paths,Actor actor){
        if(paths.contains(",")){
            String[] path = paths.split(",");
            for(String modpath : path){
                Element mod = getModuleElement(modpath);
                createModuleComponent(mod,actor);
            }
        }  
        Element mod = getModuleElement((paths));
        createModuleComponent(mod,actor);
    }
    /*
        <modlibs>
            <mod1 />
            <mod2 />
        </modlibs>
    */
    //filename#modname
    private static Element getModuleElement(String modulepath){
        String[] cmds=modulepath.split("#");
        String filepath=cmds[0];
        String modname=cmds[1];
        Element root = XMLEngineBoot.readXMLFileDocument(filepath).getRootElement();
        if(root!=null&&!root.getName().equals("modlibs")){
            Debug.log("modulepath:"+modulepath+" is not a mod lib. please check.");
            return null;
        }        
        return root.element(modname);
    }

    private static Map<String,String> loadDatas(Element root){
        Map<String,String> attributes=new HashMap<>();
        Element datase = root.element("datas");
        
        if(datase==null)return attributes;
        
        Iterator<Element> dataiter = datase.elementIterator();
        while(dataiter.hasNext()){
            Element data = dataiter.next();
            String key = data.getName();
            String value=data.attributeValue("value");
            attributes.put(key, value);
        }
        return attributes;
    }

    /*
        <modname> 模型原型只保存组件的导入设置，不允许导出，可以用以复用
            <component1 />
            <component2>
                <data key1="Value1" key2="#Value2"/> 使用#表示属性值从所属的实体的数据之中获取，不使用表示绝对参数                
            </component2>
        </modname>
    */
    private static void createModuleComponent(Element module,Actor owner){
        if(module==null)return;
        
        Iterator<Element> compiter=module.elementIterator();
        while(compiter.hasNext()){
            Element element = compiter.next();
            String compname=element.getName();
            IComponentFactory factory = ActorComponent.getFactory(compname);
            if(factory!=null){
                ActorComponent comp=factory.createComponent(element,owner.valueMap);
                Debug.log("component created successfully:"+comp.getComponentName());
                owner.addComponent(comp, false);
            }
        }
        Debug.log("module created successfully:"+module.getName());
    }
    private static Transform loadTransform(Element root){
        Transform tran=new Transform();
        Element tr = root.element("transform");
        if(tr!=null){
            setV(tran.position,"pos",tr);
            setV(tran.anchor,"anchor",tr);
            setV(tran.scalation,"scale",tr);
            setV(tran.rotation,"rotate",tr);
        }                
        return tran;
    }
    //"1,1,1,1"
    private static void setV(Vector v,String name,Element ve){
        if(ve==null)return;
        
        String pos=ve.attributeValue(name);
        if(pos==null)return;
        
        String[] poss=pos.split(",");
        if(poss.length<4)return;
        
        v.setX(Double.parseDouble(poss[0]));
        v.setY(Double.parseDouble(poss[1]));
        v.setZ(Double.parseDouble(poss[2]));
        v.setA(Double.parseDouble(poss[3]));
    }
}