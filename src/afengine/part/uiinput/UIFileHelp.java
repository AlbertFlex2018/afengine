package afengine.part.uiinput;

import afengine.part.uiinput.control.IUICreator;
import afengine.part.uiinput.control.UIButtonList;
import afengine.part.uiinput.control.UIImageButton;
import afengine.part.uiinput.control.UIImageLabel;
import afengine.part.uiinput.control.UIInputLine;
import afengine.part.uiinput.control.UIPane;
import afengine.part.uiinput.control.UITextButton;
import afengine.part.uiinput.control.UITextLabel;
import afengine.part.uiinput.control.UIToggle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Element;

/**
 *
 * @author Albert Flex
 */
public class UIFileHelp {
    public static Map<String,IUICreator> creatorMap;
    static{
        creatorMap=new HashMap<>();
        creatorMap.put("UITextButton",new UITextButton.UITextButtonCreator());
        creatorMap.put("UIImageButton",new UIImageButton.UIImageButtonCreator());
        creatorMap.put("UITextLabel",new UITextLabel.UITextLabelCreator());
        creatorMap.put("UIImageLabel",new UIImageLabel.UIImageLabelCreator());
        creatorMap.put("UIToggle",new UIToggle.UIToggleCreator());
        creatorMap.put("UIInputLine",new UIInputLine.UIInputLineCreator());
        creatorMap.put("UIButtonList",new UIButtonList.UIButtonListCreator());
        creatorMap.put("UIPane",new UIPane.UIPaneCreator());
    }
    /*
        <Face name="">
            <UIPane save="" name="" back="">
                <UIControl name="" pos=""/>
                <UIControl name="" pos=""/>                   
            </UIPane>
        </Face>
    */
    public static UIFace readFace(Element element){
        
        return null;
    }   
    
    public static UIActor createUi(Element element,UIFace face){
        IUICreator creator=creatorMap.get(element.getName());
        if(creator==null)
            return null;
        else{
            UIActor ui=creator.createUi(element);
            if(element.attributeValue("save")!=null&&element.attributeValue("save").equals("true")){
                face.addUiInAllAndReserve(ui);
            }else{
                face.addUiInAll(ui);                
            }
            return ui;
        }
    }
}
