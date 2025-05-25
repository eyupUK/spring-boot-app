package com.eyup.services;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import org.junit.jupiter.api.*;

public class AWSSigner {
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String AWS4_HMAC_SHA256 = "AWS4-HMAC-SHA256";
    private static final String SERVICE = "cognito-identity";
    private static final String REGION = "us-east-1";

    static String access = "ASIA5SXGB3FOWABQYGX7";
    static String secret = "+ZCqXkIg2caWgXwSBlOpx2US58fKaTtOfeQR8se7";
    static String session = "IQoJb3JpZ2luX2VjEDkaCXVzLWVhc3QtMSJIMEYCIQCJVe51XXSxVkN9K6J6Aln2flWJyr7g/Mv1kAkiJBXITAIhAMk0m4YmCs37/hCZfjTaVswORjZDSBjI47353dJ/+TyeKrkFCML//////////wEQBBoMOTMzNTY0MTExMTk3Igzi6I2agRXY3PSULT4qjQU759esqItQhAlxOTidpZhtqTSguMl+rXy0kQaeaTO0HUSEjVCzSQonPTtrYkNGmGsyp7Wk40OJI24JUyEHDkIIy0xneJJVPWOACWsSkqJefl1eu4bvZSM9kafu1WNuZ62o4m3UU1zNoB1oOKY8UvdrH93v9/fw0Q1z3vgt49TsZ1ubk35rjLRRYQvYCLQLjqwSXQDFkXlHxQ0LyKK4ATW8+uHZscIs3HlMXJKPwuGq3/Ibhgdjp6my5CmK4oTu8C+8H01hv7XMfjTdPrwuyg5J5/CMkXGJkb1d9rgO4Zxzr0LtnG3mTi5MNPofB6BEDHFR/vOkwTtZXb9ebjkEmEh3BAizRu9+aXlBPuj1Qn0ecq4FXYxeYAdXkPbLdokPlkMAgmwD/Wdk925KdVvdfOSIyV3QvR07F2YTjav3RXeWWJxV0VsFPlET2BdztrtYuPgHwaMpjXEpBBjoKuCaF3NAnqwqDXFpGflHCOGti9COuSTA7lL8SUqjy0aA36EHy0uOztmYWZUHyDH0VgSfGndPthTWf4d7/8N380xvpJc2LqooAe+ZD87+E3os7D/n3p4qct34CR0Hh2ehF94LwNBf7K6T6TQADOmIw4NzW0fwGvzuwbbOHQTyLjVC37VNBJwcbGds/ql2+5dO6yNP01Y0bqg5Lgv5dIe8K1Cas/Cz3OfQzE03xIlO1ZP5aaefq4owFIOW82AMyLo3LSQmLrvaR6hAC3mJLLzf+0ru9eXobZKcSJi04F+bGY1/0ecooH5vuJQiXTVQvIMYOEVCjIuyheeJjtof73ZxQasEouNouivHvuRgcQeg40ArvoU+Udk3mk0//zHW0h/UBo9PURinYaDY+HXPybV3NX7XxDDfgs65BjrdApTN1AwUGpq69WRs52CNbwhahpLwkBcvjJrZuTvcLkrQpeFmylezGwyLTgHZd8Jso+6E+81QuLKEustEvIpih6oKjMDvC2pXKY4gIPW9YTqF22QO59AVPfQTNfp+PkTXpZ184N39Fvaa3iez2hT/mAb0K4Y+lon4JR1s3RrUohNX+YuW+bG4E64wM5SjFDKPSgpGX983a+8vNM5nENEEVY6ZC0SUi6NHrjg/LdXoFqkXjTi21YeDDDPdonrqUEcxCto417oALaUhVjjF4RldZIaexhncUzogUeQNSAi34UqrOFd1IsJKeA0XwTmY2FHIcKfYAMD65c8fi4IUz1Nz8fsw7oO3ONwNwSaIAKJuiIYZc+e4YMGQKz4Mi1/MYUsO56R2Guu/n39dCqF5xXIQt/wU0ri2OADQB+9nhZ+5TLL5RXX+8znnUw3ir9LIJRNTMFjkT5o6HWMP+wyxGW0=";



