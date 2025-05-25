package com.eyup.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static io.restassured.RestAssured.given;

public class GetOffer {
    private static final Logger logger = Logger.getLogger(GetOffer.class.getName());
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

    static List<String> offerIDs;
    static List<Double> priceAmounts;
    static List<String> serviceAreaIds;
    static List<String> serviceAreaNames;
    static List<String> offerList;
    static Response response;
    static String accessToken;
    static String content = "sha-256=:1mXEhcKqvIAGtPSpIPHhsZLM2CYynXig4GgpAw/PWx8=:";
    static String signature = "x-amzn-attest=:omlzaWduYXR1cmVYRzBFAiEApys4ls9Pu95tX0Kq+5neBLfUGByzMqDp9HBusimBkcwCIGsrBfKqoMKWFB3thBUVLy1tiD71A8iJVvvygEbpDRkccWF1dGhlbnRpY2F0b3JEYXRhWCXt88KFLRuYQz1vU7XiPPZORFHIzbxYsF/oQgfG99Nl+UAAAAm3::";;
    static String signatureInput = "x-amzn-attest=(\"@path\" \"x-amzn-marketplace-id\" \"user-agent\" \"x-amzn-attest-content-digest\");alg=\"apple-attest\";created=1731424613;keyid=\"AQICAHiCx7EOfP45kL6+r+Vy3UThQhWOZt6RvUi+EIgHL8lN4AF3H7ADnJyXlxHvg7aOS44QAAABNjCCATIGCSqGSIb3DQEHBqCCASMwggEfAgEAMIIBGAYJKoZIhvcNAQcBMB4GCWCGSAFlAwQBLjARBAwDx5EjP4DdGwrP5dQCARCAger8D6oCJ5doFeKLKNM3eAu0SBI0uVamC0B405I/8yEA/lbyzm/PkHOviSrOrqyUK6gYPdODJNQIkYtffZtbyqzWe65rW/d+0sACZVjuwp73ws2kgSKegAAeRW4ZY5Cjdpil/Z+e+lOdGHNGAqZYk2UXL6xDTyLUtkCd/LeYbPxQSJI0MdAMBOzMlz6QUYuPIPD1YqcogWW+P8KTfusJGHA3JSJzs0c0fRmKMExYwX/mL/kctXT1z/XxdhfRfQy7sgeaIpBl8wMFaiLQC2Ir+KWwhu4CMBNe4B7SOpjLPE5ERHx5zwo/UY1fzqI=\";nonce=1731424613";
    static String cookie ="at-acbuk=\"Atza|IwEBIGJN9dr8QlHpHQmLTEaFNF9mDe1r8SRcWJDYVPY4-aE1GoRx8PyDkIp49J919DE9DMDD0crzVt3jfM40B_YSROaBFhEgfJQKEPE9w1ZB4pdCkJ2II5ay5QN9JEq0QT4xmcR4-FrYWserMjTUUQ48DNsEmPpg48N9Jef_DeIY-nsBcFKKDlQXwBHB0uKLKXzqssTe-wMWi1QbJYzxkb3Ts4w7LBd_stDhYqTMQw8-ytelogUmk1WuP-RWgjIs4-xB9eMQhWw-9YJxTX6aUn_c3RmhHSDfUztdqYzx_s1gPoIgcnScfzlnJGlBP2bLMOv28WpHuFx05slm1ms75vUen7xP\"; sess-at-acbuk=\"g6LKEi+MXqJcYgLib6xZRaSjyYBQmERdL7ZNnzk14CU=\"; session-id=261-7097478-0791753; session-id-time=2361868508l; session-token=eHWgpYZH4ISFToK9kE8C8igmGUChkA5UAg6PATXhTT09niygRsgTQ1aOnIr1p2DjoUT/fTjVARQjgodg7uAmXMGMiFpuFCAm+XK5ocCh9+o/Z7ROMiR8BvPCU57qbRM0bOHRwwZP1mToE1lKRkLXu7PbiRhaslvlB3wWryJ3Lw6bNQPrN939eOycjuwo5V0v9zZRCoFuZAaW2+ufKCd9sw5+L02IPf+fxMlofLspUOlcJFbvKznXuK1H/IKB4sF8RyK7AaEYJeDf0Yo7oYvmX88jzCpqhvyD+7kR+2wIK7oG4eUTWiBmfMHACeKMSjMDNlbAAHd73XGH0qh6wIq2tmy4VI0FC58GdSn+Fc7WJ9wxAUUSpC1eEvNskpSgLk6X; ubid-acbuk=260-5182571-0601526; x-acbuk=\"UWEvVIfVKPBfSEoivt7U@kMR1BVWQ0ffQW8vTKgzgPooWB8eYlKwMfN2i9zWY099\"; lc-acbgb=en_GB; i18n-prefs=GBP; lc-acbuk=en_GB";



