package com.sso.config;

import com.sso.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * @author colin
 */
@Data
@ConfigurationProperties(prefix = "rsa.key")
public class RsaKeyProperties {
    private String publicKeyFile;

    private PublicKey publicKey;

    @PostConstruct
    public void createRsaKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        publicKey = RsaUtils.getPublicKey(publicKeyFile);
    }
}
