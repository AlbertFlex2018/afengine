package afengine.part.uiinput;

import afengine.core.AbPartSupport;
import afengine.core.util.IXMLPartBoot;
import afengine.core.util.XMLEngineBoot;
import afengine.part.message.IMessageHandler;
import afengine.part.uiinput.control.UIControlHelp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 *
 * @author Albert Flex
 */
public class XMLUIInputPartBoot implements IXMLPartBoot{

    /*
        <UIInputPart>
            <InputServlets>
                <before>
                    <name handler="" types=""/>
                    <name handler="" types=""/>
                    ...
                </before>
                <after>
                    <name class="" />    
                </after>
            </InputServlets>
            <UIFaces>
                <name path="" active=""/>
                 ...
            </UIFaces>
        </UIInputPart>
    */
    @Override
    public AbPartSupport bootPart(Element element){
        UIInputPart part=new UIInputPart();
        Element servlets=element.element("InputServlets");
        if(servlets!=null){
            Element bef=servlets.element("before");
            if(bef!=null){
                Iterator<Element> biter=bef.elementIterator();
                while(biter.hasNext()){
                    Element ele=biter.next();
                    List<InputServlet> ser=createServlet(ele);
                    if(ser!=null){
                        ser.forEach((servlet)->{
                            UIInputCenter.getInstance().addPreServlet(servlet.getHandleType(),servlet);                        
                        });
                    }
                }
            }
            Element aft=servlets.element("after");
            if(aft!=null){
                Iterator<Element> biter=aft.elementIterator();
                while(biter.hasNext()){
                    Element ele=biter.next();
                    List<InputServlet> ser=createServlet(ele);
                    if(ser!=null){
                        ser.forEach((servlet)->{
                            UIInputCenter.getInstance().addAfterServlet(servlet.getHandleType(),servlet);                        
                        });
                    }
                }                
            }
        }
        Element faces=element.element("UIFaces");
        if(faces!=null){
            Iterator<Element> faceiter=faces.elementIterator();
            while(faceiter.hasNext()){
                Element ele=faceiter.next();
                createFace(ele,UIInputCenter.getInstance());
            }
        }
        return part;
    }    
    /*
         <name handler="" types=""/>    
    */
    public static List<InputServlet> createServlet(Element element){
        List<InputServlet> servletList=new LinkedList<>();
        IMessageHandler handler=(IMessageHandler)(XMLEngineBoot.instanceObj(element.attributeValue("handler")));
        String name=element.getName();
        if(handler!=null){
            String typess=element.attributeValue("types");
            if(typess==null)return null;
            String[] types=typess.split(",");
            for(String type: types){
                Long ty=Long.parseLong(type);
                InputServlet servlet=new InputServlet(ty,name,handler);
                servletList.add(servlet);
            }
        }
        return servletList;
    }
    /*
         <name path="" active="true"/>    
    */    
    public static UIFace createFace(Element element,UIInputCenter center){
        String facename=element.getName();
        String path=element.attributeValue("path");
        Document doc=XMLEngineBoot.readXMLFileDocument(path);
        if(doc!=null){
            Element root=doc.getRootElement();
            UIFace face=new UIFace(facename);
            face=UIControlHelp.loadFace(face, root);
            String active=element.attributeValue("active");
            if(center!=null)
                center.addFaceInAll(face);
            if(active!=null&&active.equals("true")&&center!=null){
                center.addFaceInActived(face);
            }
            return face;
        }
        return null;
    }
}