    public static void main(String[] args) throws Exception {
        // content-encoding;content-type;host;user-agent;x-amz-date;x- amz-security-token;x-amz-target
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        headers.put("accept-encoding", "gzip, deflate, br");
        headers.put("content-type", "application/x-amz-json-1.1");
        headers.put("host", "application/x-amz-json-1.1");
        headers.put("user-agent", "aws-sdk-iOS/2.15.1 iOS/17.6.1 en_GB");
        headers.put("x-amz-date", "20241112T162503Z");
        headers.put("x-amz-target", "AWSCognitoIdentityService.GetCredentialsForIdentity");


        //headers.put("authority", "cognito-identity.us-east-1.amazonaws.com");


        String payload = "{\"IdentityId\":\"us-east-1:84f7e3d9-2c79-462d-a684-d24699ba3c73\"}";

        String authorizationHeader = AWSSigner.generateAuthorizationHeader(
                access,
                secret,
                session,
                "POST",
                "/",
                "",
                headers,
                payload
        );

        System.out.println("Authorization Header: " + authorizationHeader);
    }

    public static String generateAuthorizationHeader(
            String accessKeyId,
            String secretKey,
            String sessionToken,
            String method,
            String uri,
            String queryString,
            LinkedHashMap<String, String> headers,
            String payload) throws Exception {

        // Step 1: Create Canonical Request
        String canonicalRequest = createCanonicalRequest(method, uri, queryString, headers, payload);
        //System.out.println("Canonical Request: " + canonicalRequest);

        // Step 2: Create String to Sign
        String dateTime = (String) headers.get("x-amz-date");
        String date = dateTime.substring(0, 8); // Extract yyyyMMdd
        String credentialScope = String.format("%s/%s/%s/aws4_request", date, REGION, SERVICE);
        //System.out.println("Credential Scope: " + credentialScope);
        String stringToSign = createStringToSign(dateTime, credentialScope, canonicalRequest);
        System.out.println("String to Sign: \n" + stringToSign);

        // Step 3: Calculate Signature
        byte[] signingKey = getSigningKey(secretKey, date, REGION, SERVICE);
        String signature = calculateSignature(signingKey, stringToSign);
        Assertions.assertEquals("6ed527c1fa4228e1e56ae4b96da03a9f46ece5d98c0758b1aea8ef09549b2073", signature, "Signature does not match");

        // Step 4: Assemble Authorization Header
        String signedHeaders = getSignedHeaders(headers);
        Assertions.assertEquals("content-encoding;content-type;host;user-agent;x-amz-date;x- amz-security-token;x-amz-target", signedHeaders, "Signed headers do not match");
        String authorizationHeader = String.format("%s Credential=%s/%s, SignedHeaders=%s, Signature=%s",
                AWS4_HMAC_SHA256, accessKeyId, credentialScope, signedHeaders, signature);

        if (sessionToken != null && !sessionToken.isEmpty()) {
            headers.put("x-amz-security-token", sessionToken);
        }

        return authorizationHeader;
    }

    private static String createCanonicalRequest(
            String method,
            String uri,
            String queryString,
            LinkedHashMap<String, String> headers,
            String payload) throws Exception {

        String canonicalUri = uri;
        String canonicalQueryString = queryString != null ? queryString : "";
        String canonicalHeaders = getCanonicalHeaders(headers);
        String signedHeaders = getSignedHeaders(headers);
        String payloadHash = hash(payload);

        return String.join("\n", method, canonicalUri, canonicalQueryString, canonicalHeaders, signedHeaders, payloadHash);
    }

    private static String createStringToSign(String dateTime, String credentialScope, String canonicalRequest) throws Exception {
        String canonicalRequestHash = hash(canonicalRequest);
        return String.join("\n", AWS4_HMAC_SHA256, dateTime, credentialScope, canonicalRequestHash);
    }

    private static byte[] getSigningKey(String secretKey, String date, String region, String service) throws Exception {
        byte[] kDate = hmac(("AWS4" + secretKey).getBytes(StandardCharsets.UTF_8), date);
        byte[] kRegion = hmac(kDate, region);
        byte[] kService = hmac(kRegion, service);
        return hmac(kService, "aws4_request");
    }

    private static String calculateSignature(byte[] signingKey, String stringToSign) throws Exception {
        byte[] signature = hmac(signingKey, stringToSign);
        return bytesToHex(signature);
    }

    private static String getCanonicalHeaders(LinkedHashMap<String, String> headers) {
        StringBuilder canonicalHeaders = new StringBuilder();
        headers.forEach((key, value) -> canonicalHeaders.append(key.toLowerCase()).append(":").append(value).append("\n"));
        return canonicalHeaders.toString();
    }

    private static String getSignedHeaders(LinkedHashMap<String, String> headers) {
        List<String> sortedKeys = new ArrayList<>(headers.keySet());
        sortedKeys.sort(String::compareTo);
        return String.join(";", sortedKeys.stream().map(String::toLowerCase).toList());
    }

    private static String hash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashed = md.digest(input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashed);
    }

    private static byte[] hmac(byte[] key, String data) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(new SecretKeySpec(key, HMAC_ALGORITHM));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}

