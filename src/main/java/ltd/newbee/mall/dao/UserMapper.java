package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.User;
import ltd.newbee.mall.util.PageQueryUtil;

import java.util.List;

/**
 * @author Richard
 */
public interface UserMapper {
    User selectByLoginName(String loginName);
    
    int insertSelective(User registerUser);
    
    List<User> findUsers(PageQueryUtil pageQueryUtil);
    
    int getTotalUser();
    
    User selectByLoginNameAndPasswd(String loginName, String password);
}
