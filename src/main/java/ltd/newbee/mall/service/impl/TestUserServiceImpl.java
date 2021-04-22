package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.dao.UserMapper;
import ltd.newbee.mall.entity.User;
import ltd.newbee.mall.service.TestUserService;
import ltd.newbee.mall.util.PageQueryUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Richard
 * @Date 2021/4/22 6:40 PM
 */
@Service
public class TestUserServiceImpl implements TestUserService {
    
    @Resource
    UserMapper userMapper;
    
    @Override
    public List<User> getUserPage(PageQueryUtil pageQueryUtil) {
        return userMapper.findUsers(pageQueryUtil.getPage(), pageQueryUtil.getLimit());
    }
}
