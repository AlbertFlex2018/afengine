package afengine.part.scene;

import org.dom4j.Element;

/**
 * contains methods for scene xml config<br>
 * need impl
 * @Scene
 * @author Albert Flex
 */
public class SceneFileHelp {
    /**
     * <scene>
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
    public static Scene loadSceneFromXML(String xmlpath){
        return null;
    }
    
    public static Actor loadActorFromXML(Element element){
        return null;
    }
    
    /**
     * <module>
     *      <transform pos="" scale="" anchor="" rotate="" />
     *      <components>
     *          <Component />
     *          <Component />
     *          ...
     *      </components>
     * </module>
     * @param modulexml
     * @return 
     */
    public static Actor loadActorFromModuel(String modulexml){
        return null;
    }
    
    public static void outputSceneToXML(Scene scene,String xmlpath){
        
    }    
    public static void outputActorToXML(Actor actor,Element element){
        
    }
}
