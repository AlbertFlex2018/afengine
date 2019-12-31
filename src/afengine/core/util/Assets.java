package afengine.core.util;

import afengine.core.AbPartSupport;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.dom4j.Element;


public class Assets {
    private final static List<String> liblist=new LinkedList<>();
    private final static Map<String,String> assetsPathMap=new HashMap<>();

    private static String rootPath;
    public static String getAssetPath(String assetname){
        return assetsPathMap.get(assetname);
    }

    public static void setRootPath(String rootPath) {
        Assets.rootPath = rootPath;
    }

    public static String getRootPath() {
        return rootPath;
    }

    public static void addAssetPath(String assetname,String path){        
        assetsPathMap.put(assetname, path);
    }
    public static void removeAssetsPath(String assetname){
        assetsPathMap.remove(assetname);
    }

    public static List<String> getLiblist() {
        return liblist;
    }    

    public static void loadLib(String libpath){
        
    }
    /*
            <Assets root="">
                <AssetName lib="true" path=""/>
                <AssetName path""/>
                <AssetName path=""/>
                ..
            </Assets>
    */
    public static void outputAssetsConfig(){
        
    }
    
    public static class AssetsPartBoot implements IXMLPartBoot{

        /*
            <Assets root="">
                <AssetName lib="true" path=""/>
                <AssetName path""/>
                <AssetName path=""/>
                ..
            </Assets>
        */
        @Override
        public AbPartSupport bootPart(Element element) {
            
            return null;
        }        
    }
}
