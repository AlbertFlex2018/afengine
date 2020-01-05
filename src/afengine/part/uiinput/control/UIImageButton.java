package afengine.part.uiinput.control;

import afengine.core.util.Vector;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;

public class UIImageButton extends UIButtonBase{
    
    private ITexture normalTexture,downTexture,coverTexture;
    private ITexture now;
    
    public UIImageButton(String name,Vector pos,ITexture normal,ITexture down,ITexture cover) {
        super(name, pos);
        this.normalTexture=normal;
        this.downTexture=down;
        this.coverTexture=cover;
        this.now=normalTexture;
        super.width=this.now.getWidth();
        super.height=this.now.getHeight();
    }

    public ITexture getNormalTexture() {
        return normalTexture;
    }

    public void setNormalTexture(ITexture normalTexture) {
        this.normalTexture = normalTexture;
    }

    public ITexture getDownTexture() {
        return downTexture;
    }

    public void setDownTexture(ITexture downTexture) {
        this.downTexture = downTexture;
    }

    public ITexture getCoverTexture() {
        return coverTexture;
    }

    public void setCoverTexture(ITexture coverTexture) {
        this.coverTexture = coverTexture;
    }

    
    @Override
    protected void doDown() {
        if(downTexture!=null){
            this.now=this.downTexture;
            super.width=now.getWidth();
            super.height=now.getHeight();            
        }
    }

    @Override
    protected void doNormal() {
        if(normalTexture!=null){
            this.now=this.normalTexture;
            super.width=now.getWidth();
            super.height=now.getHeight();            
        }
    }

    @Override
    protected void doCover() {
        if(coverTexture!=null){
            this.now=this.coverTexture;
            super.width=now.getWidth();
            super.height=now.getHeight();            
        }
    }

    @Override
    public void draw(IGraphicsTech tech) {
        if(now!=null){
            int dx = this.getUiX();
            int dy = this.getUiY();
            tech.drawTexture(dx, dy, now);
        }        
    }   
}
