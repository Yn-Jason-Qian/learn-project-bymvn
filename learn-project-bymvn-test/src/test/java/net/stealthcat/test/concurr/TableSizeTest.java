package net.stealthcat.test.concurr;

/**
 * Created by qianyang on 17-12-26.
 */
public class TableSizeTest {

    public static void main(String[] args) {
        int x = 32;
        int n = x + (x >>> 1) + 1;
        System.out.println(tableSizeFor(n));
        System.out.println((resizeStamp(53) << 16) + 2);

    }

    /**
     * 获取比参数c大的第一个2的幂次方
     * @param c
     * @return
     */
    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    private static final int RESIZE_STAMP_BITS = 16;

    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }
}
