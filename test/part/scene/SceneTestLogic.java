package part.scene;

import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.util.Debug;
import afengine.core.util.IDCreator;
import afengine.core.util.XMLEngineBoot;
import afengine.part.scene.AbSceneLoader;
import afengine.part.scene.Scene;
import afengine.part.scene.SceneCenter;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class SceneTestLogic implements IAppLogic{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/scenetestboot1.xml");        
//        System.out.println(SceneTestLogic.class.getName());
//        System.out.println(SceneDemo.class.getName());
//        System.out.println(SceneLoader.class.getName());        
    } 
    
    private final Scanner scan = new Scanner(System.in);
    private SceneCenter sceneCenter;
    @Override
    public boolean init() {
        sceneCenter=SceneCenter.getInstance();
        return true;
    }

    @Override
    public boolean update(long time) {
        String word = scan.nextLine();
        if(word.equals("exit"))
            AppState.setValue("run","false");
        else if(word.equals("show"))
            showSceneCenter();
        else{
            String[] cmd =word.split(" ");
            if(cmd[0].equals("prepare")){
                if(cmd.length==2){
                    String name = cmd[1];
                    sceneCenter.prepareScene(new Scene(IDCreator.createId(),name));
                }
            }
            else if(cmd[0].equals("push")){
                if(cmd.length==2){
                    String name=cmd[1];
                    Scene findscene = sceneCenter.findPreparedScene(name);
                    if(findscene==null){
                        Debug.log("scene "+name+",not found.\n please input a valid scene name");
                    }
                    else{
                        sceneCenter.pushScene(findscene);
                        Debug.log("scene push successfully.");
                    }                    
                }
            }
            else if(cmd[0].equals("pop")){
                if(cmd.length!=1&&cmd[1].equals("root"))
                    sceneCenter.popToRoot();
                else sceneCenter.popScene();
            }
        }
        return true;
    }
    private void showSceneCenter(){
        Debug.log("============= scene log =============");
        Scene nowScene = sceneCenter.getRunningScene();
        if(nowScene==null)
            Debug.log("no scene for now update.");
        else Debug.log("nowScene:\t"+"id - "+nowScene.id+"\t name - "+nowScene.getName());
        Scene rootScene = sceneCenter.getRootScene();
        if(rootScene==null)
            Debug.log("no root scene center");
        else Debug.log("rootScene:\t"+"id - "+rootScene.id+"\t name - "+rootScene.getName());
        
        Debug.log("----- prepared scene -----");
        Debug.log("id\t name");
        Iterator<Scene> sceneiter = sceneCenter.getPreparedSceneMap().values().iterator();
        while(sceneiter.hasNext()){
            Scene scene = sceneiter.next();
            Debug.log(scene.id+"\t "+scene.getName());
        }

        Debug.log("----- stacke scene -----");
        Debug.log("size - "+sceneCenter.getSceneStack().size());
        Debug.log("id\t name");
        sceneiter = sceneCenter.getSceneStack().iterator();
        while(sceneiter.hasNext()){
            Scene scene = sceneiter.next();
            Debug.log(scene.id+"\t "+scene.getName());
        }        
        Debug.log("============= scene log =============");        
    }

    @Override
    public boolean shutdown() {
        return true;
    }    
    
    public static class SceneLoader extends AbSceneLoader{
            @Override
            public void load() {
                Debug.log("load scene:"+this.getThisScene().getName());
            }
            @Override
            public void shutdown() {
                Debug.log("shutdown scene:"+this.getThisScene().getName());
            }
            @Override
            public void pause() {
                Debug.log("pause scene:"+this.getThisScene().getName());
            }
            @Override
            public void resume() {
                Debug.log("resume scene:"+this.getThisScene().getName());
            }                    
    }
    
    public static class SceneDemo extends Scene{        
        public static long id=0;
        public SceneDemo(){
            super(0,"");
        }        
    }
}
