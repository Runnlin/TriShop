package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.UserDAO;
import cn.hstc.trishop.pojo.User;
import cn.hstc.trishop.result.Result;
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

    public Result add(User user) {
        if (null == getByAccount(user.getAccount())) {
            userDAO.saveAndFlush(user);
            System.out.println("added user: "+user.getAccount()+"   id:"+user.getId());
            return new Result(200);
        } else {
            System.out.println("用户已存在");
            return new Result(400);
        }
    }
}
