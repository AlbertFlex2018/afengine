package afengine.part.uiinput;

import afengine.core.AbPartSupport;
import afengine.core.util.IXMLPartBoot;
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
        </UIInputPart>
    */
    @Override
    public AbPartSupport bootPart(Element element) {
        return null;
    }    
}
