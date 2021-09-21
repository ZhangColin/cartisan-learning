package com.sso;

import com.sso.utils.RsaUtils;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author colin
 */
public class JwtTest {
    private String privateKeyFileName = "/Users/colin/generateKey/id_key_rsa";
    private String publicKeyFileName = "/Users/colin/generateKey/id_key_rsa.pub";

    @Test
    public void test1() throws NoSuchAlgorithmException, IOException {
        RsaUtils.generateKey(publicKeyFileName, privateKeyFileName, "cartisan", 1024);
    }
}
