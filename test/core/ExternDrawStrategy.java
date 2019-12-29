package core;

import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;

public class ExternDrawStrategy implements IDrawStrategy{

    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/exdraw.xml");
//      System.out.println(WindowApp.WindowAppBoot.class.getName());
    }
    
    @Override
    public void draw(IGraphicsTech tech) {
        tech.drawText(80, 80,tech.getFont(),tech.getColor(),"ExternDrawStrategy");
        tech.drawText(0, tech.getFont().getFontHeight(),tech.getFont(),tech.getColor(),"FPS:"+tech.getFPS());
    }    
}
