package net.stealthcat.test;

import java.util.Scanner;

public class Test1981 {

    private static int t;
    private static int n;
    private static int c;

    private static class Op {
        char c;
        int s, e;
		public Op(char c, int s, int e) {
			this.c = c;
			this.s = s;
			this.e = e;
		}
    }

    private static char[] chars;
    private static Op[] ops = new Op[3001];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        t = scanner.nextInt();
        while (t-- > 0) {
            n = scanner.nextInt();
            c = scanner.nextInt();
            chars = scanner.next().toCharArray();
            int opCount = 0;
            scanner.nextLine();
            while (c-- > 0) {
                char opc = scanner.next().charAt(0);
                if (opc == 'Q') {
                    int resultIndex = scanner.nextInt(), count = 0;
                    for (int i = opCount - 1; i >= 0; i--) {
                        Op oldOp = ops[i];
                        if (oldOp.e < resultIndex || oldOp.s > resultIndex) {
                            continue;
                        }
                        if (oldOp.c == 'R') {
                            resultIndex = oldOp.s + oldOp.e - resultIndex;
                        } else if(oldOp.c == 'S'){
                            count++;
                        }
                    }
                    char result = chars[resultIndex - 1];
                    for(int i = 0; i < count; i++) {
                        if(result == 'z') {
                            result = 'a';
                        } else {
                            result = (char) (result + 1);
                        }
                    }
                    System.out.println(result);
                } else {
                    ops[opCount++] = new Op(opc, scanner.nextInt(), scanner.nextInt());;
                }
            }

        }
        scanner.close();
    }
    
}
