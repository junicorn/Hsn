
# Hsn

[![Build Status](https://img.shields.io/travis/junicorn/Hsn.svg?style=flat-square)](https://travis-ci.org/junicorn/Hsn)

> A simple nio server framework.

#### 设计概要

* [x] IO模型采用JDK NIO
* [x] 具备多线程单机扩容能力
	通过配置可尽可能的提高资源使用率.
* [x]基于多Selector Reactor模式
	Accept  Selector 1
	Channel Selector *	
* [x] 基于池化本地缓冲区
	所有缓冲区均为高效的本地缓冲区,并实现基于内存池的缓冲区重用机制.
* [x] 基于无锁化的串行设计理念
	尽可能的避免锁竞争带来的性能损耗.
	通过串行化设计,同一连接的业务处理始终仅在同一个线程内完成,避免了多线程竞争和同步锁.

#### 相较于Netty Mina

1. 代码更简洁, 仅40KB左右.
2. 更大的自定义扩展空间.
3. 对学习JDK NIO更具有学习意义.
4. 更快捷联系作者探讨相关问题(可忽略).

#### Demo

参见: `free.hsn.HelloHsn`
	
**Example: Echo server**

```java
public class HelloHsn {
	
	public static void main(String[] args) throws Exception {
		// 监听端口10080, 设置Backlog值为200
		HsnServer server = new HsnServer(10080, 200);
		
		// 设置用于处理连接相关操作的Selector数量(默认同CPU核心数量)
		server.setChannelSelectorCount(2);
		
		// 设置用于处理连接相关操作的线程数量(默认为ChannelSelectorCount的两倍)
		server.setChannelThreadCount(4);

		// 设置缓冲区对象池大小(默认1024)
		server.setBufferPoolSize(1024);
		// 设置缓冲区默认容量(默认1024)
		server.setBufferSize(1024);

		// 设置Channel适配器
		server.setChannelAdaptor(EchoChannelAdaptor.class);
		
		// 启动Hsn
		server.start();
	}
}
```
	
#### Jmeter测试数据 

+ 测试环境: Dell E5400、Win7、I5-4210U
+ 软件环境: 短连接、并发200、循环50次

![](http://i.imgur.com/8BrYaGk.png)

