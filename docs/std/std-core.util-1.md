## std-core.util-1
游戏引擎通用库 有关的标准  

========================  
###相关类
process,processstate,processmanager  
idcreator,debug,textcenter,threadpool,  
vector,transform,  
xmlengineboot,ixmlapptypeboot,ixmlpartboot,  

========================  
# core.util 标准相关抽象类的使用方法和需要接口的标准  
##Process - 应用进程抽象类  
###Process是代表一个可以运行的进程，  
使用方法，实现process并创建process的实例，随后调用init方法初始化process,并在生命周期中不断调用update,用以更新进程内部的状态，在生命周期之中，可以调用sucess,failed,abort,exception,方法结束进程的运行，届时将会根据结束的状态调用抽象并自定义的结束方法，用processstate状态表示自身的运行状态，
###接口标准:
普通接口:
getProcessId,getProcessName,getState,  
success() - 成为结束进程  
failed() - 错误结束进程  
abort() - 中断结束进程  
特殊接口:  
init，- 初始化进程抽象，需重写实现  
update, - 更新进程抽象，需要重写实现  
successEnd, - 成为结束回调，需要重写实现  
failedEnd, - 失败结束回调，需要重写实现  
abortEnd, - 中断结束回调，需要重写实现  
killed, - 进程被杀掉(只有当进程放入进程管理器，并调用管理器的kill方法)，需要重写实现  

##ProcessManager - 进程管理器
###ProcessManager是存放并且管理一堆进程的管理类
使用方法，创建processmanager实例，创建process的实例，使用attachprocess方法将process加入到Processmanager中，在processmanager运行当中可以调用killprocess删除指定processid的进程，也可以调用containsprocess判断是否存在指定processid的进程，需要调用updateallprocess更新全部的process
###接口标准:
特殊接口:  
attachProcess(process) - 加入process，已存在实例的引用，则不做任何事  
killProcess(processid) - 删除指定processid的进程，如果不存在，则不做任何事  
findProcess(processid) - 查找指定processid的进程，如果不存在返回null  
containsProcess(processid) - 判断是否存在指定Processid的进程，存在返回true,否则false  
updateProcess(time) - 更新全部的进程

##IDCreator - ID创造器
###IDCreator创建唯一的ID
使用方法，若需要获得针对idcreator提供的唯一的id，则调用createId方法，如果需要以一个id为判断获得唯一的id，则调用validId(id)，若以一个id生成idcreator，则调用createidcreator(id)
###接口标准皆为全局静态方法:
createIDCreator(id)  
createId(),  
validId(id),  

##Debug - 调试器
###Debug可以用以断言和日志输出
使用方法，若要打开调试功能，调用静态方法enable,否则默认不开启
###接口标准:
assetTrue(bool,falseinfo) - 如果bool的逻辑为假，则输出falseinfo  
assetFalse(bool,trueinfo) - 如果bool的逻辑为真，则输出trueinfo  
log(text) - 控制台日志输出text  
log_file(filepath,texts) - 输出日志消息到指定文件路径
log_panel(text) - 输出日志消息到屏幕上，实际上仅仅只是输出到一个保存屏幕日志消息的队列里  
clear_panellog() - 清除屏幕的日志消息，实际上只是清除日志消息队列里的消息  

##TextCenter - 文字中心
###TextCenter保存一些可以供外部访问的文字
###接口标准:
addText(id,text)   - 添加文字以id获取入口
addTexts(id[],text[])  - 添加一堆文字，以一堆id作为获取入口  
removeText(id)  - 删除指定id的text  
getText(id) - 获取指定id的text  
clearTexts() - 清楚全部的文字

##ThreadPool -线程池
###ThreadPool用于运行一些可并行运行的任务  
###接口标准:  
runTask(task) - 运行任务  
getTask()  -  获取最初的任务  
close() - 关闭线程池，并强制退出  
join() - 关闭线程池，等待所有线程结束  

