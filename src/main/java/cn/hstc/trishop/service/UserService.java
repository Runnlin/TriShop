package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.OrderDAO;
import cn.hstc.trishop.DAO.UserDAO;
import cn.hstc.trishop.pojo.Order;
import cn.hstc.trishop.pojo.Product;
import cn.hstc.trishop.pojo.User;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    OrderDAO orderDAO;

    public boolean isExist(String account) {
        User user = getByAccount(account);
        return null!=user;
    }

    public boolean existsById(int userId) {
        User user = userDAO.findById(userId);
        return null != user;
    }

    public User getByAccount(String account) {
        return userDAO.findByAccount(account);
    }

    public List<User> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return userDAO.findAll(sort);
    }

    public User get(String account, String passwd) {
        return userDAO.getByAccountAndPassword(account, passwd);
    }

    public Result addOrUpdate(User user) {
        if (null == getByAccount(user.getAccount())) {
            // 不需要查重，因为存在的话可以直接更新
            userDAO.saveAndFlush(user);
            System.out.println("added user: "+user.getAccount()+"   id:"+user.getId());
            return new Result(Constants.code_success, "");
        } else {
            return new Result(Constants.code_existed, "用户已存在");
        }
    }

    public void addFavorType(int userId, String singleType) {
        if (!"".equals(singleType)) {
            User user = userDAO.findById(userId);
            // 获取用户当前喜爱类型列表
            String userTypeList = user.getFavorTypeList();
            // 如果待添加的喜爱类型不在喜爱列表里，才添加
            if (!userTypeList.contains(String.valueOf(singleType))) {
                // 防止有时候后面已经有逗号
                if (userTypeList.endsWith(","))
                    userTypeList += singleType;
                else
                    userTypeList += "," + singleType;
                // 再将喜爱类型列表放回去
                user.setFavorTypeList(userTypeList);
                userDAO.save(user);
                System.out.println("id: "+user.getId()+" 更新用户兴趣列表: "+user.getFavorTypeList());
            }
        }
    }

//    public void addCart(int userId, int productId) {
//        User user = userDAO.findById(userId);
//        if (null != user) {
//            Cart cart = cartDAO.findById(productId);
//            cartDAO.save(cart);
//        }
//    }
}
