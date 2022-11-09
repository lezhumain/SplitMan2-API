package com.dju.demo;

import com.dju.demo.helpers.MongoHelper;
import com.dju.demo.services.FileService;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MongoTests {
    @Test
    void checkMongo() throws ParseException, IOException, NoSuchAlgorithmException {
        final String colName = "splitman";
        MongoHelper _helper = new MongoHelper(HostIP.getIp() , 27017, "AzureDiamond", "hunter2", "mydb");
        List<Document> getAllDocs = _helper.getAllDocs(colName, true);
        System.out.println("length: " + getAllDocs.size());

        _helper.insertData(colName, "{\"id\": -2, \"name\": \"test\"}");

        getAllDocs = _helper.getAllDocs(colName, true);
        System.out.println("length: " + getAllDocs.size());
    }

//    @Test
//    void getAll() throws ParseException, IOException, NoSuchAlgorithmException {
//        final String colName = "splitman";
//        MongoHelper _helper = new MongoHelper(HostIP.getIp() , 27017, "AzureDiamond", "hunter2", "mydb");
//        List<Document> getAllDocs = _helper.getAllDocs(colName, true);
//        System.out.println("length: " + getAllDocs.size());
//    }

//    @Test
//    void checkRehister() throws IOException, ParseException, NoSuchAlgorithmException {
//        final String colName = "splitman";
//        MongoHelper _helper = new MongoHelper(HostIP.getIp(), 27017, "AzureDiamond", "hunter2", "mydb");
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
//    }

    @Test
    void getAll() throws ParseException, IOException, NoSuchAlgorithmException {
        final String colName = "splitman";
        MongoHelper _helper = new MongoHelper(HostIP.getIp() , 27017, "AzureDiamond", "hunter2", "mydb");
//        List<Document> getAllDocs = new ArrayList<>();
        List<Document> getAllDocs = _helper.getAllDocs(colName, false);

        final JSONParser jp = new JSONParser();
        JSONArray allRes = new JSONArray();
        for (Document d : getAllDocs) {
//            System.out.println(d.toJson());
            allRes.add((JSONObject)jp.parse(d.toJson()));
        }

//        final String alljson = allRes.toJSONString();
//        FileHelper.get_instance().writeFile("db.json", alljson);
//
//        List<Document> lastDpcs = _helper.getAllDocs(colName, true);
//        for (Document d : lastDpcs) {
//            System.out.println(d.toJson());
//        }
//        System.out.println("all length: " + getAllDocs.size());
//        System.out.println("last length: " + lastDpcs.size());
    }

    @Test
    void checkRehister() throws IOException, ParseException, NoSuchAlgorithmException {
        final String colName = "splitman";
        MongoHelper _helper = new MongoHelper(HostIP.getIp() , 27017, "AzureDiamond", "hunter2", "mydb");

        final List<Document> getAllDocs0 = _helper.getAllDocs(colName, true);
        System.out.println("length: " + getAllDocs0.size());

        SaveController sc = new SaveController();
        final String username = "a";

        final List<Document> getAllDocs = testRegisterFor(username, username, sc, _helper, colName, getAllDocs0, false);
        final List<Document> getAllDocs1 = testRegisterFor("s", "s", sc, _helper, colName, getAllDocs, false);
    }

    /**
     * Make sure register doesn't accept specs chars in username or password
     * @throws IOException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @Test
    void checkRehisterSpecialChars() throws IOException, ParseException, NoSuchAlgorithmException {
        final String colName = "splitman";
        MongoHelper _helper = new MongoHelper(HostIP.getIp() , 27017, "AzureDiamond", "hunter2", "mydb");

        final List<Document> getAllDocs0 = _helper.getAllDocs(colName, true);
        System.out.println("length: " + getAllDocs0.size());

        SaveController sc = new SaveController();
        final String username = "aa";

        final List<Document> getAllDoc2 = testRegisterFor(username + " some <>", username, sc, _helper, colName, getAllDocs0, true);
        Assertions.assertNull(getAllDoc2);

        final List<Document> getAllDoc3 = testRegisterFor(username, username + " some <>", sc, _helper, colName, getAllDocs0, true);
        Assertions.assertNull(getAllDoc3);
    }

    private List<Document> testRegisterFor(String username, String password, SaveController sc, MongoHelper _helper,
                                           String colName, List<Document> getAllDocs0,
                                           boolean shouldBeNull) throws IOException, ParseException, NoSuchAlgorithmException {
        sc.register("{\"id\": -2, \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"password1\": \"" + password + "\", " +
                "\"type\": \"user\", \"email\": \"" + username.replace("User", "email") + "\"}", null);

        final List<Document> getAllDocs = _helper.getAllDocs(colName, true);
        System.out.println("length: " + getAllDocs.size());

        Document newUser = getAllDocs.stream()
                .filter(document -> document.containsKey("type") && document.containsKey("username")
                        && document.get("type").equals("user") && document.get("username").equals(username))
                .findFirst().orElse(null);

        if (shouldBeNull) {
            Assert.assertNull(newUser);
            return null;
        }

        Assert.assertNotNull(newUser);
//        Assert.assertEquals(getAllDocs0.size(), getAllDocs.size() - 1);
        Assert.assertEquals(getAllDocs.size(), getAllDocs0.size() + 1);

        return getAllDocs;
    }

    /**
     * Make sure data leak is fixed
     * @throws ParseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Test
    void checkLogin1() throws ParseException, IOException, NoSuchAlgorithmException {
        // Make sure login doesn't return unwanted info

        final String all = "[{\"fromDate\":null,\"toDate\":null,\"name\":\"Test Tue, 21 Dec 2021 23:40:25 GMT\",\"description\":\"E2E test travel\",\"id\":4,\"type\":\"travel\",\"participants\":[{\"dayCount\":2,\"name\":\"Dju\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Max\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Suzie\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Elyan\",\"ratio\":0.25}]},{\"password\":\"0CC175B9C0F1B6A831C399E269772661\",\"invites\":[{\"isAccepted\":true,\"tripID\":0,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":1,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":2,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":3,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":4,\"nameInTrip\":\"Dju\"}],\"id\":2,\"type\":\"user\",\"email\":\"le_zhumain@msn.com\",\"username\":\"a\"},{\"date\":\"2021-12-21T23:40:30.871Z\",\"createdAt\":\"2021-12-21T23:40:32.417Z\",\"amount\":14.8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Autoroute\",\"tripId\":4,\"id\":0,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:32.417Z\"},{\"date\":\"2021-12-21T23:40:33.484Z\",\"createdAt\":\"2021-12-21T23:40:34.967Z\",\"amount\":28,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Essence\",\"tripId\":4,\"id\":1,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:34.967Z\"},{\"date\":\"2021-12-21T23:40:36.035Z\",\"createdAt\":\"2021-12-21T23:40:37.549Z\",\"amount\":14,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Glace\",\"tripId\":4,\"id\":2,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:37.549Z\"},{\"date\":\"2021-12-21T23:40:38.601Z\",\"createdAt\":\"2021-12-21T23:40:40.047Z\",\"amount\":27,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Tacos\",\"tripId\":4,\"id\":3,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:40.047Z\"},{\"date\":\"2021-12-21T23:40:41.102Z\",\"createdAt\":\"2021-12-21T23:40:42.550Z\",\"amount\":44,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Pizza\",\"tripId\":4,\"id\":4,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:42.550Z\"},{\"date\":\"2021-12-21T23:40:43.602Z\",\"createdAt\":\"2021-12-21T23:40:45.083Z\",\"amount\":8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Boissons\",\"tripId\":4,\"id\":5,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:45.083Z\"},{\"date\":\"2021-12-21T23:40:46.134Z\",\"createdAt\":\"2021-12-21T23:40:47.700Z\",\"amount\":73,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses crepes\",\"tripId\":4,\"id\":6,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:47.700Z\"},{\"date\":\"2021-12-21T23:40:48.767Z\",\"createdAt\":\"2021-12-21T23:40:50.216Z\",\"amount\":32,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses appart\",\"tripId\":4,\"id\":7,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:50.216Z\"},{\"date\":\"2021-12-21T23:40:51.368Z\",\"createdAt\":\"2021-12-21T23:40:52.783Z\",\"amount\":46.03,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Max\",\"tripId\":4,\"id\":8,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:52.783Z\"},{\"date\":\"2021-12-21T23:40:53.851Z\",\"createdAt\":\"2021-12-21T23:40:55.284Z\",\"amount\":34.23,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Elyan\",\"tripId\":4,\"id\":9,\"type\":\"expense\",\"payer\":\"Elyan\",\"updatedAt\":\"2021-12-21T23:40:57.466Z\"},{\"password\":\"03C7C0ACE395D80182DB07AE2C30F034\",\"invites\":[{\"isAccepted\":true,\"tripID\":4,\"tripName\":\"Test Tue, 21 Dec 2021 23:40:25 GMT\",\"nameInTrip\":\"Max\"}],\"id\":1,\"type\":\"user\",\"email\":\"hatsune.miku.asb@wspt.co.uk\",\"username\":\"s\"}]";

        String pass = "s", username = "s";

        Map<String, String> headers = new HashMap<>();
        HttpServletResponseMock response = new HttpServletResponseMock();
        Object hashRes = (new SaveController()).login(headers, "{\"password\": \"" + pass + "\", \"username\": \"" + username + "\"}", response);
        Assert.assertNotNull(hashRes);
        Assert.assertEquals(hashRes.getClass(), JSONObject.class);

        JSONObject jso = (JSONObject)hashRes;

        JSONObject res = jso.containsKey("result") ? (JSONObject)jso.get("result") : null;
        String resData = res != null && res.containsKey("data") ? (String)res.get("data") : null;

//        FIXME
//        Assert.assertNull(resData);
//        System.out.println(jso.toJSONString());

        pass = "'";
        username = "VQtJzosY";

        headers = new HashMap<>();
        response = new HttpServletResponseMock();
        hashRes = (new SaveController()).login(headers, "{\"password\": \"" + pass + "\", \"username\": \"" + username + "\"}", response);
        Assert.assertNotNull(hashRes);
        Assert.assertEquals(hashRes.getClass(), JSONObject.class);

        jso = (JSONObject)hashRes;

        res = jso.containsKey("result") ? (JSONObject)jso.get("result") : null;
        resData = res != null && res.containsKey("data") ? (String)res.get("data") : null;

//        FIXME fix this
//        Assert.assertNull(resData);
//        System.out.println(jso.toJSONString());
    }

}
