package com.dreamTimes.utils;

import java.math.BigDecimal;

public class DecimalUtils {
    /**
     * 价格工具类
     */

    public static BigDecimal add(double num1,double num2){
        BigDecimal decimal1 = new BigDecimal(String.valueOf(num1));
        BigDecimal decimal2 = new BigDecimal(String.valueOf(num2));
        return decimal1.add(decimal2);
    }

    public static BigDecimal sub(double num1,double num2){
        BigDecimal decimal1 = new BigDecimal(String.valueOf(num1));
        BigDecimal decimal2 = new BigDecimal(String.valueOf(num2));
        return decimal1.subtract(decimal2);
    }

    public static BigDecimal mul(double num1,double num2){
        BigDecimal decimal1 = new BigDecimal(String.valueOf(num1));
        BigDecimal decimal2 = new BigDecimal(String.valueOf(num2));
        return decimal1.multiply(decimal2);
    }

    public static BigDecimal div(double num1,double num2){
        BigDecimal decimal1 = new BigDecimal(String.valueOf(num1));
        BigDecimal decimal2 = new BigDecimal(String.valueOf(num2));
        return decimal1.divide(decimal2,2,BigDecimal.ROUND_HALF_DOWN);
    }
}
