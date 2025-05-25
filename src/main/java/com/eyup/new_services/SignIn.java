package com.eyup.new_services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.eyup.utilities.TimestampConverter.convertToUnixTimestamp;
import static io.restassured.RestAssured.given;

public class SignIn {
    static String sessionTime;
    static Response response;

//    public static void main(String[] args) {
//        System.out.println(signIn("meyuptozcu@gmail.com", "Aa,132639"));
//    }
    public static String signIn(String email, String password) {
        String uri = _1signIn(email, password);
        String pageOTP = _2signIn(uri);
        return pageOTP;
    }

    private static String _1signIn(String email, String password) {
        // Base URI
        RestAssured.baseURI = "https://api.amazon.co.uk";
        sessionTime = convertToUnixTimestamp();
        System.out.println("sessionTime: "+sessionTime);
        // Headers
        RequestSpecification request = given()
                .header("Accept", "application/json")
                .header("Accept-Charset", "utf-8")
                .header("x-amzn-identity-auth-domain", "api.amazon.co.uk")
                .header("Accept-Language", "en-GB")
                .header("Accept-Encoding", "gzip")
                .header("Cache-Control", "no-store")
                .header("Content-Type", "application/json")
                .header("User-Agent", "AmazonWebView/Amazon Flex/0.0/iOS/17.6.1/iPhone")
                .header("Connection", "keep-alive")
                .header("Cookie", "session-id-time="+sessionTime+"; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB");

        // Request body
        String requestBody = "{\"requested_extensions\":[\"device_info\",\"customer_info\"],\"cookies\":{\"website_cookies\":[{\"Value\":\""+sessionTime+"\",\"Name\":\"session-id-time\"},{\"Value\":\"XFJRWTD1K5NP7S2HDY29+s-XFJRWTD1K5NP7S2HDY29|1730916137128\",\"Name\":\"csm-hit\"},{\"Value\":\"en_GB\",\"Name\":\"lc-acbgb\"},{\"Value\":\"GBP\",\"Name\":\"i18n-prefs\"},{\"Value\":\"en_GB\",\"Name\":\"lc-acbuk\"}],\"domain\":\".amazon.co.uk\"},\"registration_data\":{\"domain\":\"Device\",\"app_version\":\"0.0\",\"device_type\":\"A3NWHXTQ4EBCZS\",\"os_version\":\"17.6.1\",\"device_serial\":\"582F192EC946472BAFD4AB83B574E97B\",\"device_model\":\"iPhone\",\"app_name\":\"Amazon Flex\",\"software_version\":\"1\"},\"auth_data\":{\"user_id_password\":{\"user_id\":\""+email+"\",\"password\":\""+password+"\"}},\"user_context_map\":{\"frc\":\"AF\\/RSSnfg0O6011S\\/FMZ6JlT9+e5+uWdnylBPdlYomjc1SMbyn9+RUaQu\\/tFcLKuAtQ9O9ECJCnUeVyX76ZvRvv0VGjd28B6kgIMwcitS8Oq27pY7H3C0m\\/GVUIW6eiiNPb30IHyjy2v5xn5lHzkvkwOIC1P2nfcT+zlj5MO+K6sWpQOsO8edP\\/+CGMSlSFCGCcgOxO26f5+NYLA9DdUdkfi2LIHHaQwasJKFR2L0yYva+EIYYg8KiGWv+sQ1hKph4xi0b8s+gmQmwyb8BvK4DrzpoRzekC5GVqQGj8QKnGkABAxY4l3RfTRjZA3I2CNrNDbxQA7Ey1nvzbgzGKdS+SGEOor3yRmxr\\/HN9fsF3BU90El2oPiuo+8SSDCAJ1cjLEdFXrLm0cu\"},\"requested_token_type\":[\"bearer\",\"mac_dms\",\"website_cookies\"]}";

        // Sending the POST request
        response = request.body(requestBody)
                .post("/auth/register");

        System.out.println("1.status code: "+response.getStatusCode());
        // Return the response for further validation
        return response.jsonPath().getString("response.challenge.uri");
    }

