package com.eyup.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class _1SignIn {
    private static final Logger logger = Logger.getLogger(_1SignIn.class.getName());
    static {
        try {
            // Configure the logger with handler and formatter
            FileHandler fileHandler = new FileHandler("myapp.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize logger handler.", e);
        }
    }








    public static void main(String[] args) {

        try {
            //_2processOTP();
            _3sendOTP();
            _4runAuthenticator();
            _5getUnagi();
//            _6getAuthRegistered();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Response _1signInOTP() {
        /**
         * POST /auth/register HTTP/1.1
         * Host: api.amazon.co.uk
         * Accept: application/json
         * Accept-Charset: utf-8
         * x-amzn-identity-auth-domain: api.amazon.co.uk
         * Accept-Language: en-GB
         * Accept-Encoding: gzip
         * Cache-Control: no-store
         * Content-Type: application/json
         * Content-Length: 1178
         * User-Agent: AmazonWebView/Amazon Flex/0.0/iOS/17.6.1/iPhone
         * Connection: keep-alive
         * Cookie: session-id-time=2361717876l; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB
         */

        RestAssured.baseURI = "https://api.amazon.co.uk";
        String jsonPayload = "";
        try {
            jsonPayload = new String(Files.readAllBytes(Paths.get("/Users/imac/Downloads/demo/src/main/java/com/eyup/dataresources/_1requestOTP.json")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read JSON file");
        }

        Response response = RestAssured.given()
                .header("Host", "api.amazon.co.uk")
                .header("Accept", "application/json")
                .header("Accept-Charset", "utf-8")
                .header("x-amzn-identity-auth-domain", "api.amazon.co.uk")
                .header("Accept-Language", "en-GB")
                .header("Accept-Encoding", "gzip")
                .header("Cache-Control", "no-store")
                .header("Content-Type", "application/json")
                .header("User-Agent", "AmazonWebView/Amazon Flex/0.0/iOS/17.6.1/iPhone")
                .header("Connection", "keep-alive")
//                .header("Cookie", "session-id-time=2361717876l; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB")
                .body(jsonPayload)
                .post("/auth/register");

        response.then().statusCode(401);
        logger.info(response.jsonPath().getString("response.challenge.uri"));
        System.out.println(response.jsonPath().prettify());
        // Returning the response
        return response;
    }

    public static Response _2processOTP() throws Exception {
        String path = _1signInOTP().jsonPath().getString("response.challenge.uri").split("=")[1];
        String url = "https://www.amazon.co.uk/ap/challenge?openid.return_to=https://www.amazon.co.uk/ap/maplanding&openid.oa2.code_challenge_method=S256&openid.assoc_handle=amzn_device_ios_uk&arb=" + path + "&pageId=amzn_device_ios_light&accountStatusPolicy=P1&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&openid.mode=checkid_setup&openid.identity=http://specs.openid.net/auth/2.0/identifier_select&openid.ns.oa2=http://www.amazon.com/ap/ext/oauth/2&openid.oa2.client_id=device:35383246313932454339343634373242414644344142383342353734453937422341334e5748585451344542435a53&language=en_GB&openid.ns.pape=http://specs.openid.net/extensions/pape/1.0&openid.oa2.code_challenge=teGB_lPsgIpzloxssJSGks7oAUS-q6QEPanEkpWg6Oc&openid.oa2.scope=device_auth_access&openid.ns=http://specs.openid.net/auth/2.0&openid.pape.max_auth_age=0&openid.oa2.response_type=code";

        Response response = RestAssured.given()
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Charset", "utf-8")
                .header("Accept-Language", "en-US")
                .header("Cache-Control", "no-store")
                .header("Sec-Fetch-Site", "none")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Accept-Encoding", "gzip")
                .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
                .header("Cookie", "frc=AF6Prl2iaDfvePZ2k2gl1oCZmtk0A5WbRPz9rIs9af7cqn16xfhx3ntab9G+HbYTCnW6hkGYnPt3rgXeHTEjeSP+CLR8oIOw2R2m1lXhV5NTfVAdGSi26tIKYT4eNB57QAUJFECq+5WQo7cpm3W0xazaOO9nlyYzBlmmq9YKTMotSZtNV3++/WXdmSyCDgHXC2sKzUtr42zBJ/XpvsF5wTDug+21BHjf0DR97kq+IRqyI7V/9A4xHYC12nEHzV3tffbqVMYex0y6o10ZsMEysx2DolGhpnYFybgK/TIjqENLQh3wiOr1BvCkyEqlrg29KP2NUplWrVfBBvuaPVQDS5O6Gj109oMv3Qq9FeNI9hMUDLmSA/FwYRfssY6Zw6ovS09AzxEhPuvY; map-md=eyJkZXZpY2VfdXNlcl9kaWN0aW9uYXJ5IjpbXSwiZGV2aWNlX3JlZ2lzdHJhdGlvbl9kYXRhIjp7InNvZnR3YXJlX3ZlcnNpb24iOiIxIn0sImFwcF9pZGVudGlmaWVyIjp7ImFwcF92ZXJzaW9uIjoiMi4xMzguMiIsImJ1bmRsZV9pZCI6ImNvbS5hbWF6b24uZmxleC5pcmFiYml0In19; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB")
                .get(url);

        if (response.getStatusCode() == 302) {
            System.out.println(response.getBody().asString());
        } else {
            System.out.println(response.getBody().asString());
            System.out.println("GET request failed. HTTP Code: " + response.getStatusCode());
        }
        return response;
    }


    public static Response _3sendOTP() throws Exception {
        String url = _2processOTP().getHeader("location");
        logger.info("Redirect URL: " + url);

        Response response = RestAssured.given()
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Charset", "utf-8")
                .header("Accept-Language", "en-US")
                .header("Cache-Control", "no-store")
                .header("Sec-Fetch-Site", "none")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Accept-Encoding", "gzip")
                .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
                .header("Cookie", "frc=AF6Prl2iaDfvePZ2k2gl1oCZmtk0A5WbRPz9rIs9af7cqn16xfhx3ntab9G+HbYTCnW6hkGYnPt3rgXeHTEjeSP+CLR8oIOw2R2m1lXhV5NTfVAdGSi26tIKYT4eNB57QAUJFECq+5WQo7cpm3W0xazaOO9nlyYzBlmmq9YKTMotSZtNV3++/WXdmSyCDgHXC2sKzUtr42zBJ/XpvsF5wTDug+21BHjf0DR97kq+IRqyI7V/9A4xHYC12nEHzV3tffbqVMYex0y6o10ZsMEysx2DolGhpnYFybgK/TIjqENLQh3wiOr1BvCkyEqlrg29KP2NUplWrVfBBvuaPVQDS5O6Gj109oMv3Qq9FeNI9hMUDLmSA/FwYRfssY6Zw6ovS09AzxEhPuvY; map-md=eyJkZXZpY2VfdXNlcl9kaWN0aW9uYXJ5IjpbXSwiZGV2aWNlX3JlZ2lzdHJhdGlvbl9kYXRhIjp7InNvZnR3YXJlX3ZlcnNpb24iOiIxIn0sImFwcF9pZGVudGlmaWVyIjp7ImFwcF92ZXJzaW9uIjoiMi4xMzguMiIsImJ1bmRsZV9pZCI6ImNvbS5hbWF6b24uZmxleC5pcmFiYml0In19; session-id=262-1801103-4992132; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB")
                .get(url);

        if (response.getStatusCode() == 200) {
            System.out.println(response.getBody().asString());
        } else {
            System.out.println(response.getBody().asString());
            System.out.println("GET request failed. HTTP Code: " + response.getStatusCode());
        }
        return response;
    }

    public static Response _4runAuthenticator() {
        String url = "https://static.siege-amazon.com/prod/profiles/AuthenticationPortalSigninEU.js";

        Response response = RestAssured.given()
                .header("Accept", "*/*")
                .header("Sec-Fetch-Site", "cross-site")
                .header("Sec-Fetch-Dest", "script")
                .header("Accept-Language", "en-GB,en;q=0.9")
                .header("Sec-Fetch-Mode", "no-cors")
                .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
                .header("Referer", "https://www.amazon.co.uk/")
                .header("Accept-Encoding", "gzip, deflate, br")
                .get(url);

        if (response.getStatusCode() == 200) {
            System.out.println(response.getBody().asString());
        } else {
            System.out.println("GET request failed. HTTP Code: " + response.getStatusCode());
        }
        return response;
    }

public static Response _5getUnagi() {
    String url = "https://unagi.amazon.co.uk/1/events/com.amazon.csm.csa.prod";
    String jsonPayload = "";
    try {
        jsonPayload = new String(Files.readAllBytes(Paths.get("/Users/imac/Downloads/demo/src/main/java/com/eyup/dataresources/_5unagiPayload.json")));
    } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to read JSON file");
    }
    Response response = RestAssured.given()
            .header("Host", "unagi.amazon.co.uk")
            .header("Accept", "*/*")
            .header("Sec-Fetch-Site", "same-site")
            .header("Accept-Language", "en-GB,en;q=0.9")
            .header("Cache-Control", "max-age=0")
            .header("Sec-Fetch-Mode", "no-cors")
            .header("Accept-Encoding", "gzip, deflate, br")
            .header("Origin", "https://www.amazon.co.uk")
//            .header("Content-Length", "7902")  // Should match payload length
            .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
            .header("Referer", "https://www.amazon.co.uk/")
            .header("Connection", "keep-alive")
            .header("Content-Type", "text/plain;charset=UTF-8")
            .header("Sec-Fetch-Dest", "empty")
            .header("Cookie", "session-id=262-1801103-4992132; session-id-time=2361757335l; ubid-acbuk=259-4221793-7275200; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB")
            .body(jsonPayload)
            .post(url);

    if (response.getStatusCode() == 200) {
        return response;
    } else {
        throw new RuntimeException("POST request failed. HTTP Code: " + response.getStatusCode());
    }
}

public static Response _6getAuthRegistered() {
    String url = "https://api.amazon.co.uk/auth/register";
    String jsonPayload = "";

    try {
        jsonPayload = new String(Files.readAllBytes(Paths.get("/Users/imac/Downloads/demo/src/main/java/com/eyup/dataresources/_6bodyAuthRegister.json")));
    } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to read JSON file");
    }

    Response response = RestAssured.given()
            .header("Host", "api.amazon.co.uk")
            .header("Accept", "application/json")
            .header("Accept-Charset", "utf-8")
            .header("x-amzn-identity-auth-domain", "api.amazon.co.uk")
            .header("Accept-Language", "en-GB")
            .header("Accept-Encoding", "gzip")
            .header("Cache-Control", "no-store")
            .header("Content-Type", "application/json")
            .header("User-Agent", "AmazonWebView/Amazon Flex/0.0/iOS/17.6.1/iPhone")
            .header("Connection", "keep-alive")
            .header("Cookie", "session-id-time=2361717876l; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB")
            .body(jsonPayload)
            .post(url);

    if (response.getStatusCode() == 200) {
        String bearerToken = response.jsonPath().getString("response.success.tokens.bearer.access_token");
        logger.info("Bearer Token: " + bearerToken);
        String refreshToken = response.jsonPath().getString("response.success.tokens.bearer.refresh_token");
        logger.info("Refresh Token: " + refreshToken);
        return response;
    } else {
        logger.severe("POST request failed. HTTP Code: " + response.getStatusCode());
        throw new RuntimeException("POST request failed. HTTP Code: " + response.getStatusCode());
    }
}
}
