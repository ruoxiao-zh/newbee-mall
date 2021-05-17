package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.dao.UserMapper;
import ltd.newbee.mall.entity.User;
import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
