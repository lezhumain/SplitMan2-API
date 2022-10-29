package com.dju.demo;

import com.dju.demo.helpers.MongoHelper;
import org.bson.Document;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class MongoTests {
//    @Test
//    void checkMongo() throws ParseException, IOException, NoSuchAlgorithmException {
//        final String colName = "splitman";
//        MongoHelper _helper = new MongoHelper(HostIP.getIp() , 27017, "AzureDiamond", "hunter2", "mydb");
//        List<Document> getAllDocs = _helper.getAllDocs(colName);
//        System.out.println("length: " + getAllDocs.size());
//
//        _helper.insertData(colName, "{\"id\": -2, \"name\": \"test\"}");
//
//        getAllDocs = _helper.getAllDocs(colName);
//        System.out.println("length: " + getAllDocs.size());
//    }

//    @Test
//    void getAll() throws ParseException, IOException, NoSuchAlgorithmException {
//        final String colName = "splitman";
//        MongoHelper _helper = new MongoHelper(HostIP.getIp() , 27017, "AzureDiamond", "hunter2", "mydb");
//        List<Document> getAllDocs = _helper.getAllDocs(colName, true);
//        System.out.println("length: " + getAllDocs.size());
//    }
//
//    @Test
//    void checkRehister() throws IOException, ParseException, NoSuchAlgorithmException {
//        final String colName = "splitman";
//        MongoHelper _helper = new MongoHelper(HostIP.getIp() , 27017, "AzureDiamond", "hunter2", "mydb");
//
//        final List<Document> getAllDocs0 = _helper.getAllDocs(colName, true);
//        System.out.println("length: " + getAllDocs0.size());
//
//        SaveController sc = new SaveController();
//        final String username = "User " + LocalDateTime.now();
//        sc.register("{\"id\": -2, \"username\": \"" + username + "\", " +
//                "\"type\": \"user\", \"email\": \"" + username.replace("User", "email") + "\"}", null);
//
//        final List<Document> getAllDocs = _helper.getAllDocs(colName, true);
//        System.out.println("length: " + getAllDocs.size());
//
//        Document newUser = getAllDocs.stream()
//                .filter(document -> document.containsKey("type") && document.containsKey("username")
//                        && document.get("type").equals("user") && document.get("username").equals(username))
//                .findFirst().orElse(null);
//
//        Assert.assertNotNull(newUser);
////        Assert.assertEquals(getAllDocs0.size(), getAllDocs.size() - 1);
//        Assert.assertEquals(getAllDocs.size(), getAllDocs0.size() + 1);
//    }
}
