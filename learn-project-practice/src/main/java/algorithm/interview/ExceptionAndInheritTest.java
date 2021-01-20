package algorithm.interview;

/**
 * Created by qianyang on 18-3-15.
 */
public class ExceptionAndInheritTest {

    static class ExceptionA extends Exception {

    }

    static class ExceptionC extends ExceptionA {

    }

    static class ExceptionB extends ExceptionA {

    }


    public static void main(String[] args) {
        try {
            try {
                throw new ExceptionB();
            } catch (ExceptionA exception) {
                System.out.println("catch ExceptionA");
                throw exception;
            }
        } catch (ExceptionB exceptionB) {
            System.out.println("catch ExceptionB");
        }
    }

}
