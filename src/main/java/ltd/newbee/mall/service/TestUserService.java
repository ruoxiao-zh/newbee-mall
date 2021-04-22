package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.User;
import ltd.newbee.mall.util.PageQueryUtil;

import java.util.List;

/**
 * @Author Richard
 * @Date 2021/4/22 6:40 PM
 */
public interface TestUserService {
    List<User> getUserPage(PageQueryUtil pageQueryUtil);
}
