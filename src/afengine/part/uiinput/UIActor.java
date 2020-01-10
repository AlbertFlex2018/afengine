package afengine.part.uiinput;

import afengine.core.util.Debug;
import afengine.core.util.TextCenter;
import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.part.message.IMessageHandler;
import afengine.part.message.Message;
import java.util.LinkedList;
import java.util.List;


public class UIActor implements IMessageHandler,IDrawStrategy{

    private String uiName;
    private UIFace face;
    
    //理论上来说，只有容器的界面才能有子节点，容器的消息处理和绘制处理交由容器自己管理
    protected final List<UIActor> children=new LinkedList<>();
    protected UIActor parent=null;
    
    protected Vector pos;
    protected int width,height;    
    
    public UIActor(String name,Vector pos){
        this.uiName=name;
        this.pos=pos;
        width=0;
        height=0;
    }

    public String getUiName() {
        return uiName;
    }
    
    protected void addChild(UIActor child){
        if(children.contains(child)){
            Debug.log("already add child for "+child.uiName);
            return;
        }        
        children.add(child);
        if(child.parent!=null){
            child.parent.children.remove(child);
        }
        child.parent=this;
    }
    
    public static Text getRealText(String text){
        if(!text.startsWith("@")){
            return new Text(text);
        }else{
            String reg=text.substring(1,text.length());
            return TextCenter.getText(reg);
        }
    }
    public void setUiName(String uiName) {
        this.uiName = uiName;
    }
    public UIFace getFace(){
        return face;
    }
    public void setFace(UIFace face){
        setFFace(face);
    }
    private void setFFace(UIFace face){        
        this.face=face;
        loadInToFace(face);
        
        children.forEach((ui) -> {
            ui.loadOutFromFace(ui.face);
            ui.setFFace(face);
        });
    }
    
    //need override,use setface to call them
    protected void loadInToFace(UIFace face){}
    protected void loadOutFromFace(UIFace face){}
    
    
    public UIActor getParent() {
        return parent;
    }
    public List<UIActor> getUiChildren(){
        return children;
    }

    public void setParent(UIActor parent) {
        this.parent = parent;
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }
        
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }    

    public int getUiX(){
        double x=this.pos.getX();
        UIActor uiparent=this.parent;
        while(uiparent!=null){
            x+=uiparent.getPos().getX();
            uiparent=uiparent.parent;
        }
        
        return (int) x;
    }
    public int getUiY(){
        double y=this.pos.getY();
        UIActor uiparent=this.parent;
        while(uiparent!=null){
            y+=uiparent.getPos().getY();
            uiparent=uiparent.parent;
        }
        
        return (int)y;
    }
    
    protected boolean isPointInUi(int x,int y){
        int minx=getUiX();
        int maxx=getUiX()+this.width;
        int minh=getUiY();
        int maxh=getUiY()+this.height;

        return (x>=minx&&x<=maxx&&y>=minh&&y<=maxh);
    }
    
    //need override
    @Override
    public void draw(IGraphicsTech tech){}
    
    //need override
    @Override
    public boolean handle(Message msg){
        return false;
    }
    
    //need override
    public void update(long time){}
}
