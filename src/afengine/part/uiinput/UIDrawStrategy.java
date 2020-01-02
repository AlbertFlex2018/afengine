package afengine.part.uiinput;

import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import java.util.List;

public class UIDrawStrategy implements IDrawStrategy{
    private final UIInputCenter center;
    public UIDrawStrategy(){
        center=UIInputCenter.getInstance();
    }
    
    //先绘制激活的ui
    //再绘制popupFace
    @Override
    public void draw(IGraphicsTech tech) {

        List<UIFace> faceList=center.getActivedfaceList();
        faceList.forEach((face) -> {
            drawFace(face,tech);
        });
        
        UIFace popupFace=center.getPopupFace();
        if(popupFace!=null){
            drawFace(popupFace,tech);
        }
    }   
    private void drawFace(UIFace face,IGraphicsTech tech){
        List<UIActor> uilist=face.getActorList();
        uilist.forEach((ui) -> {
            ui.draw(tech);
        });
    }
}
