package net.stealthcat.test.nio;


import net.stealthcat.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class NioClient implements Client{
	private SocketChannel socketChannel;
	private Handler handler;
	private Executor executor;

	public NioClient(String host, int port) {
		try {
			socketChannel  = SocketChannel.open();
		} catch (IOException e) {
			throw new RuntimeException("Failed to open a socket channel.", e);
		}
		try {
			socketChannel.connect(new InetSocketAddress(host, port));
			LogUtil.info("Connect to {}:{}.", host, port);
			executor = new NioExecutor();
			init();
		} catch (IOException e) {
			throw new RuntimeException(String.format("Failed to connect to %s:%s.", host, port), e);
		}
	}
	
	public NioClient(SocketChannel socketChannel, Executor executor) {
		this.socketChannel = socketChannel;
        this.executor = executor;
		init();
	}
	
	private void init() {
		if(socketChannel == null) {
			throw new IllegalStateException("SocketChannel is null or not connected.");
		}
		if (executor == null) {
            throw new IllegalArgumentException("NioExecutor is null or has been shutdown.");
        }
		try {
			socketChannel.configureBlocking(false);
            executor.register(socketChannel, SelectionKey.OP_READ, NioClient.this);
		} catch (IOException e) {
			throw new RuntimeException("Set socket channel unBlocking error.", e);
		}
	}

	@Override
	public byte[] read() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		while(true) {
			int count = socketChannel.read(buffer);
			if(count > 0) {
				outputStream.write(buffer.array());
				buffer.clear();
			} else if (count == 0){
				break;
			} else {
				close();
				LogUtil.info("NioClient read noting, and count equals -1.Close client.");
				throw new IllegalStateException("NioClient has been closed!");
			}
		}
		return outputStream.toByteArray();
	}

	@Override
	public void write(byte[] data) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		while(buffer.hasRemaining()) {
			if (socketChannel.write(buffer) == 0) {
				break;
			}
		}
	}

	public void close() throws IOException {
		socketChannel.close();
	}
	
	@Override
	public void doRead() {
		try {
			handler.doMessageRead(read(), this);
		} catch (IOException e) {
			LogUtil.error("Do read error!", e);
		}
	}

	public String getRemoteAddress() {
		try {
			return socketChannel.getRemoteAddress().toString();
		} catch (IOException e) {
			LogUtil.error("GetRemoteHost error.", e);
		}
		return null;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
}
