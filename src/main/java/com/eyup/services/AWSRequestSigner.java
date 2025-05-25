package com.eyup.services;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AWSRequestSigner {
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String AWS4_SIGNING_KEY = "AWS4";
    private static final String ALGORITHM = "AWS4-HMAC-SHA256";

    private static String accessKey = "ASIA5SXGB3FOWABQYGX7";
    private static String secretKey = "+ZCqXkIg2caWgXwSBlOpx2US58fKaTtOfeQR8se7";
    private static String sessionToken;

    static {
        try {
            sessionToken = readPayloadFromFile("/Users/imac/Downloads/demo/src/main/java/com/eyup/services/sessionToken.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws Exception {
        String httpMethod = "POST";
        String service= "firehose";
        String region = "us-east-1";
        String endpoint = "firehose.us-east-1.amazonaws.com";
        String payload = readPayloadFromFile("/Users/imac/Downloads/demo/src/main/java/com/eyup/services/signerPayload.txt");
        String target = "Firehose_20150804.PutRecordBatch";
        String contentType  = "application/x-amz-json-1.1";
        String  header = signRequest(httpMethod, service, region, endpoint, payload, target, contentType);
        System.out.println(header);
        System.out.println("AWS4-HMAC-SHA256 Credential=ASIA5SXGB3FOWABQYGX7/20241112/us-east-1/firehose/aws4_request, SignedHeaders=content-encoding;content-type;host;user-agent;x-amz-date;x-amz-security-token;x-amz-target, Signature=6ed527c1fa4228e1e56ae4b96da03a9f46ece5d98c0758b1aea8ef09549b2073");
    }

    public static String signRequest(String httpMethod, String service, String region, String endpoint, String payload, String target, String contentType) throws Exception {
//        String dateTime = getAmzDate();
        String dateTime = "20241112T162503Z";
//        String date = dateTime.substring(0, 8);
        String date = "20241112";

        // Step 1: Create Canonical Request
        String canonicalRequest = createCanonicalRequest(httpMethod, endpoint, payload, target, dateTime, contentType);
        // Step 2: Create String to Sign
        String credentialScope = date + "/" + region + "/" + service + "/aws4_request";
        String stringToSign = ALGORITHM + "\n" +
                dateTime + "\n" +
                credentialScope + "\n" +
                hash(canonicalRequest);
        // Step 3: Calculate the Signature
        byte[] signingKey = getSignatureKey(secretKey, date, region, service);
        String signature = bytesToHex(hmacSHA256(stringToSign, signingKey));
        //System.out.println(signature);

        // Step 4: Create Authorization Header
        return buildAuthorizationHeader(signature, credentialScope, dateTime, contentType);
    }

    private static String createCanonicalRequest(String method, String endpoint, String payload, String target, String dateTime, String contentType) throws Exception {
        String canonicalHeaders = "content-encoding:gzip\n" +
                "content-type:" + contentType + "\n" +
                "host:" + endpoint + "\n" +
                "user-agent:aws-sdk-iOS/2.15.1 iOS/17.6.1 en_GB\n" +
                "x-amz-date:" + dateTime + "\n" +
                "x-amz-security-token:" + sessionToken + "\n" +
                "x-amz-target:" + target + "\n";

        String signedHeaders = "content-encoding;content-type;host;user-agent;x-amz-date;x-amz-security-token;x-amz-target";

        return method + "\n" +
                "/" + "\n" +
                "" + "\n" +
                canonicalHeaders +
                signedHeaders + "\n" +
                hash(payload);
    }

    private static String buildAuthorizationHeader(String signature, String credentialScope, String dateTime, String contentType) {
        return ALGORITHM + " Credential=" + accessKey + "/" + credentialScope +
                ", SignedHeaders=content-encoding;content-type;host;user-agent;x-amz-date;x-amz-security-token;x-amz-target, Signature=" + signature;
    }

    private static byte[] getSignatureKey(String key, String date, String region, String service) throws Exception {
        byte[] kDate = hmacSHA256(date, (AWS4_SIGNING_KEY + key).getBytes(StandardCharsets.UTF_8));
        byte[] kRegion = hmacSHA256(region, kDate);
        byte[] kService = hmacSHA256(service, kRegion);
        return hmacSHA256("aws4_request", kService);
    }

    private static byte[] hmacSHA256(String data, byte[] key) throws Exception {
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(new SecretKeySpec(key, HMAC_SHA256));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String hash(String text) throws Exception {
        byte[] digest = java.security.MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String getAmzDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.UK);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date());
    }

    private static String readPayloadFromFile(String filePath) throws IOException {
        StringBuilder payload = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                payload.append(line);
            }
        }
        return payload.toString();
    }
}

