package afengine.part.uiinput.control;

import afengine.core.util.Vector;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.uiinput.UIActor;

public class UIImageLabel extends UIActor{
    
    private ITexture texture;
    private boolean dirty=false;
    public UIImageLabel(String name, Vector pos) {
        super(name, pos);
    }

    public ITexture getTexture() {
        return texture;
    }

    public void setTexture(ITexture texture) {
        this.texture = texture;
        dirty=true;
    }

    @Override
    public void update(long time) {
        if(dirty){
            if(this.texture!=null){
                super.width=texture.getWidth();
                super.height=texture.getHeight();
            }
            dirty=false;
        }
    }
    
    @Override
    public void draw(IGraphicsTech tech){
        if(texture!=null){
            int dx=super.getUiX();
            int dy=super.getUiY();
            tech.drawTexture(dx, dy, texture);
        }        
    }    
}