    public static void main(String[] args) {
        accessToken = "Atna|EwICIPUfkMJXmXdJu7zy2cYQ44U_L6DU0OprAAlrL1UZL39eHNXDUUgNorkVKrTJZtsSuo0ZCHAk4BU7NtBF9YVOX37Q6q1dMrmAN8DJ9phhSvUUK32BrLLrLdJmpArcUurHLPU8aF7TpuQLt6viyGMmWGjtnsq8hx5eCYMjS48oWvuwaiZY3ADMAY7wRUBvMLeBVVg30QGPjICdNRD8wC6w2TkbrNAe6tBiB4kVPiZCj2FHeMXS41ZwlXHRKSzDy_xMAjAX6Y-aat1da5rXG6WZPJdxQamYB63Xm2RSBxzJQ6xurVwByu2S5N_Eis57fwN-ryy-KUrrgCkkDDhxHQHmkDF2a8OFZdZOhzZG_Cn9dfOJcw"; //getAuthToken().jsonPath().getString("access_token");
        logger.info("Access token: " + accessToken);
        logger.info("Starting the GetOffer service...");
        for (int i = 0; i < 500; i++) {
            if (i % 100 == 0) {
                //keepAlive();
            }
            getOfferRestAssured();
            int random = (int) ((Math.random() * 500) + 500);
            try {
                Thread.sleep(random); // Add delay to avoid rate limit
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getOffer() {
        try {
            // Define the endpoint URI
            URI uri = new URI("https://flex-capacity-eu.amazon.com/GetOffersForProviderPost");

            // Create JSON body as a string
            String jsonBody = """
                {
                    "filters": {
                        "timeFilter": {"startTime": "00:00"},
                        "serviceAreaFilter": ["cbfeaddb-2982-4320-bb48-e8027572395e", "ac37de9a-735d-4a18-ab3e-5ce925d63c8d"]
                    },
                    "serviceAreaIds": ["5edbf1e4-4b82-4ec1-8826-f899b5095efd"],
                    "apiVersion": "V2"
                }
                """;

            // Create the HttpRequest with headers and body
            String accessToken = "Atna|EwICIC1ergA6r7eZzDEwIsBjiBi67hPLMbT3ChhY-z0r7tQMmvZkddYf2hjvvnumIfpbUbVRpBv6MWquqEAuqbHqcAyM923vCbsqV-yF-_ohpBKFLVARS9PAxCIEPUMpSD5LgCYC-dcr7tJL-e9wUmSfb2d73J-azJzsXFxIJ5xBkTJKF205PjjjywMsE2X0UMSMoFo1h1S-NMKQ1N_DQR8iMhmrp4RIR1iahN3LBroDwFDYx35ume-xZaHhnSTMi6iTl4fNnWCyVrNvUDAmhq_v-16Ly2f5rTgCPILunK9nZ14yPctra1D4XGs831OrJPqYPswvjG-MLacNt2N0Uljv0DbHkQs3N5OB7xn0nFBxnnDkEQ";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Accept", "application/json")
                    .header("x-amz-access-token", accessToken)
                    .header("x-amzn-attest-content-digest", "sha-256=:4jdGXlA2H9jWo7kISTd/QRRIx8YY8Nvd1ITFOvKYMEc=:")
                    .header("x-flex-instance-id", "1BB5E705-F6AE-4BC1-ADF3-D2612436E74C")
                    .header("Accept-Language", "en-GB")
                    .header("Signature", "x-amzn-attest=:omlzaWduYXR1cmVYRzBFAiEA6F0askTqWxabs+mhho296fcCUTkbl0a0mGb7DoTmO5ICIGcc/Ur0wiewbPPHSdjpxyjU7GBrfpPy7FNuX6YCClUBcWF1dGhlbnRpY2F0b3JEYXRhWCXt88KFLRuYQz1vU7XiPPZORFHIzbxYsF/oQgfG99Nl+UAAAA7Q:")
                    .header("Signature-Input", "x-amzn-attest=(\"@path\" \"x-amzn-marketplace-id\" \"user-agent\" \"x-amzn-attest-content-digest\");alg=\"apple-attest\";created=1731027094;keyid=\"AQICAHiCx7EOfP45kL6+r+Vy3UThQhWOZt6RvUi+EIgHL8lN4AGZeoqiE54Fo53Y2/HDeWuJAAABNjCCATIGCSqGSIb3DQEHBqCCASMwggEfAgEAMIIBGAYJKoZIhvcNAQcBMB4GCWCGSAFlAwQBLjARBAyFtga2eHTJ9pnz5qICARCAgeo1L4+gHI6LTGOiMNXmA2U2QDmSBljm6b+4lAqFXatmOiEzUoNXlR3a6gEWaMC+5bdTRdjEOStwayUgD1KvZAv8NoswTDLhr1eImC10BP3DjXyrLFzzeAXzdkyDfeqCipFefHok+zCMq7A+fRiV1F9dwX5QTIhkfv7Cof3LsLHP2sML3PpcCwFMDQ2p0steoWSfqUZL7d3XW4Vb+OAlvjsps18FZkdFkcSQGTiaWPUVco2DPqyVN7OflPCs/Tu77OL2k3Ql4CK+yKMoqcKJeAKcoe1vVdQbNqlAJfe9DHdyi8kPz5nNEqdAQ8o=\";nonce=1731027094")
                    .header("User-Agent", "iOS/17.6.1 (iPhone Darwin) Model/iPhone Platform/iPhone11,6 RabbitiOS/2.138.2")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Content-Type", "application/json")
                    .header("Cookie", "at-main=\"Atza|IwEBIJzWzvW7BhPgpEXLuZkEbiPHY6iqsFDle-TZ8pUkPwv85jQqTNOWdNeT4TANkGCVKlHx_PGsfW_Rk651h77sZp3JMX5ZYoqfjsTj0rptj5VzcTIA411am8Z4xghMIFVPefUgXsDH1TggvT392JlAHiNcsvK8iAWoHd-tkI5JwC9dNW0aQbwc3F09AegqLdRLhl9RnanIM9eFIWCRROCHRcPhqSbQCK1NGmgIYaek4fFLHILxzVGEB2jIBjSgDIgcmPZGkkcpEgHssa9UgT814szde-dckDooW4OmHZWTSaJbY4xbFEHgEg_UFzDxUlN7WxGQL9a7GacAxtBaYaKvJEKZ\"; sess-at-main=\"3xy72Sezr0UD6TOQhmW0dRRy5aJyaZSviSy1a+YIh5M=\"; session-id=145-3428285-1585926; session-id-time=2361704633l; session-token=7op2NQ3Gq4NxSH0IFPKPOgUGLFMQkSkTGenjkQzvlRUOsfVA3ua806CYV2r9ahOmSHMx3ldBxIeiBA67xAE/y2/vCb3lNkYuJB4nq3Tc/h2FbPhod5oEHk2b1fdsEY8nQaEdV6qiqOjHDsY95rtzi6h+DIA4YERgwdwhVe6LXP3Qwypt5GFd7AKXy2UprDHvvvg3z1vsFU9r3g4RHh4Op6y1YpS5F3TEqDhV1zmFtVl1U9gQVrYYaDnBvwRS1GqBAwHUPKLQlBRqp3xYL2BiFstH7R2tTsUYi213Vswpi5RD191Smgd6HetUvQBZIJ1KF618MFvDj63rELg8Hd1KuxaLEpI8MZlKh6CEEX3egJ148L8sxfrzf93nHfxAy2Mg; ubid-main=133-2363644-5022116; x-main=\"kVB0kBo8swrc3lVIzI@kMBxSMiWt8NTGEVlqHs?HoQtvDPIgK2jnLii10rDmT?w7\"")
                    .header("x-amzn-marketplace-id", "A1F83G8C2ARO7P")
//                    .header("Connection", "keep-alive")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Send the request and get the response
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("Status code: " + response.statusCode());
            // Return the response body
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Response getOfferRestAssured() {
        String requestBody = """
                {
                  "apiVersion": "V2",
                  "filters": {
                    "timeFilter": {
                      "startTime": "00:45"
                    },
                    "serviceAreaFilter": [
                      "cbfeaddb-2982-4320-bb48-e8027572395e",
                      "ac37de9a-735d-4a18-ab3e-5ce925d63c8d"
                    ]
                  },
                  "serviceAreaIds": [
                    "5edbf1e4-4b82-4ec1-8826-f899b5095efd"
                  ]
                }
                """;

Response response = given()
                .baseUri("https://flex-capacity-eu.amazon.com")
                .basePath("/GetOffersForProviderPost")
                .header("Accept", "application/json")
                .header("x-amz-access-token", accessToken)
                .header("x-amzn-attest-content-digest", content)
                .header("x-flex-instance-id", "1BB5E705-F6AE-4BC1-ADF3-D2612436E74C")
                .header("Accept-Language", "en-GB")
                .header("Signature", signature)
                .header("Signature-Input", signatureInput)
                .header("User-Agent", "iOS/17.6.1 (iPhone Darwin) Model/iPhone Platform/iPhone11,6 RabbitiOS/2.138.2")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .header("x-amzn-marketplace-id", "A1F83G8C2ARO7P")
                .header("Connection", "keep-alive")
                .body(requestBody)
                .post();
        response.then().statusCode(200);
        if (response.statusCode() == 400) {
            logger.warning(response.jsonPath().getString("message"));
        }
        offerList = response.jsonPath().getList("offerList");
        if (offerList != null && !offerList.isEmpty()) {
            offerIDs = response.jsonPath().getList("offerList.offerId");
            //acceptOffer(offerIDs.get(offerIDs.size()-1));
            serviceAreaIds = response.jsonPath().getList("offerList.serviceAreaId");
            priceAmounts = response.jsonPath().getList("offerList.rateInfo.priceAmount");
            logger.info("priceAmounts: " + priceAmounts);
            logger.info("serviceAreaIds: " + serviceAreaIds);
            logger.info("offerList: " + offerList);
            logger.info("response body: " + response.body().asString());

        }

        return response;
    }

    private static Response acceptOffer(String offerId) {
        RestAssured.baseURI = "https://flex-capacity-eu.amazon.com";

        // Create JSON body payload
        String jsonPayload = "{ \"offerId\": \"" + offerId + "\" }";

        // Send POST request
        response = given()
                .header("Accept", "application/json")
                .header("x-amz-access-token", accessToken)
                .header("x-amzn-attest-content-digest", content)
                .header("x-flex-instance-id", "1BB5E705-F6AE-4BC1-ADF3-D2612436E74C")
                .header("Accept-Language", "en-GB")
                .header("Signature", signature)
                .header("Signature-Input", signatureInput)
                .header("User-Agent", "iOS/17.6.1 (iPhone Darwin) Model/iPhone Platform/iPhone11,6 RabbitiOS/2.138.2")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/json")
                .header("x-amzn-marketplace-id", "A1F83G8C2ARO7P")
                .body(jsonPayload)
                .post("/AcceptOffer");

        int statusCode = response.getStatusCode();
        logger.warning("Status code: " + statusCode);
        logger.info("x-amzn-ErrorType: " + response.header("x-amzn-ErrorType"));
        if (statusCode == 200) {
            logger.fine("Offer accepted successfully.");
        } else {
            logger.info("Failed to accept offer.");
            logger.warning("Response: " + response.getBody().asString());
        }
        // Return the response for further handling
        return response;
    }
    private static Double maxPayment(List<Double> priceAmounts) {
        return Collections.max(priceAmounts);
    }

    public static Response keepAlive() {
        logger.info("Sending keep-alive request...");
        // Set the base URI
        RestAssured.baseURI = "https://msh.amazon.co.uk";

        // Endpoint with query parameters
        String endpoint = "/mwl/triggers/v2?session-id=043-9643846-0797602" +
                "&marketplace-id=A1F83G8C2ARO7P" +
                "&directed-id=amzn1.account.AHUTRHEYZ4OP4YEYPQ2SKFIQF3QA" +
                "&os=IOS" +
                "&os-version=17.6.1" +
                "&app-version=2.139.6.63839" +
                "&host-app=RabbitApp" +
                "&app-id=com.amazon.flex.irabbit";

        // Create JSON payload
        String jsonPayload = "{ \"Triggers\": [{ \"Treatment\": \"T1\", \"AllocationVersion\": \"A1F83G8C2ARO7P--T1:1\", \"Weblab\": \"RABBIT_IOS_LARGE_PASSENGER_VEHICLE_LABEL_905870\" }] }";

        // Send POST request
        Response response = given()
                .header("Content-Type", "application/json")
                .header("x-client-id", "RabbitIOSApp")
                .header("Cookie", cookie)
                .header("Connection", "keep-alive")
                .header("Accept", "application/json")
                .header("Accept-Language", "en-GB,en;q=0.9")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("User-Agent", "RabbitIOSApp/63839 CFNetwork/1498.700.2 Darwin/23.6.0")
                .body(jsonPayload)
                .post(endpoint);

        response.then().statusCode(204);
        // Return the response for further handling
        return response;
    }

    public static Response getAuthToken() {
        // Set the base URI
        RestAssured.baseURI = "https://api.amazon.co.uk";

        // Define the form data
        String formData = "exchange_reason=miGetATForceRefresh&app_name=Amazon%20Flex&app_version=0.0" +
                "&di.sdk.version=6.15.3&source_token=Atnr%7CEwICIE1bMx2GOXDDl_QLkYtikKZXv8T3qgSl2U4j2quTtlG6V0VK3Ketxd4bkcKl7yolTd2bHvhhekBzm_Z_gfqqVgl_jblDjXChrpyM_g6J9tA_b6z_J2nr76I5YFod3FbkavCdajB9ITj5sIta9Aqt-zk2GDBO4NAeyg2pL1-39A0cQLXrBrQzXbVrCxTx_nyqhQjrbpOml06714VhHHLhyDta6JiXmmO0QblA4qPxqRYRpD_1BuTFmQErugR5ewkTREjPn6Hb29jXat43P4jBuk9ixarE2A0Fx7EQh_fq2OabKEu7TWSfhTfr7hWLsWotO8r0T224rmxLU6SsNsOn4k5z" +
                "&package_name=com.amazon.flex.irabbit&di.hw.version=iPhone&platform=iOS" +
                "&requested_token_type=access_token&source_token_type=refresh_token" +
                "&di.os.name=iOS&di.os.version=17.6.1&current_version=6.15.3&previous_version=6.15.3";

        // Send POST request
        Response response = given()
                .header("Accept", "application/json")
                .header("Accept-Charset", "utf-8")
                .header("x-amzn-identity-auth-domain", "api.amazon.co.uk")
                .header("Accept-Language", "en-GB")
                .header("Accept-Encoding", "gzip")
                .header("Cache-Control", "no-store")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", "AmazonWebView/Amazon Flex/0.0/iOS/17.6.1/iPhone")
                .header("Connection", "keep-alive")
                .header("Cookie", cookie)
                .body(formData)
                .post("/auth/token");

        // Return the response for further processing
        return response;
    }

    public static Response getCredentials() {
        // Set the base URI
        RestAssured.baseURI = "https://cognito-identity.eu-west-1.amazonaws.com";

        // Define the request payload
        String payload = "{\"IdentityId\":\"eu-west-1:0e4d1b27-d8f7-40ef-99f8-59768421a039\"}";

        // Send POST request
        Response response = given()
                .header("accept", "*/*")
                .header("content-type", "application/x-amz-json-1.1")
                .header("x-amz-target", "AWSCognitoIdentityService.GetCredentialsForIdentity")
                .header("accept-language", "en-GB,en;q=0.9")
                .header("x-amz-date", "20241110T053859Z")
                .header("user-agent", "aws-sdk-iOS/2.15.1 iOS/17.6.1 en_GB")
                .header("accept-encoding", "gzip, deflate, br")
                .body(payload)
                .post("/");

        // Return the response for further handling
        return response;
    }
}
