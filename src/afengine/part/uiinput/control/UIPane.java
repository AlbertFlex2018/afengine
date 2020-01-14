package afengine.part.uiinput.control;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.IDCreator;
import afengine.core.util.Vector;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.message.Message;
import afengine.part.uiinput.UIActor;
import org.dom4j.Element;

public class UIPane extends UIActor{
    private ITexture back;    
    
    public UIPane(String name, Vector pos) {
        super(name, pos);
    }

    @Override
    public void update(long time){        
        if(back!=null){
            super.width=back.getWidth();
            super.height=back.getHeight();
        }        
    }
        
    
    @Override
    public boolean handle(Message msg){
        for(UIActor ui:super.children){
            if(ui.handle(msg))return true;
        }
        return false;
    }

    @Override
    public void draw(IGraphicsTech tech){
        if(back!=null){
            int dx=super.getUiX();
            int dy=super.getUiY();
            tech.drawTexture(dx, dy, back);
        }
        super.children.forEach((ui) -> {
            ui.draw(tech);
        });
    }    

    public ITexture getBack() {
        return back;
    }

    public void setBack(ITexture back) {
        this.back = back;
    }    
    
    @Override
    public void addChild(UIActor ui){
        super.addChild(ui);
    }
    
    public static class UIPaneCreator implements IUICreator{

        /*
            <UIPane name pos back="">
                <UIPane name pos>
                    <UIActor/>
                </UIPane>
                <UIActor/>
                <UIActor/>
                <UIActor/>
                <UIActor/>
            </UIPane>
        */
        @Override
        public UIActor createUi(Element element){
           String name=element.attributeValue("name");
            Vector pos =createPos(element);
            if(pos==null)
                pos=new Vector(10,10,0,0);
            if(name==null)
                name="DefaultUiName"+IDCreator.createId();
            String sback=element.attributeValue("back");
            ITexture back=null;
            if(sback!=null)
                back=createTexture(sback);
            UIPane pane=new UIPane(name,pos);            
            pane.setBack(back);
            return pane;
        }        
        private final IGraphicsTech tech=((WindowApp)AppState.getRunningApp()).getGraphicsTech();
        private Vector createPos(Element element){
            String poss=element.attributeValue("pos");
            String[] posl=poss.split(",");
            double x = Double.parseDouble(posl[0]);
            double y = Double.parseDouble(posl[1]);
            return new Vector(x,y,0,0);
        }
        private ITexture createTexture(String path){
            if(path==null){
                Debug.log("path for texture is not defined.return null texture");
                return null;
            }
            else return tech.createTexture(path);
        }        
    }
}
