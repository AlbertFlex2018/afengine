package afengine.part.uiinput.control;

import afengine.core.util.Vector;

/**
 *
 * @author Albert Flex
 */
public class UIButtonList extends UIPane{
    private int nowindex;
    private int length;
    public UIButtonList(String name, Vector pos){
        super(name, pos,5);
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
}