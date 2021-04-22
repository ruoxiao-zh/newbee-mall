package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.dao.AdminUserMapper;
import ltd.newbee.mall.entity.AdminUser;
import ltd.newbee.mall.service.AdminUserService;
import ltd.newbee.mall.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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
    
    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {
        return adminUserMapper.getUserDetailById(loginUserId);
    }
    
    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            String originalPasswordMd5 = null;
            try {
                originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            String newPasswordMd5 = null;
            try {
                newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            //比较原密码是否正确
            if (Objects.equals(originalPasswordMd5, adminUser.getLoginPassword())) {
                //设置新密码并修改
                adminUser.setLoginPassword(newPasswordMd5);
                if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                    //修改成功则返回true
                    return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //设置新名称并修改
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                //修改成功则返回true
                return true;
            }
        }
        
        return false;
    }
}
