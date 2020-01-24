## std-part.scene-1  
游戏引擎通用部分-场景部分有关标准  

==============================  
###相关类
ActorComponent,Actor,Scene,AbSceneLoader,SceneCamera,  
IComponentFactory,SceneCenter,SceneFileHelp,ScenePart,XMLScenePartBoot  

==============================
#std-part.scene 标准相关类的使用方法和需要接口标准  
##ActorComponent - 实体组件类  
###ActorComponent代表一个实体的模型组件，  
使用方法，ActorComponent包含组件工厂的添加，获取，删除，同时包含获取一个字符串表达值的真正字符串值的静态方法，  
一个组件包含一系列可自定义的string-string键值对  
获取一个实体组件，可以从这个组件获取、设置这个组件所属实体，可以唤醒、沉睡这个实体组件,一个实体组件需要处理来自实体分发的消息,    
###接口标准:
静态方法:  
addFactory(compname,factory),  	- 添加组件工厂  
hasFactory(compname),  			- 判断是否存在组件工厂  
removeFactory(compname),  		- 移除指定组件名称的组件工厂  
getFactory(compname),			- 获取指定的组件工厂  
getRealValue(value,actorvalues),  - 获取字符串表达值的真正值  
对象方法:  
attributes	 					- 获取组件的自定义属性  
getComponentName(),  			- 获取组件的名称  
isActive(),  					- 判断是否组件活动  
awake(),  						- 唤醒组件  
asleep(),  						- 沉睡组件  
update(time),					- 更新组件  
handle(msg),					- 处理来自实体的消息分发  
特殊方法:  
toWake(),						- 活动激活的执行函数  
toSleep(), 						- 活动沉睡的执行函数  
  
  
##Actor - 实体类  
###Actor代表一个实体类，一个实体由一个父实体引用，和一个实体孩子节点的列表.组成基础部分,包含一个变换Transform,实体id，和组件映射  
使用方法,Actor包含静态实体，包含对静态实体的添加，获取，删除，更新的静态方法,  
使用方法，如果此实体放入了场景，可以调用removeThisActor从场景中删除此实体  
###接口标准:
静态方法:  
staticActorMap - 获取静态实体的名称-实体映射  
findStaticActor(name),  - 获取指定静态实体的名称  
addStaticActor(actor),  - 添加静态实体实例  
removeStaticActor(name),  - 移除指定名称的静态实体  
updateStaticActor(time),  - 更新全部的静态实体  
对象方法:  
set/getTransform - 设置/获取实体的变换  
isStatic() - 判断实体是否为静态实体  
set/getName() - 设置/获取实体的名称  
set/getParent()  - 设置/获取实体的父实体  
removeThisActor() - 从场景中移除此实体  
addChild(actor) - 添加孩子实体节点  
findChild(id) - 获取指定id的孩子实体  
getChildren() - 获取此实体的直属孩子实体  
removeChild(id) - 删除指定id的孩子实体  
getComponentsMap() - 获取组件名称-组件的映射  
addComponent(comp) - 添加组件  
removeComponent(name) - 移除指定组件名称的实体组件  
getComponent(name) - 获取指定名称的实体组件  
awakeAllComponents() - 唤醒所属此实体的全部组件  
sleepAllComponents() - 沉睡所属此实体的全部组件  
getModPath() - 获取实体组件的模型存储路径  
setModPath() - 设置实体组件的模型存储路径  
getAbsoluteX/Y/Z() - 获取此实体的绝对位置  
handle(msg) - 实体处理消息，交由组件应答    

##Scene - 场景类
###场景用来存放实体，并且可以管理实体的生命周期，
###接口标准:  
set/getLoader() - 设置/获取场景的导入类  
getCamera() - 获取场景的场景摄像机  
getNodeActorMap() - 获取场景根下的实体映射  
findActorByName(name) - 从场景和静态实体中获取指定名称的实体  
findActorById(id) - 从场景和静态实体中获取指定id的实体  
addNodeActor(name,actor) - 添加场景根下的实体  
hasNodeActor(name) - 判断是否存在场景根下指定名称的实体  
removeNodeActor(name) - 移除场景根下指定名称的实体  
set/getName() - 设置/获取名称  
awakeAllActros() - 唤醒全部的实体  
updateScene(time) - 更新场景  

##AbSceneLoader - 场景导入类  
###场景导入类用以导入场景，暂停、继续场景的回调方法的管理  
###接口标准:
get/setThisScene(scene) - 获取/设置导入类管理的场景  
load(), - 导入场景，需要实现  
shutdown(), -关闭场景，需要实现  
pause(), -暂停场景，需要实现  
resume(), - 继续场景，需要实现  

