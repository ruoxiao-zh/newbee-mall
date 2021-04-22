package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.User;

import java.util.List;

/**
 * @author Richard
 */
public interface UserMapper {

    /**
     * @return
     */
    List<User> findAllUsers();

    /**
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * @param id
     * @return
     */
    int deleteUser(Integer id);
}
