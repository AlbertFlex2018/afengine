/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.scene;

import afengine.core.util.Debug;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Admin
 */
public class SceneCenter {
    
    private static SceneCenter _instance;
    public static SceneCenter getInstance(){
        if(_instance==null)
            _instance=new SceneCenter();
        
        return _instance;
    }
    
    private final Map<String,Scene> preparedSceneMap=new HashMap<>();
    private final Stack<Scene> sceneStack;    
    private Scene runningScene;
    private Scene rootScene;

    private SceneCenter(){
        sceneStack=new Stack<>();
    }
    public void pushScene(Scene scene){    
        if(rootScene==null){
            rootScene=scene;            
            runningScene=rootScene;
            runningScene.getLoader().load();
            return;
        }
        if(runningScene==rootScene&&runningScene!=null&&rootScene!=null){
            runningScene.getLoader().pause();            

            runningScene=scene;
            scene.getLoader().load();
            return;
        }
        if(runningScene!=null&&runningScene!=rootScene){
            runningScene.getLoader().pause();
            sceneStack.push(runningScene);            
            scene.getLoader().load();
            runningScene=scene;
        }
    }
    public void popScene(){        
        if(runningScene==rootScene){
            return;
        }

        runningScene.getLoader().shutdown();
        if(sceneStack.isEmpty())
        {
            if(rootScene!=null){
                runningScene=rootScene;
            }
            else{
                Debug.log("There is no scene to run!");
                runningScene=null;
            }
        }
        else runningScene=sceneStack.pop();

        if(runningScene!=null)
            runningScene.getLoader().resume();
    }
    public void popToRoot(){        
        if(rootScene==null||runningScene==null){
            return;
        }

        if(rootScene==runningScene){
            return;
        }

        runningScene.getLoader().shutdown();
        if(!sceneStack.isEmpty())
        {
            ListIterator<Scene> sceneiter = sceneStack.listIterator(sceneStack.size()-1);
            while(sceneiter.hasPrevious()){
                Scene scene = sceneiter.previous();
                scene.getLoader().shutdown();
            }
        }
        sceneStack.clear();
        runningScene=rootScene;       
        if(runningScene!=null){
            runningScene.getLoader().resume();
        }
    }
    public void changeToScene(Scene scene){        
        if(rootScene!=null&&runningScene!=rootScene){
            runningScene.getLoader().shutdown();            
        }
        else if(rootScene==null&&runningScene==null){
            rootScene=scene;
        }

        scene.getLoader().load();
        runningScene=scene;
    }
    public Scene getRunningScene(){
        return runningScene;
    }
    public void update(long time){
        if(runningScene!=null){
            runningScene.updateScene(time);
        }
        else{
            if(rootScene!=null){
                runningScene=rootScene;
                rootScene.getLoader().resume();
                rootScene.updateScene(time);
            }
        }
    }
    
    public void prepareScene(Scene scene){
        if(!preparedSceneMap.containsKey(scene.getName())){
            preparedSceneMap.put(scene.getName(), scene);
            scene.getLoader().load();
            scene.getLoader().pause();
        }
    }
    
    public Scene findPreparedScene(String name){
        return preparedSceneMap.get(name);
    }    
}
