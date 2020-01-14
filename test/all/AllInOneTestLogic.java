package all;

import afengine.core.util.XMLEngineBoot;
import part.scene.AppLogicBase;

/**
 *
 * @author Admin
 */
public class AllInOneTestLogic extends AppLogicBase{
    public static void main(String[] args){
        XMLEngineBoot.bootEngine("test/all/allboot.xml");
    }
}
