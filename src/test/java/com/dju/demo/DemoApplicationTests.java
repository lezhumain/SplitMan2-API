package com.dju.demo;

import com.dju.demo.helpers.IpHelper;
import com.dju.demo.helpers.ND5Helper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class DemoApplicationTests {
    @Test
    void checkGetAll() throws ParseException, IOException, NoSuchAlgorithmException {
        final String all = "[{\"fromDate\":null,\"toDate\":null,\"name\":\"Test Tue, 21 Dec 2021 23:40:25 GMT\",\"description\":\"E2E test travel\",\"id\":4,\"type\":\"travel\",\"participants\":[{\"dayCount\":2,\"name\":\"Dju\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Max\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Suzie\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Elyan\",\"ratio\":0.25}]},{\"password\":\"0CC175B9C0F1B6A831C399E269772661\",\"invites\":[{\"isAccepted\":true,\"tripID\":0,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":1,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":2,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":3,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":4,\"nameInTrip\":\"Dju\"}],\"id\":2,\"type\":\"user\",\"email\":\"le_zhumain@msn.com\",\"username\":\"a\"},{\"date\":\"2021-12-21T23:40:30.871Z\",\"createdAt\":\"2021-12-21T23:40:32.417Z\",\"amount\":14.8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Autoroute\",\"tripId\":4,\"id\":0,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:32.417Z\"},{\"date\":\"2021-12-21T23:40:33.484Z\",\"createdAt\":\"2021-12-21T23:40:34.967Z\",\"amount\":28,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Essence\",\"tripId\":4,\"id\":1,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:34.967Z\"},{\"date\":\"2021-12-21T23:40:36.035Z\",\"createdAt\":\"2021-12-21T23:40:37.549Z\",\"amount\":14,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Glace\",\"tripId\":4,\"id\":2,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:37.549Z\"},{\"date\":\"2021-12-21T23:40:38.601Z\",\"createdAt\":\"2021-12-21T23:40:40.047Z\",\"amount\":27,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Tacos\",\"tripId\":4,\"id\":3,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:40.047Z\"},{\"date\":\"2021-12-21T23:40:41.102Z\",\"createdAt\":\"2021-12-21T23:40:42.550Z\",\"amount\":44,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Pizza\",\"tripId\":4,\"id\":4,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:42.550Z\"},{\"date\":\"2021-12-21T23:40:43.602Z\",\"createdAt\":\"2021-12-21T23:40:45.083Z\",\"amount\":8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Boissons\",\"tripId\":4,\"id\":5,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:45.083Z\"},{\"date\":\"2021-12-21T23:40:46.134Z\",\"createdAt\":\"2021-12-21T23:40:47.700Z\",\"amount\":73,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses crepes\",\"tripId\":4,\"id\":6,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:47.700Z\"},{\"date\":\"2021-12-21T23:40:48.767Z\",\"createdAt\":\"2021-12-21T23:40:50.216Z\",\"amount\":32,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses appart\",\"tripId\":4,\"id\":7,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:50.216Z\"},{\"date\":\"2021-12-21T23:40:51.368Z\",\"createdAt\":\"2021-12-21T23:40:52.783Z\",\"amount\":46.03,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Max\",\"tripId\":4,\"id\":8,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:52.783Z\"},{\"date\":\"2021-12-21T23:40:53.851Z\",\"createdAt\":\"2021-12-21T23:40:55.284Z\",\"amount\":34.23,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Elyan\",\"tripId\":4,\"id\":9,\"type\":\"expense\",\"payer\":\"Elyan\",\"updatedAt\":\"2021-12-21T23:40:57.466Z\"},{\"password\":\"03C7C0ACE395D80182DB07AE2C30F034\",\"invites\":[{\"isAccepted\":true,\"tripID\":4,\"tripName\":\"Test Tue, 21 Dec 2021 23:40:25 GMT\",\"nameInTrip\":\"Max\"}],\"id\":1,\"type\":\"user\",\"email\":\"hatsune.miku.asb@wspt.co.uk\",\"username\":\"s\"}]";

        final SaveController sc = new SaveController();

        String pass = "sa", username = "sa";
        String allObj = sc.getAllObj();
        Assert.assertNotNull(allObj);
        Assert.assertTrue(allObj.startsWith("["));
        Assert.assertTrue(allObj.endsWith("]"));
    }

    @Test
	void checkGetUser() throws ParseException, IOException, NoSuchAlgorithmException {
		final String all = "[{\"fromDate\":null,\"toDate\":null,\"name\":\"Test Tue, 21 Dec 2021 23:40:25 GMT\",\"description\":\"E2E test travel\",\"id\":4,\"type\":\"travel\",\"participants\":[{\"dayCount\":2,\"name\":\"Dju\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Max\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Suzie\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Elyan\",\"ratio\":0.25}]},{\"password\":\"0CC175B9C0F1B6A831C399E269772661\",\"invites\":[{\"isAccepted\":true,\"tripID\":0,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":1,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":2,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":3,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":4,\"nameInTrip\":\"Dju\"}],\"id\":2,\"type\":\"user\",\"email\":\"le_zhumain@msn.com\",\"username\":\"a\"},{\"date\":\"2021-12-21T23:40:30.871Z\",\"createdAt\":\"2021-12-21T23:40:32.417Z\",\"amount\":14.8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Autoroute\",\"tripId\":4,\"id\":0,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:32.417Z\"},{\"date\":\"2021-12-21T23:40:33.484Z\",\"createdAt\":\"2021-12-21T23:40:34.967Z\",\"amount\":28,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Essence\",\"tripId\":4,\"id\":1,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:34.967Z\"},{\"date\":\"2021-12-21T23:40:36.035Z\",\"createdAt\":\"2021-12-21T23:40:37.549Z\",\"amount\":14,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Glace\",\"tripId\":4,\"id\":2,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:37.549Z\"},{\"date\":\"2021-12-21T23:40:38.601Z\",\"createdAt\":\"2021-12-21T23:40:40.047Z\",\"amount\":27,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Tacos\",\"tripId\":4,\"id\":3,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:40.047Z\"},{\"date\":\"2021-12-21T23:40:41.102Z\",\"createdAt\":\"2021-12-21T23:40:42.550Z\",\"amount\":44,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Pizza\",\"tripId\":4,\"id\":4,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:42.550Z\"},{\"date\":\"2021-12-21T23:40:43.602Z\",\"createdAt\":\"2021-12-21T23:40:45.083Z\",\"amount\":8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Boissons\",\"tripId\":4,\"id\":5,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:45.083Z\"},{\"date\":\"2021-12-21T23:40:46.134Z\",\"createdAt\":\"2021-12-21T23:40:47.700Z\",\"amount\":73,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses crepes\",\"tripId\":4,\"id\":6,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:47.700Z\"},{\"date\":\"2021-12-21T23:40:48.767Z\",\"createdAt\":\"2021-12-21T23:40:50.216Z\",\"amount\":32,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses appart\",\"tripId\":4,\"id\":7,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:50.216Z\"},{\"date\":\"2021-12-21T23:40:51.368Z\",\"createdAt\":\"2021-12-21T23:40:52.783Z\",\"amount\":46.03,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Max\",\"tripId\":4,\"id\":8,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:52.783Z\"},{\"date\":\"2021-12-21T23:40:53.851Z\",\"createdAt\":\"2021-12-21T23:40:55.284Z\",\"amount\":34.23,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Elyan\",\"tripId\":4,\"id\":9,\"type\":\"expense\",\"payer\":\"Elyan\",\"updatedAt\":\"2021-12-21T23:40:57.466Z\"},{\"password\":\"03C7C0ACE395D80182DB07AE2C30F034\",\"invites\":[{\"isAccepted\":true,\"tripID\":4,\"tripName\":\"Test Tue, 21 Dec 2021 23:40:25 GMT\",\"nameInTrip\":\"Max\"}],\"id\":1,\"type\":\"user\",\"email\":\"hatsune.miku.asb@wspt.co.uk\",\"username\":\"s\"}]";

        final SaveController sc = new SaveController();

        String pass = "sa", username = "sa";
        JSONObject user = sc.getUser(pass, username, all);
        Assert.assertNull(user);

        pass = "sa"; username = "s";
        user = sc.getUser(pass, username, all);
        Assert.assertNull(user);

        pass = "s"; username = "s";
        user = sc.getUser(pass, username, all);
        Assert.assertNotNull(user);

        System.out.println(user.toJSONString());
    }

    @Test
    void checkLogin() throws ParseException, IOException, NoSuchAlgorithmException {
        final String all = "[{\"fromDate\":null,\"toDate\":null,\"name\":\"Test Tue, 21 Dec 2021 23:40:25 GMT\",\"description\":\"E2E test travel\",\"id\":4,\"type\":\"travel\",\"participants\":[{\"dayCount\":2,\"name\":\"Dju\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Max\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Suzie\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Elyan\",\"ratio\":0.25}]},{\"password\":\"0CC175B9C0F1B6A831C399E269772661\",\"invites\":[{\"isAccepted\":true,\"tripID\":0,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":1,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":2,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":3,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":4,\"nameInTrip\":\"Dju\"}],\"id\":2,\"type\":\"user\",\"email\":\"le_zhumain@msn.com\",\"username\":\"a\"},{\"date\":\"2021-12-21T23:40:30.871Z\",\"createdAt\":\"2021-12-21T23:40:32.417Z\",\"amount\":14.8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Autoroute\",\"tripId\":4,\"id\":0,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:32.417Z\"},{\"date\":\"2021-12-21T23:40:33.484Z\",\"createdAt\":\"2021-12-21T23:40:34.967Z\",\"amount\":28,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Essence\",\"tripId\":4,\"id\":1,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:34.967Z\"},{\"date\":\"2021-12-21T23:40:36.035Z\",\"createdAt\":\"2021-12-21T23:40:37.549Z\",\"amount\":14,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Glace\",\"tripId\":4,\"id\":2,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:37.549Z\"},{\"date\":\"2021-12-21T23:40:38.601Z\",\"createdAt\":\"2021-12-21T23:40:40.047Z\",\"amount\":27,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Tacos\",\"tripId\":4,\"id\":3,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:40.047Z\"},{\"date\":\"2021-12-21T23:40:41.102Z\",\"createdAt\":\"2021-12-21T23:40:42.550Z\",\"amount\":44,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Pizza\",\"tripId\":4,\"id\":4,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:42.550Z\"},{\"date\":\"2021-12-21T23:40:43.602Z\",\"createdAt\":\"2021-12-21T23:40:45.083Z\",\"amount\":8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Boissons\",\"tripId\":4,\"id\":5,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:45.083Z\"},{\"date\":\"2021-12-21T23:40:46.134Z\",\"createdAt\":\"2021-12-21T23:40:47.700Z\",\"amount\":73,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses crepes\",\"tripId\":4,\"id\":6,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:47.700Z\"},{\"date\":\"2021-12-21T23:40:48.767Z\",\"createdAt\":\"2021-12-21T23:40:50.216Z\",\"amount\":32,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses appart\",\"tripId\":4,\"id\":7,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:50.216Z\"},{\"date\":\"2021-12-21T23:40:51.368Z\",\"createdAt\":\"2021-12-21T23:40:52.783Z\",\"amount\":46.03,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Max\",\"tripId\":4,\"id\":8,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:52.783Z\"},{\"date\":\"2021-12-21T23:40:53.851Z\",\"createdAt\":\"2021-12-21T23:40:55.284Z\",\"amount\":34.23,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Elyan\",\"tripId\":4,\"id\":9,\"type\":\"expense\",\"payer\":\"Elyan\",\"updatedAt\":\"2021-12-21T23:40:57.466Z\"},{\"password\":\"03C7C0ACE395D80182DB07AE2C30F034\",\"invites\":[{\"isAccepted\":true,\"tripID\":4,\"tripName\":\"Test Tue, 21 Dec 2021 23:40:25 GMT\",\"nameInTrip\":\"Max\"}],\"id\":1,\"type\":\"user\",\"email\":\"hatsune.miku.asb@wspt.co.uk\",\"username\":\"s\"}]";

        String pass = "s", username = "s";
//        final String hash = ND5Helper.hash(pass)
        SaveController.APIResult hashRes = (new SaveController()).doSessionLogin(pass, username, all);
        Assert.assertNotNull(hashRes.result);

        System.out.println(hashRes.result.toJSONString());
//        Assert.assertEquals(hashRes.get("hash"), hash);
    }

    @Test
    void checkInvite() throws ParseException, IOException {
        final String all = "[{\"fromDate\":null,\"toDate\":null,\"name\":\"Test Tue, 21 Dec 2021 23:40:25 GMT\",\"description\":\"E2E test travel\",\"id\":4,\"type\":\"travel\",\"participants\":[{\"dayCount\":2,\"name\":\"Dju\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Max\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Suzie\",\"ratio\":0.25},{\"dayCount\":2,\"name\":\"Elyan\",\"ratio\":0.25}]},{\"password\":\"a\",\"invites\":[{\"isAccepted\":true,\"tripID\":0,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":1,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":2,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":3,\"nameInTrip\":\"Dju\"},{\"isAccepted\":true,\"tripID\":4,\"nameInTrip\":\"Dju\"}],\"id\":2,\"type\":\"user\",\"email\":\"le_zhumain@msn.com\",\"username\":\"a\"},{\"date\":\"2021-12-21T23:40:30.871Z\",\"createdAt\":\"2021-12-21T23:40:32.417Z\",\"amount\":14.8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Autoroute\",\"tripId\":4,\"id\":0,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:32.417Z\"},{\"date\":\"2021-12-21T23:40:33.484Z\",\"createdAt\":\"2021-12-21T23:40:34.967Z\",\"amount\":28,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Essence\",\"tripId\":4,\"id\":1,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:34.967Z\"},{\"date\":\"2021-12-21T23:40:36.035Z\",\"createdAt\":\"2021-12-21T23:40:37.549Z\",\"amount\":14,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Glace\",\"tripId\":4,\"id\":2,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:37.549Z\"},{\"date\":\"2021-12-21T23:40:38.601Z\",\"createdAt\":\"2021-12-21T23:40:40.047Z\",\"amount\":27,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Tacos\",\"tripId\":4,\"id\":3,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:40.047Z\"},{\"date\":\"2021-12-21T23:40:41.102Z\",\"createdAt\":\"2021-12-21T23:40:42.550Z\",\"amount\":44,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Pizza\",\"tripId\":4,\"id\":4,\"type\":\"expense\",\"payer\":\"Dju\",\"updatedAt\":\"2021-12-21T23:40:42.550Z\"},{\"date\":\"2021-12-21T23:40:43.602Z\",\"createdAt\":\"2021-12-21T23:40:45.083Z\",\"amount\":8,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Dju\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Max\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.25,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.25,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Boissons\",\"tripId\":4,\"id\":5,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:45.083Z\"},{\"date\":\"2021-12-21T23:40:46.134Z\",\"createdAt\":\"2021-12-21T23:40:47.700Z\",\"amount\":73,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true},{\"name\":\"Elyan\",\"e4xpenseRatio\":0.3333333333333333,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses crepes\",\"tripId\":4,\"id\":6,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:47.700Z\"},{\"date\":\"2021-12-21T23:40:48.767Z\",\"createdAt\":\"2021-12-21T23:40:50.216Z\",\"amount\":32,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Max\",\"e4xpenseRatio\":0.5,\"selected\":true},{\"name\":\"Suzie\",\"e4xpenseRatio\":0.5,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Courses appart\",\"tripId\":4,\"id\":7,\"type\":\"expense\",\"payer\":\"Suzie\",\"updatedAt\":\"2021-12-21T23:40:50.216Z\"},{\"date\":\"2021-12-21T23:40:51.368Z\",\"createdAt\":\"2021-12-21T23:40:52.783Z\",\"amount\":46.03,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Max\",\"tripId\":4,\"id\":8,\"type\":\"expense\",\"payer\":\"Max\",\"updatedAt\":\"2021-12-21T23:40:52.783Z\"},{\"date\":\"2021-12-21T23:40:53.851Z\",\"createdAt\":\"2021-12-21T23:40:55.284Z\",\"amount\":34.23,\"updatedBy\":\"a\",\"payees\":[{\"name\":\"Suzie\",\"e4xpenseRatio\":1,\"selected\":true}],\"createdBy\":\"a\",\"name\":\"Rembours Elyan\",\"tripId\":4,\"id\":9,\"type\":\"expense\",\"payer\":\"Elyan\",\"updatedAt\":\"2021-12-21T23:40:57.466Z\"},{\"password\":\"s\",\"invites\":[],\"id\":1,\"type\":\"user\",\"email\":\"hatsune.miku.asb@wspt.co.uk\",\"username\":\"s\"}]";

        final int userID = 2,
                tripID = 4;

        final SaveController sc = new SaveController();
        final JSONArray arr = sc.doInvite(userID,
                String.format("{\"tripID\": %s, \"email\": \"hatsune.miku.asb@wspt.co.uk\"}", tripID), all);

        System.out.println(arr);
        Assert.assertNotNull(arr);

        final JSONObject targetUser = (JSONObject) arr.stream()
                .filter(o -> {
                    JSONObject jo = (JSONObject) o;
                    return ((String)jo.get("type")).equals("user")
                            && !jo.get("id").toString().equals(String.valueOf(userID));
                }).findFirst().orElse(null);


        Assert.assertNotNull(targetUser);

        JSONArray invites = (JSONArray) targetUser.get("invites");
        Assert.assertNotNull(invites);
        Assert.assertEquals(invites.size(), 1);
        Assert.assertEquals(((JSONObject)invites.get(0)).get("tripID"), tripID);
    }

}
