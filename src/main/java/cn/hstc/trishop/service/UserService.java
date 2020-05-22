package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.UserDAO;
import cn.hstc.trishop.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String account) {
        User user = getByAccount(account);
        return null!=user;
    }

    public User getByAccount(String account) {
        return userDAO.findByAccount(account);
    }

    public User get(String account, String passwd) {
        return userDAO.getByAccountAndPassword(account, passwd);
    }

    public void add(User user) {
        userDAO.save(user);
    }
}
