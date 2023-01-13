package com.dju.demo.services;

import com.dju.demo.HostIP;
import com.dju.demo.helpers.MongoHelper;
import org.json.simple.JSONArray;
import org.sqlite.SQLiteException;

public class MongodbService extends ADataService {
    private final String _collectionName = "splitman";
//    private MongodbHelper _helper0 = new MongodbHelper();
    private MongoHelper _helper = new MongoHelper(HostIP.getIp() , 27017, "AzureDiamond", "hunter2", "mydb");

    public MongodbService() {
        super();
        try {
            final String d = selectData();
        } catch (Exception e) {
            if(e.getMessage().contains("no such table")) { // TODO check error from mongo
                _helper.createCollection(this._collectionName);
            }
        }
    }

    private String selectData() throws SQLiteException {
        String data = null;
        data = _helper.selectData(this._collectionName, false);
//        data = _helper.selectData(this._collectionName, true);
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
//        return _helper.insertData(this._collectionName, json);
        return _helper.upsertData(this._collectionName, json);
    }
}
