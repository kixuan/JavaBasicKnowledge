package com.xuan;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;

public class FailSafeExample {
    public static void main(String[] args) {
        // 创建 ConcurrentHashMap
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);

        // 获取迭代器并开始迭代
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println("Key: " + key + ", Value: " + map.get(key));

            // 在迭代过程中修改map
            if (key.equals("One")) {
                map.put("Four", 4); // 在迭代过程中添加一个新元素
            }
        }

        // 再次打印map内容，以显示修改后的状态
        System.out.println("After modification: " + map);
    }
}
