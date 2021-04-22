package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.AdminUser;

/**
 * @Author Richard
 * @Date 2021/4/22 10:55 AM
 */
public interface AdminUserService {
    
    AdminUser login(String userName, String password);
    
    AdminUser getUserDetailById(Integer loginUserId);
    
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);
    
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);
}
