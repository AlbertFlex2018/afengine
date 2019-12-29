/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

import java.util.Map;
import org.dom4j.Element;

/**
 * component factory<br>
 * for xml config<br>
 * @see ActorComponent
 * @author Albert Flex
 */
public interface IComponentFactory {
    public ActorComponent createComponent(Element element,Map<String,String> actordatas);        
}
