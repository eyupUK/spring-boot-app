package com.eyup.services;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.logging.Logger;

public class GetAuthorizationHeader {

    private static final Logger logger = Logger.getLogger(GetAuthorizationHeader.class.getName());

    public static void main(String[] args) {
        try {
            String access = "ASIA5SXGB3FOWABQYGX7";
            String secret = "+ZCqXkIg2caWgXwSBlOpx2US58fKaTtOfeQR8se7";
            String session = "IQoJb3JpZ2luX2VjEDkaCXVzLWVhc3QtMSJIMEYCIQCJVe51XXSxVkN9K6J6Aln2flWJyr7g/Mv1kAkiJBXITAIhAMk0m4YmCs37/hCZfjTaVswORjZDSBjI47353dJ/+TyeKrkFCML//////////wEQBBoMOTMzNTY0MTExMTk3Igzi6I2agRXY3PSULT4qjQU759esqItQhAlxOTidpZhtqTSguMl+rXy0kQaeaTO0HUSEjVCzSQonPTtrYkNGmGsyp7Wk40OJI24JUyEHDkIIy0xneJJVPWOACWsSkqJefl1eu4bvZSM9kafu1WNuZ62o4m3UU1zNoB1oOKY8UvdrH93v9/fw0Q1z3vgt49TsZ1ubk35rjLRRYQvYCLQLjqwSXQDFkXlHxQ0LyKK4ATW8+uHZscIs3HlMXJKPwuGq3/Ibhgdjp6my5CmK4oTu8C+8H01hv7XMfjTdPrwuyg5J5/CMkXGJkb1d9rgO4Zxzr0LtnG3mTi5MNPofB6BEDHFR/vOkwTtZXb9ebjkEmEh3BAizRu9+aXlBPuj1Qn0ecq4FXYxeYAdXkPbLdokPlkMAgmwD/Wdk925KdVvdfOSIyV3QvR07F2YTjav3RXeWWJxV0VsFPlET2BdztrtYuPgHwaMpjXEpBBjoKuCaF3NAnqwqDXFpGflHCOGti9COuSTA7lL8SUqjy0aA36EHy0uOztmYWZUHyDH0VgSfGndPthTWf4d7/8N380xvpJc2LqooAe+ZD87+E3os7D/n3p4qct34CR0Hh2ehF94LwNBf7K6T6TQADOmIw4NzW0fwGvzuwbbOHQTyLjVC37VNBJwcbGds/ql2+5dO6yNP01Y0bqg5Lgv5dIe8K1Cas/Cz3OfQzE03xIlO1ZP5aaefq4owFIOW82AMyLo3LSQmLrvaR6hAC3mJLLzf+0ru9eXobZKcSJi04F+bGY1/0ecooH5vuJQiXTVQvIMYOEVCjIuyheeJjtof73ZxQasEouNouivHvuRgcQeg40ArvoU+Udk3mk0//zHW0h/UBo9PURinYaDY+HXPybV3NX7XxDDfgs65BjrdApTN1AwUGpq69WRs52CNbwhahpLwkBcvjJrZuTvcLkrQpeFmylezGwyLTgHZd8Jso+6E+81QuLKEustEvIpih6oKjMDvC2pXKY4gIPW9YTqF22QO59AVPfQTNfp+PkTXpZ184N39Fvaa3iez2hT/mAb0K4Y+lon4JR1s3RrUohNX+YuW+bG4E64wM5SjFDKPSgpGX983a+8vNM5nENEEVY6ZC0SUi6NHrjg/LdXoFqkXjTi21YeDDDPdonrqUEcxCto417oALaUhVjjF4RldZIaexhncUzogUeQNSAi34UqrOFd1IsJKeA0XwTmY2FHIcKfYAMD65c8fi4IUz1Nz8fsw7oO3ONwNwSaIAKJuiIYZc+e4YMGQKz4Mi1/MYUsO56R2Guu/n39dCqF5xXIQt/wU0ri2OADQB+9nhZ+5TLL5RXX+8znnUw3ir9LIJRNTMFjkT5o6HWMP+wyxGW0=";
            String payload = "{\"IdentityId\":\"us-east-1:84f7e3d9-2c79-462d-a684-d24699ba3c73\"}";
            System.out.println(generateAuthorizationHeader(access, secret, session, payload));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateAuthorizationHeader(String accessKeyId, String secretKey, String sessionToken, String payload) throws Exception {
        String dateStamp = "20241112"; // YYYYMMDD
        String regionName = "us-east-1";
        String serviceName = "firehose";
        String dateTimeStamp = "20241112T162503Z";

        // Step 1: Create canonical request
        String canonicalRequest = createCanonicalRequest(payload, dateTimeStamp);
        logger.info("Canonical Request: " + canonicalRequest);

        // Step 2: Create string to sign
        String stringToSign = createStringToSign(canonicalRequest, dateTimeStamp, dateStamp, regionName, serviceName);
        logger.info("String to Sign: " + stringToSign);

        // Step 3: Calculate the signature
        byte[] signingKey = getSignatureKey(secretKey, dateStamp, regionName, serviceName);
        String signature = toHex(hmacSHA256(signingKey, stringToSign));
        logger.info("Generated Signature: " + signature);
        logger.info("Expected Signature: 6ed527c1fa4228e1e56ae4b96da03a9f46ece5d98c0758b1aea8ef09549b2073");

        // Step 4: Build the Authorization header
        return buildAuthorizationHeader(accessKeyId, dateStamp, regionName, serviceName, signature, sessionToken);
    }

    private static String createCanonicalRequest(String payload, String dateTimeStamp) throws Exception {
        String hashedPayload = hash(payload);
        return "POST\n" +
                "/\n" +
                "\n" +
                "accept:*/*\n" +
                "accept-encoding:gzip, deflate, br\n" +
                "accept-language:en-GB,en;q=0.9\n" +
                "content-length:63\n" +
                "content-type:application/x-amz-json-1.1\n" +
                "host:cognito-identity.us-east-1.amazonaws.com\n" +
                "user-agent:aws-sdk-iOS/2.15.1 iOS/17.6.1 en_GB\n" +
                "x-amz-date:" + dateTimeStamp + "\n" +
                "x-amz-target:AWSCognitoIdentityService.GetCredentialsForIdentity\n" +
                "accept;accept-encoding;accept-language;content-length;content-type;host;user-agent;x-amz-date;x-amz-target\n" +
                hashedPayload;
    }

    private static String createStringToSign(String canonicalRequest, String dateTimeStamp, String dateStamp, String regionName, String serviceName) throws Exception {
        String hashedCanonicalRequest = hash(canonicalRequest);
        return "AWS4-HMAC-SHA256\n" +
                dateTimeStamp + "\n" +
                dateStamp + "/" + regionName + "/" + serviceName + "/aws4_request\n" +
                hashedCanonicalRequest;
    }

    private static byte[] getSignatureKey(String secretKey, String dateStamp, String regionName, String serviceName) throws Exception {
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
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    private static String buildAuthorizationHeader(String accessKeyId, String dateStamp, String regionName, String serviceName, String signature, String sessionToken) {
        return "AWS4-HMAC-SHA256 " +
                "Credential=" + accessKeyId + "/" + dateStamp + "/" + regionName + "/" + serviceName + "/aws4_request, " +
                "SignedHeaders=accept;accept-encoding;accept-language;content-length;content-type;host;user-agent;x-amz-date;x-amz-target, " +
                "Signature=" + signature;
    }
}