## std-part.message-1
游戏引擎通用部分-消息部分 有关的标准  

=======================
###相关类 
Message,MessageCenter,MessageRoute,MessagePart,XMLMessagePartBoot  
IMessageHandler,BigMessageHandler,MessageHandlerRoute  

=======================
# part.message 通用消息部分标准相关抽象类的使用方法和需要接口的标准
##Message - 消息类
###Message代表一个可以传递的可以阅读的消息，内涵消息路由类型，消息类型，消息内容，消息字符串提示，消息附加内容对象，创建时间戳，消息发送的延迟，发送方类型，发送方主题对象，接收方类型，发送方Id，接收方Id
###接口标准：
获取各个属性(各个属性皆为只读属性)

##MessageRoute - 消息路由器抽象
###MessageRoute代表一个可以接受指定路由类型的消息，并且将这个消息发送到自己制定的处理流程里面
###使用方法：
创建实例，获取消息，并且调用routeMessage发送给自己制定的消息
###接口标准  
getRouteType() - 获取此路由器的路由类型  
routeMessage(msg) - 路由传入的消息  

##MessageCenter - 消息中心
###消息中心使用单例模式创建,管理分布部分的消息路由器部分
使用方法，添加消息路由器，根据消息类型获取消息路由器，删除指定消息类型的消息路由器，更新全部的额消息路由器，发送消息，设置消息每一帧发送的欣澳西数量
###接口标准  
getInstance() - 单例模式，获取消息中心实例  
addRoute(route) - 添加消息路由器  
getRoute(type) - 获取指定消息类型的消息路由器  
removeRoute(type) - 获取指定消息类型的消息路由器  
sendMessage(msg) - 添加需要发送的消息实例  
updateSendMessage(time) - 更新需要发送的消息  
getRouteMap() - 获取消息路由器映射  
get/set-sendmsgonce - 设置每一帧发送消息的最高数量  


##MessagePart - 消息分部部分  
###MessagePart代表了一个消息分部部分的支持，添加到应用的分部部分管理器后，可以自动处理其生命周期  

##内置消息路由器
IMessageHandler  
一个可以处理指定消息内容的处理器  
BigMessageHandler  
一个可以处理指定消息类型-消息内容的处理器  
MessageHandlerRoute  
消息路由器  
##!内置消息路由器

##XMLMessagePartBoot - 消息分部部分的xml配置导入类
XML规范消息分部部分的配置如下:   
分部引导的配置
```
<partsboot>
	...
	<part name="MessagePart" path="afengine.part.message.XMLMessagePartBoot"/>
	...
</partsboot>
```
note:
引导分部的名称必须与下面的配置里面的标签名称一样  
path相对的类路径必须为以上指定  
分部配置
```
<partsconfig>
	...
	<MessagePart>
		<Route path=""/>
		<Route path=""/>
	</MessagePart>
	...
</partsconfig>
```
note:
Route标签必须写正确，path为messageroute的类路径，必须写正确  
提示，内置的通用route路径为:afengine.part.message.MessageHandlerRoute  
