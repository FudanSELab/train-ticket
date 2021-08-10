package verifycode.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import verifycode.service.VerifyCodeService;
import verifycode.util.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author fdse
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    public static final int CAPTCHA_EXPIRED = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);

    String ysbCaptcha = "YsbCaptcha";

    /**
     * build local cache
     */
    public Cache<String, String> cacheCode = CacheBuilder.newBuilder()
            // max  size
            .maximumSize(CAPTCHA_EXPIRED)
            .expireAfterAccess(CAPTCHA_EXPIRED, TimeUnit.SECONDS)
            .build();

    private static char mapTable[] = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    @Override
    public Map<String, Object> getImageCode(int width, int height, OutputStream os, HttpServletRequest request, HttpServletResponse response, HttpHeaders headers) {
        Map<String, Object> returnMap = new HashMap<>();
        if (width <= 0) {
            width = 60;
        }
        if (height <= 0) {
            height = 20;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random(); //NOSONAR

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 168; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String strEnsure = "";

        for (int i = 0; i < 4; ++i) {
            strEnsure += mapTable[(int) (mapTable.length * Math.random())];

            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

            String str = strEnsure.substring(i, i + 1);
            g.drawString(str, 13 * i + 6, 16);
        }

        g.dispose();
        returnMap.put("image", image);
        returnMap.put("strEnsure", strEnsure);

        Cookie cookie = CookieUtil.getCookieByName(request, ysbCaptcha);
        String cookieId;
        if (cookie == null) {
            VerifyCodeServiceImpl.LOGGER.warn("Get image code warn.Cookie not found,Path Info: {}",request.getPathInfo());
            cookieId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            CookieUtil.addCookie(response, ysbCaptcha, cookieId, CAPTCHA_EXPIRED);
        } else {
            if (cookie.getValue() != null) {
                cookieId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                CookieUtil.addCookie(response, ysbCaptcha, cookieId, CAPTCHA_EXPIRED);
            } else {
                cookieId = cookie.getValue();
            }
        }
        VerifyCodeServiceImpl.LOGGER.info(" {}  ___ st", strEnsure);
        cacheCode.put(cookieId, strEnsure);
        return returnMap;
    }

    @Override
    public boolean verifyCode(HttpServletRequest request, HttpServletResponse response, String receivedCode, HttpHeaders headers) {
        boolean result = false;
        Cookie cookie = CookieUtil.getCookieByName(request, ysbCaptcha);
        String cookieId;
        if (cookie == null) {
            VerifyCodeServiceImpl.LOGGER.warn("Verify code warn.Cookie not found,Path Info: {}",request.getPathInfo());
            cookieId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            CookieUtil.addCookie(response, ysbCaptcha, cookieId, CAPTCHA_EXPIRED);
        } else {
            cookieId = cookie.getValue();
        }

        String code = cacheCode.getIfPresent(cookieId);
        LOGGER.info("GET Code By cookieId " + cookieId + "   is :" + code);
        if (code == null) {
            VerifyCodeServiceImpl.LOGGER.warn("Get image code warn.Code not found,CookieId: {}",cookieId);
            return false;
        }
        if (code.equalsIgnoreCase(receivedCode)) {
            result = true;
        }
        return result;
    }


    static Color getRandColor(int fc, int bc) {
        Random random = new Random(); //NOSONAR
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
