/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     *          </name1>
     *      </actordata>
     *      <actormap>
     *      </actormap>
     * </scene>
     * @param xmlpath
     * @return 
     */
    public static Scene loadSceneFromXML(String xmlpath){
        return null;
    }
    
    public static Actor loadActorFromXML(Element element){
        return null;
    }
    
    public static void outputSceneToXML(Scene scene,String xmlpath){
        
    }    
    public static void outputActorToXML(Actor actor,Element element){
        
    }
}
