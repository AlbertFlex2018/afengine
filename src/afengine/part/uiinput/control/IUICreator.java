package afengine.part.uiinput.control;

import afengine.part.uiinput.UIActor;
import org.dom4j.Element;

/**
 *
 * @author Albert Flex
 */
public interface IUICreator {
    UIActor createUi(Element element);
}
