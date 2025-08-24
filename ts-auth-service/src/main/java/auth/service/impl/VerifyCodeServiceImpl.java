package auth.service.impl;

import auth.service.VerifyCodeService;
import auth.util.VerifyCodeCookieUtil;
import auth.util.VerifyCodeInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

/**
 * Implementation of VerifyCodeService for captcha generation & validation.
 */
@Service
@Slf4j
public class VerifyCodeServiceImpl implements VerifyCodeService {
	private static final char[] MAP_TABLE = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	@Override
	public BufferedImage getImageCode(int width, int height, OutputStream os,
			HttpServletRequest request, HttpServletResponse response,
			HttpHeaders headers) {
		// 绘制验证码图片
		if (width <= 0) {
			width = 60;
		}
		if (height <= 0) {
			height = 20;
		}
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
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

		StringBuilder strEnsure = new StringBuilder();
		for (int i = 0; i < 4; ++i) {
			strEnsure.append(MAP_TABLE[(int) (MAP_TABLE.length * Math.random())]);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			String str = strEnsure.substring(i, i + 1);
			g.drawString(str, 13 * i + 6, 16);
		}
		g.dispose();

		String code = strEnsure.toString();
		log.info("[getImageCode][code: {}]", code);
		VerifyCodeCookieUtil.setVerifyCodeSessionCookie(response, code);
		return image;
	}

	@Override
	public boolean verifyCode(HttpServletRequest request, String receivedCode) {
		log.info("[verifyCode][receivedCode: {}]", receivedCode);
		VerifyCodeInfo info = VerifyCodeCookieUtil.getVerifyCodeInfo(request);
		if (info == null) {
			log.warn("[verifyCode][Cookie not found][Path Info: {}]", request.getPathInfo());
			return false;
		}

		log.info("GET Code from cookie: {}, expireTime: {}", info.getCode(), info.getExpireTime());
		return !info.isExpired() && info.getCode().equalsIgnoreCase(receivedCode);
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
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
