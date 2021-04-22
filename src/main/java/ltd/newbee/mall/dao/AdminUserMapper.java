package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Richard
 * @Date 2021/4/22 10:08 AM
 */
public interface AdminUserMapper {
    
    AdminUser login(@Param("userName") String userName, @Param("password") String password);
    
    AdminUser getUserDetailById(Integer loginUserId);
}
