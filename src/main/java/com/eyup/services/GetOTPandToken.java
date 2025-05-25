package com.eyup.services;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GetOTPandToken {
//    public static void main(String[] args) {
//        }
    public static String getOTPCode() {
        RestAssured.baseURI = "https://api.amazon.com";

        String requestBody = "{"
                + "\"requested_extensions\":[\"device_info\",\"customer_info\"],"
                + "\"cookies\":{\"website_cookies\":[{\"Value\":\"\\\"oFxVhvFtX+bRRX3uNQ3+fQ7qqHjIIHhFJFaZ4qJiQ7hk9a60xRzHvG6eZEKfkgQi2eLthg\\/PiN7JaPBJQ8MuR6faoK\\/P4N8V2XTmPht+I5MqqZiQG4w0136XamdHt57rd1j5fgGyNHlS9Iv0bha3OsGJPDw97mf3U4f5MvCB\\/atJHI4bcAiPaD8G5ZibwAm6EvmU9urTQfOcWZfc56kIhkS0qXcfj8BxtGBW2EdvGx4=\\\"\",\"Name\":\"session-token\"},{\"Value\":\"2082787201l\",\"Name\":\"session-id-time\"}],\"domain\":\".amazon.com\"},"
                + "\"registration_data\":{\"domain\":\"Device\",\"app_version\":\"0.0\",\"device_type\":\"A3NWHXTQ4EBCZS\",\"os_version\":\"13.6\",\"device_serial\":\"02BD3E1B2F1A45F59B6D1BBB4A7DD96E\",\"device_model\":\"iPhone\",\"app_name\":\"Amazon Flex\",\"software_version\":\"1\"},"
                + "\"auth_data\":{\"user_id_password\":{\"user_id\":\"meyuptozcu@gmail.com\",\"password\":\"Aa,132639\"}},"
                + "\"user_context_map\":{\"frc\":\"ALUFqY3chE+FrzTY36oUy7br4FtVEOeFHQoYSwtGrOo1DZxSWiRiCAOQZ2u3Z1g69jHGqke3zGxqE9wRSLkA7Bu2wwNkxGMsQVmPg8m2f1nvd6VWvaz64FeTOx0bGeOJ\\/QZ0O0\\/9GJZYyrxEw\\/qH6C9p6Qkv\\/RZQJ0YZAj7X+8GMElgMRgxEI5clQFwFOPoiiT0rHg8481eSTrcFmxhKPYyfuo8PTiUKKyndbGEQ+75EIZE6m+BTSSkg1qIwJLvF8\\/PXMOTNVo469CxwFnya4jy7lRsxvqjCTML\\/NY6dit3E8QC95G54v\\/J4n+0pCMo+tv8JvGuRv8NtVnA+iKABGV7m2lXBNaJpIMljvKCkQ\\/8vt8+bqiAyPCnxm0wTEZML1jj0fxdifa5EvRJvD4XzZ5atTpCB8QQILQ==\"},"
                + "\"requested_token_type\":[\"bearer\",\"mac_dms\",\"website_cookies\"]"
                + "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept-Charset", "utf-8")
                .header("x-amzn-identity-auth-domain", "api.amazon.com")
                .header("Cookie", "session-token=\"7NB8DBsIelxKhiW4xlLcxdBZmmuetu4Vy4D0NOros5f7l4324VkSFkaxekI2pNw9tZeWGpaJQ3+kI6jQ/JqqPvdttPUwbWiB9A+yyTBAm2fa0XVvm7ouC6XlT8BgCd60qQ5AcKuGixEY5mjqUK1nk3brwOpKHelv8vYebUw44PlAdVKdwcCHMFbWMUv9C7RCgTkud7doBpqhoCmSXbWsqfUSWWkiEp9=\"; session-id-time=2082787201l")
                .header("Accept", "application/json")
                .header("User-Agent", "AmazonWebView/Amazon Flex/0.0/iOS/13.6/iPhone")
                .header("Accept-Language", "en-US")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/auth/register")
                .then()
                .statusCode(200)
                .extract()
                .response();
        String token = response.jsonPath().getString("response.success.tokens.bearer.access_token");
        System.out.println(token);
        return token;
    }
    public static Response getAccessToken() {
        RestAssured.baseURI = "https://api.amazon.co.uk";

        RequestSpecification request = RestAssured.given();

        // Setting headers
        request.header("Accept", "application/json");
        request.header("Accept-Charset", "utf-8");
        request.header("x-amzn-identity-auth-domain", "api.amazon.co.uk");
        request.header("Accept-Language", "en-GB");
        request.header("Accept-Encoding", "gzip");
        request.header("Cache-Control", "no-store");
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("User-Agent", "AmazonWebView/Amazon Flex/0.0/iOS/17.6.1/iPhone");
        request.header("Connection", "keep-alive");
        request.header("Cookie", "at-acbuk=\"Atza|IwEBIAk8pINAPRKeogFN6xG3Rij7ZPKtpOhE9qCZUBTYbouxjdNX7_TRO35yT43E1lNbwNi2frq9HEvkssp0PVdJ7cRU3U0gdkdedn5d5MkdoZ_zE98FP4XFG01QMMrCN7aB-KQpBjEYmP2dlOsjuWM8nYQXrhE4dr4euokNy3iTVdnitjgEhl1M0QJZyTv0umgd8g0rzZT9yc4Bt-xBwPBlqkbj8svz56Uw_Gfb__7NBHUiRc5OGAjk8RC5UY4XYJ5tII_d6oINg9TJtLLMCklsktVZW32VJ1vJ6appWAOQQw4znwDuWCsPSJja5GIQhlzVDcxu2sL1Vs2AMc-84MiezEVI\"; sess-at-acbuk=\"4cQQK9/49VA/l2zVryJx9g4x459iyRxMFTwYMeVg6C0=\"; session-id=257-7920022-7247015; session-id-time=2361717876l; session-token=woVBTgF7S8lZ+o8fUQfltgOHVIHK3R/kmjpVPMnVI4xFtjDbQOyGEKTzqK++hm455Uc8AemQqX8zTcMPtxDfQAC6WXzUp9kQLKB/ym2aVlv3t9AQrZ+pHHvF0voflCXAD23H8EpyAS9nmKbf5+AiG8aP6TSoNmAM/E3D7wmldl8eO6vJH+LnWoAmbbQqgaJid58O3Z9g4LN4B0wOvf9OH1O2ZXTszYfo5Y7eDfZpKmCg2uZaYIhcE9d3576BLAnm1ZnpOLwauvittKnhuoY6X3v4Sl3IZs8q+kIme4l5GrJXlga8w2LTRl0UYe1t7pKlIK9EX5AxD5nmwtcqos0VeNwYUq+H1H3CjZMQR9ujOQOZm/+YDO38DjV/mes/heB5; ubid-acbuk=260-9004604-9333051; x-acbuk=\"YtQL?RQJuSRLBoKx0Opm0Yp2HlLK8efA5a9Uf3i0ZTXUWBlOimXaCjxXi@PzBF0E\"; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB");

        // Setting form parameters for the POST request body
        request.formParam("exchange_reason", "miGetATForceRefresh");
        request.formParam("app_name", "Amazon Flex");
        request.formParam("app_version", "0.0");
        request.formParam("di.sdk.version", "6.15.3");
        request.formParam("source_token", "Atnr%7CEwICIPe-FH1HrmagXZRF5M8hN-Xf1taBBcPa5n-OntF4EeL57uD8IxxEp8MOwkmPBrfsH5UjEGaSuEX9mr0tWKDhwVhfsQ33GzRlgcvTCQlqpm4Y5NGb1Wjh8MiRI-xm8esVOKHMuwmuRMQcDTJZqn0tyX7Rsv7fLoGln9V4TVSpocSoRAmz0KTzSWNK-Xi0RdLctVOqUfPrlqMPOEfZkrntw4ezQTI5eDbvohisA8wu9QGpJtuEeX8DuPETzxngL-Hwf8xqdivjfOEB5E6buNobPgEheIN56KbdTiVqqIfme7WPaZLC5fm7UU76i40us5f7eOa0RoSBXXrBn3SMFWf7WPfH");
        request.formParam("package_name", "com.amazon.flex.irabbit");
        request.formParam("di.hw.version", "iPhone");
        request.formParam("platform", "iOS");
        request.formParam("requested_token_type", "access_token");
        request.formParam("source_token_type", "refresh_token");
        request.formParam("di.os.name", "iOS");
        request.formParam("di.os.version", "17.6.1");
        request.formParam("current_version", "6.15.3");
        request.formParam("previous_version", "6.15.3");

        // Making the POST request
        Response response = request.post("/auth/token");
        response.then().statusCode(200);
        String token = response.jsonPath().getString("access_token");
        System.out.println(token);
        // Returning the response
        return response;
    }
}
