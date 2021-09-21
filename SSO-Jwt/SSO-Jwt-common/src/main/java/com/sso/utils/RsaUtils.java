package com.sso.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author colin
 */
public class RsaUtils {
    private static final int DEFAULT_KEY_SIZE = 2048;

    public static PublicKey getPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = readFile(filename);

        return getPublicKey(bytes);
    }

    public static PrivateKey getPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = readFile(filename);

        return getPrivateKey(bytes);
    }

    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) throws NoSuchAlgorithmException, IOException {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        final SecureRandom secureRandom = new SecureRandom(secret.getBytes(StandardCharsets.UTF_8));

        keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        final KeyPair keyPair = keyPairGenerator.genKeyPair();

        final byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        final byte[] encodePublicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFilename, encodePublicKeyBytes);

        final byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        final byte[] encodePrivateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFilename, encodePrivateKeyBytes);
    }

    private static PublicKey getPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        bytes = Base64.getDecoder().decode(bytes);
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        final KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePublic(spec);
    }

    private static PrivateKey getPrivateKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        bytes = Base64.getDecoder().decode(bytes);
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        final KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePrivate(spec);
    }

    private static byte[] readFile(String fileName) throws IOException {
        return Files.readAllBytes(new File(fileName).toPath());
    }

    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        final File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }

        Files.write(dest.toPath(), bytes);
    }

}
