package net.stealthcat.test.nio;


import net.stealthcat.util.LogUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;


public class NioServer implements Server{
	public static final int DEFAULT_PORT = 8080;
	private int port;
	private ServerSocketChannel serverChannel;
	private NioExecutor acceptExecutor;
	private NioExecutorGroup processExecutorGroup;
	private Handler serverHandler;
//	private List<Client> clients = Lists.newArrayList();
	
	public NioServer() {
		this(DEFAULT_PORT);
	}
	
	public NioServer(int port) {
		this.port = port;
		this.acceptExecutor = new NioExecutor();
		this.processExecutorGroup = new NioExecutorGroup();
		init();
	}
	
	public NioServer(int port, int executorCount) {
		this.port = port;
		this.acceptExecutor = new NioExecutor();
		this.processExecutorGroup = new NioExecutorGroup(executorCount);
		init();
	}
	
	private void init() {
		try {
			if(port == 0) {
				throw new IllegalStateException("Port has't set!");
			}
			serverChannel = ServerSocketChannel.open();
			serverChannel.bind(new InetSocketAddress(port));
			serverChannel.configureBlocking(false);
			acceptExecutor.register(serverChannel, SelectionKey.OP_ACCEPT, this);
			LogUtil.info("Init NioServer on port[{}] success!", port);
		} catch (IOException e) {
			LogUtil.error("Init NioServer on port[{}] error!", port, e);
		}
	}
	
	public void doAccept() {
		try {
			NioClient client = new NioClient(serverChannel.accept(), processExecutorGroup.next());
			LogUtil.info("Accept connection from {}.", client.getRemoteAddress());
			client.setHandler(serverHandler);
		} catch (IOException e) {
			LogUtil.error("Accept socketChannel error!", e);
		}
	}

	public void setServerHandler(Handler serverHandler) {
		this.serverHandler = serverHandler;
	}

	@Override
	public void shutdown() {
		acceptExecutor.shutdown();
		processExecutorGroup.shutdown();
		try {
			serverChannel.close();
		} catch (IOException e) {
			LogUtil.error("Shutdown NioServer error!", e);
		}
	}
}
