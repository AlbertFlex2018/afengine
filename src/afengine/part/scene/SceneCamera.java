package afengine.part.scene;

import afengine.core.util.Vector;


public class SceneCamera{
    private Vector pos;//position
    private Vector look;//look at
    private double widthOffSize,heightOffSize;//宽度的偏移尺寸，高度的偏移尺寸

    public SceneCamera(Vector pos, Vector look,double offwidth,double offheight) {
        this.pos = pos;
        this.look = look;        
        this.widthOffSize=offwidth;
        this.heightOffSize=offheight;
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public Vector getLook() {
        return look;
    }

    public void setLook(Vector look) {
        this.look = look;
    }


    public double getWidthOffSize() {
        return widthOffSize;
    }

    public void setWidthOffSize(double widthOffSize) {
        this.widthOffSize = widthOffSize;
    }

    public double getHeightOffSize() {
        return heightOffSize;
    }

    public void setHeightOffSize(double heightOffSize) {
        this.heightOffSize = heightOffSize;
    }
    
}
