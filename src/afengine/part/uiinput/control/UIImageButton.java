package afengine.part.uiinput.control;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.uiinput.UIActor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Element;

public class UIImageButton extends UIButtonBase{
    
    private ITexture normalTexture,downTexture,coverTexture;
    private ITexture now;
    
    public UIImageButton(String name,Vector pos,ITexture normal,ITexture down,ITexture cover) {
        super(name, pos);
        this.normalTexture=normal;
        this.downTexture=down;
        this.coverTexture=cover;
        this.now=normalTexture;
        super.width=this.now.getWidth();
        super.height=this.now.getHeight();
    }

    public ITexture getNormalTexture() {
        return normalTexture;
    }

    public void setNormalTexture(ITexture normalTexture) {
        this.normalTexture = normalTexture;
    }

    public ITexture getDownTexture() {
        return downTexture;
    }

    public void setDownTexture(ITexture downTexture) {
        this.downTexture = downTexture;
    }

    public ITexture getCoverTexture() {
        return coverTexture;
    }

    public void setCoverTexture(ITexture coverTexture) {
        this.coverTexture = coverTexture;
    }

    
    @Override
    protected void doDown() {
        if(downTexture!=null){
            this.now=this.downTexture;
            super.width=now.getWidth();
            super.height=now.getHeight();            
        }
    }

    @Override
    protected void doNormal() {
        if(normalTexture!=null){
            this.now=this.normalTexture;
            super.width=now.getWidth();
            super.height=now.getHeight();            
        }
    }

    @Override
    protected void doCover() {
        if(coverTexture!=null){
            this.now=this.coverTexture;
            super.width=now.getWidth();
            super.height=now.getHeight();            
        }
    }

    @Override
    public void draw(IGraphicsTech tech) {
        if(now!=null){
            int dx = this.getUiX();
            int dy = this.getUiY();
            tech.drawTexture(dx, dy, now);
        }        
    }           
    
    public static class UIImageButtonCreator implements IUICreator{
        /*
            <UIImageButton name=""  pos="">
                <textures/>
                    <normal path=""/>
                    <cover path=""/>
                    <down path=""/>        
                </textures/>
                <actions>
                    <donormal action=""/>
                    <docover action=""/>
                    <dodown action=""/>
                </actions>
            </UIImageButton>
        */
        @Override
        public UIActor createUi(Element element){
            String name=element.attributeValue("name");
            Vector pos =createPos(element);
            if(pos==null)
                pos=new Vector(10,10,0,0);
            if(name==null)
                name="DefaultUiName";
            Element textures=element.element("textures");            
            ITexture normalimage=null,downimage=null,coverimage=null;
            if(textures==null){
                Debug.log("no texture for button defined.!");
                return null;
            }
            Element normal=textures.element("normal");
            if(normal==null){
                Debug.log("no normal texture for button defined.!");                
                return null;
            }
            normalimage=createTexture(normal);
            Element down=textures.element("down");
            if(down!=null){
                downimage=createTexture(down);
            }
            Element cover=textures.element("cover");
            if(cover!=null){
                coverimage=createTexture(cover);
            }
            
            if(normalimage==null){
                Debug.log("normal texture for button creation failed.!");                
                return null;
            }
            
            UIImageButton button=new UIImageButton(name,pos,normalimage,downimage,coverimage);
            Element actions=element.element("actions");
            if(actions!=null){
                Element docover=actions.element("dcover");
                Element dodown=actions.element("dodown");
                Element donormal=actions.element("donormal");
                if(docover!=null){
                    IUIAction coveraction=createAction(docover);
                    if(coveraction!=null){
                        button.setToCoverAction(coveraction);
                    }
                }
                if(dodown!=null){
                    IUIAction action=createAction(dodown);
                    if(action!=null){
                        button.setToDownAction(action);
                    }
                }
                if(donormal!=null){
                    IUIAction action=createAction(donormal);
                    if(action!=null){
                        button.setToCoverAction(action);
                    }
                }
            }
            return button;
        }        
        /*
            <name path="" url=""/>
        */
        private final IGraphicsTech tech=((WindowApp)AppState.getRunningApp()).getGraphicsTech();
        private Vector createPos(Element element){
            String poss=element.attributeValue("pos");
            String[] posl=poss.split(",");
            double x = Double.parseDouble(posl[0]);
            double y = Double.parseDouble(posl[1]);
            return new Vector(x,y,0,0);
        }
        private ITexture createTexture(Element element){
            String path=element.attributeValue("path");
            ITexture texture=null;
            if(path==null){
                Debug.log("path for texture is not defined.return null texture");
                return null;
            }
            return tech.createTexture(path);
        }
        /*
            <name action=""/>
        */
        private IUIAction createAction(Element element){
            String action=element.attributeValue("action");
            if(action==null){
                Debug.log("action for button not defined");
                return null;
            }
            IUIAction act=(IUIAction)XMLEngineBoot.instanceObj(action);
            return act;
        }
    }
}
