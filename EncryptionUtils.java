package com.intellimission.services;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

/**
 * Simple AES encryption utilities using Bouncy Castle provider if available.
 * Note: For production, use proper key storage, IVs, and authenticated encryption (GCM).
 */
public final class EncryptionUtils {
    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Throwable ignored) { }
    }

    private EncryptionUtils() {}

    /** Generate a random AES key (128 bits). Falls back to default provider if BC isn't present. */
    public static SecretKey generateKey() throws Exception {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES", "BC");
            kg.init(128);
            return kg.generateKey();
        } catch (Exception ex) {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            return kg.generateKey();
        }
    }

    private static Cipher getCipher(String transform, int mode, SecretKey key) throws Exception {
        try {
            Cipher c = Cipher.getInstance(transform, "BC");
            c.init(mode, key);
            return c;
        } catch (Exception e) {
            Cipher c = Cipher.getInstance(transform);
            c.init(mode, key);
            return c;
        }
    }

    public static byte[] encryptAES(byte[] plaintext, SecretKey key) throws Exception {
        Cipher c = getCipher("AES/ECB/PKCS5Padding", Cipher.ENCRYPT_MODE, key);
        return c.doFinal(plaintext);
    }

    public static byte[] decryptAES(byte[] ciphertext, SecretKey key) throws Exception {
        Cipher c = getCipher("AES/ECB/PKCS5Padding", Cipher.DECRYPT_MODE, key);
        return c.doFinal(ciphertext);
    }

    /** Convenience: Base64-encode encryption for storing small strings. */
    public static String encryptToBase64(String plain, SecretKey key) throws Exception {
        byte[] ct = encryptAES(plain.getBytes(StandardCharsets.UTF_8), key);
        return Base64.getEncoder().encodeToString(ct);
    }

    public static String decryptFromBase64(String b64, SecretKey key) throws Exception {
        byte[] ct = Base64.getDecoder().decode(b64);
        return new String(decryptAES(ct, key), StandardCharsets.UTF_8);
    }
}
