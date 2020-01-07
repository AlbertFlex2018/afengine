package afengine.part.uiinput.control;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.window.IColor;
import afengine.core.window.IFont;
import afengine.core.window.IGraphicsTech;
import afengine.part.uiinput.UIActor;
import org.dom4j.Element;


public class UITextButton extends UIButtonBase{
    
    public static class UITextButtonCreator implements IUIActorCreator{
        
        /*
            <UITextButton reserve="" name="" pos="">
                <Text></Text>
                <Font></Font>
                <Color></Color>
                <Actions>
                    <toCover class=""/>
                    <toNormal class=""/>
                    <toDown class=""/>
                </Actions>
            </UITextButton>
        */
        @Override
        public UIActor createUi(Element element){
            String name=element.attributeValue("name");
            String pos=element.attributeValue("pos");
            Element text=element.element("Text");
            Element font=element.element("Font");
            Element color=element.element("Color");
            Element actions=element.element("Actions");            

            Vector posv=new Vector(0,0,0);
            String[] poss=pos.split(",");
            if(poss!=null){
                double x=Double.parseDouble(poss[0]);
                double y=Double.parseDouble(poss[1]);                
                posv.setX(x);
                posv.setY(y);
            }
            
            Text textt=new Text("DefaultText");
            if(text!=null){
                textt.value=text.getText();
            }
            
            IGraphicsTech tech=((WindowApp)(AppState.getRunningApp())).getGraphicsTech();
            
            return null;
        }        
    }
    
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
        super.height=font.getFontHeight();
        super.width=font.getFontWidth(text.value);
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
}