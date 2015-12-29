

# Hsn
> A simple nio server framework.


####设计概要
	1. IO模型采用JDK NIO

	2. 具备多线程单机扩容能力
			通过配置可尽可能的提高资源使用率

	3. 基于多Selector Reactor模式
			Accept  Selector 1
			Channel Selector *	
			
	4. 基于池化本地缓冲区
			所有缓冲区均为高效的本地缓冲区,并实现基于内存池的缓冲区重用机制
			
	5. 基于无锁化的串行设计理念
			尽可能的避免锁竞争带来的性能损耗，通过串行化设计，即消息的处理尽可能在同一个线程内完成，期间不进行线程切换，避免了多线程竞争和同步锁。


####相较于Netty Mina
	1. 代码更简洁, 仅40KB左右.
	
	2. 更大的自定义扩展空间.

	3. 更快捷联系作者探讨相关问题(可忽略).


####Demo
	参见: free.hsn.HelloHsn