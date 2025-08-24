package auth.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for cookie operations, used by verification-code feature.
 */
@Slf4j
public class VerifyCodeCookieUtil {

    private VerifyCodeCookieUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static final String VERIFY_CODE_COOKIE_NAME = "ts-vcode";

    /**
     * Set verification-code session cookie. The cookie name is fixed to
     * {@link #VERIFY_CODE_COOKIE_NAME}.
     * Value format: "${code}-${unixSeconds}".
     */
    public static void setVerifyCodeSessionCookie(HttpServletResponse response, String code) {
        String value = VerifyCodeInfo.of(code).encrypt();
        Cookie cookie = new Cookie(VERIFY_CODE_COOKIE_NAME, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * Get decrypted value of verification cookie, return null if cookie not
     * present.
     */
    public static VerifyCodeInfo getVerifyCodeInfo(HttpServletRequest request) {
        String c = getVerifyCodeSessionCookie(request);
        if (c.isEmpty()) {
            return null;
        }

        return VerifyCodeInfo.decrypt(c);
    }

    /**
     * Get verification-code session cookie. The cookie name is fixed to
     * {@link #VERIFY_CODE_COOKIE_NAME}.
     */
    private static String getVerifyCodeSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (VERIFY_CODE_COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
