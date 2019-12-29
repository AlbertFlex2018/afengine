package afengine.component.render;

import afengine.core.util.Vector;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;

/**
 * render for texture,commonly image.<br>
 * @see RenderComponent
 * @author Albert Flex
 */
public class TextureRenderComponent extends RenderComponent{
    
    private ITexture texture;

    public TextureRenderComponent(ITexture texture){
        super();
        this.texture=texture;
    }        

    public ITexture getTexture() {
        return texture;
    }

    public void setTexture(ITexture texture) {
        this.texture = texture;
    }
    
    @Override
    public void render(IGraphicsTech tech){
        if(texture==null)return;
        
        double ax = this.getActor().getAbsoluteX();
        double ay = this.getActor().getAbsoluteY();
        Vector r = this.getActor().getTransform().rotation;
        Vector a = this.getActor().getTransform().anchor;
        Vector s = this.getActor().getTransform().scalation;
        
        tech.drawOther("transformtexture",
                new Object[]{texture, (int)ax,(int)ay,
                    a.getX(),a.getY(),s.getX(),s.getY(),(int)r.getZ()});
    }    
}
