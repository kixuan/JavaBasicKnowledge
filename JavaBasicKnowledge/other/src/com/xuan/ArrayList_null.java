package com.xuan;

import java.util.ArrayList;

/**
 * ArrayListDemo 可以添加 null 值
 *
 * @author kixuan
 * @version 1.0
 */
public class ArrayList_null {
    public static void main(String[] args) {
        ArrayList<String> listOfStrings = new ArrayList<>();
        listOfStrings.add(null);
        listOfStrings.add("java");
        System.out.println(listOfStrings);
    }
}
