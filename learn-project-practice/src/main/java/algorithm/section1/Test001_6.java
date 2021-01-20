package algorithm.section1;

public class Test001_6 {

	public static void permute(String str) {
		permute(str.toCharArray(), 0, str.length() - 1);
	}
	
	private static void permute(char[] str, int low, int high) {
		if(low == high) {
			for(char c : str) {
				System.out.print(c);
			}
			System.out.println();
		} else {
			for(int i=low; i<=high; i++) {
				char tmp = str[low];
				str[low] = str[i];
				str[i] = tmp;
				
				permute(str, low + 1, high);
				
				tmp = str[low];
				str[low] = str[i];
				str[i] = tmp;
			}
		}
	}
	
	public static void permutation(char[] buf, int start, int end) {  
        if (start == end) {// 当只要求对数组中一个字母进行全排列时，只要就按该数组输出即可  
            for (int i = 0; i <= end; i++) {  
                System.out.print(buf[i]);  
            }  
            System.out.println();  
        } else {// 多个字母全排列  
            for (int i = start; i <= end; i++) {  
                char temp = buf[start];// 交换数组第一个元素与后续的元素  
                buf[start] = buf[i];  
                buf[i] = temp;  
  
                permutation(buf, start + 1, end);// 后续元素递归全排列  
  
                temp = buf[start];// 将交换后的数组还原  
                buf[start] = buf[i];  
                buf[i] = temp;  
            }  
        }  
    }  
	
	public static void main(String[] args) {
		permute("abcd");
	}

}
