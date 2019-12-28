/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * contains some Text for app.<br>
 * @author Albert Flex
 */
public class TextCenter {

    public static class Text{
        public String value;
        public Text(String value) {
            this.value = value;
        }
    }

    private static final Map<String, Text> textMap = new HashMap<>();

    public static Text getText(String id) {
        return textMap.get(id);
    }

    public static void addText(String id, Text text) {
        textMap.put(id, text);
    }

    public static void addTexts(String[] ids, Text[] texts) {
        for (int i = 0; i <= ids.length; ++i) {
            textMap.put(ids[i], texts[i]);
        }
    }    
    
    public static void removeText(String id){
        textMap.remove(id);    
    }
    public static void clearTexts(){
        textMap.clear();
    }
}