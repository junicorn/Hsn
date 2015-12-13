package free.hsn.buffer;

import java.nio.ByteBuffer;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


public class BufferPool {
	
	private GenericObjectPool<ByteBuffer> objectPool;

	public BufferPool(int corePoolSize, int maxPoolSize, int keepAliveTime, int bufferSize) {
		super();
		
		objectPool = buildPool(corePoolSize, maxPoolSize, keepAliveTime, bufferSize);
	}
	
	public ByteBuffer borrowObject() throws Exception {
		return objectPool.borrowObject();
	}
	
	public void returnObject(ByteBuffer byteBuffer) {
		objectPool.returnObject(byteBuffer);
	}
	
	private GenericObjectPool<ByteBuffer> buildPool(int corePoolSize, int maxPoolSize, int keepAliveTime, int bufferSize) {
		GenericObjectPoolConfig poolConfig = buildPoolConfig(corePoolSize, maxPoolSize, keepAliveTime);
		
		return new GenericObjectPool<ByteBuffer>(new BufferFactory(bufferSize), poolConfig);
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

	private class BufferFactory implements PooledObjectFactory<ByteBuffer> {
	
		private int bufferSize;
		
		public BufferFactory(int bufferSize) {
			super();
			this.bufferSize = bufferSize;
		}
	
		@Override
		public PooledObject<ByteBuffer> makeObject() throws Exception {
			return new DefaultPooledObject<ByteBuffer>(ByteBuffer.allocateDirect(bufferSize));
		}
	
		@Override
		public void destroyObject(PooledObject<ByteBuffer> pooledObject) throws Exception {
			// Do nothing
		}
	
		@Override
		public boolean validateObject(PooledObject<ByteBuffer> pooledObject) {
			return true;
		}
	
		@Override
		public void activateObject(PooledObject<ByteBuffer> pooledObject) throws Exception {
			// Do nothing
		}
	
		@Override
		public void passivateObject(PooledObject<ByteBuffer> pooledObject) throws Exception {
			pooledObject.getObject().clear();
		}
	}
}
