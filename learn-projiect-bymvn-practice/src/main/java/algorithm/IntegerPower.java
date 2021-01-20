package algorithm;

import java.math.BigDecimal;

/**
 * Created by qianyang on 18-3-7.
 *
 * 求数值的n次方，n可为正数，可为负数
 */
public class IntegerPower {
    public static double power(double base, int exponent) {
        int absExponent = Math.abs(exponent);

        double result = 1.0;
        for (int i = 0; i < absExponent; i++) {
            result *= base;
        }

        if (exponent < 0) {
            if (base == 0.0) {
                result = 0.0;
            } else {
                result = 1.0 / result;
            }
        }
        return result;
    }

    public static double powerByBigDecimal(double base, int exponent) {
        BigDecimal result = BigDecimal.ONE;
        BigDecimal baseByBigDecimal = BigDecimal.valueOf(base);
        int absExponent = Math.abs(exponent);

        for (int i = 0; i < absExponent; i++) {
            result = result.multiply(baseByBigDecimal);
        }

        if (exponent < 0) {
            if (baseByBigDecimal.equals(BigDecimal.ZERO)) {
                return 0.0;
            }
            result = BigDecimal.ONE.divide(result);
        }
        return result.doubleValue();
    }

    public static double powerByOptimal(double base, int exponent) {
        BigDecimal baseBD = BigDecimal.valueOf(base);
        if (baseBD.equals(BigDecimal.ZERO)) {
            return 0.0;
        }
        int absEx = Math.abs(exponent);
        BigDecimal result = power(baseBD, absEx);
        if (exponent < 0) {
            result = BigDecimal.ONE.divide(result);
        }
        return result.doubleValue();
    }

    private static BigDecimal power(BigDecimal base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("");
        }
        if (base.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        if (exponent == 0) {
            return BigDecimal.ONE;
        }
        if (exponent == 1) {
            return base;
        }
        BigDecimal result = power(base, exponent >> 1);
        result = result.multiply(result);
        if ((exponent & 1) == 1) {
            result = result.multiply(base);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(power(0.0, -2));
        System.out.println(powerByBigDecimal(2.0, -3));
        System.out.println(powerByOptimal(2, -3));
    }
}
