package com.proj.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jasweensingh on 04/03/17.
 */
public class DataReaderUtil {

    private static final String FILE_PATH = "src/main/resources/data";

 /*   public static Map<String, Integer> findDataWithCountValue(){

        Map<String, Integer> wordCount = null;
        try {
            Path path = Paths.get(FILE_PATH);
            wordCount = Files.lines(path).flatMap(line -> Arrays.stream(line.trim().split(" ")))
                    .map(word -> word.replaceAll("[,.]", "").toLowerCase().trim())
                    .filter(word -> word.length() > 0)
                    .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(), (v1, v2) -> v1 + v2));

            wordCount.forEach((k, v) -> System.out.println("word: " + k + " count: " + v));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordCount;

    }*/

    public static Map<String, Integer> findDataWithCountValue(){
        Map<String,Integer> wordMap = new HashMap<>();
        String line = "";

        Path path = Paths.get(FILE_PATH);
        try(BufferedReader bufferedReader = Files.newBufferedReader(path);) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    int count = 0;
                    word = word.replaceAll("[,.]", "").toLowerCase();
                    if(wordMap.containsKey(word)){
                        count = wordMap.get(word);
                        wordMap.put(word, ++count);
                    }else{
                        wordMap.put(word, ++count);
                    }
                }
            }

        }
         catch (IOException e) {
            e.printStackTrace();
        }
        return wordMap;

    }

}
