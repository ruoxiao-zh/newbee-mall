package ltd.newbee.mall.controller;

import ltd.newbee.mall.dao.UserDao;
import ltd.newbee.mall.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author richard
 */
@RestController
public class MyBatisController {
    @Resource
    UserDao userDao;

    @GetMapping("/users/mybatis/queryAll")
    public List<User> queryAll() {
        return userDao.findAllUsers();
    }

    @GetMapping("/users/mybatis/insert")
    public Boolean insert(String name, String password) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return false;
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);

        return userDao.insertUser(user) > 0;
    }

    @GetMapping("/users/mybatis/update")
    public Boolean insert(Integer id, String name, String password) {
        if (id == null || id < 1 || StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return false;
        }

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);

        return userDao.updateUser(user) > 0;
    }

    @GetMapping("/users/mybatis/delete")
    public Boolean insert(Integer id) {
        if (id == null || id < 1) {
            return false;
        }

        return userDao.deleteUser(id) > 0;
    }
}
