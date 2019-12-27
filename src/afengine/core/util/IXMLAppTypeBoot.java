/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

import afengine.core.AbApp;
import org.dom4j.Element;

/**
 *
 * @author Admin
 */
public interface IXMLAppTypeBoot{
    public AbApp bootApp(Element element);
}