package part.uiinput;

import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import part.scene.AppLogicBase;

public class InputAdapterTestLogic extends AppLogicBase{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/inputtest1.xml");
    }
    @Override
    public boolean init(){
        Debug.log("init logic");   
        return true;
    }    
}
