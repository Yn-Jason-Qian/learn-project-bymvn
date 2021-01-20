package algorithm;

/**
 * 
 * 最长公共子序列 
 *
 */
public class LongestCommonSequence {

	/**
	 * 			  f(i-1)(j-1) + 1, a(i) == b(j)
	 * f(i)(j) = {
	 * 			  max{f(i-1)(j), f(i)(j-1)}, a(i) != b(j)
	 * @param a
	 * @param b
	 * @return
	 */
	public static int longestSequenceLength(String a, String b) {
		int[][]  records = new int[a.length() + 1][b.length() + 1];
		for(int i = 0; i < a.length(); i++) {
			for(int j = 0; j < b.length(); j++) {
				
			}
		}
		return 0;
	}
}
