package afengine.part.uiinput.control;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.Debug;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.window.IColor;
import afengine.core.window.IFont;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.message.Message;
import afengine.part.uiinput.InputServlet;
import afengine.part.uiinput.UIActor;
import afengine.part.uiinput.UIFace;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.dom4j.Element;

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
    private char secretput;
    private boolean secret=false;
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
        return texts[curpos];
    }    
    public String getText(){
        return String.copyValueOf(texts,0,curpos);
    }
    public void setText(String text){        
        for(int i=0;i!=length&&i!=text.length();++i){
            char word=text.charAt(i);
            texts[curpos]=word;
            ++curpos;            
        }
        dirty=true;        
    }
    public void enableSecretMode(boolean enable){
        secret=enable;
    }

    public char getSecretput() {
        return secretput;
    }

    public void setSecretput(char secretput) {
        this.secretput = secretput;
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
    private boolean isOn=true;
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
        if(!isOn)return;
        
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
            String text=null;
             if(secret){
                    int size=getText().length();
                    char[] chars=new char[size];
                    for(int i=0;i!=size;++i)
                        chars[i]=secretput;                    
                    text=String.copyValueOf(chars);
             }else text=getText();
            tech.drawText(dx, dy, font, color, text);
            int dxx=dx+font.getFontWidth(text);
            if(isOn&&showsplit){
                tech.drawText(dxx, dy, font, color,"|");
            }
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
    public boolean handle(Message msg){        
        long type=msg.msgType;
        if(type==InputServlet.INPUT_KEY_TYPE){
            if(isOn){
                KeyEvent keyevent=(KeyEvent) msg.extraObjs[0];
                char word=keyevent.getKeyChar();
                int code=(word);
                Debug.log_panel(new Text("type - code:"+code));
                if(code!=KeyEvent.VK_BACK_SPACE)
                    append(word);
                return true;
            }
        }else if(type==InputServlet.INPUT_KEY_UP){
            if(isOn){
                KeyEvent keyevent=(KeyEvent) msg.extraObjs[0];
                int code=keyevent.getKeyCode();
                Debug.log_panel(new Text("up - code:"+code));
                if(code==KeyEvent.VK_BACK_SPACE){
                    back();
                    return true;
                }
            }
        }else if(type==InputServlet.INPUT_MOUSE_CLICK){
            MouseEvent event=(MouseEvent)msg.extraObjs[0];
            int mx=event.getX();
            int my=event.getY();
            boolean isIn=isPointInUi(mx,my);
            if(isOn&&!isIn){
                isOn=false;
                return true;
            }else if(!isOn&&isIn){
                isOn=true;
                return true;
            }
            return false;
        }
        return false;
    }           
    public static class UIInputLineCreator implements IUICreator{
        /*
            <UIInputLine name pos secret="">
                <back path=""></back>
                <color></color>
                <font></font>
                <size></size>
                <length></length>
                <holder></holder>
            </UIInputLine>
        */
        @Override
        public UIActor createUi(Element element){
            String name=element.attributeValue("name");
            Vector pos =createPos(element);
            if(pos==null)
                pos=new Vector(10,10,0,0);
            if(name==null)
                name="DefaultUiName";
        
            boolean bsecret=false;
            ITexture back=null;
            int length=0;
            String holder="";
            String esecret=element.attributeValue("secret");
            if(esecret!=null&&esecret.equals("true")){
                bsecret=true;
            }
            Element eback=element.element("back");
            if(eback!=null){
                ITexture backt=createTexture(eback);
                if(backt!=null)
                    back=backt;
            }
            Element elength=element.element("length");
            if(elength!=null){
                int ilength=Integer.parseInt(elength.getText());
                length=ilength;
            }
            Element eholder=element.element("holder");
            if(eholder!=null){
                holder=eholder.getText();
            }

            IFont font;
            IColor color=null;
            Element fonte = element.element("font");
            String sizes = element.elementText("size");
            String path=null;
            if(fonte==null){
                font= tech.createFont("Dialog", false,
                                IFont.FontStyle.PLAIN, 30);
            }
            else if(fonte.attribute("path")!=null){
                path=fonte.attributeValue("path");
                    font = tech.createFont(fonte.getText(), true, IFont.FontStyle.PLAIN, Integer.parseInt(sizes));                        
            }
            else{
                font= tech.createFont("Dialog", false,
                                IFont.FontStyle.PLAIN, 30);            
            }
            Element colore = element.element("color");
            String colors;

            if(colore==null){
               colors=IColor.GeneraColor.ORANGE.toString();
            }
            else colors = element.elementText("color");

            color=tech.createColor(IColor.GeneraColor.valueOf(colors));
            
            UIInputLine line=new UIInputLine(name,pos,length,color,font);
            line.setText(holder);
            line.back=back;
            line.enableSecretMode(bsecret);

            return line;
        }        
        
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
            if(path==null){
                Debug.log("path for texture is not defined.return null texture");
                return null;
            }
            else return tech.createTexture(path);
        }        
    }
}
