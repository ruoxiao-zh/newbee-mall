package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.dao.AdminUserMapper;
import ltd.newbee.mall.entity.AdminUser;
import ltd.newbee.mall.service.AdminUserService;
import ltd.newbee.mall.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Richard
 * @Date 2021/4/22 10:56 AM
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    
    @Resource
    private AdminUserMapper adminUserMapper;
    
    @Override
    public AdminUser login(String userName, String password) {
        String passwordMd5 = null;
        
        try {
            passwordMd5 = MD5Util.MD5Encode(password, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return adminUserMapper.login(userName, passwordMd5);
    }
}
