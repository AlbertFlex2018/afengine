/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

import org.dom4j.Element;

/**
 *
 * @author Albert Flex
 */
public interface IComponentFactory {
    public ActorComponent createComponent(Element element);        
}
