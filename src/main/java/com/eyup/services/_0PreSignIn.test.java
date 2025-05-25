package com.eyup.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _0PreSignInTest {


    @Test
    void shouldGenerateValidAuthorizationHeader() throws Exception {
        String accessKeyId = "ASIA5SXGB3FOWABQYGX7";
        String secretKey = "+ZCqXkIg2caWgXwSBlOpx2US58fKaTtOfeQR8se7";
        String payload = "{\"IdentityId\":\"us-east-1:84f7e3d9-2c79-462d-a684-d24699ba3c73\"}";

        String result = _0PreSignIn.generateSignature(accessKeyId, secretKey, payload);

    }
}
