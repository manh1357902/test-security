package com.example.security.util;
import com.example.security.constant.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AesUtil {

    private static final String SECRET_KEY = "1234567890abcdef"; // 16 byte

    /**
     * Create a SecretKeySpec from the SECRET_KEY.
     */
    private static SecretKeySpec getSecretKeySpec() {
        return new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), Constant.AES);
    }

    /**
     * Encrypts a plain text string using AES/CBC/PKCS5Padding.
     *
     * @param plainText the original string to encrypt
     * @return the encrypted string encoded in Base64
     * @throws InvalidAlgorithmParameterException if the IV is invalid
     * @throws InvalidKeyException if the secret key is invalid
     * @throws NoSuchPaddingException if the padding mechanism is not available
     * @throws NoSuchAlgorithmException if the AES algorithm is not available
     * @throws IllegalBlockSizeException if the block size is incorrect
     * @throws BadPaddingException if the padding is incorrect
     */
    public static String encrypt(String plainText) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        if (plainText == null || plainText.isEmpty()) {
            return null;
        }

        // 1. Tạo IV ngẫu nhiên (16 byte)
        byte[] ivBytes = new byte[Constant.SIX_TEEN];
        new SecureRandom().nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // 2. Mã hóa dữ liệu
        Cipher cipher = Cipher.getInstance(Constant.AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(), iv);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // 3. Ghép IV + ciphertext và trả về Base64
        byte[] combined = new byte[ivBytes.length + encrypted.length];
        System.arraycopy(ivBytes, Constant.ZERO, combined, Constant.ZERO, ivBytes.length);
        System.arraycopy(encrypted, Constant.ZERO, combined, ivBytes.length, encrypted.length);
        return Base64.getEncoder().encodeToString(combined);
    }


    /**
     * Decrypts an AES-encrypted Base64 string back to plain text.
     *
     * @param encryptedText the encrypted string in Base64
     * @return the decrypted plain text string
     * @throws NoSuchPaddingException if the padding mechanism is not available
     * @throws NoSuchAlgorithmException if the AES algorithm is not available
     * @throws InvalidAlgorithmParameterException if the IV is invalid
     * @throws InvalidKeyException if the secret key is invalid
     * @throws IllegalBlockSizeException if the block size is incorrect
     * @throws BadPaddingException if the padding is incorrect
     */
    public static String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (encryptedText == null || encryptedText.isEmpty()) {
            return null;
        }

        // 1. Giải mã Base64 và tách IV + ciphertext
        byte[] combined = Base64.getDecoder().decode(encryptedText);
        byte[] ivBytes = new byte[Constant.SIX_TEEN];
        byte[] encrypted = new byte[combined.length - ivBytes.length];
        System.arraycopy(combined, Constant.ZERO, ivBytes, Constant.ZERO, ivBytes.length);
        System.arraycopy(combined, ivBytes.length, encrypted, Constant.ZERO, encrypted.length);

        // 2. Giải mã với IV đã tách
        Cipher cipher = Cipher.getInstance(Constant.AES_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(), new IvParameterSpec(ivBytes));
        byte[] original = cipher.doFinal(encrypted);
        return new String(original, StandardCharsets.UTF_8);
    }
}
