package net.stealthcat.test;

import java.util.ArrayList;
import java.util.Collection;

public class Align2Grain {

	public static void main(String[] args) {
		short i = 117;
		int grain = 8;
		align2Grain(i, grain);
		
		convertTest(new ArrayList<>());//在调用方法之前，当前的栈帧不会将ArrayList的实例转换为Collection类型
	}
	
	public static int align2Grain(int i, int grain) {
		return (i + grain - 1) & ~(grain - 1);
	}
	
	public static void convertTest(Collection<?> coll) {
		
	}

}