##Vector - 矢量
###Vector矢量用以位置和空间计算，保存了三维矢量的三个坐标x,y,z并且保存一个a待使用的方法，通常用以三维操作，但现在仅仅保存为以后拓展3d使用
使用方法，创建矢量，可以使用相关的方法获取相关的数据
##接口标准:
x-set/get,y-set/get,z-set/get,a-set/get  
getLength() - 获取矢量的长度  
addVector(vector) - 返回此矢量加上指定矢量后的结果，矢量加  
delVector(vector) - 返回此矢量减去指定矢量厚的结果，矢量减  
dotNumber(number) - 矢量的数乘  
dotVector(vector) - 矢量点积  
crossVector(vector) - 矢量叉积  
normal() - 矢量单位化  
cosAngleWithVector(vector) - 获取与指定矢量的夹角的cos值  

##Transform - 变换
###Transform变换通常以位置和空间计算的工具，保存实体的位置，旋转，缩放，为主要内容
###接口标准:
scale(sx,sy,sz) - 对x方向，y方向，z反向施加指定的缩放大小  
scaleTo(sx,sy,sz) - 缩放到指定大小  
rotate(type,rotate) - 对指定的方向，旋转指定的角度  
rotateTo(type,rotate) - 对指定的方向，旋转到指定的角度  
translate(x,y,z) - 对位置移动指定的偏移  
translateTo(x,y,z) - 移动到指定的位置


##XMLEngineBoot - 引擎XML引导器
###XMLEngineBoot用以从xml文件中获取相关的内容创建App应用,另外提供相对的xml导入的一些常见的方法
###接口标准:
bootEngine(xmlpath) - 从指定xml文件路径引导app应用的创建和运行
instanceObj(classpath) - 从指定的classpath中实例化对象，class需要有默认无参数的构造函数,如果创建失败会抛出异常并返回null  
writeXMLFile(xmlpath,xmldoc) - 向指定的xmlpath的xml文件中中，写入指定保存在内存之中的xmldoc内容  
readXMLFileDocument(xmlpath) - 从指定的xml路径中读取xmldoc内容，如果没有则输出日志不存在  
getXMLWritableDocument(xmlpath) - 从指定的xml路径中获取可写入的xmldoc内容，若xml路径不存在则创建新的xmldoc，否则读入xml文件的doc  

##IXMLAppTypeBoot - 应用类型XML引导器
使用方法，实现方法，严格按照自定义的xml格式从xml的节点之中读取相关数据，创建并返回一个App实例，运行这个app以运行游戏应用  
###接口标准:
bootApp(xmlelement) - 从xml文件元素中读取数据，创建并返回一个app实例，需要实现的抽象  

##IXMLPartBoot - 应用分部部分XML引导器
使用方法，实现方法，严格按照自定义的xml格式从节点中读取相关数据，创建并返回一个partsupport实例，操作partsupport
###接口标准
bootPart(xmlelement) - 从xml文件元素中读取数据，创建并返回一个partsupport实例，需要实现的抽象  

###相关的XML文件规范
引擎的配置xml文件
```
<app>
	<afengine debug="" logicpath="" typeboot="" type="apptype">		
	</afengine>
	<apptype />
	<partsboot>
		<part name="" path=""/>
		...
	</partsboot>
	<partsconfig>
		<partname/>
		...
	</partsconfig>
</app>
```
### 文档规范[其中些许需要用到基于java或许其他面向对象语言不存在的的反射方法，待升级]
1.afengine标签节点必须存在，其中的debug属性若存在且值为true则打开Debug模式，
logicpath若存在，则指向自定义的AppLogic的classpath，typeboot必须存在且属性的值ixmlapptypeboot的classpath,type属性必须存在且属性值为需要配置的特定app的标签名
2.afengine标签节点中的type属性的值命名的一个标签节点必须在app根目录下，并且编写格式严格艳照typeboot的属性的值所指定的IXMLAppTypeBoot指定的格式
3.partsboot下为一列part，name用以设置partname，而path指向IXMLPartBoot的classpath，
4.partsconfig下为一列标签，标签名为partsboot中的一个part所设置的name，并且由所设定的IXMLPartBoot所指定的格式
