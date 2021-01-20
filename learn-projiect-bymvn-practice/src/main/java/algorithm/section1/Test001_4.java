package algorithm.section1;

import java.io.*;
import java.util.Scanner;

public class Test001_4 {

	static String processFile(String filename) {
		String filepath = getPath(filename);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
			StringBuilder sb = new StringBuilder();
			String str = null;
			while((str = reader.readLine()) != null) {
				if(str.startsWith("#include")) {
					String filename0 = str.substring(str.lastIndexOf(" ") + 1);
					String content = processFile(filename0);
					sb.append(content + "\r\n");
				} else {
					sb.append(str + "\r\n");
				}
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	static String getPath(String filename) {
		return Test001_4.class.getClassLoader().getResource(filename).getPath();
	}
	
	public static void main(String[] args) {
		Scanner s = null;
		try {
			s = new Scanner(System.in);
			String filename = s.next();
			System.out.println(processFile(filename));	
		} finally {
			if(s != null) {
				s.close();
			}
		}
		
	}

}
