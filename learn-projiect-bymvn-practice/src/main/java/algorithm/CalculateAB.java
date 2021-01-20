package algorithm;

import java.util.Scanner;

public class CalculateAB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            boolean findFirstNum = true;
            boolean startFindFirstNum = false;

            boolean startFindSecondNum = false;
            String num1 = "";
            String num2 = "";
            for (char c : line.toCharArray()) {
                if (findFirstNum) {
                    if (c >= '0' && c <= '9') {
                        startFindFirstNum = true;
                        num1 += c;
                    } else if (c <= 32){
                        if (startFindFirstNum) {
                            findFirstNum = false;
                        }
                    } else {
                        throw new IllegalArgumentException("Illegal input!" + c + " Not Number!");
                    }
                } else {
                    if (c >= '0' && c <= '9') {
                        startFindSecondNum = true;
                        num2 += c;
                    } else if (c <= 32) {
                        if (startFindSecondNum) {
                            break;
                        }
                    } else {
                        throw new IllegalArgumentException("Illegal input!" + c + " Not Number!");
                    }
                }
            }
            System.out.println(Integer.parseInt(num1) + Integer.parseInt(num2));
        }

    }

    public static void calculate() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            System.out.println(scanner.nextInt() + scanner.nextInt());
        }
    }

}
