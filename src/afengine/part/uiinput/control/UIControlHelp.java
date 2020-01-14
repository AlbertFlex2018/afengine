package afengine.part.uiinput.control;

import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.UIFace;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Element;

/**
 *
 * @author Admin
 */
public class UIControlHelp {
    
    public static final Map<String,IUICreator> creatorMap;
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
        <face name="">
            <pane name="" pos="">
            </pane>
        </face>
    */
    public static UIFace loadFace(UIFace face,Element faceroot){        
        Iterator<Element> eiter=faceroot.elementIterator();
        while(eiter.hasNext()){
            Element ele=eiter.next();
            loadUi(ele,face,null);
        }
        return face;
    }
    
    /*
        <controlname name="" pos="">
            <controlname />
            <controlname>
                <controlname/>
            </controlname>
        </controlname>
    */
    public static void loadUi(Element uiroot,UIFace face,UIActor pane){
        String controlname=uiroot.getName();
        String name=uiroot.attributeValue("name");
        IUICreator uicreator=creatorMap.get(controlname);
        UIActor ui=uicreator.createUi(uiroot);        
        if(name!=null)
            face.addUiInAllAndReserve(ui);
        else face.addUiInAll(ui);
        if(pane!=null&&pane instanceof UIPane){            
            UIPane parent=(UIPane)pane;
            parent.addChild(ui);
        }
        
        if(controlname.equals("UIPane")){
            Iterator<Element> eiter=uiroot.elementIterator();
            while(eiter.hasNext()){
                Element element=eiter.next();
                loadUi(element,face,ui);
            }
        }
    }
}
