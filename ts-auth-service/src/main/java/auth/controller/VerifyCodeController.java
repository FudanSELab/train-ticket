package auth.controller;

import auth.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Controller exposing endpoints for captcha generation and validation.
 */
@RestController
@RequestMapping("/api/v1/auth/verifycode")
@Slf4j
public class VerifyCodeController {
	@Autowired
	private VerifyCodeService verifyCodeService;

	@GetMapping("/generate")
	public void imageCode(@RequestHeader HttpHeaders headers,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("[imageCode][Generate image code]");
		OutputStream os = response.getOutputStream();
		BufferedImage image = verifyCodeService.getImageCode(60, 20, os, request, response, headers);
		try {
			ImageIO.write(image, "JPEG", os);
		} catch (IOException e) {
			String error = "Can't generate verification code";
			os.write(error.getBytes());
		}
	}

	@GetMapping("/verify/{verifyCode}")
	public boolean verifyCode(@PathVariable String verifyCode,
			HttpServletRequest request,
			HttpServletResponse response) {
		log.info("[verifyCode][ReceivedCode: {}]", verifyCode);
		boolean result = verifyCodeService.verifyCode(request, verifyCode);
		log.info("[verifyCode][Verify result: {}]", result);
		return result;
	}
}
