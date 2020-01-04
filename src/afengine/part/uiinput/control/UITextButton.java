package afengine.part.uiinput.control;

import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.window.IColor;
import afengine.core.window.IFont;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;


public class UITextButton extends UIButtonBase{

    private Text text;
    private IFont font;
    private IColor fontColor;    
    private ITexture backTexture;
      
    public UITextButton(String name,Vector pos,Text text){
        this(name,pos,text,null,null,null);
    }
    public UITextButton(String name,Vector pos,Text text,IFont font,IColor color,ITexture back) {
        super(name, pos);
        this.text=text;
        this.font=font;
        this.fontColor=color;
        this.backTexture=back;
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
    }

    public IColor getFontColor() {
        return fontColor;
    }
    public void setFontColor(IColor fontColor) {
        this.fontColor = fontColor;
    }

    public ITexture getBackTexture() {
        return backTexture;
    }
    public void setBackTexture(ITexture backTexture) {
        this.backTexture = backTexture;
    }

    @Override
    public void update(long time){
        if(backTexture!=null){            
            super.width=backTexture.getWidth();
            super.height=backTexture.getHeight();
            return;
        }        
        if(font!=null){
            super.width=font.getFontWidth(text.value);
            super.height=font.getFontHeight();
        }
    }
    
    
    //draw back fist.
    //figure out pos of render text.
    //draw text
    @Override
    public void draw(IGraphicsTech tech) {
        IFont f=font;
        IColor c=fontColor;
        if(f==null){
           f=tech.getFont();
           font=f;
        }
        if(c==null){
            c=tech.getColor();
            fontColor=c;
        }

        int dx=this.getUiX();
        int dy=this.getUiY();        
        int tx=dx;
        int ty=dy;
        if(backTexture!=null){
            tech.drawTexture(dx, dy, backTexture);
            int w = backTexture.getWidth();
            int h = backTexture.getHeight();
            tx+=w/2;            
            ty+=h/2;
            tx-=f.getFontWidth(text.value)/2;
            ty-=f.getFontHeight()/2;
        }                

        if(text!=null){
            tech.drawText(tx, ty, f, c, text.value);
        }
    }   
}
