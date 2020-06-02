package cn.hstc.trishop.DAO;

import cn.hstc.trishop.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户数据库操作
 */
public interface UserDAO extends JpaRepository<User, Integer> {

    User findById(int id);
    /*
       使用用户帐号搜索用户
     */
    User findByAccount(String account);
    /*
        使用账号密码搜索用户
     */
    User getByAccountAndPassword(String account, String password);

}
