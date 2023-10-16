package com.hspedu.wrapper;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class WrapperExercise02 {
    public static void main(String[] args) {
        Integer i = new Integer(1);
        Integer j = new Integer(1);
        // 对象 == 判断就是判断是否是同一个对象
        System.out.println(i == j);  //False

        Integer m = 1; //底层 Integer.valueOf(1);
        Integer n = 1;
        System.out.println(m == n); //T
        //  阅读源码Integer valueof(int)的源码/打断点
        //  1. 如果i 在 IntegerCache.low(-128)~IntegerCache.high(127),就直接从数组返回
        //  2. 如果不在 -128~127,就直接 new Integer(i)
        //  public static Integer valueOf(int i) {
        //     if (i >= IntegerCache.low && i <= IntegerCache.high)
        //         return IntegerCache.cache[i + (-IntegerCache.low)];
        //     return new Integer(i);
        // }

        //所以，这里主要是看范围 -128 ~ 127 就是直接返回；否则，就new Integer(xx);
        Integer x = 128;//底层Integer.valueOf(128);
        Integer y = 128;
        System.out.println(x == y);//False
    }
}
