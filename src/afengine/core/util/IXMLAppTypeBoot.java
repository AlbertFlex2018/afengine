/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

import afengine.core.AbApp;
import org.dom4j.Element;

/**
 * provide a entry point for XML read for boot app.<br>
 * contains some initial statements for the apptype relatived config.<br>
 * create a AbApp on the bootApp methods, and return it,<br>
 * you could use XMLEngineBoot to boot app xml config file.<br>
 * and you get a element of xml app relatived config.<br>
 * use it Impl class path to xml config, such as following.<br>
 * <app>
 *      <afengine typeboot="test.test1.AppTypeBoot1" type="type1"/>
 *      <type1 attri1="" attri2=""> 
 *          <key1>values</key1>
 *          <key1>values</key1>
 *          ...some nodes.
 *      </type1>
 * </app>
 * @see AbApp
 * @see XMLEngineBoot
 * @author Albert Flex
 */
public interface IXMLAppTypeBoot{
    public AbApp bootApp(Element element);
}