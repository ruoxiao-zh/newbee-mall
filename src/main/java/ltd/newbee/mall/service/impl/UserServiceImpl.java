package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.dao.UserMapper;
import ltd.newbee.mall.entity.User;
import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Richard
 * @date 2021/5/17 4:16 PM
 */
@Service
public class UserServiceImpl implements NewBeeMallUserService {
    @Resource
    UserMapper userMapper;
    
    @Override
    public String register(String loginName, String password) {
        if (userMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        
        User registerUser = new User();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = null;
        try {
            passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        registerUser.setPasswordMd5(passwordMD5);
        
        if (userMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    
    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        User user = userMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            
            // 昵称太长 影响页面展示
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            
            NewBeeMallUserVO newBeeMallUserVO = new NewBeeMallUserVO();
            BeanUtil.copyProperties(user, newBeeMallUserVO);
            // 设置购物车中的数量
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, newBeeMallUserVO);
            
            return ServiceResultEnum.SUCCESS.getResult();
        }
        
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }
}
