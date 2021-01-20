package algorithm.section4;

import java.io.File;

public class Test004_10 {

	public static void main(String[] args) {
		listFiles(new File("/home/qianyang/文档/接口文档"), 0);
	}
	
	static void listFiles(File file, int depth) {
		System.out.printf("%1$"+ (depth+1) +"s", "");
		if(file.isDirectory()) {
			System.out.println("dir : " + file.getName());
			File[] files = file.listFiles();
			for(File child : files) {
				listFiles(child, depth + 1);
			}
		} else {
			System.out.println(file.getName() + ":" + file.length());
		}
	} 

}
