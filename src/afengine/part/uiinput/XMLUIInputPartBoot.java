package afengine.part.uiinput;

import afengine.core.AbPartSupport;
import afengine.core.util.IXMLPartBoot;
import java.util.Iterator;
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
                    <name class="" handle=""/>
                    <name class="" />
                    ...
                </before>
                <after>
                    <name class="" />    
                </after>
            </InputServlets>
            <UIFaces>
                <name path="" />
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
                    InputServlet ser=createServlet(ele);
                    if(ser!=null){
                        UIInputCenter.getInstance().addPreServlet(ser.getHandleType(),ser);
                    }
                }
            }
            Element aft=servlets.element("after");
            if(aft!=null){
                Iterator<Element> biter=aft.elementIterator();
                while(biter.hasNext()){
                    Element ele=biter.next();
                    InputServlet ser=createServlet(ele);
                    if(ser!=null){
                        UIInputCenter.getInstance().addAfterServlet(ser.getHandleType(),ser);
                    }
                }                
            }
        }
        Element faces=element.element("UIFaces");
        if(faces!=null){
            Iterator<Element> faceiter=faces.elementIterator();
            while(faceiter.hasNext()){
                Element ele=faceiter.next();
                UIFace face=createFace(ele);
                if(face!=null){
                    UIInputCenter.getInstance().addFaceInAll(face);
                }
            }
        }
        return part;
    }    
    public static InputServlet createServlet(Element element){
        return null;
    }
    public static UIFace createFace(Element element){
        return null;
    }
}
