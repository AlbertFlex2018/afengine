package afengine.part.uiinput.control;

import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.window.IColor;
import afengine.core.window.IFont;
import afengine.core.window.IGraphicsTech;
import afengine.part.uiinput.UIActor;

/**
 *
 * @author Admin
 */
public class UITextLabel extends UIActor{
    
    private Text text;
    private IColor color;
    private IFont font;
    private boolean hdirty=false;
    public UITextLabel(String name,Vector pos,Text text){
        this(name,pos,text,null,null);        
    }
    public UITextLabel(String name, Vector pos,Text text,IColor color,IFont font) {
        super(name, pos);
        this.text=text;
        this.color=color;
        this.font=font;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;        
    }

    public IColor getColor() {
        return color;
    }

    
    public void setColor(IColor color) {
        this.color = color;
    }

    public IFont getFont() {
        return font;
    }

    public void setFont(IFont font) {
        this.font = font;
        hdirty=true;
    }

    @Override
    public void update(long time){
        if(hdirty){
            if(font!=null&&text!=null&&text.value!=null){
                super.height=font.getFontHeight();
            }            
            hdirty=false;
        }
        super.width=font.getFontWidth(text.value);
    }

    
    @Override
    public void draw(IGraphicsTech tech) {
        if(text!=null&&text.value!=null){
            if(font==null){
                font=tech.getFont();
            }
            if(color==null){
                color=tech.getColor();
            }
            int dx=super.getUiX();
            int dy=super.getUiY();
            tech.drawText(dx, dy, font, color, text.value);
        }        
    }    
}
