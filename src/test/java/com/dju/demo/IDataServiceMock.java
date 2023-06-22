package com.dju.demo;

import com.dju.demo.services.IDataService;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class IDataServiceMock implements IDataService {
    private final String allStr;

    public IDataServiceMock(String all) {
        this.allStr = all;
    }

    @Override
    public JSONArray getData() throws ParseException {
        return null;
    }

    @Override
    public String getStringData() {
        return allStr;
    }

    @Override
    public String addData(JSONArray data) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
