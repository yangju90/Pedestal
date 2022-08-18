package indi.mat.work.project.util;

import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class RSAUtil {

    private final static String KEY_RSA = "RSA";

    /**
     * 初始化密钥
     * 公钥加密，私钥解密
     *
     * return base64: [publicKey, privateKey]
     */
    public static String[] init() {
        String[] ans = new String[2];
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            generator.initialize(512);
            KeyPair keyPair = generator.generateKeyPair();

            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            ans[0] = encryptBase64(publicKey.getEncoded());
            ans[1] = encryptBase64(privateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }


    public static byte[] encryptByPublicKey(String data, String key) {
        byte[] result = null;
        try {
            byte[] bytes = decryptBase64(key);
            PublicKey publicKey = RSAPublicKeyImpl.newKey(bytes);

            Cipher cipher = Cipher.getInstance(KEY_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encode = cipher.doFinal(data.getBytes());
            result = Base64.getEncoder().encode(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decryptByPrivateKey(byte[] data, String key) {
        String result = null;
        try {
            byte[] bytes = decryptBase64(key);
            PrivateKey privateKey = RSAPrivateCrtKeyImpl.newKey(bytes);

            Cipher cipher = Cipher.getInstance(KEY_RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decoded = Base64.getDecoder().decode(data);
            result = new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] decryptBase64(String key) throws Exception {
        return Base64.getDecoder().decode(key);
    }

    public static String encryptBase64(byte[] key) throws Exception {
        return Base64.getEncoder().encodeToString(key);
    }


    public static void main(String[] args) throws Exception {
        System.out.println("公钥加密======私钥解密");
        String str = "Longer程序员";
        String[] rsaCypher = RSAUtil.init();

        byte[] enStr = RSAUtil.encryptByPublicKey(str, rsaCypher[0]);
        String miyaoStr = new String(enStr, StandardCharsets.ISO_8859_1);
        byte[] bytes = miyaoStr.getBytes(StandardCharsets.ISO_8859_1);
        System.out.println(miyaoStr.length() + "    " + bytes.length);
        String decStr = RSAUtil.decryptByPrivateKey(bytes, rsaCypher[1]);
        System.out.println("加密前：" + str + "\n\r解密后：" + decStr);
    }
}
