package algorithm.interview;


public class Test3_ReplaceBlank {
    /**
     * 实现一个函数，把字符串中的每个空格替换成“%20”，
     * 例如输入“We are happy.”，替换成“We%20are%20happy.”
     *
     * 实现：在数组内移动字符，实现替换。从后往前复制，从而实现O(n)的时间复杂度。
     */
    public static void replace(char[] str, int originLength) {
        if (str == null || str.length == 0 || originLength == 0 || originLength > str.length) {
            return;
        }
        int blankCount = 0;
        for (int i = 0; i < originLength; i++) {
            if (str[i] == ' ') {
                blankCount++;
            }
        }
        int newLength = originLength + 2 * blankCount;
        System.out.println(originLength);
        System.out.println(newLength);
        if (newLength > str.length) {
            return;
        }
        int newIndex = newLength - 1;
        int originIndex = originLength - 1;
        while (originIndex >= 0 && newIndex > originIndex) {
            System.out.println(str[originIndex]);
            if (str[originIndex] == ' ') {
                str[newIndex] = '0';
                str[--newIndex] = '2';
                str[--newIndex] = '%';
            } else {
                str[newIndex] = str[originIndex];
            }
            newIndex--;
            originIndex--;
        }
    }

    public static void main(String[] args) {
        String test = "We are happy.";
        char[] str = new char[20];
        test.getChars(0, test.length(), str, 0);
        replace(str, test.length());
        System.out.println(new String(str));
    }


}
