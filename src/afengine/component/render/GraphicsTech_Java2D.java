/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.component.render;

import afengine.core.util.Debug;
import afengine.core.window.IColor;
import afengine.core.window.IColor.GeneraColor;
import afengine.core.window.IDrawStrategy;
import afengine.core.window.IFont;
import afengine.core.window.IFont.FontStyle;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.core.window.IWindowAdjustHandler;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.List;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The GraphicsTech impl by Java2D<br>
 * @see IGraphicsTech
 * @author Albert Flex
 */
public class GraphicsTech_Java2D implements IGraphicsTech{            
    
    private final List<IWindowAdjustHandler> handlerlist;
    private JFrame window;
    private ITexture icon;
    private String title;
    private int width,height;
    private int mwidth,mheight;
    private boolean isFull;
    private int x,y;
    
    private IFont nowFont;
    private IColor nowColor;
    private ITexture nowCursor;
    
    
    private IDrawStrategy strategy;
    private BufferStrategy buffer;
    private Graphics2D graphics;
    private boolean isRendering;
    private int renderFPS;
    private Map<String,Object[]> valueMap;
    
    private boolean create;
    public GraphicsTech_Java2D()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        mwidth=kit.getScreenSize().width;
        mheight=kit.getScreenSize().height;
        handlerlist=new ArrayList<>();       
        Java2DFont f =new Java2DFont("Dialog",FontStyle.PLAIN,20);
        f.createFont();
        nowFont=f;        
        nowColor = new Java2DColor(GeneraColor.GREEN);
        isFull=false;
        isRendering=false;
        renderFPS=0;       
        create=true;
        valueMap=new HashMap<>();        
    }
    
    @Override
    public void createWindow(int x, int y, int w, int h, ITexture icon, String title) {
        isFull=false;
        this.title=title;
        this.icon=icon;
        this.x=x;
        this.y=y;
        if(w>mwidth)
            width=mwidth;
        else width=w;
        if(h>mheight)
            height=mheight;
        else height=h;
        setRenderSize(width,height);
        addListener();
        create=true;
    }
    
    private void addListener(){
        if(key){
            window.addKeyListener(keyl);            
        }
        if(mouse){
            window.addMouseListener(mousel);
        }
        if(mousemove){
            window.addMouseMotionListener(mousemovel);
        }
        if(mousewheel){
            window.addMouseWheelListener(mousewheell);
        }
        if(windowli){
            window.addWindowListener(windowl);
        }
    }

    @Override
    public void createWindow(int w, int h, ITexture icon, String title) {        
        if(w>mwidth)
            width=mwidth;
        else width=w;
        if(h>mheight)
            height=mheight;
        else height=h;

        int dx = mwidth/2-width/2;
        int dy = mheight/2-height/2;
        createWindow(dx,dy,w,h,icon,title);        
    }

    @Override
    public void createFullWindow(ITexture icon, String title) {
        this.title=title;
        this.icon=icon;
        isFull=true;
        setRenderFull();
        addListener();
        create=true;
    }
    
    @Override
    public void adjustWindow(int x, int y, int w, int h, ITexture newIcon, String newTitle) {
        window.setSize(width, height);
        window.setIconImage(((Java2DTexture)newIcon).getBufferedImage());
        window.setTitle(newTitle);
        window.setLocation(x, y);
        tick(false,x,y,w,h);
        this.x=x;
        this.y=y;
        this.width=w;
        this.height=h;
        this.icon=newIcon;
        this.title=newTitle;        
    }
    private void tick(boolean newfull,int newx,int newy,int newwidth,int newheight)
    {
        Iterator<IWindowAdjustHandler> iter = handlerlist.iterator();
        while(iter.hasNext())
        {
            IWindowAdjustHandler handler = iter.next();
            handler.handleWindowAdjust(this,isFull,x,y,width,height,newfull,newx,newy,newwidth,newheight);
        }        
    }

    @Override
    public void adjustWindow(int w, int h, ITexture newIcon, String newTitle) {
        if(w>mwidth)
            width=mwidth;
        else width=w;
        if(h>mheight)
            height=mheight;
        else height=h;

        int dx = mwidth/2-width/2;
        int dy = mheight/2-height/2;
        adjustWindow(dx,dy,w,h,newIcon,newTitle);
    }
    @Override
    public void adjustFullWindow(ITexture newIcon, String newTitle) {
        window.setSize(this.getMoniterWidth(),this.getMoniterHeight());
        window.setIconImage(((Java2DTexture)newIcon).getBufferedImage());
        window.setTitle(newTitle);        
        tick(true,0,0,this.getMoniterWidth(),this.getMoniterHeight());
        this.width=this.getMoniterWidth();
        this.height=this.getMoniterHeight();
        this.icon=newIcon;
        this.title=newTitle;
        window.setLocation(0,0);
        this.x=this.y=0;
    }

    @Override
    public void addAdjustHandler(IWindowAdjustHandler handler) {
        handlerlist.add(handler);
    }
    @Override
    public boolean isFullWindow()
    {
        return isFull;
    }
    @Override
    public void setTitle(String title) {
        this.title=title;
        window.setTitle(title);
    }

    @Override
    public void setIcon(ITexture texture) {
        this.icon=texture;
        window.setIconImage(((Java2DTexture)texture).getBufferedImage());
    }         
    
    @Override
    public void destroyWindow() {
        if(window!=null)
            window.dispose();
        buffer=null;                
    }
    
    @Override
    public ITexture createTexture(String iconPath) {
        Java2DTexture te = new Java2DTexture(iconPath);
        te.createTexture();
        return te;
    }
    
    @Override
    public ITexture createTexture(String iconPath, int cutFromX, int cutFromY, int cutWidth, int cutHeight) {
        return null;
    }
    
    @Override
    public IFont createFont(String fontvalue, boolean valueisFile, IFont.FontStyle style, int height) {
        Java2DFont font=null;
        if(valueisFile)
        {
            font = new Java2DFont(fontvalue,fontvalue,style,height);
        }
        else
        {
            font=new Java2DFont(fontvalue,style,height);
        }
        font.createFont();
        return font;
    }

    @Override
    public IColor createColor(int red, int green, int blue, int alpha) {
        return new Java2DColor(red,green,blue,alpha);
    }

    @Override
    public IColor createColor(IColor.GeneraColor colortype) {
        return new Java2DColor(colortype);
    }

    @Override
    public boolean isDrawing() {
        return isRendering;
    }

            Toolkit tk=Toolkit.getDefaultToolkit();  

    @Override
    public void beginDraw() {
        isRendering=true;
        graphics=(Graphics2D)buffer.getDrawGraphics();        
//        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);                
//        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);        
//        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);        
//        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);        
//        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);        
//        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);        
//        graphics.setRenderingHint(RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_ENABLE);                
        graphics.setFont(((Java2DFont)nowFont).getFont());
        graphics.setColor(((Java2DColor)nowColor).getColor());
        graphics.clearRect(0, 0, width, height);
    }

    private long lasttime;
    private long totaltime;
    private int count;
    @Override
    public void endDraw(){
        isRendering=false;
        if(graphics!=null)
        {           
            graphics.dispose();
        }
        else graphics=null;
        if(!buffer.contentsLost())
            buffer.show();
                
        Toolkit.getDefaultToolkit().sync();        
        
        long nowtime = System.currentTimeMillis();
        long delttime = nowtime-lasttime;
        lasttime=nowtime;
        totaltime+=delttime;
        ++count;
        if(totaltime>1000)
        {
            totaltime=0;
            renderFPS=count;
            count=0;
        }       
    }

    @Override
    public int getFPS()
    {
        return renderFPS;
    }
    @Override
    public IDrawStrategy getDrawStrategy() {
        return strategy;
    }

    @Override
    public void setDrawStrategy(IDrawStrategy strategy) {
        this.strategy=strategy;
    }

    
    @Override
    public void clear(int x, int y, int width, int height,IColor color) {
        Color oldc = graphics.getColor();        
        graphics.setColor(((Java2DColor)color).getColor());
        graphics.clearRect(x, y, width, height);
        graphics.setColor(oldc);
    }
    @Override
    public void drawPoint(float x, float y){
        graphics.drawRect((int)x,(int)y,1,1);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {
        graphics.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

    @Override
    public void drawPolygon(float[] x, float[] y, boolean fill) {
        int[] px =new int[x.length];
        int[] py =new int[x.length];
        for(int i=0;i!=px.length;++i)
        {
            px[i]=((int)x[i]);
            py[i]=(int)y[i];
        }

        if(fill)
        {
            graphics.fillPolygon(px, py,px.length);
        }
        else graphics.drawPolygon(px,py,px.length);
    }

    @Override
    public void drawOval(float x, float y, float w, float h, boolean fill) {
        if(fill)
        {
            graphics.fillOval((int)x, (int)y, (int)w,(int)h);
        }
        else
        {
            graphics.drawOval((int)x,(int)y, (int)w,(int)h);
        }
    }

    @Override
    public void drawCircle(float x, float y, float radius, boolean fill) {
        if(fill)
        {
            graphics.fillOval((int)(x-radius),(int)(y-radius),(int)(2*radius),(int)(2*radius));
        }
        else
        {
            graphics.drawOval((int)(x-radius),(int)(y-radius),(int)(2*radius),(int)(2*radius));
        }
    }

    @Override
    public void drawTexture(float x, float y, ITexture texture) {
        graphics.drawImage(((Java2DTexture)texture).getBufferedImage(), (int)x, (int)y,null);
    }

    @Override
    public void drawText(float x, float y, IFont font,IColor color, String text) {        
        Font oldf = graphics.getFont();
        Color oldc = graphics.getColor();
        graphics.setColor(((Java2DColor)color).getColor());
        graphics.setFont(((Java2DFont)font).getFont());
        graphics.drawString(text, x, y-font.getFontHeight());
        
        graphics.setFont(oldf);
        graphics.setColor(oldc);
    }
    @Override
    public void drawTexts(float[] x,float[] y,IFont[] font,IColor[] color,String[] text)
    {
        Font oldf = graphics.getFont();
        Color oldc = graphics.getColor();
        for(int i=0;i!=x.length;++i)
        {
            graphics.setFont(((Java2DFont)font[i]).getFont());
            graphics.setColor(((Java2DColor)color[i]).getColor());
            graphics.drawString(text[i], x[i], y[i]-font[i].getFontHeight());            
        }
        graphics.setFont(oldf);
        graphics.setColor(oldc);
    }
    @Override
    public void drawOther(String drawType, Object[] value) {
        if(drawType.equals("transformtexture"))
        {            
            //ITexture texture,int x,int y,float ax,float ay,
            //double sx,double sy,int degree
            ITexture texture = (ITexture)value[0];
            int x = (int)value[1];
            int y = (int)value[2];
            double ax =(double)value[3];
            double ay =(double)value[4];
            double sx = (double)value[5];
            double sy = (double)value[6];
            int degree = (int)value[7];
            AffineTransform trans = new AffineTransform();
            double w0=ax*texture.getWidth();
            w0*=sx;
            double h0=ay*texture.getHeight();
            h0*=sy;
            double sins = Math.sin(Math.toRadians(degree));
            double coss = Math.cos(Math.toRadians(degree));
            float dx = (float) (x + sins*h0-w0*coss);
            float dy = (float) (y - coss*h0-w0*sins);
            trans.translate(dx,dy);        
            trans.scale(sx,sy);
            trans.rotate(Math.toRadians(degree));
            graphics.drawImage(((Java2DTexture)texture).getBufferedImage(), trans,null);            
        }
        else
        {
            System.out.println("Do not support this type:"+drawType);
        }
    }

    @Override
    public String getRenderName() {
        return "Java2DGraphicsRender";
    }

    @Override
    public IFont getFont() {
        return nowFont;
    }

    @Override
    public void setFont(IFont font) {
        graphics.setFont(((Java2DFont)font).getFont());
        nowFont=font;        
    }

    @Override
    public IColor getColor() {
        return nowColor;
    }

    @Override
    public void setColor(IColor color) {
        graphics.setColor(((Java2DColor)color).getColor());
        nowColor=color;
    }

    @Override
    public int getMoniterWidth() {
        return mwidth;
    }

    @Override
    public int getMoniterHeight() {
        return mheight;
    }

    @Override
    public int getDisplayX() {
        return x;
    }

    @Override
    public int getDisplayY() {
        return y;
    }

    @Override
    public int getWindowWidth() {
        return width;
    }

    @Override
    public int getWindowHeight() {
        return height;
    }

    public Graphics2D getGraphics() {
        return graphics;
    }
    
    

    @Override
    public ITexture getMouseIcon() {        
        return nowCursor;
    }

    @Override
    public void setMouseIcon(ITexture texture){
        Toolkit tk=Toolkit.getDefaultToolkit();  
        Cursor cu=tk.createCustomCursor(((Java2DTexture)texture).getBufferedImage(),new Point(30,30),"stick");        
        window.setCursor(cu);        
        nowCursor=texture;
    }    

    @Override
    public void rotate(double rx, double ry, double rz){
    }    

    @Override
    public void scale(double sx, double sy, double sz){
    }

    @Override
    public void translate(double x, double y, double z){
    }

    @Override
    public Object[] getValue(String name) {
        return valueMap.get(name);
    }

    boolean key=false;
    boolean mouse=false;
    boolean mousemove=false;
    boolean mousewheel=false;
    boolean windowli=false;
    
    KeyListener keyl;
    MouseListener mousel;
    MouseMotionListener mousemovel;
    MouseWheelListener mousewheell;
    WindowListener windowl;
    @Override
    public void setValue(String name, Object[] obj){
        if(this.window==null){
            if(name.equals("keylistener")&&key==false)
            {
                KeyListener listener = (KeyListener)(obj[0]); 
                keyl=listener;
                key=true;
                valueMap.put(name,obj);
            }
            else if(name.equals("mouselistener")&&mouse==false)
            {
                MouseListener listener = (MouseListener)(obj[0]);
                mousel=listener;            
                mouse=true;
                valueMap.put(name,obj);
            }
            else if(name.equals("mousemovelistener")&&mousemove==false)
            {
                MouseMotionListener listener = (MouseMotionListener)(obj[0]);
                mousemovel=listener;
                mousemove=true;
                valueMap.put(name,obj);
            }
            else if(name.equals("mousewheellistener")&&mousewheel==false)
            {
                MouseWheelListener listener = (MouseWheelListener)(obj[0]);
                mousewheell=listener;
                mousewheel=true;
                valueMap.put(name,obj);
            }
            else if(name.equals("windowlistener")&&windowli==false){
                windowl=(WindowListener)(obj[0]);
                windowli=true;
                valueMap.put(name,obj);
            }
            else
            {
                System.out.println("Do not support this value.");
            }            
        }
        else{
            if(name.equals("keylistener")&&key==false)
            {
                KeyListener listener = (KeyListener)(obj[0]); 
                window.addKeyListener(listener);
                valueMap.put(name,obj);
            }
            else if(name.equals("mouselistener")&&mouse==false)
            {
                MouseListener listener = (MouseListener)(obj[0]);
                window.addMouseListener(listener);
                valueMap.put(name,obj);
            }
            else if(name.equals("mousemovelistener")&&mousemove==false)
            {
                MouseMotionListener listener = (MouseMotionListener)(obj[0]);
                window.addMouseMotionListener(listener);
                valueMap.put(name,obj);
            }
            else if(name.equals("mousewheellistener")&&mousewheel==false)
            {
                MouseWheelListener listener = (MouseWheelListener)(obj[0]);
                window.addMouseWheelListener(listener);
                valueMap.put(name,obj);
                valueMap.put(name,obj);
            }
            else if(name.equals("windowlistener")&&windowli==false){
                WindowListener windowlistener=(WindowListener)(obj[0]);                
                window.addWindowListener(windowlistener);
                valueMap.put(name,obj);
            }
            else
            {
                System.out.println("Do not support this value.");
            }                        
        }
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public ITexture getIcon() {
        return this.icon;
    }

    @Override
    public ITexture createTexture(URL url) {
        Java2DTexture texture = new Java2DTexture(url.toString(),url);
        texture.createTexture();
        return texture;
    }

    @Override
    public void moveWindowTo(int x, int y) {
        if(this.isFull){
            Debug.log("can not move ,cause full window.");
            return;
        }
        this.window.setLocation(x, y);
    }
    
    private static class NullRepaint extends RepaintManager
    {   
        public static void install()
        {
            RepaintManager repaint = new NullRepaint();
            repaint.setDoubleBufferingEnabled(false);
            RepaintManager.setCurrentManager(repaint);
        }

        public void addInvalidComponent(JComponent c)
        {
        // do nothing
        }

            public void addDirtyRegion(JComponent c, int x, int y,
                                       int w, int h)
            {
                // do nothing
            }

            public void markCompletelyDirty(JComponent c)
            {
                // do nothing
            }

            public void paintDirtyRegions()
            {
                // do nothing
            }
    }       

    private void setRenderSize(int width,int height)
    {
        if(window!=null)
            window.dispose();

        window = new JFrame(title);
        window.setSize(width, height);
        this.width=width;
        this.height=height;
        window.setLocation(x, y);        

        if(icon!=null)
        {
            if(icon instanceof Java2DTexture)
            {
                Java2DTexture texture=(Java2DTexture)icon;
                window.setIconImage(texture.getBufferedImage());
            }
        }
        setFrame();
        Container con = window.getContentPane();
        if(con instanceof JComponent)
            ((JComponent)con).setOpaque(false);
        NullRepaint.install();
        buffer = window.getBufferStrategy();
    }
    private void setRenderFull()
    {
        if(window!=null)
            window.dispose();

        GraphicsEnvironment env =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        window = new JFrame();
        
        window.setTitle(title);
        if(icon!=null)
        {
            if(icon instanceof Java2DTexture)
            {
                Java2DTexture texture=(Java2DTexture)icon;
                window.setIconImage(texture.getBufferedImage());
            }
        }
        
        
        setFrame();
        window.requestFocus();
        device.setFullScreenWindow(window);

        DisplayMode displayMode =device.getDisplayMode();
        if (displayMode != null &&
                device.isDisplayChangeSupported())
        {
            try {
                device.setDisplayMode(displayMode);
            }
            catch (IllegalArgumentException ex) { }
            // fix for mac os x
            window.setSize(displayMode.getWidth(),
                    displayMode.getHeight());            
        }
        width=displayMode.getWidth();
        height=displayMode.getHeight();
        createBuffer();
    }
    private void setFrame()
    {
        JFrame frame = window;
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e)
        {}
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setBackground(Color.black);
        frame.setResizable(false);
        frame.setIgnoreRepaint(true);
        frame.setLocationRelativeTo(null);
        
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setVisible(true);
        createBuffer();        
    }
    private void createBuffer()
    {
        try {
            EventQueue.invokeAndWait(() -> {
                window.createBufferStrategy(2);
            });
        }
        catch (InterruptedException | InvocationTargetException ex) 
        {
        }
        
        Container con = window.getContentPane();        
        if(con instanceof  JComponent)
            ((JComponent)con).setOpaque(false);
        
        NullRepaint.install();        
        buffer = window.getBufferStrategy();   
    }    
    
    private class Java2DColor implements IColor{

        public Java2DColor(int r,int g,int b,int a)
        {
            this.color=new Color(r,g,b,a);
        }
        public Java2DColor(GeneraColor type)
        {
            //        RED,GREEN,BLUE,
            //YELLOW,GREY,ORANGE,
            //WHITE,BLACK,PINK,
            switch(type)
            {
                case BLACK:
                    this.color=Color.BLACK;break;
                case WHITE:
                    this.color=Color.WHITE;break;                
                case PINK:
                    this.color=Color.PINK;break;                
                case YELLOW:
                    this.color=Color.YELLOW;break;                
                case GREY:
                    this.color=Color.GRAY;break;                
                case ORANGE:
                    this.color=Color.ORANGE;break;                
                case RED:
                    this.color=Color.RED;break;                
                case GREEN:
                    this.color=Color.GREEN;break;                
                case BLUE:
                    this.color=Color.BLUE;break;                
                default:this.color=Color.WHITE;break;
             }
        }

        private final Color color;

        public Color getColor() {
            return color;
        }


        @Override
        public int getRed() {
            return color.getRed();
        }


        @Override
        public int getGreen() {
            return color.getGreen();
        }

        @Override
        public int getBlue() {
            return color.getBlue();
        }

        @Override
        public int getAlpha() {
           return color.getAlpha();
        }

        @Override
        public IColor getAntiColor() {
            int r = 255-color.getRed();
            int g = 255-color.getGreen();
            int b = 255-color.getBlue();
            return new Java2DColor(r,g,b,this.getAlpha());
        }    
    }
    
    private  class Java2DFont implements IFont{
        private Font font;
        private String fontpath;
        private String fontName;
        private FontStyle style;
        private int fontSize;

        public Java2DFont(String fontpath,String fontName,FontStyle style,int size)
        {
            this.fontName=fontName;
            this.fontpath=fontpath;
            this.style=style;
            this.fontSize=size;
        }
        public Java2DFont(String fontName,FontStyle style,int size)
        {
            this.fontName=fontName;
            this.style=style;
            this.fontSize=size;
        }
        public void createFont()
        {
            if(fontpath==null)
            {
                    int st=Font.PLAIN;
                    if(style==FontStyle.BOLD)
                    {
                        st=Font.BOLD;
                    }
                    else if(style==FontStyle.ITALIC)
                    {
                        st=Font.ITALIC;
                    }
                    font = new Font(fontName,st,fontSize);            
            }
            else
            {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT,new File(fontpath));            
                if(font!=null)
                {
                    int st=Font.PLAIN;
                    if(style==FontStyle.BOLD)
                    {
                        st=Font.BOLD;
                    }
                    else if(style==FontStyle.ITALIC)
                    {
                        st=Font.ITALIC;
                    }
                    font = font.deriveFont(st, fontSize);                
                }
                else
                {
                    System.out.println("Create Font failed!!");
                }
            } catch (FontFormatException | IOException ex) {
                System.out.println("Create Font failed!!");
            }
            }
        }

        @Override
        public String getFontPath() {
            return fontpath;
        }

        @Override
        public String getFontName() {
            return fontName;
        }

        @Override
        public FontStyle getFontStyle() {
            return style;
        }

        public Font getFont() {
            return font;
        }

        @Override
        public int getFontHeight() {
            return fontSize;
        }

        @Override
        public int getFontWidth(String text) {
            FontMetrics trics= Toolkit.getDefaultToolkit().getFontMetrics(this.font);        
            return trics.stringWidth(text);
        }    
    }

    private class Java2DTexture implements ITexture{
        private BufferedImage img;
        private final String texturepath;
        private final boolean isURL;
        private final URL url;

        public Java2DTexture(String imgpath)
        {
            this.texturepath=imgpath;
            isURL=false;
            url=null;
        }
        public Java2DTexture(String imgpath,URL url)
        {
            this.texturepath=imgpath;
            this.url=url;
            isURL=true;
        }
        public Java2DTexture(Java2DTexture model)
        {
            this.texturepath=model.texturepath;
            this.url=model.url;        
            this.isURL=model.isURL;
        }
        private Toolkit kit = Toolkit.getDefaultToolkit();
        public void createTexture()
        {
            if(isURL)
            {
                try {
                    img=ImageIO.read(this.url);
                    System.out.println(url);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(GraphicsTech_Java2D.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
            else{                
                try {
                    img = ImageIO.read(new File(this.texturepath));
                } catch (IOException ex) {
                    Debug.log("image not found."+texturepath);
                }
            }                
        }
        @Override
        public String getTexturePath() {
            return texturepath;
        }

        @Override
        public int getWidth() {
            return img.getWidth(null);
        }

        @Override
        public int getHeight() {
            return img.getHeight(null);
        }

        public BufferedImage getBufferedImage() {
            return img;
        }

        @Override
        public ITexture getScaleInstance(double sx, double sy) {
            Java2DTexture texture = new Java2DTexture(this);                        
            BufferedImage bi = new BufferedImage((int)(img.getWidth(null) * sx),(int)(sy* img.getHeight(null)),BufferedImage.TYPE_INT_ARGB);

            Graphics2D grph = (Graphics2D) bi.getGraphics();
            grph.scale(sx, sy);
            grph.drawImage(img, 0, 0, null);
            grph.dispose();            
            texture.img=bi;
            return texture;
        }

        @Override
        public ITexture getCutInstance(int x, int y, int w, int h) {
            Java2DTexture texture = new Java2DTexture(this);                        
            texture.img=this.img.getSubimage(x, y, w, h);
            return texture;
        }    
    }    
}
