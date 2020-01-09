package afengine.part.uiinput.control;

import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
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
    public static class UITextButtonCreator implements IUICreator{
        
        /*
            <UITextButton name="" pos="">
                <text value=""/>
                <color value=""/>
                <font path=""/>
                <size value=""/>
            </UITextButton>
        */
        @Override
        public UIActor createUi(Element element){
            
            return null;
        }                
        /*
            <text  value=""/>
        */
        private Text createText(Element element){
            String value=element.attributeValue("value");
            return null;
        }
        private IColor createColor(Element element){
            return null;
        }
        private IFont createFont(Element element){
            return null;
        }
    }
}