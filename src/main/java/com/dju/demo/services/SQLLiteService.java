package com.dju.demo.services;

import com.dju.demo.helpers.SQLiteJDBC;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.sqlite.SQLiteException;

public class SQLLiteService extends ADataService {
    private SQLiteJDBC _helper = new SQLiteJDBC();

    public SQLLiteService() {
        super();
        try {
            final String d = selectData();
        } catch (SQLiteException e) {
            if(e.getMessage().contains("no such table")) {
                _helper.createTable("CREATE TABLE SPLITMAN (VALUE  TEXT  NOT NULL)");
            }
        }
    }

    private String selectData() throws SQLiteException {
        String data = null;
        data = _helper.selectData(
                "SELECT value from (SELECT rowid, value from \"SPLITMAN\" order by rowid desc LIMIT 1)");
        return data;
    }

    @Override
    public String getStringData() {
        String data = null;
        try {
            data = selectData();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public boolean addData(JSONArray data) {
        final String json = data.toString();
        try {
            return _helper.insertData(String.format("INSERT INTO SPLITMAN (VALUE) VALUES ('%s')", json));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
