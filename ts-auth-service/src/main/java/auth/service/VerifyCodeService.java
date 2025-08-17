package auth.service;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

/**
 * Service interface for generating and validating verification codes.
 */
public interface VerifyCodeService {

    Map<String, Object> getImageCode(int width, int height, OutputStream os,
                                     HttpServletRequest request, HttpServletResponse response,
                                     HttpHeaders headers);

    boolean verifyCode(HttpServletRequest request, HttpServletResponse response,
                       String receivedCode, HttpHeaders headers);
}
