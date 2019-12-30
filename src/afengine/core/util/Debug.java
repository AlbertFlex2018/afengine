/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

import afengine.core.util.TextCenter.Text;
import afengine.core.window.IColor;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Debug Class is a static Class for the Global access.<br>
 * and provide some debug methods for the app.
 * @author Albert Flex
 */
public class Debug {
    
    private static boolean on=false;
    
    public static void enable(){
        on=true;
    }
    public static void off(){
        on=false;
    }
    
    public static void assetTrue(boolean statement,String falseInfo){
        if(!on)return;
        
        if(statement==false){
            System.out.println("asset false :"+falseInfo);
        }
    }
    
    public static void assetFalse(boolean statement,String trueInfo){
        assetTrue(!statement,trueInfo);
    }
    
    public static void log(String logText) {
        if(!on)return;

        System.out.println(logText);
    }

    public static void log_file(String filepath, String... logTexts) {
        if(!on)return;

        try {
            File file =new File("filepath");
            Writer out =new FileWriter(file);
            String data="888";
            out.write(data);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static final Deque<Text> logTexts = new ArrayDeque<>();

    public static void log_panel(Text text) {
        if(!on)return;

        logTexts.addLast(text);
        if(logTexts.size()>50){
            logTexts.pollFirst();
        }
    }    
    public static void clear_panellog(){
        logTexts.clear();
    }
    
    public static class DebugDrawStrategy implements IDrawStrategy{
        IColor color;
        @Override
        public void draw(IGraphicsTech tech) {
            if(color==null){
                color=tech.createColor(IColor.GeneraColor.RED);
            }

            Iterator<Text> logiter = logTexts.iterator();
            int height = tech.getFont().getFontHeight();
            while(logiter.hasNext()){
                Text tex = logiter.next();
                tech.drawText(0, height,tech.getFont(),color,tex.value);
                height+=tech.getFont().getFontHeight();
            }
        }        
    }
}
