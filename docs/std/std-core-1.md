## std-core-1
游戏引擎核心部分和游戏逻辑 有关的标准  

======================
### 相关类
app,appstate,applogic,partsupport,partmanager,applogic

=======================
# core标准相关抽象类的使用方法和需要接口的标准
##App - 应用抽象类  
###App抽象类是游戏应用的本体  
使用方法如下：
实例化一个app抽象类的实现对象
调用run方法，并传入一个applogic抽象类的实现对象，或者不传（将只运行app内置的流程），app的run方法会自动调用应用周期的方法，并且将自己的引用传给appstate，通知创建一个appstate应用状态，并使用appstate里的run属性是否为true判断更新是否继续,
还需要提供这个应用的类型和应用的名称，app管理一个partsmanager，里面可以存放partsupport用以逻辑和相关的使用
###接口标准:
普通接口:
appType-get/set,appName-get/set,
partsmanager-get,
特殊的接口:需要实现
init(),update(time),shutdown(),

##AppState - 应用状态类
###AppState是用来管理运行中的唯一一个App实例本体的状态类，保存很多的可以配置的string-string的键值映射存储
使用方法：
在游戏运行中，想要获取游戏的实例，调用静态方法getRunningApp，而想要在创建一个app实例之后（在run方法调用之前）直接存入appstate中保存的，调用createState(app)，如果一个app已经传入了，appstate不会在接受第二个app的实例，以保证app的唯一性和保持不变
###接口标准:
普通接口:
value-set,get,has
特殊的接口:用于管理App实例的管理和获取
createState(app),getRunningApp()

##PartSupport - 应用分部部分支持
###PartSupport是用来支持插入式的外部逻辑
使用方法：
按照你想要的逻辑实现这个抽象类，实例化一个partsupport，并将她放入app保存的partmanager，app则会自动管理这个partsupport的生命周期，并且在更新的时候调用抽象的update方法，partsupport应当实现一个只调用一次的init方法，避免在各处地方多次调用引发的问题；另外一个partsupport需要提供一个属性的保存以供后续使用，还需要提供一个name的属性表示partsupport的名称
###接口标准:
普通接口:
name-get，obj-get
特殊接口：
initPart() - 只调用一次的方法，调用init方法初始化这个part  
init() - 初始化partsupport，需要重写  
update() - 更新这个support，需要重写  
shutdown() - 关闭这个support，需要重写  

##AppPartsManager - 应用分部支持管理器
###用来管理partsupport
使用方法：
实例化一个管理器，以优先序列插入partsupport的实例，并在必要的时候调用管理器的update方法和shutdown方法，优先级数越大的，越先进行处理
###接口标准:
特殊接口:管理partsupport  
hasPartSupport(name) - 是否存在指定名称的partsupport  
getPartSupport(name) - 返回指定名称的partsupport，如果没有，则返回Null    
getPartPriority(name) - 返回指定名称的partsupport的优先级数，若没有这个partsupport，则返回 -1  
addPartPriority(priority,support) - 添加partsupport，并且以指定优先级存入管理器
removePartSupport(name) - 移除指定名称的partsupport,如果没有，则什么也不做  
initParts() - 初始化全部的存入的partsupport，按优先吮吸  
updateParts(time) -更新全部的存入的partsupport，按优先顺序  
shutdownParts() -关闭全部的存入的partsupport，按优先倒序  

##AppLogic - 应用逻辑
###用以插入式的形式来提供外部的逻辑
使用方法：
配合app，调用app的run方法，传入applogic的实例，用app管理applogic的生命周期
###接口标准:
特殊接口：用以生命周期回调
init() - 初始化逻辑，需重写  
update(time) - 更新逻辑，需重写  
shutdown() - 关闭逻辑，需重写  

==============================
## 额外部分，app的内部实现有serviceapp和windowapp，windowapp的有关接口涉及std-core.window-1标准