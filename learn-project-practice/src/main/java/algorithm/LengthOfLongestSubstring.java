package algorithm;

/**
 * Created by qianyang on 18-4-13.
 *  给定一个字符串，找出不含有重复字符的最长子串的长度。

    示例：

    给定 "abcabcbb" ，没有重复字符的最长子串是 "abc" ，那么长度就是3。

    给定 "bbbbb" ，最长的子串就是 "b" ，长度是1。

    给定 "pwwkew" ，最长子串是 "wke" ，长度是3。请注意答案必须是一个子串，"pwke" 是 子序列  而不是子串。
 */
public class LengthOfLongestSubstring {

    public static int lengthOfLongestSubstring(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int[] charIndex = new int[256];
        for (int i = 0; i < charIndex.length; i++) {
            charIndex[i] = -1;
        }
        int current;
        int len = 0;
        int pre = -1;
        for (int i = 0; i < str.length(); i++) {
            pre = maxNum(pre, charIndex[str.charAt(i)]);
            current = i - pre;
            len = maxNum(len, current);
            charIndex[str.charAt(i)] = i;
        }
        return len;
    }

    private static int maxNum(int first, int second) {
        return first > second ? first : second;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("avau"));
    }

}
