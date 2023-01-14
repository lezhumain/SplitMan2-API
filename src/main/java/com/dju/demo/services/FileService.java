package com.dju.demo.services;

import com.dju.demo.helpers.FileHelper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileService extends ADataService {
    private final String _dbFile = Paths.get(".", "target.txt").toString(); // TODO remove

    @Override
    protected String doGetStringData() throws IOException {
        final String fileContent = FileHelper.get_instance().readLastLine(_dbFile);
        return fileContent;
    }

    @Override
    public String addData(JSONArray data) {
        if(data == null) {
            return null;
        }

        try {
            FileHelper.get_instance().writeFile(_dbFile, data.toString());
            return "";
        } catch (IOException e) {
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
        } catch (IOException | ParseException e) {
            return -1;
        }
    }
}
