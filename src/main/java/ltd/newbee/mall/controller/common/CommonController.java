package ltd.newbee.mall.controller.common;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import ltd.newbee.mall.common.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @Author Richard
 * @Date 2021/4/22 9:51 AM
 */
@Controller
@RequestMapping("/common")
public class CommonController {
    @Resource
    private DefaultKaptcha captchaProducer;
    
    @GetMapping("/kaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[]                captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream     = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String verifyCode = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", verifyCode);
            BufferedImage challenge = captchaProducer.createImage(verifyCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        
        captchaOutputStream = imgOutputStream.toByteArray();
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    
    @GetMapping("/mall/kaptcha")
    public void mallKaptcha(HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");
        
        // 三个参数分别为宽、高、位数
        SpecCaptcha captcha = new SpecCaptcha(110, 40, 4);
        
        // 设置类型 数字和字母混合
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        
        //设置字体
        captcha.setCharType(Captcha.FONT_9);
        
        // 验证码存入session
        httpServletRequest.getSession().setAttribute(Constants.MALL_VERIFY_CODE_KEY, captcha.text().toLowerCase());
        
        // 输出图片流
        captcha.out(httpServletResponse.getOutputStream());
    }
}
