package part.scene;

import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.part.scene.Actor;
import afengine.part.scene.ActorComponent;
import afengine.part.scene.SceneFileHelp;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Albert Flex
 */
public class ActorElementLogic implements IAppLogic{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("test/assets/actortest1.xml");
    }
    
    private Scanner scan = new Scanner(System.in);
    private Actor actor;
    private final String actorxmlpath="test/assets/actor.xml";

    @Override
    public boolean init() {        
        return true;
    }
    
    public static void output(Actor actor,String xmlpath){
        try {
            //output actor
            Document doc=XMLEngineBoot.getXMLFileRoot(null);
            Element root = doc.getRootElement();
            SceneFileHelp.outputActorToXML(actor,root);
            XMLWriter writer = null;
            File file = new File(xmlpath);
//            if(!file.exists()){
//                file.createNewFile();
//            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            writer=new XMLWriter(new FileWriter(file),format);
            writer.write(doc);
            writer.close();            
        } catch (IOException ex) {
            Logger.getLogger(SceneFileHelp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static Actor load(String xmlpath){
        //output actor
        Document doc=XMLEngineBoot.getXMLFileRoot(xmlpath);
        Element root = doc.getRootElement();
        return SceneFileHelp.loadActorFromXML(root);
    }
    @Override
    public boolean update(long time) {
        String word = scan.nextLine();
        if(word.equals("load")){
            actor=load(actorxmlpath);
        }
        else if(word.equals("exit"))
            AppState.setValue("run", "false");
        else if(word.startsWith("output")){
            String[] cmd = word.split(" ");
            if(cmd.length==1){                
                output(actor,actorxmlpath);
            }
            else{
                String path = cmd[1];
                Debug.log("output to - "+path);
                output(actor,path);
            }            
        }
        else if(word.equals("show")){
            show(actor);
        }
        else if(word.startsWith("add")){
            String[] cmd=word.split(" ");
            actor.valueMap.put(cmd[1],cmd[2]);
        }
        else if(word.startsWith("remove")){
            String[] cmd=word.split(" ");            
            actor.valueMap.remove(cmd[1]);
        }
        else;
        return true;
    }
    public static void show(Actor actor){
        Debug.log("=========== log actor test ============");
        if(actor==null)
            Debug.log("actor NULL");
        else{
            Debug.log("id\t"+actor.id);
            Debug.log("name\t"+actor.getName());
            Debug.log("--datas--");
            Iterator<Map.Entry<String,String>> dataentry = actor.valueMap.entrySet().iterator();
            while(dataentry.hasNext()){
                Map.Entry<String,String> data = dataentry.next();
                Debug.log(data.getKey()+"\t"+data.getValue());
            }
            Debug.log("--components--");
            Iterator<ActorComponent> compiter = actor.getComponentsMap().values().iterator();
            while(compiter.hasNext()){
                ActorComponent comp=compiter.next();
                Debug.log("name\t"+comp.getComponentName());
                Debug.log("value\t"+comp.attributes.get("data"));
                Debug.log("realvalue\t"+comp.attributes.get("realvalue"));                
            }
        }
        Debug.log("=========== log actor test ============");
    }
    @Override
    public boolean shutdown() {
        return true;
    }
}
