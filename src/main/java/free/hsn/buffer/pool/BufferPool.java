package free.hsn.buffer.pool;

import java.io.Closeable;
import java.nio.ByteBuffer;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import free.hsn.buffer.ChannelBuffer;


public class BufferPool implements Closeable {
	
	private GenericObjectPool<ChannelBuffer> objectPool;

	public BufferPool(int corePoolSize, int maxPoolSize, int keepAliveTime, int bufferSize) {
		super();
		
		objectPool = buildPool(corePoolSize, maxPoolSize, keepAliveTime, bufferSize);
	}
	
	public ChannelBuffer borrowObject() throws Exception {
		return objectPool.borrowObject();
	}
	
	public void returnObject(ChannelBuffer byteBuffer) {
		objectPool.returnObject(byteBuffer);
	}
	
	public void close() {
		objectPool.close();
	}
	
	private GenericObjectPool<ChannelBuffer> buildPool(int corePoolSize, int maxPoolSize, int keepAliveTime, int bufferSize) {
		GenericObjectPoolConfig poolConfig = buildPoolConfig(corePoolSize, maxPoolSize, keepAliveTime);
		
		return new GenericObjectPool<ChannelBuffer>(new BufferFactory(bufferSize), poolConfig);
	}
	
	private GenericObjectPoolConfig buildPoolConfig(int corePoolSize, int maxPoolSize, int keepAliveTime) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(corePoolSize);
		config.setMaxTotal(maxPoolSize);
		config.setMaxWaitMillis(keepAliveTime);
		
		return config;
	}
	
	public void prestartCorePool() throws Exception {
		for (int i = 0; i < objectPool.getMaxIdle(); i++) {
			objectPool.addObject();
		}
	}

	private class BufferFactory implements PooledObjectFactory<ChannelBuffer> {
	
		private int bufferSize;
		
		public BufferFactory(int bufferSize) {
			super();
			this.bufferSize = bufferSize;
		}
	
		@Override
		public PooledObject<ChannelBuffer> makeObject() throws Exception {
			return new DefaultPooledObject<ChannelBuffer>(new ChannelBuffer(ByteBuffer.allocateDirect(bufferSize)));
		}
	
		@Override
		public void destroyObject(PooledObject<ChannelBuffer> pooledObject) throws Exception {
			// Need do nothing.
		}
	
		@Override
		public boolean validateObject(PooledObject<ChannelBuffer> pooledObject) {
			return true;
		}
	
		@Override
		public void activateObject(PooledObject<ChannelBuffer> pooledObject) throws Exception {
			// See passivateObject.
		}
	
		@Override
		public void passivateObject(PooledObject<ChannelBuffer> pooledObject) throws Exception {
			pooledObject.getObject().byteBuffer().clear();
		}
	}
}
