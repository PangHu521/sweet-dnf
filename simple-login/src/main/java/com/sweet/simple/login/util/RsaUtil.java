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
     * 私钥
     */
    public final static String PRIVATE_KEY_CONTENT = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCV1do0Nv3p80RzVMCgtRz1i0N4c9M1c4MJyze0opF73TbrE5oNwxUoGW0cwUjTPlLZjBVB3oAFmjGkJ1ABnyR7EH1C/pp9SD13NUKgRjNfjIGzH4V1V2cxV6HSPo0DyV7DsHH71XDiHMGNSN5VnLhn5hACr6d6YCzx2KpH1X/MYMDxFomhuTYGczkkO29fUikLLsWKX2+WQFwuiL8YTOohfCd4cOAnU2rRcxAVrNju0u1BksuxyZRBSAyAk3hFwPI46eYKhN+LPQTaqB7g008ZyANZGu8SFlKSevJ946ni8I3IBFpXpNQsthXalq3sUijF4PzzL1z1Y6n2H74k/QHTAgMBAAECggEACCFfYpecpei4N/qLLKKXQdr+3Bbg3j94hqawd6HzUKwmHOcROW+dWByFF1svnJ1++Q9eX0F3uJYaMnpFn548kwbyh8OcAMdTj9fj18w8tmcVP0crF+fDEYzb8DYfsxW8CX9WDWBoFy+Qmx4vSP2k41JltQJGDWBm9UVDUXw2ypFc2hBEGdf12I8W6gc3MoLhVHXVLuytJtJX3gyZF7YXardNP6v6jYmZiaG1oy9oi0vgJZU0ngpxWGEsgUOcwz0BmBYoXouLzMNzudofw4EKNEqCl0j82T9BrNyQBCLtJmPyfugsQjCDguF+SCQR06KVOP+Bqq1Cg7Ja7+0En3UUmQKBgQDNa0bukjqQXcxICmOViWfJ58ROaCfABLat+z3bPdqrmyJI1jrSJ4qs1FtTZPt9cSc7jMt23P+F5oyxVtq+f1DhAcqCIRKYeXze2QjJLaWuBrvybrI1R/OI4FzTlk1L/pfcLWxM5mHvHsplKCGsE9uex/PKEcYbX86xkClqPCdAmwKBgQC6utJE96mS9vF8pwmdN7qnv9VSFunfqxsU9VyWu3dh0xkejG42fbQZrBa+8P+aejJydL6I0xTMp42j6TNCOfh1ujS5zXMFSU6QkvnpLmxW5C4ESNnnl8LPWaz9LRVkbpqL8Nvmo5XGJ8B8ZoJBWRA+K81bOncTd6T6WID13xcLKQKBgCasFZSAbwoSvoypeAoE7kcynOBt913NBV9Ht2N5B5va/t9bhYNe8lqLOA5BKp95F/u8C+n6vRhGSSJxyYini2Bw5+ELvOZ7qKiDj4XgVBqpJeUENgSV4SFZq+Ahobe23HwVCRiJHtLXpx7YKU6uBmCkIB9grT0mElGXZFSdsn4VAoGBAI+6r5EQhQEvfS+yry7Jr63hdgSKyJg2b1ERj6QiRkERKPufolHlLhS4poVwoX45Ys1A2UweFZt1uQuau67VSk7r9huybT97brLPvvdimVfEqTr5DGk8ImJSJ1p1Mkss616eFdWQwv9Up8/ZY5eDQ4JnWtlGxlNAu8dLCnC2uFkJAoGAeNpf4OoMhijzJ/YwzoUwTnIhn9ikkkr9W7wTaCkzFS8iaMz1S3Vh5Yn8ItvNpFca8NRfqrZ5wNVLfd/Gh7onKno99GhJXptPTyfYaPJlWsaDxLhROdcfhV3QPOKm6mbHVuo9FMqVtkdpUSByfE/In0+7V7m2bdWXBlN78pVlYT8=";

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
