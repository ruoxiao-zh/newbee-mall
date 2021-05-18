package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.util.MD5Util;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Richard
 * @date 2021/5/17 4:15 PM
 */
@RequestMapping(value = "/")
@Controller
public class PersonalController {
    @Resource
    NewBeeMallUserService userService;
    
    @GetMapping({"/login", "login.html"})
    public String login() {
        return "mall/login";
    }
    
    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }
    
    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request, HttpSession httpSession) {
        request.setAttribute("path", "personal");
        
        return "mall/personal";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        
        return "mall/login";
    }
    
    @GetMapping("/personal/addresses")
    public String addressesPage() {
        return "mall/addresses";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("password") String password,
                           @RequestParam("verifyCode") String verifyCode,
                           HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult("参数异常!");
        }
        
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        if (!verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult("验证码错误!");
        }
        
        String registerResult = userService.register(loginName, password);
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        
        return ResultGenerator.genFailResult(registerResult);
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult("参数异常!");
        }
    
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        if (!verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult("验证码错误!");
        }
    
        String loginResult = null;
        try {
            loginResult = userService.login(loginName, MD5Util.MD5Encode(password, "UTF-8"), httpSession);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {
            return ResultGenerator.genSuccessResult();
        }
        
        return ResultGenerator.genFailResult(loginResult);
    }
}
