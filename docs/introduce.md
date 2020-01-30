## afengine -[v0.1-dev+alpha]

## 版本号:[v0.1-dev+alpha]
主版本号从0开始，以x.x-x+x来命名。
第一个x表示为主版本号最少一年进行一次主版本升级；第二个x表示为主版本的修订升级版本号，每个发布的主版本，最少三个月进行一次修订升级；第三个x表示发布的是开发版[dev]，还是生产版[env],开发板包括全部的源码、测试程序、示例程序、开发文档和javadoc的api文档，而生产版仅仅只有运行时需要的jar库文件和所需的一些引擎依赖的三方jar库；第四个为调试的版本号，是开发调试[alpha]，使用调试版[beta]，和稳定发布版[release]。

## afengine简介:artwork
afengine是Albert Flex在albert factory(af) 的 af artstudio 进行开发的属于af artwork作品集的一款简易的游戏引擎，或者说游戏框架。这个版本的开发时间虽然只有二十多天的时间，但是其所继承的遗产是albert flex花了将近两年的时间才摸索而出，游戏引擎采用传统游戏引擎的运行构架和现代引擎的拓展模式合并进行的开发方式。

## 开发语言:Java语言
此引擎采用普通的Java语言编程方式，然而并不引入复杂的基于Java特殊的方法，如注解和高级的反射，有助于其他语言对于afengine的标准文档的移植。

## 协议方式:Apache License 2.0
引擎采用apache license 2.0的开源协议，任何人可以轻松地获取源码进行再生产，详细内容请阅读项目根目录下的License协议书。

## 项目源代码构架:根目录src/afengine
### /core  					- 存在运行游戏最重要的核心部分
### /part  					- 内置的普遍游戏分部部分的实现
### /component				- 内置的普遍游戏实体组件的实现
### 项目依赖于dom4j-1.6.1	- 解析xml文件

## docs文档目录介绍:根目录docs
### std 					- afengine的引擎设计标准相关文档
### manual 					- afengine的使用手册
### javadocs                - afengine的Java 接口api的文档

## 开发者:夏文纯一(Albert Flex)
是一个崇尚新颖的自由开发者，属于albert factory下的af artstudio的工作者(实际上这两个组织都只有这个家伙一个人而已)。

## 联系方式:
夏文纯一(Albert Flex):
tencent/qq - 2662842460  
email	   - 2662842460@qq.com  