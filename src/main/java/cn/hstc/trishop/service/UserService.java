package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.OrderDAO;
import cn.hstc.trishop.DAO.UserDAO;
import cn.hstc.trishop.pojo.Order;
import cn.hstc.trishop.pojo.User;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.utils.Constants;
import cn.hstc.trishop.utils.StringUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    OrderService orderService;

    public boolean isExist(String account) {
        User user = getByAccount(account);
        return null != user;
    }

    public boolean existsById(int userId) {
        User user = userDAO.findById(userId);
        return null != user;
    }

    public User getByAccount(String account) {
        return userDAO.findByAccount(account);
    }

    public User getById(int id) {
        return userDAO.getOne(id);
    }

    public List<User> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return userDAO.findAll(sort);
    }

    public User get(String account, String passwd) {
        return userDAO.getByAccountAndPassword(account, passwd);
    }

    public Result addOrUpdate(User user) {
        if (null == getById(user.getId()) || 0 == user.getId()) {
            // ADD
            if (null == user.getAccount() || "".equals(user.getAccount())) {
                if (null == user.getPassword() || "".equals(user.getPassword()))
                    return new Result(Constants.code_existed, "账号或密码不能为空");
            } else if (null != getByAccount(user.getAccount()))
                return new Result(Constants.code_existed, "用户名已存在");
            else {
                userDAO.save(user);
                System.out.println("added user: " + user.toString());
                return new Result(Constants.code_success, "用户注册成功");
            }
        } else {
            // UPDATE
            User userUpdated = getById(user.getId());
            if (null != userUpdated) {
//                userUpdated.setAccount(user.getAccount()); // 不能修改 account
                if (!StringUtil.isNullOrEmpty(user.getFavorTypeList()))
                    userUpdated.setFavorTypeList(user.getFavorTypeList());
//                if (!StringUtil.isNullOrEmpty(user.isAdmin()))
//                    userUpdated.setAdmin(user.isAdmin());
                if (!StringUtil.isNullOrEmpty(user.getEmail()))
                    userUpdated.setEmail(user.getEmail());
                if (0 != user.getGender())
                    userUpdated.setGender(user.getGender());
                if (!StringUtil.isNullOrEmpty(user.getHeadUrl()))
                    userUpdated.setHeadUrl(user.getHeadUrl());
                if (!StringUtil.isNullOrEmpty(user.getPassword()))
                    userUpdated.setPassword(user.getPassword());
                if (!StringUtil.isNullOrEmpty(user.getPhoneNum()))
                    userUpdated.setPhoneNum(user.getPhoneNum());
                userDAO.saveAndFlush(userUpdated);
                System.out.println("updated user: " + user.toString());
                return new Result(Constants.code_success, "用户信息更新成功");
            }
        }
        return new Result(Constants.code_unknow, "未知错误");
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
                System.out.println("id: " + user.getId() + " 更新用户兴趣列表: " + user.getFavorTypeList());
            }
        }
    }

    public List<Order> getOrders(int userId) {
        if (existsById(userId)) {
            return orderService.findByUidLike(userId);
        }
        return null;
    }

    public int getUserNum() {
        return list().size();
    }

    public int getOrderNum() {
        return orderService.list().size();
    }

    public Result deleteOrder(int orderId) {
        return orderService.deleteOrder(orderId);
    }
}
