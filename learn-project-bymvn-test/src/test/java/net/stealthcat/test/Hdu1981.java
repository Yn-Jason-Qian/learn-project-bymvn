package net.stealthcat.test;

import java.util.Scanner;

public class Hdu1981 {

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
    private static Op[] ops;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        t = scanner.nextInt();
        while (t-- > 0) {
            n = scanner.nextInt();
            c = scanner.nextInt();
            ops = new Op[c];
            chars = scanner.next().toCharArray();
            scanner.nextLine();
            for(int i=0; i < c; i++) {
            	char opc = scanner.next().charAt(0);
            	if(opc == 'Q') {
            		ops[i] = new Op(opc, scanner.nextInt(), 0);
            	} else {
            		ops[i] = new Op(opc, scanner.nextInt(), scanner.nextInt());
            	}
            }
            for(int j=0; j < c; j++) {
            	if(ops[j].c == 'Q') {
            		int resultIndex = ops[j].s, count = 0;
                    for (int i = j - 1; i >= 0; i--) {
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
            	}
            }
        }
        scanner.close();
    }
    
    static void readChars(Scanner scanner) {
    	String s = scanner.next();
    	for(int i = 0; i < n; i++) {
    		chars[i] = s.charAt(i);
    	}
    }
    
}
