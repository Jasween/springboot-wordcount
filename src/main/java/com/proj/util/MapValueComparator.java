package com.proj.util;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by jasweensingh on 04/03/17.
 */
public class MapValueComparator implements Comparator<String> {

    Map<String,Integer> map;
    public MapValueComparator(Map<String,Integer> map) {
        this.map = map;
    }

    public int compare(String first, String second) {
        if (map.get(first) >= map.get(second)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
