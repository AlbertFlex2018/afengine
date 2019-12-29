package afengine.part.scene;

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
        <actorname mod=""> 组件模型原型，组件全部在模型之中设计，组件不需要导出
            <transform />
            <datas>
                <key value=""/>
                ...
            </datas>
        </actorname>
    */
    public static void outputActorToXML(Actor actor, Element root) {
        //先输出变换
        //再输出属性值
    }
    
    /*
        <mod> 模型原型只保存组件的导入设置，不允许导出，可以用以复用
            <component1 />
            <component2>
                <data key1="Value1" key2="#Value2"/> 使用#表示属性值从所属的实体的数据之中获取，不使用表示绝对参数                
            </component2>
        </mod>
    */
    public static Actor loadActorFromXML(Element root) {
        //先导入变换
        //再导入属性值
        //最后加载组件的模块
        return null;
    }
}