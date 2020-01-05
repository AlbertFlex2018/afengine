package afengine.part.uiinput.control;

import afengine.core.util.Vector;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.message.Message;
import afengine.part.uiinput.UIActor;

public class UIPane extends UIActor{
    private ITexture back;    
    
    public UIPane(String name, Vector pos,int margin) {
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
        return super.children.stream().anyMatch((ui) -> (ui.handle(msg)));
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
}