    private static String _2signIn(String uri) {
        // Base URI for Rest Assured
        RestAssured.baseURI = "https://www.amazon.co.uk";
        String arb = uri.split("arb=")[1];
        System.out.println("arb: "+arb);
        // Perform the GET request
        Response response = given()
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("accept-charset", "utf-8")
                .header("accept-language", "en-US")
                .header("cache-control", "no-store")
                .header("sec-fetch-site", "none")
                .header("sec-fetch-mode", "navigate")
                .header("accept-encoding", "gzip")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
                .header("cookie", "frc=AF6Prl2iaDfvePZ2k2gl1oCZmtk0A5WbRPz9rIs9af7cqn16xfhx3ntab9G+HbYTCnW6hkGYnPt3rgXeHTEjeSP+CLR8oIOw2R2m1lXhV5NTfVAdGSi26tIKYT4eNB57QAUJFECq+5WQo7cpm3W0xazaOO9nlyYzBlmmq9YKTMotSZtNV3++/WXdmSyCDgHXC2sKzUtr42zBJ/XpvsF5wTDug+21BHjf0DR97kq+IRqyI7V/9A4xHYC12nEHzV3tffbqVMYex0y6o10ZsMEysx2DolGhpnYFybgK/TIjqENLQh3wiOr1BvCkyEqlrg29KP2NUplWrVfBBvuaPVQDS5O6Gj109oMv3Qq9FeNI9hMUDLmSA/FwYRfssY6Zw6ovS09AzxEhPuvY; map-md=eyJkZXZpY2VfdXNlcl9kaWN0aW9uYXJ5IjpbXSwiZGV2aWNlX3JlZ2lzdHJhdGlvbl9kYXRhIjp7InNvZnR3YXJlX3ZlcnNpb24iOiIxIn0sImFwcF9pZGVudGlmaWVyIjp7ImFwcF92ZXJzaW9uIjoiMi4xMzguMiIsImJ1bmRsZV9pZCI6ImNvbS5hbWF6b24uZmxleC5pcmFiYml0In19; session-id-time="+sessionTime+"; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB")
                .queryParam("openid.return_to", "https://www.amazon.co.uk/ap/maplanding")
                .queryParam("openid.oa2.code_challenge_method", "S256")
                .queryParam("openid.assoc_handle", "amzn_device_ios_uk")
                .queryParam("arb", arb)
                .queryParam("pageId", "amzn_device_ios_light")
                .queryParam("accountStatusPolicy", "P1")
                .queryParam("openid.claimed_id", "http://specs.openid.net/auth/2.0/identifier_select")
                .queryParam("openid.mode", "checkid_setup")
                .queryParam("openid.identity", "http://specs.openid.net/auth/2.0/identifier_select")
                .queryParam("openid.ns.oa2", "http://www.amazon.com/ap/ext/oauth/2")
                .queryParam("openid.oa2.client_id", "device:35383246313932454339343634373242414644344142383342353734453937422341334e5748585451344542435a53")
                .queryParam("language", "en_GB")
                .queryParam("openid.ns.pape", "http://specs.openid.net/extensions/pape/1.0")
                .queryParam("openid.oa2.code_challenge", "teGB_lPsgIpzloxssJSGks7oAUS-q6QEPanEkpWg6Oc")
                .queryParam("openid.oa2.scope", "device_auth_access")
                .queryParam("openid.ns", "http://specs.openid.net/auth/2.0")
                .queryParam("openid.pape.max_auth_age", "0")
                .queryParam("openid.oa2.response_type", "code")
                .when()
                .get("/ap/challenge");


        sessionTime = convertToUnixTimestamp();
        System.out.println("sessionTime: "+sessionTime);
        System.out.println("2.status code: "+response.getStatusCode());
        // Return the response
        //response.prettyPrint();
        return response.body().asString();
    }



    public static String _3signIn(String location) {
        String sessionId = location.split("mfa/")[1].split("?")[0];
        String arb = location.split("arb=")[1].split("&mfa")[0];
        System.out.println("sessionId: "+sessionId);
        System.out.println("arb: "+arb);

        RestAssured.baseURI = "https://www.amazon.co.uk";

        // Perform the GET request
        response = given()
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("accept-charset", "utf-8")
                .header("sec-fetch-site", "none")
                .header("accept-language", "en-US")
                .header("cache-control", "no-store")
                .header("sec-fetch-mode", "navigate")
                .header("accept-encoding", "gzip")
                .header("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_6_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
                .header("sec-fetch-dest", "document")
                .header("cookie", "frc=AF6Prl2iaDfvePZ2k2gl1oCZmtk0A5WbRPz9rIs9af7cqn16xfhx3ntab9G+HbYTCnW6hkGYnPt3rgXeHTEjeSP+CLR8oIOw2R2m1lXhV5NTfVAdGSi26tIKYT4eNB57QAUJFECq+5WQo7cpm3W0xazaOO9nlyYzBlmmq9YKTMotSZtNV3++/WXdmSyCDgHXC2sKzUtr42zBJ/XpvsF5wTDug+21BHjf0DR97kq+IRqyI7V/9A4xHYC12nEHzV3tffbqVMYex0y6o10ZsMEysx2DolGhpnYFybgK/TIjqENLQh3wiOr1BvCkyEqlrg29KP2NUplWrVfBBvuaPVQDS5O6Gj109oMv3Qq9FeNI9hMUDLmSA/FwYRfssY6Zw6ovS09AzxEhPuvY; map-md=eyJkZXZpY2VfdXNlcl9kaWN0aW9uYXJ5IjpbXSwiZGV2aWNlX3JlZ2lzdHJhdGlvbl9kYXRhIjp7InNvZnR3YXJlX3ZlcnNpb24iOiIxIn0sImFwcF9pZGVudGlmaWVyIjp7ImFwcF92ZXJzaW9uIjoiMi4xMzguMiIsImJ1bmRsZV9pZCI6ImNvbS5hbWF6b24uZmxleC5pcmFiYml0In19; session-id="+sessionId+"; session-id-time="+sessionTime+"; JSESSIONID=2BE4117F58D1FEE27480C8458AE7DB5A; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB")
                .queryParam("ie", "UTF8")
                .queryParam("arb", arb)
                .queryParam("mfa.arb.value", arb)
                .queryParam("mfa.arb.key", "arb")
                .when()
                .get("/ap/mfa/"+sessionId);

        return response.body().asString();
    }
}
