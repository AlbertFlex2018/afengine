/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core;

import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;

/**
 *
 * @author Admin
 */
public class WindowApp extends AbApp{
    public static final String APPTYPE="window";
    int x,y,width,height;
    boolean full;
    String title;
    ITexture icon;
    
    private IGraphicsTech graphicsTech;

    public WindowApp(int x,int y,int width,int height,String title,ITexture icon,IGraphicsTech tech){
        super(WindowApp.APPTYPE);
        this.x=x;this.y=y;
        if(width>tech.getMoniterWidth())
            this.width=tech.getMoniterWidth();
        else this.width=width;
        if(height>tech.getMoniterHeight())
            this.height=tech.getMoniterHeight();
        full=false;
        this.icon=icon;this.title=title;
        this.graphicsTech=tech;
    }
    public WindowApp(int width,int height,String title,ITexture icon,IGraphicsTech tech){
        super(WindowApp.APPTYPE);        
        if(width>tech.getMoniterWidth())
            this.width=tech.getMoniterWidth();
        else this.width=width;
        if(height>tech.getMoniterHeight())
            this.height=tech.getMoniterHeight();
        else this.height=height;        
        this.x=tech.getMoniterWidth()/2-this.width/2;
        this.y=tech.getMoniterHeight()/2-this.height/2;
        full=false;
        this.icon=icon;this.title=title;
        this.graphicsTech=tech;        
    }
    public WindowApp(String title,ITexture icon,IGraphicsTech tech){
        super(WindowApp.APPTYPE);
        full=true;
        this.icon=icon;
        this.title=title;        
        this.graphicsTech=tech;
    }

    public IGraphicsTech getGraphicsTech() {
        return graphicsTech;
    }

    public void setGraphicsTech(IGraphicsTech graphicsTech) {
        this.graphicsTech = graphicsTech;
    }
        
    @Override
    public boolean init() {
        if(graphicsTech==null){
            System.out.println("You should set a graphicsTech for WindowApp ,while you haven't done this.");
            return false;
        }
        if(full){            
            graphicsTech.createFullWindow(icon, title);
        }
        else{
            graphicsTech.createWindow(x,y,width,height,icon, title);
        }
        return true;
    }

    @Override
    public boolean update(long time) {
        graphicsTech.beginDraw();
        if(graphicsTech.getDrawStrategy()!=null){
            graphicsTech.getDrawStrategy().draw(graphicsTech);
        }
        graphicsTech.endDraw();
        return true;
    }

    @Override
    public boolean shutdown(){
        graphicsTech.destroyWindow();
        return true;
    }    
}
