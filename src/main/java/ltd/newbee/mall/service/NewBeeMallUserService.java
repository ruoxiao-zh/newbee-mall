package ltd.newbee.mall.service;

import javax.servlet.http.HttpSession;

/**
 * @author Richard
 * @date 2021/5/17 4:16 PM
 */
public interface NewBeeMallUserService {
    String register(String loginName, String password);
    
    String login(String loginName, String md5Password, HttpSession httpSession);
}
