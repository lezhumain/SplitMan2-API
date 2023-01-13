package com.dju.demo.services;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public interface IDataService {
    public JSONArray getData() throws ParseException;
    public String getStringData();
    public String addData(JSONArray data);
}
