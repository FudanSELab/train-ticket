package auth.service;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.awt.image.BufferedImage;

/**
 * Service interface for generating and validating verification codes.
 */
public interface VerifyCodeService {

	BufferedImage getImageCode(int width, int height, OutputStream os,
			HttpServletRequest request, HttpServletResponse response,
			HttpHeaders headers);

	boolean verifyCode(HttpServletRequest request, String receivedCode);
}
