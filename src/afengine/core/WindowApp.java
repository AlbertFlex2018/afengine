/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core;

import afengine.core.util.Debug;
import afengine.core.util.IXMLAppTypeBoot;
import afengine.core.util.XMLEngineBoot;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.core.window.RenderStrategy;
import java.util.Iterator;
import org.dom4j.Element;

/**
 * Impl of AbApp for a GUIBased app,use instanced Interface of GraphicsTech for Display.<br>
 * @see IGraphicsTech
 * @see AbApp
 * @author Albert Flex
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

    public static class WindowAppBoot implements IXMLAppTypeBoot{

        /*
            <window size="full" or size="800,600" tech="" title="" icon="" exdraw="">                
                or.
                <before>
                    <draw pri="" class="" />
                </before>
                <root class=""/>
                <after>
                    <draw pri="" class=""/>
                </after>
            </window>
        */
        @Override
        public AbApp bootApp(Element element) {
            WindowApp wapp=null;
            boolean full=false;
            int width=800;
            int height=600;
            String sizee=element.attributeValue("size");
            if(sizee!=null){
                if(sizee.equals("full"))
                    full=true;
                else{
                    String[] size=sizee.split(",");
                    if(size.length==2){
                        width=Integer.parseInt(size[0]);
                        height=Integer.parseInt(size[1]);
                    }
                }
            }
            String title=element.attributeValue("title");
            IGraphicsTech tech = (IGraphicsTech)XMLEngineBoot.instanceObj(element.attributeValue("tech"));
            if(tech==null){
                System.out.println("window tech class not found!");
                return null;
            }
            String iconpath=element.attributeValue("icon");
            ITexture icont = tech.createTexture(iconpath);
            if(full){                
                wapp=new WindowApp(title,icont,tech);
            }
            else{
                wapp=new WindowApp(width,height,title,icont,tech);
            }
            
            String exdraw=element.attributeValue("exdraw");
            if(exdraw!=null){
                IDrawStrategy strategy =(IDrawStrategy)XMLEngineBoot.instanceObj(exdraw);
                
                if(strategy==null){
                    System.out.println("extern draw strategy class not found,will use renderstrategy for default.");
                    tech.setDrawStrategy(RenderStrategy.getInstance());
                }else{
                    tech.setDrawStrategy(strategy);
                }                
            }            
            else{
                /*
                <before>
                    <draw pri="" class="" />
                </before>
                <root class=""/>
                <after>
                    <draw pri="" class=""/>
                </after>                
                */
                tech.setDrawStrategy(RenderStrategy.getInstance());
                Element be = element.element("before");
                if(be!=null){
                    Iterator<Element> beiter=be.elementIterator();
                    long pril=1;
                    while(beiter.hasNext()){
                        Element before=beiter.next();
                        String pri = before.attributeValue("pri");
                        if(pri==null){
                            ++pril;
                        }else pril=Long.parseLong(pri);
                        IDrawStrategy strategy =(IDrawStrategy)XMLEngineBoot.instanceObj(before.attributeValue("class"));
                        if(strategy==null){
                            System.out.println("strategy class not found,will skeep for.");
                            continue;
                        }            
                        Debug.log("add before draw..");
                        RenderStrategy.getInstance().beforeRootMaps.put(pril,strategy);
                    }
                }
                Element root = element.element("root");
                if(root!=null){
                    IDrawStrategy rootstrategy =(IDrawStrategy)XMLEngineBoot.instanceObj(root.attributeValue("class"));
                    if(rootstrategy==null){
                         System.out.println("root strategy class not found,will skeep for."+root.attributeValue("class"));
                     }                                                    
                    else RenderStrategy.getInstance().rootStrategy=rootstrategy;
                }
                Element af = element.element("after");
                if(af!=null){
                    Iterator<Element> afiter=af.elementIterator();
                    long pril2=1;
                    while(afiter.hasNext()){
                        Element after=afiter.next();
                        String pri = after.attributeValue("pri");
                        if(pri==null){
                            ++pril2;
                        }else pril2=Long.parseLong(pri);
                        IDrawStrategy strategy =(IDrawStrategy)XMLEngineBoot.instanceObj(after.attributeValue("class"));
                        if(strategy==null){
                            System.out.println("strategy class not found,will skeep for."+after.attributeValue("class"));
                            continue;
                        }                                    
                        Debug.log("add before draw..");
                        RenderStrategy.getInstance().afterRootMaps.put(pril2,strategy);
                    }
                }
            }
            
            return wapp;
        }        
    }
}
