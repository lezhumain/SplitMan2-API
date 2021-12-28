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
    public boolean addData(JSONArray data) {
        if(data == null) {
            return false;
        }

        try {
            FileHelper.get_instance().writeFile(_dbFile, data.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
