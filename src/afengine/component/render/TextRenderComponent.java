package afengine.component.render;

import afengine.core.util.TextCenter.Text;
import afengine.core.util.Vector;
import afengine.core.window.IColor;
import afengine.core.window.IFont;
import afengine.core.window.IGraphicsTech;

/**
 * text render<br>
 * @see RenderComponent
 * @author Albert Flex
 */
public class TextRenderComponent extends RenderComponent{

    private IFont font;
    private IColor color;
    private Text text;
    private IColor backColor;
    private boolean hasBack;

    public TextRenderComponent(IFont font,IColor color,Text text) {
        super();
        this.font=font;
        this.color=color;
        this.text=text;
        hasBack=false;
        backColor=null;
    }        
    public TextRenderComponent(IFont font,IColor color,IColor back,Text text){
        this(font,color,text);
        hasBack=true;
        backColor=back;
    }

    public IColor getBackColor() {
        return backColor;
    }

    public boolean isHasBack() {
        return hasBack;
    }

    public void setBackColor(IColor backColor) {
        if(!hasBack)hasBack=true;
        
        this.backColor = backColor;
    }

    public IColor getColor() {
        return color;
    }

    public void setColor(IColor color) {
        this.color = color;
    }

    
    @Override
    public void render(IGraphicsTech tech){
        double ax=this.getActor().getAbsoluteX();
        double ay=this.getActor().getAbsoluteY();
        Vector a=this.getActor().getTransform().anchor;
        float width = font.getFontWidth(text.value);
        float height=(float) (font.getFontHeight());
        float dx = (float) (ax-width*a.getX());
        float dy = (float) (ay-height*a.getY());
        
        float x1 = dx;
        float x2 = (float)(dx+width);
        float y1 = (float)(dy);
        float y2 = (float)(dy+height);
        float[] x = new float[]{x1,x2,x2,x1};
        float[] y = new float[]{y1,y1,y2,y2};
        if(hasBack){
            IColor oldc = tech.getColor();
           tech.setColor(backColor);
           tech.drawPolygon(x,y,true);
           tech.setColor(oldc);
        }
       tech.drawText(dx, (float) (dy+height*0.9),font,color,text.value);
    }
    
    @Override
    public boolean isPointIn(Vector point){
        double ax=this.getActor().getAbsoluteX();
        double ay=this.getActor().getAbsoluteY();
        Vector a = this.getActor().getTransform().anchor;
        float width = font.getFontWidth(text.value);
        float height=(float) (font.getFontHeight());
        float dx = (float) (ax-width*a.getX());
        float dy = (float) (ay-height*a.getY());
        float px = (float) ax;
        float py = (float) ay;
        if(px<dx||px>(dx+width))return false;
        
        return !(py<dy||py>(dy+height));
    }
}
