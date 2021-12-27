package com.dju.demo.helpers;

import io.restassured.http.ContentType;
import io.restassured.response. Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;


public class IpHelper {
    /**
     * Check fraud score on https://scamalytics.com/ip/<ip></ip>
     * @param ip target ip
     * @return
     */
    public static int getFraudScore(final String ip) {
        Response response = given().header("Host", "scamalytics.com")
                .when().get("https://scamalytics.com/ip/" + ip)
                .then().statusCode(200).and().contentType(ContentType.HTML)
                .extract().response();

        Pattern pattern = Pattern.compile("Fraud Score: (\\d+)");
        Matcher matcher = pattern.matcher(response.asString());
        boolean matches = matcher.matches();

        String res = null;
        while(matcher.find()) {
            res = matcher.group(1);
        }

        return res != null ? Integer.parseInt(res) : -1;
    }

    /**
     * Gets geo ip info
     * @param ip target ip
     * @return
     */
    public static JSONObject getInfo(final String ip) throws ParseException {
        Response response = given()
                .when().get("http://demo.ip-api.com/json/" + ip)
                .then().statusCode(200).and().contentType(ContentType.JSON)
                .extract().response();

        org.json.simple.parser.JSONParser jp = new JSONParser();
        return (JSONObject) jp.parse(response.asString());
    }
}
