package auth.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyCodeInfo {
    private String code;
    private long expireTime; // 秒级时间戳

    // Must be 16 bytes for AES-128; keep same across services if shared
    private static final String SECRET_KEY = "rpkxvwltzmbcfynd"; // 16 bytes

    /** 通过 code 生成 VerifyCodeInfo */
    public static VerifyCodeInfo of(String code) {
        return VerifyCodeInfo.builder()
                .code(code)
                .expireTime(System.currentTimeMillis() / 1000 + 60 * 5) // 5分钟过期
                .build();
    }

    /** 使用 SECRET_KEY 解密 VerifyCodeInfo */
    public static VerifyCodeInfo decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET_KEY.getBytes(), "AES"));
            String decoded = new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
            return decode(decoded);
        } catch (Exception e) {
            log.warn("[CookieUtil] Decrypt value failed", e);
            return null;
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() / 1000 > expireTime;
    }

    /** 将 encoded 字符串解析为 VerifyCodeInfo */
    private static VerifyCodeInfo decode(String encoded) {
        String[] parts = encoded.split("-");
        if (parts.length != 2) {
            log.warn("[VerifyCodeInfo] Invalid encoded string: {}", encoded);
            return null;
        }
        return VerifyCodeInfo.builder().code(parts[0]).expireTime(Long.parseLong(parts[1])).build();
    }

    /** 将 code 和 expireTime 拼接成字符串 */
    private String encode() {
        return code + "-" + expireTime;
    }

    /** 使用 SECRET_KEY 加密 VerifyCodeInfo */
    public String encrypt() {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET_KEY.getBytes(), "AES"));
            return Base64.getEncoder().encodeToString(cipher.doFinal(this.encode().getBytes()));
        } catch (Exception e) {
            log.error("[CookieUtil] Encrypt value failed", e);
            return "";
        }
    }

}
