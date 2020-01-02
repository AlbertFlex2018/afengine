package afengine.part.uiinput;

import afengine.part.message.Message;

/**
 *
 * @author Albert Flex
 */
public interface IUIAction {
    public void action(UIActor actor,Message msg,String actionInfo);
}
