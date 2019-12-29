package afengine.part.scene;

import afengine.core.util.Debug;
import afengine.core.util.Transform;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Element;

public class SceneFileHelp{

    /*
        <scene>
            <actordata>
            </actordata>
            <actormap>
            </actormap>
        </scene>
    */
    public static Scene loadSceneFromXML(String path) {
        //先导入全部实体
        //再创建实体父子映射
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void outputSceneToXML(Scene scene,String path){
        //清楚全部的内容
        //创建实体数据
        //创建实体父子映射
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }

    /*
        <staticactor> 只考虑没有树形结构的实体
            <actor1/>
            <actor2/>
            ...
        </staticactor>
    */
    public static void loadStaticActorFromXML(String path) {
        //按次序导入实体
        //将实体附加再Actor上
    }
    
    public static void outputStaticActorToXML(String path){
        //清楚全部的实体数据
        //按次序导出实体
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
        Element root = XMLEngineBoot.getXMLFileRoot(filepath).getRootElement();
        if(!root.getName().equals("modlibs")){
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