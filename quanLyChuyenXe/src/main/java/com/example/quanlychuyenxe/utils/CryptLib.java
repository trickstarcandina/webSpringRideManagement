package com.example.quanlychuyenxe.utils;

import com.google.common.primitives.Longs;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class CryptLib {

    private String KEY_CRYPT = "MZygpewJsCpRrfOd";

    /**
     * Encryption mode enumeration
     */
    private enum EncryptMode {
        ENCRYPT, DECRYPT
    }

    // cipher to be used for encryption and decryption
    private Cipher _cx;

    // encryption key and initialization vector
    private byte[] _key, _iv;

    public CryptLib() {
        // initialize the cipher with transformation AES/CBC/PKCS5Padding
        try {
            _cx = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        _key = new byte[32]; //256 bit key space
        _iv = new byte[16]; //128 bit IV
    }


    /**
     * @param inputText     Text to be encrypted or decrypted
     * @param encryptionKey Encryption key to used for encryption / decryption
     * @param mode          specify the mode encryption / decryption
     * @param initVector    Initialization vector
     * @return encrypted or decrypted bytes based on the mode
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private byte[] encryptDecrypt(String inputText, String encryptionKey,
                                  EncryptMode mode, String initVector) throws UnsupportedEncodingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {

        int len = encryptionKey.getBytes("UTF-8").length; // length of the key	provided

        if (encryptionKey.getBytes("UTF-8").length > _key.length)
            len = _key.length;

        int ivlength = initVector.getBytes("UTF-8").length;

        if (initVector.getBytes("UTF-8").length > _iv.length)
            ivlength = _iv.length;

        System.arraycopy(encryptionKey.getBytes("UTF-8"), 0, _key, 0, len);
        System.arraycopy(initVector.getBytes("UTF-8"), 0, _iv, 0, ivlength);


        SecretKeySpec keySpec = new SecretKeySpec(_key, "AES"); // Create a new SecretKeySpec for the specified key data and algorithm name.

        IvParameterSpec ivSpec = new IvParameterSpec(_iv); // Create a new IvParameterSpec instance with the bytes from the specified buffer iv used as initialization vector.

        // encryption
        if (mode.equals(EncryptMode.ENCRYPT)) {
            // Potentially insecure random numbers on Android 4.3 and older. Read for more info.
            // https://android-developers.blogspot.com/2013/08/some-securerandom-thoughts.html
            _cx.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);// Initialize this cipher instance
            return _cx.doFinal(inputText.getBytes("UTF-8")); // Finish multi-part transformation (encryption)
        } else {
            _cx.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);// Initialize this cipher instance

            byte[] decodedValue = Base64.getDecoder().decode(inputText.getBytes());
            return _cx.doFinal(decodedValue); // Finish multi-part transformation (decryption)
        }
    }

    /***
     * This function computes the SHA256 hash of input string
     * @param text input text whose SHA256 hash has to be computed
     * @param length length of the text to be returned
     * @return returns SHA256 hash of input text
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static String SHA256(String text, int length) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String resultString;
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(text.getBytes("UTF-8"));
        byte[] digest = md.digest();

        StringBuilder result = new StringBuilder();
        for (byte b : digest) {
            result.append(String.format("%02x", b)); //convert to hex
        }

        if (length > result.toString().length()) {
            resultString = result.toString();
        } else {
            resultString = result.toString().substring(0, length);
        }

        return resultString;

    }


    public String encryptPlainText(String plainText, String iv) throws Exception {
        byte[] bytes = encryptDecrypt(plainText, CryptLib.SHA256(KEY_CRYPT, 32), EncryptMode.ENCRYPT, iv);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String decryptCipherText(String cipherText, String iv) throws Exception {
        byte[] bytes = encryptDecrypt(cipherText, CryptLib.SHA256(KEY_CRYPT, 32), EncryptMode.DECRYPT, iv);
        return new String(bytes);
    }


    public String encryptPlainTextWithRandomIV(String plainText) throws Exception {
        byte[] bytes = encryptDecrypt(generateRandomIV16() + plainText, CryptLib.SHA256(KEY_CRYPT, 32), EncryptMode.ENCRYPT, generateRandomIV16());
        return Base64.getEncoder().encodeToString(bytes).trim();
    }

    public String decryptCipherTextWithRandomIV(String cipherText) throws Exception {
        byte[] bytes = encryptDecrypt(cipherText.trim(), CryptLib.SHA256(KEY_CRYPT, 32), EncryptMode.DECRYPT, generateRandomIV16());
        String out = new String(bytes);
        return out.substring(16, out.length());
    }


    /**
     * Generate IV with 16 bytes
     *
     * @return
     */
    public String generateRandomIV16() {
        SecureRandom ranGen = new SecureRandom();
        byte[] aesKey = new byte[16];
        ranGen.nextBytes(aesKey);
        StringBuilder result = new StringBuilder();
        for (byte b : aesKey) {
            result.append(String.format("%02x", b)); //convert to hex
        }
        if (16 > result.toString().length()) {
            return result.toString();
        } else {
            return result.toString().substring(0, 16);
        }
    }

    public static long decryptPositiveLong(String secretKey, String encrypted) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] byteDecrypted = cipher.doFinal(Base58.decode(encrypted));
            return decodePositiveLong(byteDecrypted);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptPositiveLong(String secretKey, long l) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] byteEncrypted = cipher.doFinal(encodePositiveLong(l));
            return Base58.encode(byteEncrypted);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encodePositiveLong(long l) {
        byte[] bytes = Longs.toByteArray(l);
        int longSize = bytes.length;
        int from = 0;
        while (bytes[from] == 0 && from < longSize) from ++;
        return Arrays.copyOfRange(bytes, from, longSize);
    }

    public static long decodePositiveLong(byte[] bytes) {
        byte[] des = new byte[Long.BYTES];
        System.arraycopy(bytes, 0, des, Long.BYTES - bytes.length, bytes.length);
        return Longs.fromByteArray(des);
    }

//    public static void main(String[] args) {
//        String encrypted = encryptPositiveLong("12345678", 7891656);
//        System.out.println(encrypted);
//
//        long l = decryptPositiveLong("12345678", encrypted);
//        System.out.println(l);
//    }
}
