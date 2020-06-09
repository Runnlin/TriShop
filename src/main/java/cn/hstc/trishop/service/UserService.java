package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.UserDAO;
import cn.hstc.trishop.pojo.User;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.utils.Constants;
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
            return new Result(Constants.code_success, "");
        } else {
            return new Result(Constants.code_existed, "用户已存在");
        }
    }

    public void addFavorType(int userId, String singleType) {
        if (!"".equals(singleType)) {
            // 获取用户当前喜爱类型列表
            String userTypeList = userDAO.findById(userId).getFavorTypeList();
            // 如果待添加的喜爱类型不在喜爱列表里，才添加
            if (!userTypeList.contains(String.valueOf(singleType))) {
                // 防止有时候后面已经有逗号
                if (userTypeList.endsWith(","))
                    userTypeList += singleType;
                else
                    userTypeList += "," + singleType;
                // 再将喜爱类型列表放回去
                userDAO.findById(userId).setFavorTypeList(userTypeList);
            }
        }
    }
}
