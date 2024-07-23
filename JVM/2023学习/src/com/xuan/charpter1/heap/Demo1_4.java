package com.xuan.charpter1.heap;

/**
 * 演示堆内存
 */
public class Demo1_4 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("1...");
        Thread.sleep(20000);
        byte[] array = new byte[1024 * 1024 * 10]; // 10 Mb
        System.out.println("2...");
        Thread.sleep(10000);
        array = null;
        System.gc();
        System.out.println("3...");
        Thread.sleep(10000L);
    }
}
