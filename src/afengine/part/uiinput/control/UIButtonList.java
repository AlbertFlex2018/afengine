package afengine.part.uiinput.control;

import afengine.core.util.Debug;
import afengine.core.util.Vector;
import afengine.part.uiinput.UIActor;
import java.util.Iterator;
import org.dom4j.Element;

/**
 *
 * @author Albert Flex
 */
public class UIButtonList extends UIPane{
    private int nowindex;
    private int length;
    public UIButtonList(String name, Vector pos){
        super(name, pos);
    }

    public int getNowindex() {
        return nowindex;
    }

    public int getLength() {
        return length;
    }
    
    public void addUiButton(UIButtonBase button){
        addChild(button);
        ++length;
    }   
    public UIButtonBase getUiButton(int index){
        return (UIButtonBase)super.children.get(index);                
    }
    public void removeUiButton(int index){
        super.children.remove(index);
        --length;
    }
    public void activeUiButton(int index){
        UIButtonBase preb=(UIButtonBase)super.children.get(nowindex);
        UIButtonBase button=(UIButtonBase)super.children.get(index);
        if(preb!=null&&button!=null){
            preb.normal();            
            button.cover();
            nowindex=index;
        }
    }
    public void upActive(){
        int nindex=nowindex-1;
        if(nindex<0)
            nindex+=length;        
        activeUiButton(nindex);        
    }
    public void downActive(){
        int nindex=(nowindex+1)%length;
        activeUiButton(nindex);
    }
    
    /*
        
    */
    public static class UIButtonListCreator implements IUICreator{
        
        /* 
            <UIButtonList name pos >
                <UIButton />
                <UIButton />
                <UIButton />
            </UIButtonList>
        */
        @Override
        public UIActor createUi(Element element) {
            String name=element.attributeValue("name");
            Vector pos =createPos(element);
            if(pos==null)
                pos=new Vector(10,10,0,0);
            if(name==null)
                name="DefaultUiName";
            UIButtonList list=new UIButtonList(name,pos);
            Iterator<Element> eleiter=element.elementIterator();
            if(eleiter!=null){
                while(eleiter.hasNext()){
                    Element ele=eleiter.next();
                    UIButtonBase button=createButton(ele);
                    if(button!=null){
                        list.addUiButton(button);
                    }
                }
            }           
            return list;
        }        
        private UIButtonBase createButton(Element element){
            String name=element.getName();
            if(name.equals("UIImageButton")){
                IUICreator creator=new UIImageButton.UIImageButtonCreator();
                return (UIButtonBase)creator.createUi(element);
            }
            else if(name.equals("UITextButton")){
                IUICreator creator=new UITextButton.UITextButtonCreator();
                return (UIButtonBase)creator.createUi(element);                
            }else{
                Debug.log("not type for button, in buttonlist");
                return null;                
            }
        }
        private Vector createPos(Element element){
            String poss=element.attributeValue("pos");
            String[] posl=poss.split(",");
            double x = Double.parseDouble(posl[0]);
            double y = Double.parseDouble(posl[1]);
            return new Vector(x,y,0,0);
        }        
    }
}