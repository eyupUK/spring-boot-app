package com.eyup.services;

import io.restassured.RestAssured;

import io.restassured.response.Response;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class _0PreSignIn {
    private static final Logger logger = Logger.getLogger(_0PreSignIn.class.getName());


    static  String _0SessionToken;
    static String _0AccessKeyId;
    static String _0SecretKey;
    static String _0IdentityId;
    static Response response;
    static  String dateStamp ="20241112";
    static String dateTimeStamp;


    public static Response getCognito() {
        // Set the base URI for the request
        RestAssured.baseURI = "https://cognito-identity.us-east-1.amazonaws.com";

        // Define the request body
        String requestBody = "{\"IdentityId\":\"us-east-1:84f7e3d9-2c79-462d-a684-d24699ba3c73\"}";
        dateTimeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));

        // Send the POST request and capture the response
        response = RestAssured
                .given()
                .contentType("application/x-amz-json-1.1")
                .accept("*/*")
                .header("x-amz-target", "AWSCognitoIdentityService.GetCredentialsForIdentity")
                .header("accept-language", "en-GB,en;q=0.9")
                .header("x-amz-date", dateTimeStamp)
                .header("user-agent", "aws-sdk-iOS/2.15.1 iOS/17.6.1 en_GB")
                .header("accept-encoding", "gzip, deflate, br")
                .body(requestBody)
                .post("/");

        response.then().statusCode(200);

        _0AccessKeyId = response.jsonPath().getString("Credentials.AccessKeyId");
        _0SecretKey = response.jsonPath().getString("Credentials.SecretKey");
        _0SessionToken = response.jsonPath().getString("Credentials.SessionToken");
        _0IdentityId = response.jsonPath().getString("IdentityId");


        // Return the response for further processing
        return response;
    }

    public static Response postFireHose() throws Exception {
        String responseBody = getCognito().body().asString();
        String url = "https://firehose.us-east-1.amazonaws.com";
        dateStamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String signature = ""; //generateSignature(_0AccessKeyId, _0SecretKey);
        String authorization = generateSignature(_0AccessKeyId, _0SecretKey, responseBody);// "AWS4-HMAC-SHA256 Credential=" + _0AccessKeyId + "/"+dateStamp+"/us-east-1/firehose/aws4_request, SignedHeaders=content-encoding;content-type;host;user-agent;x-amz-date;x-amz-security-token;x-amz-target, Signature="+signature;

        Response response = RestAssured.given()
                .header("Host", "firehose.us-east-1.amazonaws.com")
                .header("Accept", "*/*")
                .header("Authorization", authorization)
                .header("Content-Encoding", "gzip")
                .header("X-Amz-Date", dateTimeStamp)
                .header("X-Amz-Security-Token", _0SessionToken)
                .header("Accept-Language", "en-GB,en;q=0.9")
                .header("Content-Type", "application/x-amz-json-1.1")
                .header("Accept-Encoding", "gzip, deflate, br")
//                .header("Content-Length", "3985")
                .header("User-Agent", "aws-sdk-iOS/2.15.1 iOS/17.6.1 en_GB")
                .header("Connection", "keep-alive")
                .header("X-Amz-Target", "Firehose_20150804.PutRecordBatch")
                .post(url);

        response.then().statusCode(200);

        return response;
    }

    static String generateSignature(String accessKeyId, String secretKey, String payload) throws Exception {
        String dateStamp = "20241112"; // YYYYMMDD
        String regionName = "us-east-1";
        String serviceName = "firehose";

        // Step 1: Format the date in the required AWS signature format
        String date = "20241112T162503Z"; // LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));

        // Step 2: Create canonical request
        String hashedPayload = hash(payload);
        System.out.println(hashedPayload);
        String canonicalRequest = "POST\n" +
                "/\n" +
                "\n" +
                "accept:*/*\n" +
                "accept-encoding:gzip, deflate, br\n" +
                "accept-language:en-GB,en;q=0.9\n" +
                "content-length:63\n" +
                "content-type:application/x-amz-json-1.1\n" +
                "host:cognito-identity.us-east-1.amazonaws.com\n" +
                "user-agent:aws-sdk-iOS/2.15.1 iOS/17.6.1 en_GB\n" +
                "x-amz-date:20241112T162503Z\n" +
                "x-amz-target:AWSCognitoIdentityService.GetCredentialsForIdentity\n" +
                "accept;accept-encoding;accept-language;content-length;content-type;host;user-agent;x-amz-date;x-amz-target\n" +
                hashedPayload;
        //logger.info("Canonical Request: " + canonicalRequest);

        //System.out.println(hash(canonicalRequest));
        // Step 3: String to sign
        String stringToSign = "AWS4-HMAC-SHA256\n" + date + "\n" + "us-east-1/firehose/aws4_request\n" + canonicalRequest;
        //logger.info("String to Sign: " + stringToSign);

        // Step 4: Signing key
        byte[] signingKey = getSignatureKey(secretKey, dateStamp, regionName, serviceName);

        // Step 5: Calculate the signature
        String signature = generateSignature(stringToSign, signingKey);
        //logger.info("Signature: " + signature);
        assertEquals(signature, "6ed527c1fa4228e1e56ae4b96da03a9f46ece5d98c0758b1aea8ef09549b2073", "signatures not equal");

        // Build the Authorization header
        String authorizationHeader = "AWS4-HMAC-SHA256 " +
                "Credential=" + accessKeyId + "/"+dateStamp+"/us-east-1/firehose/aws4_request, " +
                "SignedHeaders=accept;accept-encoding;accept-language;content-length;content-type;host;user-agent;x-amz-date;x-amz-target, Signature=" + signature;

        return authorizationHeader;
    }

    public static byte[] getSignatureKey(String secretKey, String dateStamp, String regionName, String serviceName) throws NoSuchAlgorithmException, InvalidKeyException, Exception {
        byte[] kSecret = ("AWS4" + secretKey).getBytes(StandardCharsets.UTF_8);
        byte[] kDate = hmacSHA256(kSecret, dateStamp);
        byte[] kRegion = hmacSHA256(kDate, regionName);
        byte[] kService = hmacSHA256(kRegion, serviceName);
        return hmacSHA256(kService, "aws4_request");
    }

    private static byte[] hmacSHA256(byte[] key, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String hash(String payload) throws Exception {
        try {
            // Get a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hash on the input string
            byte[] hashBytes = digest.digest(payload.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String generateSignature(String stringToSign, byte[] signingKey) throws NoSuchAlgorithmException, InvalidKeyException, Exception {
        byte[] signature = hmacSHA256(signingKey,stringToSign);
        StringBuilder hexString = new StringBuilder();
        for (byte b : signature) {
            hexString.append(String.format("%02x", b & 0xff));
        }
        return hexString.toString();
    }
}

