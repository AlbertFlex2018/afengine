## std-core.window-1
游戏引擎窗口渲染管理 有关的标准  

========================
###相关类  
IColor,IFont,ITexture,IWindowAdjustHandler,  
IGraphicsDraw,IGraphicsState,IGraphicsCreate,  
IGraphicsTech,RenderStrategy  


========================
# core.window 标准相关抽象类的使用方法和需要接口的标准  
##IColor - 表示颜色的接口
###IColor代表一个颜色的相关操作，含有内置的普通颜色枚举
使用方法，获取红，绿，蓝，通道的颜色数值，获取颜色的反色  
###接口标准:全部需要实现
getRed(),getGreen(),getBlue(),getAlpha() - 获取颜色的数值，通道的数值  
getAntiColor(), - 获取相反的颜色  

##IFont - 表示字体的接口
###IFont代表一个字体的相关操作
使用方法，可以获取字体的路径，字体的名称，字体的风格(普通，粗体，斜体)，字体的高度，和给予字符串的绘制长度  
###接口标准:全部需要实现
getFontPath(),getFontName(),getFontStyle(),getFontHeight(),  
getFontWidth(text)  

##ITexture - 表示纹理的接口
###ITexture代表一个纹理的相关操作(通常指图片)
使用方法，获取纹理路径，纹理宽度、高度，获取缩放后的图片，获取截取后的图片  
###接口标准:全部需要实现
getTexturePath(),getWidth(),getHeight(),  
getScaleInstance(sx,sy),getCutInstance(x,y,w,h)  

##IGraphicsDraw - 表示一个图形绘制的接口
###IGraphicsDraw代表一系列的图形绘制操作
使用方法,若需要使用这个接口的绘制，需要先调用beginDraw,然后由传入的绘制策略绘制，最后需要调用endDraw方法释放资源,实际上绘制操作由绘制策略调用，  
在绘制策略里面，可以调用相关的绘制方法:清除屏幕，绘制点，绘制线，绘制多边形，绘制椭圆，绘制圆，绘制纹理，绘制文字，绘制一堆文字，绘制其他的(用以提供后续的兼容)  
###接口标准:全部需要实现
isDrawing(),beginDraw(),endDraw(),setDrawStrategy(),getDrawStrategy()  
clear(x,y,width,height,color)   -以什么颜色清楚屏幕区域  
drawPoint(x,y) 	- 绘制在x，y座标上的点  
drawLine(x,y,x2,y2) -绘制从屏幕座标的x,y 到x2,y2的直线  
drawPolygon(x[],y[],isFill) - 绘制多边形，如果isFill是true，则顺便填充  
drawOval(x,y,w,h,isFill) - 绘制椭圆，如果isFill是true，则顺便填充  
drawCircle(x,y,radius,isFill) - 绘制圆形，如果isFill是true，则顺便填充  
drawTexture(x,y,texture) - 在制定的位置上绘制纹理  
drawText(x,y,font,color,text) - 在制定位置绘制文字  
drawTexts(x[],y[],font[],color[],text[]) - 在指定的位置集以制定颜色和字体绘制制定的文字集合  
drawOther(type,value[]) - 绘制其他的东西

##IGraphicsState -表示一个图形绘制的状态的接口  
###IGraphicsState代表一个系列的图形绘制状态的操作   
使用方法，获取渲染图形绘制的渲染器名称，目前使用的字体、颜色的获取和使用，目前显示器的宽度，高度，窗口的位置，窗口的高度、宽度，是否是全屏，一分钟渲染的帧数，窗口的题目，窗口图标，  
###接口标准:全部需要实现
getRenderName(),getFont(),setFont(),getColor(),setColor()   
getMoniterWidth(),getMoniterHeight(),getDisplayX(),getDisplatY(),   
isFullWindow(),getWindowWidth(),getWindowHeight(),getFPS(),  
getFPS(),getTitle(),getIcon()  
moveWindowTo(x,y),rotate(rx,ry,rz),scale(sx,sy,sz),translate(x,y,z)  
setValue(name,obj),getValue(name)  
getMouseIcon(),setMouseIcon(texture)  

##IGraphicsCreate - 表示图形渲染器有关图形的相关创建的接口
###IGraphicsCreate代表一系列有关渲染的创建接口
使用方法，创建窗口，调整窗口，设置窗口调节处理器，销毁窗口，创建纹理，创建字体，创建颜色  
###接口标准:全部需要实现
createWindow(x,y,w,h,icon,title),createWindow(w,h,icon,title)  
createFullWindow(icon,title),destoryWindow(),    
setTitle(title),setIcon(texture),  
adjustWindow(x,y,w,h,newicon,newtitle),adjustWindow(w,h,newicon,newtitle)  
adjustFullWindow(newicon,newtitle)  
addAdjustHandler(handler)  
createFont(path,style,height)  
createColor(r,g,b,alpha),createColor(colortype)  

##IWindowAdjustHandler - 表示窗口调整的处理器的接口
###接口标准:
handleWindowAdjust(tech,oldfull,oldx,oldwidth,oldheigh,
				   newfull,newx,newy,newwidth,newheight);

##IGraphicsTech - 表示图形渲染
继承了IGraphicsState,IGraphicsCreate,IGraphicsDraw三个接口的全部图形接口

