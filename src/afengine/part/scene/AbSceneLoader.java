/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

/**
 * sceneloader for scene<br>
 * get a scene ,then use scene.setLoader(loader),<br>
 * and then you can call load,pause,resume,shutdown<br>
 * sceneloader present the lifespan of scene<br>
 * commonly use by SceneCenter
 * @see Scene
 * @see SceneCenter
 * @author Albert Flex
 */
public abstract class AbSceneLoader{
    private Scene thisScene;
    
    public Scene getThisScene() {
        return thisScene;
    }
    public void setThisScene(Scene thisScene) {
        this.thisScene = thisScene;
    }
    
    public abstract void load();
    public abstract void shutdown();

    public abstract void pause();
    public abstract void resume();    
}
