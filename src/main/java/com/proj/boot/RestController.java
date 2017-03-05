package com.proj.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proj.domain.RequestParam;
import com.proj.domain.ResponseObject;
import com.proj.service.AuthenticationService;
import com.proj.service.ISearchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by jasweensingh on 04/03/17.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("counter-api")
public class RestController {

    @Autowired
    ISearchDataService searchDataService;

    @Autowired
    AuthenticationService authenticationService;

    private final String AUTHENTICATION_FAIL = "User Authentication fails. \n";

    // curl -H 'Content-Type: application/json' -X POST -H 'Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==' -d'{"searchText":["Duis","Sed","Augue"]}' http://localhost:8080/counter-api/search
    @RequestMapping(value = "/search",
    method = RequestMethod.POST)
    public String search(@RequestBody(required = false) String jsonInputString, @RequestHeader String Authorization) throws IOException {
        if(!authenticationService.isUserAuthorized(Authorization)){
            return AUTHENTICATION_FAIL;
        }
        ObjectMapper mapper = new ObjectMapper();
        RequestParam requestParam = mapper.readValue(jsonInputString, RequestParam.class);
        ResponseObject responseObject = searchDataService.findDataWithCountValue(requestParam);
        return mapper.writeValueAsString(responseObject) +"\n";
    }

    // curl -H 'Content-Type: text/csv' -H 'Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==' http://localhost:8080/counter-api/top/5
        //http://localhost:8080/counter-api/top/20 -H'Accept: text/csv' -H'Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw=='

    @RequestMapping(value = "/top/{count}")
    @ResponseBody
    public ResponseEntity top(@PathVariable int count, @RequestHeader String Authorization) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/csv");
        if(!authenticationService.isUserAuthorized(Authorization)){
            return new ResponseEntity<String>(AUTHENTICATION_FAIL, responseHeaders, HttpStatus.UNAUTHORIZED);
        }
        ObjectMapper mapper = new ObjectMapper();
        ResponseObject responseObject = searchDataService.findMostUsedWords(count);
        String output = mapper.writeValueAsString(responseObject);
        return new ResponseEntity<String>(output, responseHeaders, HttpStatus.OK);
    }

}
