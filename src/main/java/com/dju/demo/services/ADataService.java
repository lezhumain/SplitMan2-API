package com.dju.demo.services;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sqlite.SQLiteException;

import java.io.IOException;

public abstract class ADataService implements IDataService {
    @Override
    public JSONArray getData() throws ParseException {
        final String d = getStringData();
        org.json.simple.parser.JSONParser jp = new JSONParser();
        return (org.json.simple.JSONArray)jp.parse(d);
    }

    @Override
    public String getStringData() {
        String res;
        try {
            res = doGetStringData();
            if(res == null) {
                res = "[]";
            }
        }
        catch (Exception e) {
            System.out.println("Just printing error:");
            e.printStackTrace();
            res = "[]";
        }

        return res;
    }

    protected abstract String doGetStringData() throws IOException, SQLiteException;
}
