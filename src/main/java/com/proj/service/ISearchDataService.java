package com.proj.service;

import com.proj.domain.RequestParam;
import com.proj.domain.ResponseObject;

/**
 * Created by jasweensingh on 04/03/17.
 */
public interface ISearchDataService {

    ResponseObject findDataWithCountValue(RequestParam requestParam);

    ResponseObject findMostUsedWords(int count);
}
