package com.proj.service;

import com.proj.domain.RequestParam;
import com.proj.domain.ResponseObject;
import com.proj.util.DataReaderUtil;
import com.proj.util.MapValueComparator;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by jasweensingh on 04/03/17.
 */
@Service
public class SearchDataService implements ISearchDataService{


    @Override
    public ResponseObject findDataWithCountValue(RequestParam requestParam){
        Map<String, Integer> wordCountMap = DataReaderUtil.findDataWithCountValue();
        Map<String,Integer> outputMap = new HashMap<>();
        for (String word : requestParam.getSearchText()) {
            word = word.toLowerCase();
            int count = 0;
            if(wordCountMap.containsKey(word)){
                count = wordCountMap.get(word);
            }
            outputMap.put(word,count);
        }

        List<Map<String,Integer>> list = new ArrayList<>();
        list.add(outputMap);
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCounts(list);

        return responseObject;
    }

    @Override
    public ResponseObject findMostUsedWords(int count){

        Map<String, Integer> wordCountUnsortedMap = DataReaderUtil.findDataWithCountValue();
        MapValueComparator mapValueComparator = new MapValueComparator(wordCountUnsortedMap);

        TreeMap<String,Integer> treeMap = new TreeMap<>(mapValueComparator);
        treeMap.putAll(wordCountUnsortedMap);


        Map<String,Integer> outputMap = new HashMap<>();
        int size = treeMap.size();
        if(count > size){
            System.out.println("Not a valid request");
        }
        Iterator<Map.Entry<String,Integer>> iterator = treeMap.entrySet().iterator();

        while(count > 0){
            Map.Entry<String,Integer> entry = iterator.next();
            outputMap.put(entry.getKey(),entry.getValue());
            --count;
        }

        List<Map<String,Integer>> list = new ArrayList<>();
        list.add(outputMap);
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCounts(list);

        return responseObject;
    }


}


