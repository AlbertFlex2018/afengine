/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

import afengine.core.AbPartSupport;
import org.dom4j.Element;

/**
 * provide a entry point for XML boot, used by hand or XMLEngineBoot.<br>
 * contains some part relatived initial statements,and used for xml config<br>
 * create a partsupport in bootPart ,and config this part based by element.<br>
 * unless you make sure that you have no nessesary to return a support,you could return null<br>

 * if you use XMLEngineBoot, remember config the xmlpartboot class to  xmlconfig<br>
 * as following<br>
 * <app>
    *      <afengine />
    *      <apptype />
    *      <partsboot>
    *        <part name="PartName1" path="test.test1.XMLPartBoot1"/>
    *        <part name="PartName2" path="test.test1.XMLPartBoot2"/>
    *      </partsboot>
    *      <partsconfig>
    *           <PartName1 />
    *           <PartName2 />
    *           ...
    *      </partsconfig>
 * </app>
 * @see XMLEngineBoot
 * @see AbPartSupport
 * @author Albert Flex
 */
public interface IXMLPartBoot{    
    /**
     * <PartName>
     *      ......
     * </PartName>
     * @param element 
     */
    public AbPartSupport bootPart(Element element);
}
