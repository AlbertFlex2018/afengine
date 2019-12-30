package part.scene;

import afengine.core.IAppLogic;

/**
 *
 * @author Admin
 */
public class AppLogicBase implements IAppLogic{

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public boolean update(long time) {
        return true;
    }

    @Override
    public boolean shutdown() {
        return true;
    }    
}