##SceneCamera - 场景摄像机  
###接口标准:
set/getPos - 设置/获取摄像机位置  
set/getLook - 设置/获取摄像机摄像的目标点  
set/getWidthoffSize() - 设置/获取摄像机在窗口上的宽度偏移  
set/getHeightoffSize() - 设置/获取摄像机在窗口上的高度偏移  

##IComponentFactory - 组件工厂  
###组件工厂管理相关的组件的xml导入方法
###接口标准:
createComponent(xmlelement) - 从xml标签中创建组件，需要实现  

##SceneCenter - 场景中心  
###场景中心管理一系列场景
使用方法，调用pushScene(scene)将需要更新的场景推入场景栈，popScene将场景栈出栈，popToRoot将场景栈全部弹出，并且将当前场景设为根场景rootScene,
###接口标准:
pushScene(scene) - 将场景插入场景栈中，如果存在相同的场景名称，则失败    
popScene() - 场景栈弹出第一个  
popToRoot() - 场景栈全部弹出，并设置当前场景为rootScene
changeToScene(scene) - 将当前的场景弹出关闭，替换成传入的场景，并完成导入  
prepareScene(scene) - 准备传入的场景  
pushPreparedScene(name) - 将指定准备好的场景推入场景栈  
findPreparedScene(name) - 从准备好的场景中获取指定名称的场景  
getPreparedSceneMap() - 获取全部的已准备好的名称-场景映射  
getSceneStack() - 获取场景栈  
getRootScene()  - 获取根场景    
getRunningScene() - 获取当前场景  

##SceneFileHelp - 场景文件帮助
###接口标准:全部是静态方法  
loadSceneFromXML(xmlelement) - 从指定的xml标签中导入场景  
outputScenetoXML(scene,xmlelement) - 将场景输出到指定的xml标签之中  
loadStaticActorFromXML(xmlelement) - 从指定的xml标签中导入静态实体  
outputStaticActorToXML(xmlelement) - 将静态实体输出到指定的xml标签中  
loadActorFromXML(xmlelement) - 从指定的xml标签中导入实体  
outputActorToXML(actor,xmlelement) - 将实体输出到指定的xml标签之中  

##ScenePart
场景部分的支持  

##XMLScenePartBoot
场景部分支持的配置导入  

==相关xml文件配置
1.场景xml的配置
```
<scene>
	<actordata>
		<actorname1/>
		<actorname2/>
		<actorname3/>
	</actordata>
	<actormap>
		<actorname1>
			<actorname2/>
		</actorname1>
		<actorname3/>
	</actormap>
</scene>
```
注意:scene,actordata,actormap必须正确拼写，actordata下的标签为实体的配置标签，具体以实体的xml配置为准，actormap为配置actor的层次映射，如下：  
```
<actor1>
	<actor2/>
</actor1>
```
表示actor1为actor2的父实体，actor2为actor1的孩子节点实体  

2.实体xml的配置  
```
<actorname mod=",">
	<transform pos="" rotate="" scale=""/>
	<datas>
		<key value=""/>
		...
	</datas>
</actorname>
```
注意:actorname为可自定义的实体名称，实体创建后以此字符串为实体名称，  
datas里面存储实体的可存储键值映射字符串属性  
mod为模型组件的存储位置，以模型组件存储文件#模型名称为基准，可以定义多个模型组件，以逗号分开，实体数据必须定义组件所需的属性键值
```
<actorname mod="1.xml#mod1,1.xml#mod3,2.xml#mod2">
</actorname>

```

3.模型组件xml的配置  
```
<modlibs>
	<mod1 />
	<mod2 />
</modlibs>
```
注意:  
modlibs需要拼写正确,mod1,mod2等等都是自定义的模型名称，模型包含一系列组件的声明，各个组件的xml配置格式请参照各个组件工厂规定的规则，字符串可以使用#value，表示从实体之中提取属性名称为value的数值，否则就使用定义的字符串  

4.静态实体的xml配置  
```
<staticactor-list>
	<actor1/>
	<actor2/>
	<actor3/>
	...
</staticactor-list>
```
note:  
staticactor-list必须拼写正确,actor1,actor2等等标签遵从实体的xml配置  

5.scene导入类的xml配置
```
<ScenePart main="">
	<ComponentFactoryList>
		<Component name="" class="" />
		<Component name="" class="" />
		...
	</ComponentFactoryList>
	<SceneList>
		<Scene id="" name="" loader="" class="" output="true"/>
		<Scene id="" name="" loader="" path="" />
		...
	</SceneList>
	<StaticActors path=""/>
</ScenePart>
```
note:  
ScenePart,ComponentFactoryList,Component,SceneList,Scene,StaticActors必须拼写正确,ComponentFactoryList为所需的实体组件工厂的类,