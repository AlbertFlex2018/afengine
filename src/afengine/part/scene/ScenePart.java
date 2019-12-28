/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

import afengine.core.AbPartSupport;

/**
 * 
 * @author Albert Flex
 */
public class ScenePart extends AbPartSupport{
    private final SceneCenter manager=SceneCenter.getInstance();
    public static final String PART_NAME="ScenePart";
    public ScenePart(){
        super(ScenePart.PART_NAME);
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public boolean update(long time) {
        manager.update(time);
        return true;
    }

    @Override
    public boolean shutdown() {
        manager.popToRoot();
        Scene scene = manager.getRunningScene();
        if(scene!=null){
            scene.getLoader().shutdown();
        }
        return true;
    }
}
