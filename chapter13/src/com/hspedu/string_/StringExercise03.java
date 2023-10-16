package com.hspedu.string_;

/**
 * @author 韩顺平
 * @version 1.0
 */
public class StringExercise03 {
    public static void main(String[] args) {
        String a = "hsp"; //a 指向 常量池的 “hsp”
        String b = new String("hsp");//b 指向堆中对象
        System.out.println(a.equals(b)); //T，只比较值
        System.out.println(a == b); //F，比较指向对象
        // 当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串 (用equals(Obiect) 方法确定)，则返回池中的字符串。
        // 否则，将此 String 对象添加到池中，并返回此 String 对象的引用
        // b.intern() 方法返回常量池地址
        System.out.println(a == b.intern()); //T，因为常量池已经有“hsp”了
        System.out.println(b == b.intern()); //F，b对象在堆中，b.intern()返回常量池地址，和b的堆中地址不一样
    }
}
