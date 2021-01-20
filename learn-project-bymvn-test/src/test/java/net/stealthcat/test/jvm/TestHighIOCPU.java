package net.stealthcat.test.jvm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestHighIOCPU {
	
	private static int threadCount = Runtime.getRuntime().availableProcessors() * 2;
	private static String filename = "/tmp/test.txt";
	private static File file;
	public static void main(String[] args) {
		test();
	}

	public static void test() {
		file = new File(filename);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0;i<threadCount;i++) {
			new Thread(new Task()).start();
		}
	}
	
	static class Task implements Runnable {

		@Override
		public void run() {
			while(true) {
				StringBuilder builder = new StringBuilder();
				builder.append("================start===============");
				for(int i=0;i<10000;i++) {
					builder.append(Thread.currentThread().getName() + "\r\n");
				}
				builder.append("================end===============");
		
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter(file, true));
					writer.write(builder.toString());
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if(writer != null)
							writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
