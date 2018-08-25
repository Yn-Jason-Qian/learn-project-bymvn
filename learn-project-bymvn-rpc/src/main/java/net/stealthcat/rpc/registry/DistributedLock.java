package net.stealthcat.rpc.registry;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class DistributedLock implements Lock,Watcher{
	private static final String CONNECTION_STRING = "localhost:2181";
	private static final String ROOT = "/lock";
	private static Logger logger = LoggerFactory.getLogger(DistributedLock.class); 
	
	private ZooKeeper zk;
	private String lockPath;
	private ThreadLocal<String> currentNodeName = new ThreadLocal<String>();
	private ThreadLocal<String> waitNodeName = new ThreadLocal<String>();
	private ConcurrentMap<String, CountDownLatch> waiters = Maps.newConcurrentMap();
	
	public DistributedLock(String lockName) throws IOException, KeeperException, InterruptedException {
		this.lockPath = ROOT + "/" + lockName;
		init();
	}
	
	private void init() throws IOException, KeeperException, InterruptedException {
		zk = new ZooKeeper(CONNECTION_STRING, 3000, this);
		if(zk.exists(ROOT, false) == null) {
			zk.create(ROOT, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getPath() != null) {
			String nodeName = event.getPath().substring(event.getPath().lastIndexOf("/") + 1);
			CountDownLatch countDownLatch = waiters.get(nodeName);
			if(countDownLatch != null) {
				countDownLatch.countDown();
			}
		} else if(event.getState() == KeeperState.Expired){
			try {
				init();
			} catch (IOException | KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void lock() {
		try {
			if(tryLock()) {
				return;
			} else {
				waitForLock(30000, TimeUnit.MILLISECONDS);
			}
		} catch (Exception e) {
			logger.warn("Lock error.", e);
		}
	}
	
	private void waitForLock(long timeout, TimeUnit unit) {
		try {
			CountDownLatch latch = new CountDownLatch(1);
			waiters.put(waitNodeName.get(), latch);
			latch.await(timeout, unit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean tryLock() {
		try {
			String nodePath = zk.create(lockPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			String currentNode = nodePath.substring(nodePath.lastIndexOf("/") + 1);
			this.currentNodeName.set(currentNode);
			
			List<String> nodes = zk.getChildren(ROOT, false);
			Collections.sort(nodes);
			if(nodes.get(0).equals(currentNode)) {
				return true;
			} else {
				int before = Collections.binarySearch(nodes, currentNode) - 1;
				if(before >= 0) {
					String beforeNodeName = nodes.get(before);
					this.waitNodeName.set(beforeNodeName);
					return zk.exists(ROOT + "/" + beforeNodeName, true) == null;
				} else {
					return true;
				}
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		if(tryLock()) {
			return true;
		} else {
			waitForLock(time, unit);
		}
		return true;
	}

	@Override
	public void unlock() {
		String node = currentNodeName.get();
		if(node != null) {
			try {
				zk.delete(ROOT + "/" + node, 0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (KeeperException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Condition newCondition() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		throw new UnsupportedOperationException();
	}
}
