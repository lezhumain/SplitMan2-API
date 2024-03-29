package com.dju.demo;

import com.dju.demo.checkers.UserChecker;
import com.dju.demo.helpers.*;
import com.dju.demo.services.FileService;
import com.dju.demo.services.IDataService;
import com.dju.demo.services.MongodbService;
import com.dju.demo.services.SQLLiteService;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.dju.demo.HostIP.HOST_IP;
import static com.dju.demo.SessionEnv._sessionFile;

//import io.restassured.path.json.JsonPath;


class AItem {
    public int id;
    public String type; // expense, travel, user, ...
    public Optional<Integer> travelID;

    public AItem() {}
}

@RestController
public class SaveController {
    private static final String COOKIE_NAME = "Spliman_Session";

    public static Class aClass = MongodbService.class;

    protected final IDataService _service;
//    private final IDataService _service = new MongodbService();
//    private final IDataService _service = new SQLLiteService();
//    private final IDataService _service = new FileService();

    //    private final String _dbFile = Paths.get(System.getProperty("user.dir"), "target.txt").toString();

//    private final String _dbFile = Paths.get(".", "target.txt").toString(); // TODO remove
    private JSONObject _currentUser;

    SaveController() {
//        _service = service;
        if (aClass.equals(MongodbService.class)) {
            _service = new MongodbService();
        } else if (aClass.equals(SQLLiteService.class)) {
            _service = new SQLLiteService();
        } else if (aClass.equals(FileService.class)) {
            _service = new FileService();
        }else {
            _service = null;
        }
    }

    SaveController(final IDataService service) {
        _service = service;
    }

