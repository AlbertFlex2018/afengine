package afengine.part.uiinput;

import afengine.core.AbPartSupport;
import java.util.List;

/**
 *
 * @author Albert Flex
 */
public class UIInputPart extends AbPartSupport{
    public static final String PART_NAME="UIInputPart";
    public UIInputPart() {
        super(UIInputPart.PART_NAME);
    }
    private UIInputCenter center;
    
    @Override
    public boolean init() {
        center=UIInputCenter.getInstance();
        return true;
    }
    @Override
    public boolean update(long time) {
        if(center.getPopupFace()!=null){
            updateFace(center.getPopupFace(),time);
            return true;
        }
        
        List<UIFace> activedFaces=center.getActivedfaceList();
        activedFaces.forEach((face) -> {
            updateFace(face,time);
        });
        return true;
    }
    private void updateFace(UIFace face,long time){
        List<UIActor> alluis=face.getActorList();
        alluis.forEach((ui) -> {
            ui.update(time);
        });
    }
    @Override
    public boolean shutdown(){
        return true;
    }    
}
