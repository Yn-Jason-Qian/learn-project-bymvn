package net.stealthcat.test.nio;

import java.io.IOException;

public interface Client {
	
	void doRead();

	byte[] read() throws IOException;
	
	void write(byte[] data) throws IOException;
	
	void close() throws IOException;
}
