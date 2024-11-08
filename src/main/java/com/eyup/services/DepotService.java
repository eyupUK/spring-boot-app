package com.eyup.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class DepotService {

    public static void main(String[] args) {
        System.out.println(getDepotNames());
        System.out.println(getDepotIds());
    }

    private static Response getDepots() {

        // Base URL for the API
        RestAssured.baseURI = "https://flex-capacity-eu.amazon.com";
        String accessToken = "Atna|EwICIP7kTT0Mb0U4yJHFYq60fK3Tmiy-nNXRybx1lnhh6B2aIGWJQMjJbVPPpllrhTQpGmqkaLp7pF4asJodoHGEJU-bcv75XoyYObjK5nnxbf8WhMm4IEOMWxzzX94wGAlyESFQYwNJkJhnIMtAj0hxiIvjmW3n3BWXpxQNNal9OjrqGx7ICimrU-jPEjSR-1fI1byvexOWLfxLdFtTKU_YN9NDGH5vvGO3-E3wpMxErxixpCOh8i12WoEU0CEQCRw47ZohvYmk1J6riotq-mqiaQQdQQeYwzAqdvzyxDD9dg0-hPAByPbiF5e3m_eBC9yDTYznpYgDl-td1WEQHZvbz5VX_2Ry1ySzbmOc9zLpHSKcrQ";

        // Send GET request with necessary headers and cookies
        return given()
                .header("x-amz-access-token",   accessToken)
                .header("x-flex-instance-id", "1BB5E705-F6AE-4BC1-ADF3-D2612436E74C")
                .header("Signature-Input", "x-amzn-attest=(@path x-amzn-marketplace-id user-agent);alg=apple-attest;created=1731032870;keyid=AQICAHiCx7EOfP45kL6+r+Vy3UThQhWOZt6RvUi+EIgHL8lN4AGZeoqiE54Fo53Y2/HDeWuJAAABNjCCATIGCSqGSIb3DQEHBqCCASMwggEfAgEAMIIBGAYJKoZIhvcNAQcBMB4GCWCGSAFlAwQBLjARBAyFtga2eHTJ9pnz5qICARCAgeo1L4+gHI6LTGOiMNXmA2U2QDmSBljm6b+4lAqFXatmOiEzUoNXlR3a6gEWaMC+5bdTRdjEOStwayUgD1KvZAv8NoswTDLhr1eImC10BP3DjXyrLFzzeAXzdkyDfeqCipFefHok+zCMq7A+fRiV1F9dwX5QTIhkfv7Cof3LsLHP2sML3PpcCwFMDQ2p0steoWSfqUZL7d3XW4Vb+OAlvjsps18FZkdFkcSQGTiaWPUVco2DPqyVN7OflPCs/Tu77OL2k3Ql4CK+yKMoqcKJeAKcoe1vVdQbNqlAJfe9DHdyi8kPz5nNEqdAQ8o=")
                .header("Signature", "x-amzn-attest=:omlzaWduYXR1cmVYRzBFAiEAxEW1UjE+wkU1IzLcW4jVnuAj9XI/uBRahvf/sP0fy2MCIFxtiZmBzGY537elUe26Y6JIhCkHIe68oXyRo+hKJvitcWF1dGhlbnRpY2F0b3JEYXRhWCXt88KFLRuYQz1vU7XiPPZORFHIzbxYsF/oQgfG99Nl+UAAAA7s:")
                .header("Accept-Language", "en-GB")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("User-Agent", "iOS/17.6.1 (iPhone Darwin) Model/iPhone Platform/iPhone11,6 RabbitiOS/2.138.2")
                .header("Connection", "keep-alive")
                .header("Accept", "application/json")
                .header("x-amzn-marketplace-id", "A1F83G8C2ARO7P")
                .cookie("at-main", "Atza|IwEBIJzWzvW7BhPgpEXLuZkEbiPHY6iqsFDle-TZ8pUkPwv85jQqTNOWdNeT4TANkGCVKlHx_PGsfW_Rk651h77sZp3JMX5ZYoqfjsTj0rptj5VzcTIA411am8Z4xghMIFVPefUgXsDH1TggvT392JlAHiNcsvK8iAWoHd-tkI5JwC9dNW0aQbwc3F09AegqLdRLhl9RnanIM9eFIWCRROCHRcPhqSbQCK1NGmgIYaek4fFLHILxzVGEB2jIBjSgDIgcmPZGkkcpEgHssa9UgT814szde-dckDooW4OmHZWTSaJbY4xbFEHgEg_UFzDxUlN7WxGQL9a7GacAxtBaYaKvJEKZ")
                .cookie("sess-at-main", "3xy72Sezr0UD6TOQhmW0dRRy5aJyaZSviSy1a+YIh5M=")
                .cookie("session-id", "145-3428285-1585926")
                .cookie("session-id-time", "2361704633l")
                .cookie("session-token", "7op2NQ3Gq4NxSH0IFPKPOgUGLFMQkSkTGenjkQzvlRUOsfVA3ua806CYV2r9ahOmSHMx3ldBxIeiBA67xAE/y2/vCb3lNkYuJB4nq3Tc/h2FbPhod5oEHk2b1fdsEY8nQaEdV6qiqOjHDsY95rtzi6h+DIA4YERgwdwhVe6LXP3Qwypt5GFd7AKXy2UprDHvvvg3z1vsFU9r3g4RHh4Op6y1YpS5F3TEqDhV1zmFtVl1U9gQVrYYaDnBvwRS1GqBAwHUPKLQlBRqp3xYL2BiFstH7R2tTsUYi213Vswpi5RD191Smgd6HetUvQBZIJ1KF618MFvDj63rELg8Hd1KuxaLEpI8MZlKh6CEEX3egJ148L8sxfrzf93nHfxAy2Mg")
                .cookie("ubid-main", "133-2363644-5022116")
                .cookie("x-main", "kVB0kBo8swrc3lVIzI@kMBxSMiWt8NTGEVlqHs?HoQtvDPIgK2jnLii10rDmT?w7")
                .when()
                .get("/regions/2")
                .then()
                .extract()
                .response();
    }

    public static List<String> getDepotNames() {
        return getDepots().jsonPath().getList("region.serviceAreas.name");
    }

    public static List<String> getDepotIds() {
        return getDepots().jsonPath().getList("region.serviceAreas.id");
    }
}
