package com.dju.demo;

import com.dju.demo.helpers.FileHelper;
import com.dju.demo.helpers.MongoHelper;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * test -Dtest=MongoBackupTests -f pom.xml
 */
@SpringBootTest
class MongoBackupTests {
    @Test
    void backup() throws ParseException, IOException {
        String colName = "splitman";
        this.doGetAll(colName, String.format("db_%s.json", colName));
        colName = "archive";
        this.doGetAll(colName, String.format("db_%s.json", colName));
    }

    private void doGetAll(final String colName, final String fileName) throws ParseException, IOException {
        MongoHelper _helper = new MongoHelper(HostIP.MONGO_IP, 27017, "", "", "mydb");
        List<Document> getAllDocs = _helper.getAllDocs(colName, false);

        final JSONParser jp = new JSONParser();
        JSONArray allRes = new JSONArray();
        for (Document d : getAllDocs) {
            final String json = d.toJson();
            System.out.println(d.toJson());
            allRes.add(jp.parse(json));
        }

        final String alljson = allRes.toJSONString();
        final File outputFile = new File(fileName);
        if(outputFile.exists()) {
            outputFile.delete();
        }
        FileHelper.get_instance().writeFile(fileName, alljson);

        System.out.println("all length: " + getAllDocs.size());
    }
}
