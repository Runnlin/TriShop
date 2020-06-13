package cn.hstc.trishop.service;

import cn.hstc.trishop.DAO.OrderDAO;
import cn.hstc.trishop.pojo.Order;
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

    public Result addOrUpdate(int userId, int productId) {
        if (0!=userId && 0!=productId) {
            Order order = new Order();
            order.setUid(userId);
            order.setPid(productId);
            orderDAO.save(order);
            System.out.println("添加订单:" + order.toString());
            return new Result(Constants.code_success, "购买/添加订单 成功");
        }
        return new Result(Constants.code_nofind, "添加订单失败:用户或商品id错误");
    }

    public List<Order> listByUserId(int userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        return orderDAO.findByUidLike(userId);
    }
}
