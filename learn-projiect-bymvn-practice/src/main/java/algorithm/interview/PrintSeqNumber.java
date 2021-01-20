package algorithm.interview;

/**
 * Created by qianyang on 18-3-16.
 */
public class PrintSeqNumber {

    public static void printSeq(int length) {
        char[] number = new char[length];
        for (int i = 0; i < length; i++) {
            number[i] = '0';
        }
        do {
            inc(number);
            printNumber(new String(number));
        } while (!checkNumber(number));
    }

    public static boolean checkNumber(char[] number) {
        for (int i = 0; i < number.length; i++) {
            if (number[i] != '9') {
                return false;
            }
        }
        return true;
    }


    public static void inc(char[] number) {
        if (number == null || number.length == 0) {
            return;
        }
        int carryon = 0;
        for (int i = number.length - 1; i >= 0; i--) {
            int numberOfIndex = number[i] - '0';
            if (numberOfIndex > 9 || numberOfIndex < 0) {
                return;
            }
            if (i == number.length - 1) {
                if (++numberOfIndex >= 10) {
                    number[i] = '0';
                    carryon = 1;
                } else {
                    number[i] = (char)(numberOfIndex + '0');
                    break;
                }
            } else {
                if (carryon == 0) {
                    break;
                } else {
                    numberOfIndex += carryon;
                    if (numberOfIndex >= 10) {
                        number[i] = '0';
                    } else {
                        number[i] = (char)(numberOfIndex + '0');
                        break;
                    }
                }
            }
        }
    }

    public static void printNumber(String n) {
        if (n == null || n.length() == 0) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        boolean firstNotZero = true;
        for (int i = 0; i < n.length(); i++) {
            int number = n.charAt(i) - '0';
            if (number > 9 || number < 0) {
                return;
            }
            if (number == 0) {
                if (!firstNotZero) {
                    builder.append(number);
                }
            } else {
                if (firstNotZero) {
                    firstNotZero = false;
                }
                builder.append(number);
            }
        }
        System.out.println(builder.toString());
    }

    public static void main(String[] args) {
        printSeq(3);
    }

}
