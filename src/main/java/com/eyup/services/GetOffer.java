package com.eyup.services;

import io.restassured.response.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    public static void main(String[] args) {
        //System.out.println(getOffer());
        for (int i = 0; i < 1000; i++) {
            response = getOfferRestAssured();
            if(response.statusCode()==200){
                break;
            }
        }
        getOfferRestAssured();
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
                "filters": {
                    "timeFilter": {"startTime": "00:35"},
                    "serviceAreaFilter": ["cbfeaddb-2982-4320-bb48-e8027572395e", "ac37de9a-735d-4a18-ab3e-5ce925d63c8d"]
                },
                "serviceAreaIds": ["5edbf1e4-4b82-4ec1-8826-f899b5095efd"],
                "apiVersion": "V2"
            }
            """;

        String accessToken = "Atna|EwICIP7kTT0Mb0U4yJHFYq60fK3Tmiy-nNXRybx1lnhh6B2aIGWJQMjJbVPPpllrhTQpGmqkaLp7pF4asJodoHGEJU-bcv75XoyYObjK5nnxbf8WhMm4IEOMWxzzX94wGAlyESFQYwNJkJhnIMtAj0hxiIvjmW3n3BWXpxQNNal9OjrqGx7ICimrU-jPEjSR-1fI1byvexOWLfxLdFtTKU_YN9NDGH5vvGO3-E3wpMxErxixpCOh8i12WoEU0CEQCRw47ZohvYmk1J6riotq-mqiaQQdQQeYwzAqdvzyxDD9dg0-hPAByPbiF5e3m_eBC9yDTYznpYgDl-td1WEQHZvbz5VX_2Ry1ySzbmOc9zLpHSKcrQ";
        Response response = given()
                .baseUri("https://flex-capacity-eu.amazon.com")
                .basePath("/GetOffersForProviderPost")
                .header("Accept", "application/json")
                .header("x-amz-access-token", accessToken)
                .header("x-amzn-attest-content-digest", "sha-256=:84+xIlcmCM4RoK+iCTknub5LDmDyBuaentw25f2ZHpM=:")
                .header("x-flex-instance-id", "1BB5E705-F6AE-4BC1-ADF3-D2612436E74C")
                .header("Accept-Language", "en-GB")
                .header("Signature", "x-amzn-attest=:omlzaWduYXR1cmVYRzBFAiAYnEupXja9bl0UCNMq1tNWvOTpz4V98IqZVh9XvqD85AIhAMy6yc0FBN+XZo0H9mP+xhL/JK4dbhcBnKLRPTCilbimcWF1dGhlbnRpY2F0b3JEYXRhWCXt88KFLRuYQz1vU7XiPPZORFHIzbxYsF/oQgfG99Nl+UAAAA8H:")
                .header("Signature-Input", "x-amzn-attest=(\"@path\" \"x-amzn-marketplace-id\" \"user-agent\" \"x-amzn-attest-content-digest\");alg=\"apple-attest\";created=1731036248;keyid=\"AQICAHiCx7EOfP45kL6+r+Vy3UThQhWOZt6RvUi+EIgHL8lN4AGZeoqiE54Fo53Y2/HDeWuJAAABNjCCATIGCSqGSIb3DQEHBqCCASMwggEfAgEAMIIBGAYJKoZIhvcNAQcBMB4GCWCGSAFlAwQBLjARBAyFtga2eHTJ9pnz5qICARCAgeo1L4+gHI6LTGOiMNXmA2U2QDmSBljm6b+4lAqFXatmOiEzUoNXlR3a6gEWaMC+5bdTRdjEOStwayUgD1KvZAv8NoswTDLhr1eImC10BP3DjXyrLFzzeAXzdkyDfeqCipFefHok+zCMq7A+fRiV1F9dwX5QTIhkfv7Cof3LsLHP2sML3PpcCwFMDQ2p0steoWSfqUZL7d3XW4Vb+OAlvjsps18FZkdFkcSQGTiaWPUVco2DPqyVN7OflPCs/Tu77OL2k3Ql4CK+yKMoqcKJeAKcoe1vVdQbNqlAJfe9DHdyi8kPz5nNEqdAQ8o=\";nonce=1731036248" +
                        "Content-Length: 224")
                .header("User-Agent", "iOS/17.6.1 (iPhone Darwin) Model/iPhone Platform/iPhone11,6 RabbitiOS/2.138.2")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .header("Cookie", "at-main=\"Atza|IwEBIJzWzvW7BhPgpEXLuZkEbiPHY6iqsFDle-TZ8pUkPwv85jQqTNOWdNeT4TANkGCVKlHx_PGsfW_Rk651h77sZp3JMX5ZYoqfjsTj0rptj5VzcTIA411am8Z4xghMIFVPefUgXsDH1TggvT392JlAHiNcsvK8iAWoHd-tkI5JwC9dNW0aQbwc3F09AegqLdRLhl9RnanIM9eFIWCRROCHRcPhqSbQCK1NGmgIYaek4fFLHILxzVGEB2jIBjSgDIgcmPZGkkcpEgHssa9UgT814szde-dckDooW4OmHZWTSaJbY4xbFEHgEg_UFzDxUlN7WxGQL9a7GacAxtBaYaKvJEKZ\"; sess-at-main=\"3xy72Sezr0UD6TOQhmW0dRRy5aJyaZSviSy1a+YIh5M=\"; session-id=145-3428285-1585926; session-id-time=2361704633l; session-token=7op2NQ3Gq4NxSH0IFPKPOgUGLFMQkSkTGenjkQzvlRUOsfVA3ua806CYV2r9ahOmSHMx3ldBxIeiBA67xAE/y2/vCb3lNkYuJB4nq3Tc/h2FbPhod5oEHk2b1fdsEY8nQaEdV6qiqOjHDsY95rtzi6h+DIA4YERgwdwhVe6LXP3Qwypt5GFd7AKXy2UprDHvvvg3z1vsFU9r3g4RHh4Op6y1YpS5F3TEqDhV1zmFtVl1U9gQVrYYaDnBvwRS1GqBAwHUPKLQlBRqp3xYL2BiFstH7R2tTsUYi213Vswpi5RD191Smgd6HetUvQBZIJ1KF618MFvDj63rELg8Hd1KuxaLEpI8MZlKh6CEEX3egJ148L8sxfrzf93nHfxAy2Mg; ubid-main=133-2363644-5022116; x-main=\"kVB0kBo8swrc3lVIzI@kMBxSMiWt8NTGEVlqHs?HoQtvDPIgK2jnLii10rDmT?w7\"")
                .header("x-amzn-marketplace-id", "A1F83G8C2ARO7P")
                .header("Connection", "keep-alive")
                .body(requestBody)
                .post();
        response.then().statusCode(200);
        offerList = response.jsonPath().getList("offerList");
        logger.info("Offers: " + offerList);
        if (!offerList.isEmpty()){
            offerIDs = response.jsonPath().getList("offerList.offerId");
            priceAmounts = response.jsonPath().getList("offerList.rateInfo.priceAmount");
            logger.info("priceAmounts: " + priceAmounts);
            serviceAreaIds = response.jsonPath().getList("offerList.serviceAreaId");
            logger.info("serviceAreaIds: " + serviceAreaIds);
        }

        return response;
    }


}
