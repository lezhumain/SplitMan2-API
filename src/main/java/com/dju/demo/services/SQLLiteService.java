package com.dju.demo.services;

import com.dju.demo.helpers.SQLiteJDBC;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sqlite.SQLiteException;

public class SQLLiteService extends ADataService {
    private SQLiteJDBC _helper = new SQLiteJDBC();

    public SQLLiteService() {
        super();
        try {
            final String d = selectData();
        } catch (Exception e) {
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
    public String doGetStringData() throws SQLiteException {
        String data = selectData();
        return data;
    }

    @Override
    public String addData(JSONArray data) {
        final String json = data.toString();
        try {
            return _helper.insertData(String.format("INSERT INTO SPLITMAN (VALUE) VALUES ('%s')", json)) ? "" : null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        try {
            final String data = this.doGetStringData();
            final JSONParser jp = new JSONParser();
            final JSONArray arr = (JSONArray)jp.parse(data);

            return arr.size();
        } catch (ParseException | SQLiteException e) {
            return -1;
        }
    }
}
