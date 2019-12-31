package afengine.core.util;

import afengine.core.AbPartSupport;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;


public class AssetCenter {
    private final static List<String> liblist=new LinkedList<>();
    private final static Map<String,String> assetsPathMap=new HashMap<>();

    private static String rootPath;
    public static String getAssetPath(String assetname){
        return assetsPathMap.get(assetname);
    }

    public static void setRootPath(String rootPath) {
        AssetCenter.rootPath = rootPath;
    }

    public static String getRootPath() {
        return rootPath;
    }
    public static String getAssetRealPath(String assetname,String assetpath){
        String path = assetsPathMap.get(assetname);
        if(path!=null){
            return path+"/"+assetpath;
        }
        return null;
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
                <AssetPaths>
                    <AssetName path=""/>
                    <AssetName path""/>
                    <AssetName path=""/>   
                    ..
                </AssetPaths>
                <AssetLibs>
                    <lib path=""/>
                    ...
                </AssetLibs>
            </Assets>
    */
    public static void outputAssetsConfig(Document doc){
        
    }
    
    public static class AssetsPartBoot implements IXMLPartBoot{

    /*
            <Assets root="">
                <AssetPaths>
                    <AssetName path=""/>
                    <AssetName path""/>
                    <AssetName path=""/>   
                    ..
                </AssetPaths>
                <AssetLibs>
                    <lib path=""/>
                    ...
                </AssetLibs>
            </Assets>
    */
        @Override
        public AbPartSupport bootPart(Element element) {
            
            return null;
        }        
    }
}
