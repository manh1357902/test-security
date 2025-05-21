package com.example.security.util;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Implementation of the RSA for asymmetric encryption operations.
 * Uses RSA with SHA256withRSA for signatures, 2048-bit keys, BASE64 encoding,
 * and PKCS12 format compatibility.
 */
public class RsaUtil {

    private static final String RSA_ALGORITHM = "RSA";
    private static final String RSA_CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final int KEY_SIZE = 2048;


    /**
     * Generates a new RSA key pair.
     *
     * @return KeyPair containing RSA public and private keys
     * @throws NoSuchAlgorithmException if the RSA algorithm is not available
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Encrypts a plain text string using the RSA public key.
     *
     * @param plainText The text to encrypt
     * @param publicKey The RSA public key
     * @return Base64-encoded encrypted string
     * @throws NoSuchPaddingException if the padding mechanism is not available
     * @throws NoSuchAlgorithmException if the RSA algorithm is not available
     * @throws InvalidKeyException if the public key is invalid
     * @throws IllegalBlockSizeException if the block size is incorrect
     * @throws BadPaddingException if the padding is incorrect
     */
    public static String encrypt(String plainText, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(RSA_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts an encrypted string using the RSA private key.
     *
     * @param encryptedText Base64-encoded encrypted text
     * @param privateKey The RSA private key
     * @return The decrypted plain text
     */
    public static String decrypt(String encryptedText, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(RSA_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Creates a digital signature for data using SHA256withRSA.
     *
     * @param data The data to sign
     * @param privateKey The RSA private key for signing
     * @return Base64-encoded signature
     */
    public String sign(String data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /**
     * Verifies a digital signature against data using the public key.
     *
     * @param data The original data
     * @param signature Base64-encoded signature
     * @param publicKey The RSA public key
     * @return true if the signature is valid, false otherwise
     */
    public boolean verify(String data, String signature, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(publicKey);
        sig.update(data.getBytes(StandardCharsets.UTF_8));
        return sig.verify(Base64.getDecoder().decode(signature));
    }

    /**
     * Converts a public key to a Base64-encoded string.
     *
     * @param publicKey The RSA public key
     * @return Base64-encoded string representation
     */
    public String publicKeyToString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * Converts a private key to a Base64-encoded string.
     *
     * @param privateKey The RSA private key
     * @return Base64-encoded string representation
     */
    public String privateKeyToString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * Converts a Base64-encoded string back to an RSA public key.
     *
     * @param publicKeyString Base64-encoded public key string
     * @return The RSA public key
     */
    public static PublicKey stringToPublicKey(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * Converts a Base64-encoded string back to an RSA private key.
     *
     * @param privateKeyString Base64-encoded private key string
     * @return The RSA private key
     */
    public static PrivateKey stringToPrivateKey(String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }
}
