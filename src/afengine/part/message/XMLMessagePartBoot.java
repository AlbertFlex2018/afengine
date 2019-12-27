/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.message;

import afengine.core.AbPartSupport;
import afengine.core.util.Debug;
import org.dom4j.Element;
import afengine.core.util.IXMLPartBoot;
import java.util.Iterator;

/**
 *
 * @author Admin
 */
public class XMLMessagePartBoot implements IXMLPartBoot{
    
    /**
     * <MessagePart>
     *      <Route path=""/>
     *      <Route path=""/>
     * </MessagePart>
     * @param element 
     * @return  partsupport
     */
    @Override
    public AbPartSupport bootPart(Element element){
        MessageCenter center =MessageCenter.getInstance();        
        Iterator<Element> routeiter = element.elementIterator();
        while(routeiter.hasNext()){
            Element ele = routeiter.next();
            String path = ele.attributeValue("path");
            try{
                Class<?> cls = Class.forName(path);
                Object obj=cls.newInstance();
                IMessageRoute route = (IMessageRoute)obj;                
                center.addRoute(route);
                Debug.log("add route ["+path+"]");
            }catch(Exception ex){
                Debug.log("add message route error!");
                continue;
            }                            
        }
        return new MessagePart();
    }    
}
