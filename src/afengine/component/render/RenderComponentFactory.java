package afengine.component.render;

import afengine.core.AppState;
import afengine.core.WindowApp;
import afengine.core.util.TextCenter.Text;
import afengine.core.window.IColor;
import afengine.core.window.IFont;
import afengine.core.window.IGraphicsTech;
import afengine.core.window.ITexture;
import afengine.part.scene.ActorComponent;
import afengine.part.scene.IComponentFactory;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.Element;

/**
 * factory for rendercomp,default contains TextRender and TextureRender<br>
 * @see TextRenderComponent
 * @see TextureRenderComponent
 * @author Albert Flex
 */
public class RenderComponentFactory implements IComponentFactory{

    public static interface IRenderCreator{
        public String getType();
        public RenderComponent create(Element element);
    }

    public final Map<String,IRenderCreator> extraCreatorMap=new HashMap<>();
        
    @Override
    public ActorComponent createComponent(Element element,Map<String,String> datas) {
        switch(element.attributeValue("type")){
            case "Text":
                return createText(element);
            case "Texture":
                return createTexture(element);
            default:
                return createExtra(element);
        }
    }   
    
    private RenderComponent createExtra(Element element){
        IRenderCreator creator = extraCreatorMap.get(element.attributeValue("type"));
        if(creator==null)
        {
            System.out.println("Creator Rendr Error,no creator for rendercomponent type"+element.attributeValue("type"));
            return null;
        }
        
        return creator.create(element);
    }
    /**
     * <Render type="Text">
     *      <text></text>
     *      <font path=""></font>
     *      <size></size>
     *      <color></color>
     *      //<backcolor></backcolor>
     * </Render>
     */
    private RenderComponent createText(Element element){
        Text text;
        IFont font;
        IColor color=null;
        IColor backcolor=null;
        Element texte = element.element("text");
        if(texte!=null){
            text=new Text(texte.getText());
        }
        else{
            text=new Text("DefaultText");
        }
        Element fonte = element.element("font");
        String sizes = element.elementText("size");
        String path=null;
        if(fonte==null){
            font= ((IGraphicsTech)((WindowApp)AppState.getRunningApp())
                    .getGraphicsTech()).createFont("Dialog", false,
                            IFont.FontStyle.PLAIN, 30);
        }
        else if(fonte.attribute("path")!=null){
            path=fonte.attributeValue("path");            
                font = ((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                    getGraphicsTech()).createFont(fonte.getText(), true, IFont.FontStyle.PLAIN, Integer.parseInt(sizes));                        
        }
        else{
            font= ((IGraphicsTech)((WindowApp)AppState.getRunningApp())
                    .getGraphicsTech()).createFont("Dialog", false,
                            IFont.FontStyle.PLAIN, 30);            
        }
        Element colore = element.element("color");
        String colors;

        if(colore==null){
           colors=IColor.GeneraColor.ORANGE.toString();
        }
        else colors = element.elementText("color");
        
        color=((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                getGraphicsTech()).createColor(IColor.GeneraColor.valueOf(colors));
        Element backcolore = element.element("backcolor");

        if(backcolore!=null){
            backcolor=((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                getGraphicsTech()).createColor(IColor.GeneraColor.valueOf(backcolore.getText()));
        }
        
        TextRenderComponent textcomp;
        if(backcolore!=null){
             textcomp= new TextRenderComponent(font,color,backcolor,text);            
        }
       else textcomp= new TextRenderComponent(font,color,text);
        
        return textcomp;
    }
    
    /**
     * <Render type="Texture">
     *      <texture>path</texture>
     *      //<cutsize x="" y="" width="" height=""/>
     * </Render>
     */
    private RenderComponent createTexture(Element element){
        String texturepath = element.elementText("texture");
        ITexture texture = ((IGraphicsTech)((WindowApp)AppState.getRunningApp()).
                getGraphicsTech()).createTexture(texturepath);
        Element cut = element.element("cutsize");
        if(cut!=null){
            String x = cut.attributeValue("x");
            String y = cut.attributeValue("y");
            String width = cut.attributeValue("width");
            String height = cut.attributeValue("height");
            if(x!=null&&y!=null&&width!=null&&height!=null){
                texture=texture.getCutInstance(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(width),Integer.parseInt(height));
            }
        }
        return new TextureRenderComponent(texture);
    }
}
