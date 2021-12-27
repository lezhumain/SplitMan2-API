package com.dju.demo.services;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class ADataService implements IDataService {
    @Override
    public JSONArray getData() throws ParseException {
        final String d = getStringData();
        org.json.simple.parser.JSONParser jp = new JSONParser();
        return (org.json.simple.JSONArray)jp.parse(d);
    }
}
