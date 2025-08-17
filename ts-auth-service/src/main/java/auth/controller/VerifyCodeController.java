package auth.controller;

import auth.service.VerifyCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Controller exposing endpoints for captcha generation and validation.
 */
@RestController
@RequestMapping("/api/v1/verifycode")
public class VerifyCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyCodeController.class);

    @Autowired
    private VerifyCodeService verifyCodeService;

    @GetMapping("/generate")
    public void imageCode(@RequestHeader HttpHeaders headers,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        LOGGER.info("[imageCode][Generate image code]");
        OutputStream os = response.getOutputStream();
        Map<String, Object> map = verifyCodeService.getImageCode(60, 20, os, request, response, headers);
        request.getSession().setAttribute("simpleCaptcha", map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime", System.currentTimeMillis());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
        } catch (IOException e) {
            String error = "Can't generate verification code";
            os.write(error.getBytes());
        }
    }

    @GetMapping("/verify/{verifyCode}")
    public boolean verifyCode(@PathVariable String verifyCode,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestHeader HttpHeaders headers) {
        LOGGER.info("[verifyCode][ReceivedCode: {}]", verifyCode);
        boolean result = verifyCodeService.verifyCode(request, response, verifyCode, headers);
        LOGGER.info("[verifyCode][Verify result: {}]", result);
        return result;
    }
}
