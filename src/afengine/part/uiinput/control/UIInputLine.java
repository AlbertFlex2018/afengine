package afengine.part.uiinput.control;

import afengine.core.util.Debug;
import afengine.core.util.Vector;
import afengine.core.window.IColor;
import afengine.core.window.IFont;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.UIFace;

/**
 *
 * @author Admin
 */
public class UIInputLine extends UIActor{
    
    private final char[] texts;
    private final int length;
    private IColor color;
    private IFont font;
    private int curpos;
    private boolean dirty=false;

    private ITexture back;

    public UIInputLine(String name, Vector pos,int length,IColor color,IFont font) {
        super(name, pos);
        texts=new char[length];
        this.length=length;
        this.color=color;
        this.font=font;
        curpos=0;
    }
    
    public void append(char word){
        if(curpos>=length){
            Debug.log("no more capacity for append char word.");
            return;
        }
        
        texts[curpos]=word;
        ++curpos;
        dirty=true;
    }
    public char back(){
        if(curpos<=0){
            Debug.log("no more capacity for append char word.");
            return (char) -1;            
        }
        
        --curpos;
        dirty=true;        
        return texts[curpos+1];
    }    
    public String getText(){
        return String.copyValueOf(texts);
    }
    public void setText(String text){        
        for(int i=0;i!=length&&i!=text.length();++i){
            char word=text.charAt(i);
            texts[curpos]=word;
            ++curpos;            
        }
        dirty=true;        
    }
    public void clear(){
        curpos=0;
        dirty=true;        
    }
    public int getTextLength(){
        return curpos;
    }
    public int getTextCapacity(){
        return length;
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
        dirty=true;        
    }

    private boolean showsplit=false;
    private long t;
    @Override
    public void update(long time) {
                
        if(back!=null){
            super.width=back.getWidth();
            super.height=back.getHeight();
        }
        if(font!=null&&dirty){
            super.width=font.getFontWidth(getText());
            super.height=font.getFontHeight();
            dirty=false;
        }
        
        t+=time;
        if(t>1000)
            t=0;
        
        if(t>0&&t<600){
            showsplit=true;            
        }else{
            showsplit=false;
        }
    }

    @Override
    public void draw(IGraphicsTech tech){
        if(font==null){
            font=tech.getFont();
        }
        if(color==null){
            color=tech.getColor();
        }
        int dx=super.getUiX();
        int dy=super.getUiY();
        if(back!=null){
            tech.drawTexture(dx, dy, back);
            dy=dy+back.getHeight()/2+font.getFontHeight()/2;            
            dx+=3;
        }
        if(curpos!=0){
            String text=getText();
            tech.drawText(dx, dy, font, color, text);
            int dxx=dx+font.getFontWidth(text);
            if(showsplit)
                tech.drawText(dxx, dy, font, color,"|");
        }        
    }
    
    //key type,key up,
    //mouse click,
    @Override
    protected void loadOutFromFace(UIFace face){
        face.removeMsgUiMap(InputServlet.INPUT_KEY_TYPE,this);
        face.removeMsgUiMap(InputServlet.INPUT_KEY_UP,this);
        face.removeMsgUiMap(InputServlet.INPUT_MOUSE_CLICK,this);
    }

    @Override
    protected void loadInToFace(UIFace face){
        face.addMsgUiMap(InputServlet.INPUT_KEY_TYPE,this);
        face.addMsgUiMap(InputServlet.INPUT_KEY_UP,this);
        face.addMsgUiMap(InputServlet.INPUT_MOUSE_CLICK,this);
    }    

    //key type,key up
    //mouse click
    @Override
    public boolean handle(Message msg) {
        return false;
    }        
}
