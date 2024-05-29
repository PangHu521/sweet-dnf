package com.sweet.simple.login.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 针对DNF登录封装的RSA工具
 *
 * @author 大师兄
 */
public class RsaUtil {

    /**
     * 固定填充值
     */
    private final static String FIXED = "010101010101010101010101010101010101010101010101010101010101010155914510010403030101";

    /**
     * 私钥前缀
     */
    private final static String PRIVATE_PREFIX = "-----BEGIN PRIVATE KEY-----";

    /**
     * 私钥后缀
     */
    private final static String PRIVATE_SUFFIX = "-----END PRIVATE KEY-----";

    /**
     * 公钥前缀
     */
    private final static String PUBLIC_PREFIX = "-----BEGIN PUBLIC KEY-----";

    /**
     * 公钥后缀
     */
    private final static String PUBLIC_SUFFIX = "-----END PUBLIC KEY-----";

    /**
     * 使用私钥加密账号id
     *
     * @param privateKeyContent 私钥原始字符串内容
     * @param id                账号id
     * @return 经过一系列处理后，可直接在命令行启动游戏的启动参数
     */
    public static String secureId(String privateKeyContent, Integer id) {
        String data = String.format("%08x" + FIXED, id);
        byte[] encryptedData = rsaEncrypt(privateKeyContent, hexStringToByteArray(data));
        return Base64.encode(encryptedData);
    }

    /**
     * 创建长度为2048，格式为PKCS#8的秘钥对
     *
     * @param hasPrefix 生成的秘钥对是否包含前缀
     * @param hasSuffix 生成的秘钥对是否包含后缀
     */
    @SneakyThrows
    public static Tuple createSecretKeyPair(boolean hasPrefix, boolean hasSuffix) {
        // 生成RSA密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取生成的PKCS#8格式的私钥和公钥
        String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.encode(keyPair.getPublic().getEncoded());

        if (hasPrefix) {
            privateKey = PRIVATE_PREFIX + CharPool.LF + privateKey + CharPool.LF + PRIVATE_SUFFIX;
        }

        if (hasSuffix) {
            publicKey = PUBLIC_PREFIX + CharPool.LF + publicKey + CharPool.LF + PUBLIC_SUFFIX;
        }

        return new Tuple(privateKey, publicKey);
    }

    /**
     * 使用私钥加密
     *
     * @param privateKeyContent 私钥base64表示
     * @param data              被加密的内容
     * @return 加密后的字节数组表示
     */
    @SneakyThrows
    private static byte[] rsaEncrypt(String privateKeyContent, byte[] data) {
        // 移除所有无用字符
        privateKeyContent = StrUtil.removeAny(privateKeyContent, PRIVATE_PREFIX, PRIVATE_SUFFIX, "\\s");
        byte[] keyBytes = Base64.decode(privateKeyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param s 字符串
     * @return 字节数组
     */
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
