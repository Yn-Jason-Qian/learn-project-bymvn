package algorithm.section1;

public class Test001_5 {
	
	static int getByte1Count(int num) {
		if(num == 1 || num == 0) {
			return num;
		} else if(num > 1) {
			if(num % 2 == 0) {
				return getByte1Count(num / 2);
			} else {
				return getByte1Count(num / 2) + 1;
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println(getByte1Count(16));
	}

}
