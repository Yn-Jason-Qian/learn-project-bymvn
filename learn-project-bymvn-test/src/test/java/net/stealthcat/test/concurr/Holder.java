package net.stealthcat.test.concurr;

/**
 * Created by qianyang on 18-1-12.
 */
public class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if (n != n)
            throw new AssertionError("This statement is false.");
    }

    public static Holder holder;

    public static void main(String[] args) {
        while (true){
            new Thread(() -> {
                holder = new Holder(1);
            }).start();
            for (int j = 0; j < 4; j++) {
                new Thread(() -> {
                    if (holder != null) {
                        holder.assertSanity();
                    }
                }).start();
            }
        }
    }
}
