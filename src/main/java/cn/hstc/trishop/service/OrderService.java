package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.OrderDAO;
import cn.hstc.trishop.pojo.Order;
import cn.hstc.trishop.pojo.Product;
import cn.hstc.trishop.result.Result;
import cn.hstc.trishop.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;

    public List<Order> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        return orderDAO.findAll(sort);
    }

    public Order getOne(int id) {
        Order order = orderDAO.findById(id).orElse(null);
        return order;
    }

    public Result addOrUpdate(int userId, Product product, int num) {
        if (0!=userId && 0!=product.getId()) {
            Order order = new Order();
            order.setUid(userId);
            order.setPid(product.getId());
            order.setProductName(product.getName());
            order.setFee(product.getFee());
            order.setPhotoUrl(product.getPhotoUrl());
            order.setNum(num);
            orderDAO.save(order);
            System.out.println("添加订单:" + order.toString());
            return new Result(Constants.code_success, "购买成功");
        }
        return new Result(Constants.code_nofind, "购买失败:添加订单失败:用户或商品id错误");
    }

    public List<Order> listByUserId(int userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        return orderDAO.findByUidLike(userId);
    }
}
