package net.stealthcat.test.nio;

public interface Handler {
	
	void doMessageRead(byte[] data, Client client);

	boolean isRead();
}
