package com.proj.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jasweensingh on 04/03/17.
 */
public class ResponseObject {

    List<Map<String,Integer>> counts = new ArrayList<>();


    public List<Map<String, Integer>> getCounts() {
        return counts;
    }

    public void setCounts(List<Map<String, Integer>> counts) {
        this.counts = counts;
    }
}

