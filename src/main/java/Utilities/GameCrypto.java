package Utilities;


import Game.Board.Board;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class GameCrypto {

    /**
     * Encrypts string using AES
     *
     * @param strToEncrypt
     * @return encrypted string or null if encryption failed
     */
    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey());
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /**
     * Decrypt string using AES
     *
     * @param strToDecrypt
     * @return decrypted string or null if decryption failed
     */
    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Reads the private key from the config file
     *
     * @return the AES 16bit Key
     */
    private static SecretKeySpec getPrivateKey(){

        MessageDigest sha = null;
        try {
            ClassLoader classLoader = Board.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("secretkey.config");
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            byte[] key = (s.hasNext() ? s.next() : "").getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
