package com.eyup.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class _1SignIn {

    public static void main(String[] args) {
        System.out.println(_1signInOTP());
    }
    public static Response _1signInOTP() {
        RestAssured.baseURI = "https://api.amazon.co.uk";

        RequestSpecification request = RestAssured.given();

        // Setting headers
        request.header("Accept", "application/json");
        request.header("Accept-Charset", "utf-8");
        request.header("x-amzn-identity-auth-domain", "api.amazon.co.uk");
        request.header("Accept-Language", "en-GB");
        request.header("Accept-Encoding", "gzip");
        request.header("Cache-Control", "no-store");
        request.header("Content-Type", "application/json");
        request.header("User-Agent", "AmazonWebView/Amazon Flex/0.0/iOS/17.6.1/iPhone");
        request.header("Connection", "keep-alive");
        request.header("Cookie", "session-id-time=2361717876l; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB");

        // Constructing the JSON body
        JSONObject body = new JSONObject();

        body.put("requested_extensions", new String[]{"device_info", "customer_info"});

        JSONObject cookies = new JSONObject();
        cookies.put("website_cookies", new JSONObject[] {
                new JSONObject().put("Value", "2361717876l").put("Name", "session-id-time"),
                new JSONObject().put("Value", "XFJRWTD1K5NP7S2HDY29+s-XFJRWTD1K5NP7S2HDY29|1730916137128").put("Name", "csm-hit"),
                new JSONObject().put("Value", "en_GB").put("Name", "lc-acbgb"),
                new JSONObject().put("Value", "GBP").put("Name", "i18n-prefs"),
                new JSONObject().put("Value", "en_GB").put("Name", "lc-acbuk")
        });
        cookies.put("domain", ".amazon.co.uk");
        body.put("cookies", cookies);

        JSONObject registrationData = new JSONObject();
        registrationData.put("domain", "Device");
        registrationData.put("app_version", "0.0");
        registrationData.put("device_type", "A3NWHXTQ4EBCZS");
        registrationData.put("os_version", "17.6.1");
        registrationData.put("device_serial", "582F192EC946472BAFD4AB83B574E97B");
        registrationData.put("device_model", "iPhone");
        registrationData.put("app_name", "Amazon Flex");
        registrationData.put("software_version", "1");
        body.put("registration_data", registrationData);

        JSONObject authData = new JSONObject();
        JSONObject userIdPassword = new JSONObject();
        userIdPassword.put("user_id", "meyuptozcu@gmail.com");
        userIdPassword.put("password", "Aa,132639");
        authData.put("user_id_password", userIdPassword);
        body.put("auth_data", authData);

        JSONObject userContextMap = new JSONObject();
        userContextMap.put("frc", "AF/RSSnfg0O6011S/FMZ6JlT9+e5+uWdnylBPdlYomjc1SMbyn9+RUaQu/tFcLKuAtQ9O9ECJCnUeVyX76ZvRvv0VGjd28B6kgIMwcitS8Oq27pY7H3C0m/GVUIW6eiiNPb30IHyjy2v5xn5lHzkvkwOIC1P2nfcT+zlj5MO+K6sWpQOsO8edP/+CGMSlSFCGCcgOxO26f5+NYLA9DdUdkfi2LIHHaQwasJKFR2L0yYva+EIYYg8KiGWv+sQ1hKph4xi0b8s+gmQmwyb8BvK4DrzpoRzekC5GVqQGj8QKnGkABAxY4l3RfTRjZA3I2CNrNDbxQA7Ey1nvzbgzGKdS+SGEOor3yRmxr/HN9fsF3BU90El2oPiuo+8SSDCAJ1cjLEdFXrLm0cu");
        body.put("user_context_map", userContextMap);

        body.put("requested_token_type", new String[]{"bearer", "mac_dms", "website_cookies"});

        // Adding JSON body to request
        request.body(body.toString());

        // Sending POST request to /auth/register endpoint
        Response response = request.post("/auth/register");

        response.then().statusCode(401);
        System.out.println(response.jsonPath().prettify());
        // Returning the response
        return response;
    }

    public String _2processOTP() throws Exception {
        String url = "https://www.amazon.co.uk/ap/challenge?openid.return_to=https://www.amazon.co.uk/ap/maplanding&openid.oa2.code_challenge_method=S256&openid.assoc_handle=amzn_device_ios_uk&arb=9ebbba06-b549-4119-9801-d8a8e1b77c2a&pageId=amzn_device_ios_light&accountStatusPolicy=P1&openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&openid.mode=checkid_setup&openid.identity=http://specs.openid.net/auth/2.0/identifier_select&openid.ns.oa2=http://www.amazon.com/ap/ext/oauth/2&openid.oa2.client_id=device:35383246313932454339343634373242414644344142383342353734453937422341334e5748585451344542435a53&language=en_GB&openid.ns.pape=http://specs.openid.net/extensions/pape/1.0&openid.oa2.code_challenge=teGB_lPsgIpzloxssJSGks7oAUS-q6QEPanEkpWg6Oc&openid.oa2.scope=device_auth_access&openid.ns=http://specs.openid.net/auth/2.0&openid.pape.max_auth_age=0&openid.oa2.response_type=code";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // Request setup
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Accept-Language", "en-US");
        connection.setRequestProperty("Cache-Control", "no-store");
        connection.setRequestProperty("Sec-Fetch-Site", "none");
        connection.setRequestProperty("Sec-Fetch-Mode", "navigate");
        connection.setRequestProperty("Accept-Encoding", "gzip");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");

        // Add cookies
        connection.setRequestProperty("Cookie", "frc=AF6Prl2iaDfvePZ2k2gl1oCZmtk0A5WbRPz9rIs9af7cqn16xfhx3ntab9G+HbYTCnW6hkGYnPt3rgXeHTEjeSP+CLR8oIOw2R2m1lXhV5NTfVAdGSi26tIKYT4eNB57QAUJFECq+5WQo7cpm3W0xazaOO9nlyYzBlmmq9YKTMotSZtNV3++/WXdmSyCDgHXC2sKzUtr42zBJ/XpvsF5wTDug+21BHjf0DR97kq+IRqyI7V/9A4xHYC12nEHzV3tffbqVMYex0y6o10ZsMEysx2DolGhpnYFybgK/TIjqENLQh3wiOr1BvCkyEqlrg29KP2NUplWrVfBBvuaPVQDS5O6Gj109oMv3Qq9FeNI9hMUDLmSA/FwYRfssY6Zw6ovS09AzxEhPuvY; map-md=eyJkZXZpY2VfdXNlcl9kaWN0aW9uYXJ5IjpbXSwiZGV2aWNlX3JlZ2lzdHJhdGlvbl9kYXRhIjp7InNvZnR3YXJlX3ZlcnNpb24iOiIxIn0sImFwcF9pZGVudGlmaWVyIjp7ImFwcF92ZXJzaW9uIjoiMi4xMzguMiIsImJ1bmRsZV9pZCI6ImNvbS5hbWF6b24uZmxleC5pcmFiYml0In19; session-id-time=2361717876l; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB");

        // Fetching the response
        int responseCode = connection.getResponseCode();  // 302
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();  // Return the response as a string
        } else {
            return "GET request failed. HTTP Code: " + responseCode;
        }
    }


    public String _3sendOTP() throws Exception {
        String url = "https://www.amazon.co.uk/ap/mfa/262-1801103-4992132?ie=UTF8&arb=55ff2ca4-e8ec-4b57-a3a1-6b3ff1ee0168&mfa.arb.value=55ff2ca4-e8ec-4b57-a3a1-6b3ff1ee0168&mfa.arb.key=arb";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // Request setup
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Accept-Language", "en-US");
        connection.setRequestProperty("Cache-Control", "no-store");
        connection.setRequestProperty("Sec-Fetch-Site", "none");
        connection.setRequestProperty("Sec-Fetch-Mode", "navigate");
        connection.setRequestProperty("Accept-Encoding", "gzip");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");

        // Add cookies
        connection.setRequestProperty("Cookie", "frc=AF6Prl2iaDfvePZ2k2gl1oCZmtk0A5WbRPz9rIs9af7cqn16xfhx3ntab9G+HbYTCnW6hkGYnPt3rgXeHTEjeSP+CLR8oIOw2R2m1lXhV5NTfVAdGSi26tIKYT4eNB57QAUJFECq+5WQo7cpm3W0xazaOO9nlyYzBlmmq9YKTMotSZtNV3++/WXdmSyCDgHXC2sKzUtr42zBJ/XpvsF5wTDug+21BHjf0DR97kq+IRqyI7V/9A4xHYC12nEHzV3tffbqVMYex0y6o10ZsMEysx2DolGhpnYFybgK/TIjqENLQh3wiOr1BvCkyEqlrg29KP2NUplWrVfBBvuaPVQDS5O6Gj109oMv3Qq9FeNI9hMUDLmSA/FwYRfssY6Zw6ovS09AzxEhPuvY; map-md=eyJkZXZpY2VfdXNlcl9kaWN0aW9uYXJ5IjpbXSwiZGV2aWNlX3JlZ2lzdHJhdGlvbl9kYXRhIjp7InNvZnR3YXJlX3ZlcnNpb24iOiIxIn0sImFwcF9pZGVudGlmaWVyIjp7ImFwcF92ZXJzaW9uIjoiMi4xMzguMiIsImJ1bmRsZV9pZCI6ImNvbS5hbWF6b24uZmxleC5pcmFiYml0In19; session-id=262-1801103-4992132; session-id-time=2361757335l; JSESSIONID=2BE4117F58D1FEE27480C8458AE7DB5A; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB");

        // Fetching the response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();  // Return the response as a string
        } else {
            return "GET request failed. HTTP Code: " + responseCode;
        }
    }

    public String _4runAuthenticator() throws Exception {
        String url = "https://static.siege-amazon.com/prod/profiles/AuthenticationPortalSigninEU.js";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // Request setup
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Sec-Fetch-Site", "cross-site");
        connection.setRequestProperty("Sec-Fetch-Dest", "script");
        connection.setRequestProperty("Accept-Language", "en-GB,en;q=0.9");
        connection.setRequestProperty("Sec-Fetch-Mode", "no-cors");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");
        connection.setRequestProperty("Referer", "https://www.amazon.co.uk/");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");

        // Handling the response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();  // Return the response as a string
        } else {
            return "GET request failed. HTTP Code: " + responseCode;
        }
    }

    public String _5getUnagi(String payload) throws Exception {
        String url = "https://unagi.amazon.co.uk/1/events/com.amazon.csm.csa.prod";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // Set up POST request
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Host", "unagi.amazon.co.uk");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Sec-Fetch-Site", "same-site");
        connection.setRequestProperty("Accept-Language", "en-GB,en;q=0.9");
        connection.setRequestProperty("Cache-Control", "max-age=0");
        connection.setRequestProperty("Sec-Fetch-Mode", "no-cors");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        connection.setRequestProperty("Origin", "https://www.amazon.co.uk");
        connection.setRequestProperty("Content-Length", "7902");  // Should match payload length
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");
        connection.setRequestProperty("Referer", "https://www.amazon.co.uk/");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
        connection.setRequestProperty("Sec-Fetch-Dest", "empty");
        connection.setRequestProperty("Cookie", "session-id=262-1801103-4992132; session-id-time=2361757335l; ubid-acbuk=259-4221793-7275200; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB");

        // Enable input/output streams
        connection.setDoOutput(true);

        // Write payload to output stream
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = payload.getBytes("UTF-8");
            os.write(input, 0, input.length);
        }

        // Handle response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "POST request sent successfully.";
        } else {
            return "POST request failed. HTTP Code: " + responseCode;
        }
    }

    public String _6getAuthRegistered(String jsonPayload) throws Exception {
        String url = "https://api.amazon.co.uk/auth/register";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // Set up POST request
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Host", "api.amazon.co.uk");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("x-amzn-identity-auth-domain", "api.amazon.co.uk");
        connection.setRequestProperty("Accept-Language", "en-GB");
        connection.setRequestProperty("Accept-Encoding", "gzip");
        connection.setRequestProperty("Cache-Control", "no-store");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", String.valueOf(jsonPayload.length()));
        connection.setRequestProperty("User-Agent", "AmazonWebView/Amazon Flex/0.0/iOS/17.6.1/iPhone");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Cookie", "session-id-time=2361717876l; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB");

        // Enable output to send JSON payload
        connection.setDoOutput(true);

        // Write JSON payload to output stream
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("UTF-8");
            os.write(input, 0, input.length);
        }

        // Handle response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "POST request sent successfully.";
        } else {
            return "POST request failed. HTTP Code: " + responseCode;
        }
    }
}
