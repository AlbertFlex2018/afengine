package afengine.part.uiinput.control;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.IDCreator;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IColor;
import afengine.core.window.IFont;
import afengine.core.window.IGraphicsTech;
import afengine.part.uiinput.UIActor;
import org.dom4j.Element;


public class UITextButton extends UIButtonBase{        
    private Text text;
    private IFont font;
    private IColor fontColor;    
      
    public UITextButton(String name,Vector pos,Text text){
        this(name,pos,text,null,null);
    }
    public UITextButton(String name,Vector pos,Text text,IFont font,IColor color) {
        super(name, pos);
        this.text=text;
        this.font=font;
        this.fontColor=color;
        buttonState=NORMAL;            
        super.width=font.getFontWidth(text.value);
        super.height=font.getFontHeight();
    }

    @Override
    public void update(long time) {
        if(font!=null){
            super.width=font.getFontWidth(text.value);
            super.height=font.getFontHeight();            
        }
    }
    
    
    public Text getText() {
        return text;
    }
    public void setText(Text text) {
        this.text = text;
    }

    public IFont getFont() {
        return font;
    }
    public void setFont(IFont font) {
        this.font = font;
    }

    public IColor getFontColor() {
        return fontColor;
    }
    public void setFontColor(IColor fontColor) {
        this.fontColor = fontColor;
    }
    
    //figure out pos of render text.
    //draw text
    @Override
    public void draw(IGraphicsTech tech) {
        IFont f=font;
        IColor c=fontColor;
        if(f==null){
           f=tech.getFont();
           setFont(f);
        }
        if(c==null){
            c=tech.getColor();
            fontColor=c;
        }

        int dx=this.getUiX();
        int dy=this.getUiY();        

        if(text!=null){
            tech.drawText(dx, dy, f, c, text.value);
        }
    }   
    public static class UITextButtonCreator implements IUICreator{
        private final IGraphicsTech tech=((WindowApp)AppState.getRunningApp()).getGraphicsTech();        
        /*
            <UITextButton name="" pos="">
        *      <text></text>
        *      <font path=""></font>
        *      <size></size>
        *      <color></color>
                <actions>
                    <donormal action=""/>
                    <dodown action=""/>
                    <docover action=""/>
                </actions>
            </UITextButton>
        */
        @Override
        public UIActor createUi(Element element){
            String name=element.attributeValue("name");
            Vector pos =createPos(element);
            if(pos==null)
                pos=new Vector(10,10,0,0);
            if(name==null)
                name="DefaultUiName"+IDCreator.createId();
            
            Text text;
            IFont font;
            IColor color=null;
            IColor backcolor=null;
            Element texte = element.element("text");
            if(texte!=null){
                text=UIActor.getRealText(texte.getText());
            }
            else{
                text=new Text("DefaultText");
            }
            Element fonte = element.element("font");
            String sizes = element.elementText("size");
            String path=null;
            if(fonte==null){
                font= ((IGraphicsTech)((WindowApp)AppState.getRunningApp())
                        .getGraphicsTech()).createFont("Dialog", false,
                                IFont.FontStyle.PLAIN, 30);
            }
            else if(fonte.attribute("path")!=null){
                path=fonte.attributeValue("path");
                    font = ((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                        getGraphicsTech()).createFont(fonte.getText(), true, IFont.FontStyle.PLAIN, Integer.parseInt(sizes));                        
            }
            else{
                font= ((IGraphicsTech)((WindowApp)AppState.getRunningApp())
                        .getGraphicsTech()).createFont("Dialog", false,
                                IFont.FontStyle.PLAIN, 30);            
            }
            Element colore = element.element("color");
            String colors;

            if(colore==null){
               colors=IColor.GeneraColor.ORANGE.toString();
            }
            else colors = element.elementText("color");

            color=((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                    getGraphicsTech()).createColor(IColor.GeneraColor.valueOf(colors));
                       
            UITextButton button=new UITextButton(name,pos,text,font,color);
            addActions(button,element.element("actions"));
            return button;            
        }                
         private Vector createPos(Element element){
            String poss=element.attributeValue("pos");
            String[] posl=poss.split(",");
            double x = Double.parseDouble(posl[0]);
            double y = Double.parseDouble(posl[1]);
            return new Vector(x,y,0,0);
        }
       
         /*
            <actions>
                
            </actions>
         */
        private void addActions(UITextButton button,Element actions){
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
        }
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