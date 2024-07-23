package com.xuan;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kixuan
 * @version 1.0
 */
public class ArrayListDemo {
    int initialCapacity = 10;
    ArrayList<String> list = new ArrayList<>(initialCapacity);

    Hashtable<String, String> hashtable = new Hashtable<>();
    final HashMap<Integer, String> map = new HashMap<>();

    ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
}
