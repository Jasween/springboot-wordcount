package com.proj.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.proj.domain.RequestParam;
import com.proj.domain.ResponseObject;
import com.proj.service.ISearchDataService;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sun.javafx.tools.resource.DeployResource.Type.data;

/**
 * Created by jasweensingh on 04/03/17.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("counter-api")
public class RestController {

    @Autowired
    ISearchDataService searchDataService;

    @RequestMapping("/data")
    public String getData(){
        return "Data test successfully";
    }

    // curl URL => curl -H 'Content-Type: application/json' -X POST -d'{"searchText":["Duis","Sed","Augue"]}' http://localhost:8080/counter-api/search

    @RequestMapping(value = "/search",
    method = RequestMethod.POST)
    public String search(@RequestBody(required = false) String jsonInputString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RequestParam requestParam = mapper.readValue(jsonInputString, RequestParam.class);
        ResponseObject responseObject = searchDataService.findDataWithCountValue(requestParam);
        return mapper.writeValueAsString(responseObject) +"\n";
    }

    // curl URL => curl -H 'Accept: text/csv' http://localhost:8080/counter-api/top/5
    //http://localhost:8080/counter-api/top/20

    @RequestMapping(value = "/top/{count}")
    public ResponseEntity top(@PathVariable int count) throws IOException {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/plain; charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        ResponseObject responseObject = searchDataService.findMostUsedWords(count);
        String output = mapper.writeValueAsString(responseObject);
        return new ResponseEntity<String>(output, responseHeaders, HttpStatus.OK);
    }

}