    // TODO check how to make private
    public String getAllObj() throws IOException, ParseException {
//        try {return String.format("TODO (%s) (%s)", HOST_IP, HOST_IP.length());
//            final String fileContent = FileHelper.get_instance().readFile(_dbFile);
//            String lastL = getLastLine(fileContent);
//
//            return lastL;
//        } catch (IOException e) {
////            return String.format("{\"error\"}: \"%s\"", e.getMessage());
//            throw e;
//        }

        return _service.getStringData();
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/version")
    public String version() {
        return String.format("TODO (%s) (%s)", HOST_IP, HOST_IP.length());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/itemCount")
    public String itemCount() {
        return String.valueOf(this._service.getItemCount());
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/genimg", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public byte[] genimg(@RequestParam String fileName, @RequestParam String payload,
                         @RequestPart MultipartFile document, HttpServletResponse response) throws ParseException, IOException, InterruptedException {
        final JSONParser jp = new JSONParser();
        final JSONObject o = (JSONObject)jp.parse(payload);

        System.out.println(fileName);

        Path targetPath = Path.of("Lecteur/" + fileName);

        String docType = document.getContentType();
        byte[] docContent = document.getBytes();
        Files.write(targetPath, docContent, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

        final CMDHelperResponse resp =  new CMDHelper().genImg(
                (String)o.get("name"), (String)o.get("surname"),(String)o.get("birth"), (String)o.get("date"), fileName);

        response.setContentType("image/png");
        return docContent;
    }

    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @GetMapping("/get")
    public String getAll(@RequestHeader Map<String, String> headers, @CookieValue(COOKIE_NAME) String fooCookie, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Credentials", "true");
        try {
//            final String fooCookie = getCookieFromHeaders(headers); // in case local CORS errors

            JSONArray allSessions = getAllSessions();
            if(allSessions == null || allSessions.size() == 0) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return null;
            }

            JSONObject session = getSessionToken(fooCookie, allSessions);
            final boolean hashExists = session != null;

            if(!hashExists) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return null;
            }

            final String res = removeAllPasswords(getAllObj()); // TODO put at the end ?

            return res;
        } catch (IOException | ParseException e) {
            response.setStatus(500);
            return String.format("{\"error\"}: \"%s\"", e.getMessage());
        }
    }

    private String getCookieFromHeaders(Map<String, String> headers) {
        final String cookie = headers.containsKey("cookie") ? headers.get("cookie") : null;
        System.out.println("Cookie: " + cookie);
        return cookie.replace("Spliman_Session=", "");
    }

    private String removeAllPasswords(String allObj) throws ParseException {
        org.json.simple.parser.JSONParser jp = new JSONParser();
        org.json.simple.JSONArray o = (org.json.simple.JSONArray)jp.parse(allObj);

        for(int i = 0; i < o.size(); ++i) {
            JSONObject o1 = (JSONObject) o.get(i);
            if(o1.containsKey("password")) {
//                o1.remove("password");
//                o1.put("password", "");
                o1.replace("password", "");
            }
            // TODO check if 2nd pass
//                if(o1.containsKey("password")) {
//                    o1.remove("password")
//                }
        }

        return o.toString();
    }

    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @GetMapping("/path")
    public String getPath() {
        Path p = Paths.get(".").toAbsolutePath();
        return p.toString();
    }

    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @PostMapping("/save")
    public void saveAll(@CookieValue(COOKIE_NAME) String fooCookie, @RequestHeader Map<String, String> headers, @RequestBody String res) throws IOException, ParseException {
        // TODO get userID
//        int userID = this.checkUserID(fooCookie);
        int userID = 1;

        org.json.simple.parser.JSONParser jp = new JSONParser();
        org.json.simple.JSONArray o = (org.json.simple.JSONArray)jp.parse(res);

        // FIXME

//        final String usserID = "0"; // TODO

        //  get user's tripID


        //  get all data
        //  delete all things from user

        //  add things from request
//        FileHelper.get_instance().writeFile(_dbFile, res);
        _service.addData(o);
    }

    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @PostMapping("/login")
    public JSONObject login(@RequestHeader Map<String, String> headers, @RequestBody String res, HttpServletResponse response) throws IOException, ParseException, NoSuchAlgorithmException {
        // TODO get userID
//        int userID = this.checkUserID(headers);
         response.addHeader("Access-Control-Allow-Credentials", "true");

        org.json.simple.parser.JSONParser jp = new JSONParser();
        org.json.simple.JSONObject o;
        try {
            o = (JSONObject)jp.parse(res);
        } catch (ParseException e) {
            System.out.println("Erreur parsing payload");
            System.out.println(res);
            System.out.println(headers);
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            return null;
        }

        final String pass = (String) o.get("password"),
                userName = (String) o.get("username");

        if(pass == null || pass.isEmpty() || userName == null || userName.isEmpty()) { // why?
            final String cookieStr = headers.get("cookie");
            final String[] cookies = cookieStr != null && cookieStr.contains(";")
                    ? cookieStr.split(";")
                    : new String[]{};
            final String session_cookie = Arrays.stream(cookies).filter(s -> s.trim().startsWith(COOKIE_NAME + "=")).findFirst().orElse(null);
            final String fooCookie = session_cookie != null ? session_cookie.split("=")[1] : null;

            if(fooCookie == null || fooCookie.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return null;
            }

            JSONObject sessionUser = checkUser(fooCookie);
            if(sessionUser == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return null;
            }
//            System.out.println(headers);

            sessionUser.remove("password");
            sessionUser.remove("password1");

            return sessionUser;
        }


        APIResult sessionRes = doSessionLogin(pass, userName);
//        if(sessionRes.hasError) {
//            sessionRes.result = null;
//        }
        final JSONObject us = sessionRes.toJson();

        JSONObject usToReturn = null;

        if(!sessionRes.hasError) {
            response.setStatus(HttpServletResponse.SC_OK);

            JSONArray allSessions = getAllSessions();
            if (allSessions == null || allSessions.size() == 0) {
                allSessions = new JSONArray();
            }

            Optional<JSONObject> alreadyHashed = allSessions
                    .stream().filter(o1 -> ((JSONObject) o1).get("username").equals(userName))
                    .findFirst();

            if (!alreadyHashed.isPresent()) {
                JSONObject sessClone = (JSONObject)((JSONObject)us.get("result")).clone();
                sessClone.remove("user");
                allSessions.add(sessClone);
                FileHelper.get_instance().writeFile(_sessionFile, allSessions.toString());
            } else {
                final String existingHash = (String) alreadyHashed.get().get("hash");
//            sessionRes.result.remove("hash");
                sessionRes.result.replace("hash", existingHash);
            }

            // add session
            final ResponseCookie responseCookie = ResponseCookie
                    .from(COOKIE_NAME, (String) sessionRes.result.get("hash"))
                    .secure(true)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(12345)
                    .sameSite("Strict")
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
//            Cookie cook = new Cookie(COOKIE_NAME, (String) sessionRes.result.get("hash"));
//            cook.setHttpOnly(true);
//            cook.setPath("/");
//            cook.setSecure(true); // TODO true when we use https
//
//            response.addCookie(cook);


            JSONObject target = sessionRes.result.containsKey("user") && sessionRes.result.get("user") != null
                    ? (JSONObject) sessionRes.result.get("user")
                    : null;

            usToReturn = target;
            if(target == null) {
                usToReturn = (new APIResult("login failed", null, true)).toJson();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            else {
                response.setStatus(HttpServletResponse.SC_OK);
            }
//            usToReturn = sessionRes.result.get("user") != null ? (JSONObject) sessionRes.result.get("user") : null;

        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            usToReturn = (JSONObject) us.clone();
            usToReturn.remove("user");
            usToReturn.remove("result");
        }

        return usToReturn;
    }

    private JSONArray getAllSessions() throws IOException, ParseException {
        final String fioleContent = FileHelper.get_instance().readLastLine(_sessionFile);
        final org.json.simple.parser.JSONParser jp = new JSONParser();

//        final String lastData = fioleContent == null || fioleContent.equals("") ? "[]" : ""; // TODO
        final String lastData = fioleContent == null || fioleContent.equals("") ? "[]" : fioleContent;

        try {
            return (JSONArray)jp.parse(lastData);
        } catch (ParseException e) {
            return new JSONArray();
        }
    }

    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @GetMapping("/logout")
    public void logout(@CookieValue(COOKIE_NAME) String fooCookie, HttpServletResponse response) throws IOException, ParseException, NoSuchAlgorithmException {

        System.out.println(fooCookie);
        response.addHeader("Access-Control-Allow-Credentials", "true");

        JSONArray allSessions = getAllSessions();
        if(allSessions == null || allSessions.size() == 0) {
            // TODO
        }

        // check if hash exists
        JSONObject session = getSessionToken(fooCookie, allSessions);
        final boolean hashExists = session != null;

        if(!hashExists) {
            response.setStatus(418);
            return;
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        Cookie cook = new Cookie(COOKIE_NAME, fooCookie);
        cook.setHttpOnly(true);
        cook.setPath("/");
        cook.setSecure(true);
        cook.setMaxAge(0);
        response.addCookie(cook);

        // remove it
        allSessions.remove(session);

        // write session file
        FileHelper.get_instance().writeFile(_sessionFile, allSessions.toString());

            // no
                // return

        //        // TODO get userID
//        int userID = this.checkUserID(headers);
//
//        if(!headers.containsKey("Cookie")) {
//            return;
//        }
//
//        String
//
////        org.json.simple.parser.JSONParser jp = new JSONParser();
////        org.json.simple.JSONObject o = (org.json.simple.JSONObject)jp.parse(res);
////
////        final String pass = (String) o.get("password"),
////                userName = (String) o.get("username");
////
////        org.json.simple.JSONObject sessionRes = doSessionLogin(pass, userName);
////        if(sessionRes == null) {
////            // return error 401
////            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////            return;
////        }
////
////        FileHelper.get_instance().writeFile(_sessionFile, sessionRes.toString());
////
////        // add session cookie
////        response.addCookie(new Cookie(COOKIE_NAME, (String) sessionRes.get("hash")));
////        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private JSONObject getSessionToken(String fooCookie, JSONArray allSessions) {
        return (JSONObject)allSessions.stream().filter(o -> ((JSONObject)o).get("hash").equals(fooCookie))
                .findFirst().orElse(null);
    }

    class APIResult {
        String message;
        JSONObject result;
        boolean hasError;

        APIResult(String msg, JSONObject res, boolean hasError) {
            message = msg;
            result = res;
            this.hasError = hasError;
        }

        public JSONObject toJson() {
            final JSONObject o = new JSONObject();
            o.put("message", message);
            o.put("result", result);
            o.put("hasError", hasError);
            return o;
        }
    }

    public APIResult doSessionLogin(final String pass, final String userName, final String all) throws IOException, ParseException, NoSuchAlgorithmException {
        // check user exist
        org.json.simple.JSONObject targetUser = getUser(pass, userName, all);

        // no
        if(targetUser == null) {
//            JSONObject r = new JSONObject();
//            r.put("data", all);
            return new APIResult("Wrong pass or username.", null, true);
        }

//        this._currentUser = targetUser;

        // yes
        // make hash
        final String timeStamp = new Date().toString(),
                userHash = ND5Helper.hashForSession(pass, userName, timeStamp);

        if(userHash == null || userHash.equals("")) {
            return new APIResult("Couldn't create hash.", null, true);
        }

        // store hash + userID
        org.json.simple.JSONObject sessionRes = new org.json.simple.JSONObject();
        //noinspection unchecked
        sessionRes.put("hash", userHash);
        //noinspection unchecked
        sessionRes.put("username", userName);
        sessionRes.put("user", targetUser);

        return new APIResult("", sessionRes, false);
    }

    private APIResult doSessionLogin(String pass, String userName) throws IOException, ParseException, NoSuchAlgorithmException {
        return doSessionLogin(pass, userName, getAllObj());
    }

    public JSONObject getUser(final String pass, final String userName) throws IOException, ParseException, NoSuchAlgorithmException {
        return getUser(pass, userName, getAllObj());
    }

    public JSONObject getUser(final String pass, final String userName, final String all) throws IOException, ParseException, NoSuchAlgorithmException {
        final JSONParser jp = new JSONParser();
        JSONArray arr = (JSONArray)jp.parse(all);
        System.out.println("arr length: " + arr.size());

        final String hashPass = pass != null ? ND5Helper.hash(pass) : null;
        System.out.println("hashPass: " + hashPass);

        Optional<JSONObject> targetUser0 = arr.stream()
                .filter(o1 -> ((JSONObject)o1).get("type").equals("user")
                        && (((JSONObject)o1).get("username")).equals(userName))
                .findFirst();
        System.out.println("targetUser0 present: " + targetUser0.isPresent());

        Optional<JSONObject> targetUser = arr.stream()
                .filter(o1 -> ((JSONObject)o1).get("type").equals("user")
                        && (((JSONObject)o1).get("username")).equals(userName)
                        && (hashPass == null || (((JSONObject)o1).get("password")).equals(hashPass)))
                .findFirst();

        return targetUser.orElse(null);
    }

    private JSONObject checkUser(final String fooCookie) {
//        int userID = -2;
        try {
            JSONArray allSessions = getAllSessions();
            if(allSessions == null || allSessions.size() == 0) {
                return null;
            }

            // check if hash exists
            JSONObject session = getSessionToken(fooCookie, allSessions);
            final boolean hashExists = session != null;

            if(!hashExists) {
                return null;
            }

            // get user from cooke
            final String username = (String) ((JSONObject)session).get("username");
            final JSONObject user = getUser(null, username);

            return user;
        }
        catch (Exception e) {
            System.out.println("No user_id specified in request");
            return null;
        }
    }

    protected int checkUserID(final String fooCookie) {
        int userID = -2;
        try {
            final JSONObject user = checkUser(fooCookie);

            // get user ID
            userID = Integer.parseInt(((JSONObject)user).get("id").toString());
        }
        catch (Exception e) {
            System.out.println("No user_id specified in request");
        }

        return userID;
    }

    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @PostMapping("/register")
    public void register(@RequestBody String res, HttpServletResponse response) throws IOException, ParseException, NoSuchAlgorithmException {
        if (response != null) {
            response.addHeader("Access-Control-Allow-Credentials", "true");
        }

        final JSONArray arr = this.updateStuffSafe(-2, res);
        if (arr == null) {
            if (response != null) {
                response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
            return;
        }
//        final JSONArray arr = this.updateStuff(-2, "{}");
        _service.addData(arr);

        if (response != null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @PostMapping("/saveOne")
    public JSONObject saveOne(@CookieValue(COOKIE_NAME) String fooCookie, @RequestHeader Map<String, String> headers, @RequestBody String res, HttpServletResponse response) throws IOException, ParseException, NoSuchAlgorithmException
//    public JSONObject saveOne(@RequestHeader Map<String, String> headers, @RequestBody String res, HttpServletResponse response) throws IOException, ParseException, NoSuchAlgorithmException
    {
        response.addHeader("Access-Control-Allow-Credentials", "true");
//        final String fooCookie = getCookieFromHeaders(headers);
        final int userID = this.checkUserID(fooCookie);
//        final int userID = 1;

        if(userID < 0) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new JSONObject();
        }

        final JSONArray arr = this.updateStuffSafe(userID, res);
        if(arr == null) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            return new JSONObject();
        }

        final String obj = handleRes(_service.addData(arr));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        System.out.println("HTTP returning " + obj);

        // TODO handle parse errors
		final org.json.simple.parser.JSONParser jp = new JSONParser();
		final JSONObject o = obj == null
            ? null
            : (org.json.simple.JSONObject)jp.parse(obj);

        return o;
    }

    private String handleRes(final String addData) {
        // TODO handle this in service
        return addData.equals("") ? "{}" : addData;
    }

    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @PostMapping("/invite")
    public void invite(@CookieValue(COOKIE_NAME) String fooCookie, @RequestHeader Map<String, String> headers,
                       @RequestBody String res, HttpServletResponse response) throws IOException, ParseException {
        final int userID = this.checkUserID(fooCookie);
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if(userID < 0) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        final JSONArray arr = this.doInvite(userID, res);
//        FileHelper.get_instance().writeFile(_dbFile, arr.toString());
        if(arr == null) {
            System.out.println("invite error: target - res");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        _service.addData(arr);

        // checking invite is present
        final JSONParser jp = new JSONParser();
        final JSONObject o = (JSONObject)jp.parse(res);
        final String email = (String)o.get("email");
        JSONObject targetUser = (JSONObject) (arr.stream()
                .filter(o1 -> ((JSONObject)o1).get("type").equals("user")
                        && (((JSONObject)o1).get("email")).equals(email))
                .findFirst().orElse(null));

        JSONArray invites = targetUser.containsKey("invites") ? (JSONArray)targetUser.get("invites") : new JSONArray();

        final String testingTripID = String.valueOf(o.get("tripID"));

        final boolean testingHasTripID = (invites.stream().filter(o1 -> {
            return String.valueOf(((JSONObject)o1).get("tripID")).equals(testingTripID);
        }).findFirst().orElse(null)) != null;

        if(!testingHasTripID) {
            System.out.println("invite error");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }


    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @PostMapping("/generateLink")
    public JSONObject generateLink(@CookieValue(COOKIE_NAME) String fooCookie, @RequestBody String res, HttpServletResponse response) throws IOException, ParseException {
        final int userID = this.checkUserID(fooCookie);
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if(userID < 0) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        final String linkBit = this.doGenerateLink(userID, res);
        final String link = this.getFullLink(linkBit);

        JSONObject resu = new JSONObject();
        resu.put("link", link);

        response.setStatus(HttpServletResponse.SC_OK);
        return resu;
    }

    public String getFullLink(String linkBit) {
        return String.format("%s/api/join?link=%s", HOST_IP, linkBit);
    }

    @CrossOrigin(origins = {"http://86.18.16.122:8080", "https://86.18.16.122:8083", HOST_IP})
    @PostMapping("/join")
    public void join(@RequestParam String link, @CookieValue(COOKIE_NAME) String fooCookie, HttpServletResponse response) throws Exception {
        final int userID = this.checkUserID(fooCookie);
        response.addHeader("Access-Control-Allow-Credentials", "true");

        if(userID < 0) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        final JSONArray dataAll = this.joinFromLink(userID, link);
        if(dataAll == null) {
            Logger.getAnonymousLogger().warning("Error while joining link");
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
        else
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    public JSONArray joinFromLink(final int userID, final String link) throws Exception {
        // decode link
        final InviteLinkData ild = InviteLinkHelper.parseLink(link);

        if(ild == null) {
            return null;
        }

        JSONObject res = new JSONObject();
        res.put("email", ""); // current user's email
        res.put("userID", userID); // current user's ID (extra)
        res.put("tripID", ild.getTravelID());

        // add accepted invite
        final JSONArray arr = this.doInvite(ild.getUserID(), res.toJSONString());

        return arr;
    }

    // FIXME should be private but need to test
    public String doGenerateLink(int userID, String res) {
        // TODO check user exists?
        final JSONParser jp = new JSONParser();
        final JSONObject o;
        try {
            o = (JSONObject)jp.parse(res);
        } catch (ParseException e) {
            Logger.getAnonymousLogger().warning("Error: " + e.getMessage());
            return null;
        }

        try {
            return InviteLinkHelper.generateLink(new InviteLinkData(userID, Integer.parseInt(String.valueOf((Long)o.get("tripId")))));
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning("Error: " + e.getMessage());
            return null;
        }
    }

    public JSONArray doInvite(int userID, String res) throws ParseException, IOException {
        return doInvite(userID, res, getAllObj());
    }

    public JSONArray doInvite(int userID, String res, final String all) throws ParseException, IOException {
        if (userID < 0) {
            System.out.println("REM -- No user ID specified");
            return null;
        }

        final JSONParser jp = new JSONParser();
        final JSONObject o = (JSONObject)jp.parse(res);

        final int tripID = Integer.parseInt(String.valueOf(o.get("tripID")));
        final String email = (String)o.get("email");

        final boolean hasUserID = o.containsKey("userID"); // should only be added from join link
        final Long oUserID = hasUserID ? (Long)o.get("userID") : null;

//        final String all = getAllObj();
        JSONArray arr = (JSONArray)jp.parse(all);

        Optional<JSONObject> targetUserOp = arr.stream()
                .filter(o1 -> ((JSONObject)o1).get("type").equals("user")
                        && (
                                (((JSONObject)o1).get("email")).equals(email) || (hasUserID && ((Long)((JSONObject)o1).get("id")).equals(oUserID))
                        ))
                .findFirst();

        if(!targetUserOp.isPresent()) {
            System.out.println("REM -- Couldn't find target iser in DB");
            return null;
        }

        JSONObject targetUser = targetUserOp.get();

//        JSONArray invites = (JSONArray)targetUser.get().get("invites");
        if(!targetUser.containsKey("invites")) {
            targetUser.put("invites", new JSONArray());
        }
        JSONArray invites = (JSONArray)targetUser.get("invites");

        Optional<JSONObject> existingInvite = invites.stream()
                .filter(o1 -> intEquals(((JSONObject)o1).get("tripID"), tripID))
                .findFirst();

        if(existingInvite.isPresent()) {
            System.out.println("REM -- invite already present");
            return null;
        }

        Optional<JSONObject> targetTrip = arr.stream().filter(o1 -> {
            JSONObject o1Obj = (JSONObject)o1;
            return o1Obj.get("type").toString().equals("travel")
                    && intEquals(o1Obj.get("id"), tripID);
        }).findFirst();

        if(!targetTrip.isPresent()) {
            System.out.println("REM -- Couldn't find target trip");
            return null;
        }

        JSONObject newInvite = new JSONObject();
        newInvite.put("isAccepted", hasUserID);
        newInvite.put("tripID", tripID);
        newInvite.put("tripName", targetTrip.get().get("name"));

        invites.add(newInvite);

        if(!targetUser.containsKey("updating")) {
            targetUser.put("updating", true);
        }

        return arr;
    }

    public JSONArray updateStuffSafe(int usserID, String res) throws IOException, ParseException, NoSuchAlgorithmException {
        // TODO get userID
        final String all = getAllObj();
////        org.json.simple.parser.JSONParser jp = new JSONParser();
        try {
            return this.updateStuff(usserID, res, all);
        } catch (Exception e) {
            System.out.println("Parse exception on update.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Returning null");
            return null;
        }
    }

    public JSONArray updateStuff(int usserID, String res) throws IOException, ParseException, NoSuchAlgorithmException {
        // TODO get userID
        final String all = getAllObj();
////        org.json.simple.parser.JSONParser jp = new JSONParser();
        return this.updateStuff(usserID, res, all);
    }

    public JSONArray updateStuff(int usserID, String res, String all) throws ParseException, NoSuchAlgorithmException {
        org.json.simple.parser.JSONParser jp = new JSONParser();
        org.json.simple.JSONObject objectToUpdateOrAdd = (org.json.simple.JSONObject)jp.parse(res);

        org.json.simple.JSONArray allObjects = (org.json.simple.JSONArray)jp.parse(all);

        final JSONObject user0 = SaveController.getUserFromAll(all, usserID);
        final JSONObject user = usserID == -2 ? null : SaveController.getUserFromAll(allObjects, usserID);

        if(user == null) {
            if(usserID != -2) {
                // TODO throw error ?
                return null;
            }

            if(!objectToUpdateOrAdd.containsKey("type") || !objectToUpdateOrAdd.get("type").equals("user")) {
                return null;
            }

            // adding a user
            if(!objectToUpdateOrAdd.containsKey("username") || !objectToUpdateOrAdd.containsKey("password")
                    || this.hasSpecialChars((String)objectToUpdateOrAdd.get("username"))
                    || this.hasSpecialChars((String)objectToUpdateOrAdd.get("password"))) {
                return null;
            }

//            final int userID = (int)Integer.parseInt(objectToUpdateOrAdd.get("id").toString());
            final String userEmail = (String)objectToUpdateOrAdd.get("email");

            // check email
            final UserChecker checker = new UserChecker(allObjects);
            if (!checker.checkEmailValid(userEmail) || !checker.checkEmailUnused(userEmail) || !checker.checkUsernameUnused((String)objectToUpdateOrAdd.get("username"))) {
                return null;
            }

            Object[] allUsers = allObjects.stream().filter(o1 -> ((JSONObject)o1).containsKey("type") && ((JSONObject)o1).get("type").equals("user"))
//                    .map(o -> (JSONObject)o)
                    .toArray();

            // get max id
//            final int userID = (int)Arrays.stream(allUsers).map(o -> Integer.parseInt((String)((JSONObject)o).get("id")))
//                    .sorted(Comparator.comparingInt(o -> (Integer) o))
//                    .findFirst()
//                    .orElse(null);

//            final List<Integer> userIDs = Arrays.stream(allUsers).map(o -> Integer.parseInt((String)((JSONObject)o).get("id")))
//                    .sorted(Comparator.comparingInt(o -> (Integer) o))
//                    .collect(Collectors.toList());
//
//            final int userID = userIDs.get(0);

            int maxID = 0;
            for(Object oo: allUsers) {
                JSONObject o = (JSONObject)oo;
                Long idLong = (Long) (o.get("id"));
                int id0 = (int)Integer.parseInt(idLong.toString());
                if(id0 > maxID) {
                    maxID = id0;
                }
            }

            final int userID = maxID + 1;

            objectToUpdateOrAdd.replace("id", userID);


            final boolean exists = Arrays.stream(allUsers)
                    .filter(o1 -> intEquals(((JSONObject)o1).get("id"), userID)
                        || ( ((JSONObject)o1).containsKey("email") && ((JSONObject)o1).get("email").equals(userEmail)))
                    .findFirst().isPresent();

            if(exists) {
                return null;
            }

            // hash pass
            final String clearPass = objectToUpdateOrAdd.containsKey("password")
                ? (String)(((JSONObject)objectToUpdateOrAdd).get("password"))
                : null;

            if (clearPass != null) {
                final String md5Pass = ND5Helper.hash(clearPass);
                objectToUpdateOrAdd.replace("password", md5Pass);
                objectToUpdateOrAdd.remove("password1");
            }

//            allObjects.add(objectToUpdateOrAdd);
//            return allObjects;
        }
        else
        {
//        Object[] myIds = ((JSONArray)user.get("invites")).stream().map(o1 -> ((JSONObject)o1).get("tripID")).toArray(Object[]::new);
            JSONArray myInvites = user.containsKey("invites") ? (JSONArray)user.get("invites") : new JSONArray();
//        List<Object> myIds = myInvites.stream().map(o1 -> ((JSONObject)o1).get("tripID")).collect(Collectors.toList());
            List<String> myIds = (List<String>) myInvites.stream().map(o1 -> String.valueOf(((JSONObject)o1).get("tripID"))).distinct().collect(Collectors.toList());

            int targetIndex = -1;
            for(int i = 0; i < allObjects.size(); ++i) {
                JSONObject jo = (JSONObject) allObjects.get(i);
    //            AItem iiii = (AItem)JsonConvert.DeserializeObject<AItem>(jo.toString());
    //            JsonPath jpp = new JsonPath(jo.toString());
    //            AItem aa = jpp.getObject(".", AItem.class);

                if(!jo.containsKey("id") || !jo.containsKey("type")) {
                    continue;
                }

                if(!intEquals(jo.get("id"), objectToUpdateOrAdd.get("id")) || !intEquals(jo.get("type"), objectToUpdateOrAdd.get("type"))) {
                    continue;
                }

                // check if user's travel
                if(jo.get("type").equals("travel") && !myIds.contains(String.valueOf(jo.get("id").toString()))) {
                    continue;
                }

                // check if user's expense
                if(jo.get("type").equals("expense") && !myIds.contains(String.valueOf(jo.get("tripId").toString()))) {
                    continue;
                }

                // check if user
                if(jo.get("type").equals("user") && !intEquals(jo.get("id"), usserID)) {
                    continue;
                }

                targetIndex = i;
                break;
            }

            if (targetIndex > -1) {
                JSONObject target = ((JSONObject) allObjects.get(targetIndex));
                if(target.containsKey("password")) {
                    final String pass = (String)(target.get("password"));
                    objectToUpdateOrAdd.replace("password", "", pass);
                }
                ((JSONArray)allObjects).remove(targetIndex) ;
            }
        }

        objectToUpdateOrAdd.put("updating", true);
        allObjects.add(objectToUpdateOrAdd);

        return allObjects;
    }

    private boolean hasSpecialChars(String data) {
        Pattern pattern = Pattern.compile("[^\\dA-Za-z ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        boolean matchFound = matcher.find();

        return matchFound;
    }

    private static JSONObject getUserFromAll(final String jsonStr, final int usserID) throws ParseException {
        org.json.simple.parser.JSONParser jp = new JSONParser();
        org.json.simple.JSONArray o = (org.json.simple.JSONArray)jp.parse(jsonStr);
//        org.json.simple.JSONArray arr = (org.json.simple.JSONArray)jp.parse(all);

        return getUserFromAll(o, usserID);
    }

    private boolean intEquals(Object a, Object b) {
        return a.toString().equals(b.toString());
    }

    public static JSONObject getUserFromAll(final JSONArray o, final int usserID) {
        JSONObject user = null;
        for(int i = 0; i < o.size(); ++i) {
            JSONObject jo = (JSONObject) o.get(i);
            if(!jo.containsKey("id") || !jo.containsKey("type") || !jo.get("type").equals("user")) {
                continue;
            }

            if((usserID == -1 || jo.get("id").toString().equals(String.valueOf(usserID))) && jo.get("type").equals("user")) {
                user = jo;
                break;
            }
        }

        return user;
    }

    public static JSONObject getFirstUser(final JSONArray o) {
        return getUserFromAll(o, -1);
    }

}
